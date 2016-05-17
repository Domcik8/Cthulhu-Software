/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.mailService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Recommendation;
import lt.vu.mif.labanoro_draugai.entities.Type;

/**
 *
 * @author NecrQ
 */
@WebServlet("/recommend")
public class recommendServlet extends HttpServlet {

    @Inject
    private DatabaseManager dbm;

    @Inject
    private EmailBean ema;

    private Integer minimumRecommendations = 2;
    private Integer maximumRecommendations = 5;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getUserPrincipal() != null) {

            String recommenderEmail = request.getUserPrincipal().toString();
            Person recommender = (Person) dbm.getEntity("Person", "Email", recommenderEmail);
            String userKey = request.getParameter("user");
            Person recommendedPerson = (Person) dbm.getEntity("Person", "Uniquekey", userKey);

            try {
                minimumRecommendations = Integer.parseInt(dbm.getSystemParameter("SystemParameter.RequiredRecommendations").getValue());
                maximumRecommendations = Integer.parseInt(dbm.getSystemParameter("SystemParameter.MaxRecommendations").getValue());
            } catch (NumberFormatException exc) {
            }

            // Candidate is not allowed to recommend someone
            Type candidateType = (Type) dbm.getEntity("Type", "Internalname", "Person.Candidate");

            if (recommender.getTypeid().equals(candidateType)) {
                response.sendRedirect(request.getContextPath() + "/index.html");
                return;
            }

            // If recommendedPerson exists and if recommender != himself
            if (recommendedPerson != null && !(recommendedPerson.getEmail()).equals(recommenderEmail)) {

                Integer recommendedPersonReceivedRecommendations = recommendedPerson.getRecommendationsreceived();

                // If person reached maximum allowed recommendation, recommend process terminated
                if (recommendedPersonReceivedRecommendations >= maximumRecommendations) {
                    response.sendRedirect(request.getContextPath() + "/index.html");
                    return;
                }

                Recommendation recommendation = dbm.getRecommendation(recommenderEmail, recommendedPerson.getEmail());

                if (recommendation != null) {

                    // If there is no recommendation date, when it means that recommendation was not confirmed
                    if (recommendation.getRecommendationdate() == null) {
                        recommendation.setRecommendationdate(new Date());
                        dbm.updateEntity(recommendation);
                        recommendedPersonReceivedRecommendations += 1;
                        recommendedPerson.setRecommendationsreceived(recommendedPersonReceivedRecommendations);
                        dbm.updateEntity(recommendedPerson);
                    }
                } else {
                    // If there was no recommendation, create one with confirm date
                    recommendation = dbm.addRecommendation(recommenderEmail, recommendedPerson.getEmail(), new Date(), "Recommendation");
                    if (recommendation != null) {
                        recommendedPersonReceivedRecommendations += 1;
                        recommendedPerson.setRecommendationsreceived(recommendedPersonReceivedRecommendations);
                        dbm.updateEntity(recommendedPerson);
                    }
                }

                // Check if candidate have reached necessary number of recommendation to become a member
                if (recommendedPerson.getRecommendationsreceived() >= minimumRecommendations) {

                    Type memberType = (Type) dbm.getEntity("Type", "Internalname", "Person.User");

                    if (memberType != null) {
                        recommendedPerson.setTypeid(memberType);
                        dbm.updateEntity(recommendedPerson);
                        ema.sendCandidateApprovalMessage(recommendedPerson);
                    }
                }

            }
        }

        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}
