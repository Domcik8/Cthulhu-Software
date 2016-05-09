/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.mailService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author NecrQ
 */
@WebServlet("/confirm")
public class confirmServlet extends HttpServlet {

    @Inject
    private DatabaseManager dbm;

    @PersistenceContext
    private EntityManager em;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doProcess(request, response);
    }

//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        doProcess(request, response);
//    }
    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String confirmKey = request.getParameter("key");
        System.out.println(confirmKey);

        try {
            Person person = (Person) dbm.getEntity("Person", "Emailconfirmation", confirmKey);

            if (person != null) {
                System.out.println("Worked1");
                person.setEmailconfirmation("validated");    
                System.out.println("Worked2: " + person.getEmailconfirmation());
                String test = dbm.getSystemParameter("ServiceParameter.Redirect.Login").getValue();
                System.out.println("Worked3");
                System.out.println(test);

                response.sendRedirect(request.getContextPath() + test);
            }
        } catch (Exception e) {
            System.out.println("some error arised");
            e.printStackTrace();
        }

    }
}
