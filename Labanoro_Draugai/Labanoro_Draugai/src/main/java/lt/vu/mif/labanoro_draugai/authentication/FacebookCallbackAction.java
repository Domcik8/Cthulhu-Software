/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.Action;
/*
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
*/

/**
 *
 * @author AlaNote
 */
public class FacebookCallbackAction {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory("App ID", "App Secret");
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        String authCode = request.getParameter("code");
        AccessGrant accessGrant = oauthOperations.exchangeForAccess(authCode, "https://my.app.net/fb/facebookCallback.do", null);
        Connection connection = connectionFactory.createConnection(accessGrant);

        UserProfile userProfile = connection.fetchUserProfile();
*/
    }
}
