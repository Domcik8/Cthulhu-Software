/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.mailService;

import java.io.IOException;
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

            try {
                minimumRecommendations = Integer.parseInt(dbm.getSystemParameter("ServiceParameter.RequiredRecommendations").getValue());
                maximumRecommendations = Integer.parseInt(dbm.getSystemParameter("ServiceParameter.MaxRecommendations").getValue());
            } catch (NumberFormatException exc) {
            }

            Person recommendedPerson = (Person) dbm.getEntity("Person", "Uniquekey", userKey);

            // If recommendedPerson exists and if recommender != himself
            if (recommendedPerson != null && !(recommendedPerson.getEmail()).equals(recommenderEmail)) {

                String personEmail = recommendedPerson.getEmail();
                
                // Get list of persons who recommended this person
                List<Recommendation> recommendationList = recommendedPerson.getRecommendationList1();
                
                // Check if this person has maximum allowed recommendation number
                if (recommendationList.size() >= maximumRecommendations) {
                    return;
                }
                
                // Check if recommender hasn't already recommended this person
                for (Recommendation rec : recommendationList) {
                    if (rec.getRecommenderid().equals(recommender.getId())) {
                        return;
                    }
                }

                // add one more recommendation
                Recommendation recommend = dbm.addRecommendation(recommenderEmail, personEmail, "Recommendation");
                if (recommend != null) {
                    dbm.updateEntity(recommend);
                }

                // if enough recommendation reached, change user status from "Candidate" to "User"
                recommendationList = recommendedPerson.getRecommendationList1();
                if (recommendationList.size() > minimumRecommendations) {
                    
                    Type typeid = (Type) dbm.getEntity("Type", "Internalname", "Person.User");
                    recommendedPerson.setTypeid(typeid);
                    dbm.updateEntity(recommendedPerson);
                    return;
                }
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}
