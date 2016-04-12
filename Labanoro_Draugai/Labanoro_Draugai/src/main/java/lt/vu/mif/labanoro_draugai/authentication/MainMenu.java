/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/user/home.html")
public class MainMenu extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String code = "";

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        code = req.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR: Didn't get code parameter in callback.");
        }
        FBConnection fbConnection = new FBConnection();
        String accessToken = fbConnection.getAccessToken(code);

        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
        ServletOutputStream out = res.getOutputStream();
        out.println("<h1>Facebook Login using Java</h1>");
        out.println("<h2>Application Main Menu</h2>");
        out.println("<div>Welcome " + fbProfileData.get("first_name"));
        out.println("<div>Your Email: " + fbProfileData.get("email"));
        out.println("<div>You are " + fbProfileData.get("gender"));
    }
}
