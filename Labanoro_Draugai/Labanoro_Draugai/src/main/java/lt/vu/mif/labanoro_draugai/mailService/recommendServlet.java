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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Recommendation;

/**
 *
 * @author NecrQ
 */
@WebServlet("/recommend")
public class recommendServlet {

    @Inject
    private DatabaseManager dbm;

    @PersistenceContext
    private EntityManager em;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) {

        if (request.getUserPrincipal() != null) {

            String recommenderEmail = request.getUserPrincipal().toString();
            String userKey = request.getParameter("user");

            Query query = em.createNamedQuery("Person.findByUniquekey").setParameter("Uniquekey", userKey);
            Person recommendedPerson = (Person) query.getResultList().get(0);

            if (recommendedPerson != null) {

                String personEmail = recommendedPerson.getEmail();

                // query for all recommendation for particular user and calculate results of list
                List recommendationList = recommendedPerson.getRecommendationList();
                if (recommendationList.size() > 2) {
                    return;
                }

                // if recommendation number < required recommendations , add one more recommendation.
                Recommendation recommend = dbm.addRecommendation(recommenderEmail, personEmail, userKey);
                // add to Recommendation table
                if (recommend != null) {

                }
                
                // if enough recommendation reached, change user status from "Candidate" to "User"
                recommendationList = recommendedPerson.getRecommendationList();
                if (recommendationList.size() > 2) {
                    
                    //recommendedPerson.setTypeid(5);
                    return;
                }

            }

        }
    }

}
