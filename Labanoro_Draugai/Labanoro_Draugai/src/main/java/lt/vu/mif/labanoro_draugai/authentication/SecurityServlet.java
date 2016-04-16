package lt.vu.mif.labanoro_draugai.authentication;

import com.github.scribejava.core.oauth.OAuthService;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

// import lt.vu.mif.labanoro_draugai.{someImport}               -- for database operation (user registration)
// import lt.vu.mif.labanoro_draugai.administration.settings    -- for settings import

@WebServlet("*.sec")
public class SecurityServlet extends HttpServlet {

    private static final long serialVersionUID = 8071426090770097330L;
    
    
    private String appId = "";
    private String redirectUrl = "";
    
        
    @Inject
    private FBGraph fbGraph;
    
    public SecurityServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("*** Called SecurityServlet");
        HttpSession httpSession = request.getSession();
        String faceCode = request.getParameter("code");
        String state = request.getParameter("state");
        
        setAppId();
        setRedirectUrl();
        
        String accessToken = getFacebookAccessToken(faceCode);
        
//        String email = getUserMailAddressFromJsonResponse(accessToken, httpSession);
        String email = fbGraph.getEmail(accessToken);
        String birthday = fbGraph.getAge(accessToken);
        String firstName = fbGraph.getFirstName(accessToken);
        String lastName = fbGraph.getLastName(accessToken);
        Map profile = fbGraph.getFirstLastName(accessToken);
        String profileUrl = fbGraph.getProfilePicture(accessToken);
        
        System.out.println("Check: " 
                + email + "\n" 
                + birthday + "\n" 
                + profileUrl + "\n"
                + firstName + "\n" 
                + lastName + "\n" 
                + profile.get("first_name") + "\n" 
                + profile.get("last_name")
        );
  
        String sessionID = httpSession.getId();
        
        if (state.equals(sessionID)) {
            try {                
                //do some specific user data operation like saving to DB or login user
                //request.login(email, "somedefaultpassword");

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/user/facebookError.html");
                return;
            }
            
            response.sendRedirect(request.getContextPath() + "/index.html");
            
            
        } else {
            System.err.println("CSRF protection validation");
        }
    }

    private String getFacebookAccessToken(String faceCode) throws IOException {
        
        String token = null;
        if (faceCode != null && !"".equals(faceCode)) {

            String faceAppSecret = getAppSecret();
            String newUrl = "https://graph.facebook.com/oauth/access_token?client_id="
                    + appId + "&redirect_uri=" + redirectUrl + "&client_secret="
                    + faceAppSecret + "&code=" + faceCode;
            
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();

            try {
                HttpGet httpget = new HttpGet(newUrl);
                
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = httpclient.execute(httpget, responseHandler);
                token = StringUtils.removeEnd(StringUtils.removeStart(responseBody, "access_token="), "&expires=5180795");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpclient.close();
            }
        }
        return token;
    }

//    private String getUserMailAddressFromJsonResponse(String accessToken, HttpSession httpSession) throws IOException {
//        String email = null;
//        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
//
//        try {
//            if (accessToken != null && !"".equals(accessToken)) {
//                String newUrl = "https://graph.facebook.com/v2.5/me?fields=email&access_token=" + accessToken;
//                //String newUrl = "https://graph.facebook.com/v2.5/me?fields=first_name,last_name,email&access_token=" + accessToken;
//                //String newUrl = "https://graph.facebook.com/me?access_token=" + accessToken;
//                httpclient = HttpClientBuilder.create().build();
//
//                HttpGet httpget = new HttpGet(newUrl);
//                System.out.println("Get info from face --> executing request: " + httpget.getURI());
//                ResponseHandler<String> responseHandler = new BasicResponseHandler();
//                String responseBody = httpclient.execute(httpget, responseHandler);
//                JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
//                String facebookId = json.getString("id");
//                email = json.getString("email");
//                
//                System.out.println("getUserMailAddressFromJsonResponse method: " + facebookId + " " + email);
//
//            } else {
//                System.err.println("Facebook token is null");
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            httpclient.close();
//        }
//        return email;
//    }
    
    private void setAppId() {
        
        // get AppId from settings and return it;
        this.appId = "198659840500311";
    }
    
    private void setRedirectUrl() {
        
        // get RedirectUrl from settings and return it;
        this.redirectUrl = "http://localhost:8080/Labanoro_Draugai/user/index.sec";
    }
    
    private String getAppSecret() {
        
        // get app secret from settings and return it;  
        return "97d6fc7c788463e2de89f1571385cc75";    
    }
}