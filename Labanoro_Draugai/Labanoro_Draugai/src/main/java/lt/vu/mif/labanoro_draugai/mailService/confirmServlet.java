/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.mailService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        String confirmKey = request.getParameter("key");
        String redirect = dbm.getSystemParameter("SystemParameter.Redirect.Login").getValue();

        try {
            Person person = (Person) dbm.getEntity("Person", "Emailconfirmation", confirmKey);
            if (person != null) {
                person.setEmailconfirmation("validated");
                person = (Person) dbm.updateEntity(person);
            }
            response.sendRedirect(request.getContextPath() + redirect);

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + redirect);
        }
    }
}
