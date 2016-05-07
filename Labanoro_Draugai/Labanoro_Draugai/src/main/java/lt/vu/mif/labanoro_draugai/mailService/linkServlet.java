/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.mailService;

import java.io.IOException;
import javax.inject.Inject;
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
public class linkServlet extends HttpServlet {

    @Inject
    private DatabaseManager dbm;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) {

        String conf = request.getParameter("confirm");
        String user = request.getParameter("user");

        System.out.println(user);
        System.out.println(conf);

        try {

            Person person = (Person) dbm.getEntity("Person", "Email", user);
            if (person.getFacebookaccesstoken().equals(conf)) {
                person.setFacebookaccesstoken("");
                response.sendRedirect(request.getContextPath() + "/index.html");
            }
        } catch (Exception e) {

        }

    }
}
