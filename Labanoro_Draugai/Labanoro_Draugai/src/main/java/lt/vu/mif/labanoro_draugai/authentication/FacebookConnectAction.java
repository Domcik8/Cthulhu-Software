/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
*/
/**
 *
 * @author AlaNote
 */
public class FacebookConnectAction extends HttpServlet{
    /*
    private static final long serialVersionUID = 1L;
    private String code = "";

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
    
    */
    
    
    /*
    public void executeAction(HttpServletRequest request, HttpServletResponse response, String previousAction) throws Exception {

        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory("App ID", "App Secret");
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("https://my.app.net/fb/facebookCallback.do");
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
        response.sendRedirect(authorizeUrl);
    }*/
}
