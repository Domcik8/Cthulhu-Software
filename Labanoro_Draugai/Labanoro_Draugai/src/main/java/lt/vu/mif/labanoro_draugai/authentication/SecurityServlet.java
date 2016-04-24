package lt.vu.mif.labanoro_draugai.authentication;

import com.github.scribejava.core.oauth.OAuthService;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

import lt.vu.mif.labanoro_draugai.authentication.controller.LoginController;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;              //for database operation (user registration)
// import lt.vu.mif.labanoro_draugai.administration.settings    -- for settings import

@WebServlet("*.sec")
public class SecurityServlet extends HttpServlet {

    private static final long serialVersionUID = 8071426090770097330L;
    private String appId = "";
    private String redirectUrl = "";
    
    @Inject
    private FBGraph fbGraph;
    @Inject
    private LoginController loginController;
    
    public SecurityServlet() {}

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
        
        String facebookId = (String) fbGraph.getIdEmail(accessToken).get("id");
        String email = fbGraph.getEmail(accessToken);
//        String birthday = fbGraph.getAge(accessToken);
//        String firstName = fbGraph.getFirstName(accessToken);
//        String lastName = fbGraph.getLastName(accessToken);
//        Map profile = fbGraph.getFirstLastName(accessToken);
//        String profileUrl = fbGraph.getProfilePicture(accessToken);
        
//        System.out.println("Check: " 
//                + facebookId + "\n"
//                + email + "\n" 
//                + birthday + "\n" 
//                + profileUrl + "\n"
//                + firstName + "\n" 
//                + lastName + "\n" 
//                + profile.get("first_name") + "\n" 
//                + profile.get("last_name")
//        );
  
        String sessionID = httpSession.getId();
        
        if (state.equals(sessionID)) {
            try {  
 
                if(loginController.login(email,facebookId)) {
                    //request.login(email, "somedefaultpassword");
                    System.out.println("User logged");
                }
                

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/user/loginError.html");
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
    
    private void setAppId() {
        
        // get AppId from settings and return it;
        this.appId = "198659840500311";
    }
    
    private void setRedirectUrl() {
        
        this.redirectUrl = "http://localhost:8080/Labanoro_Draugai/user/login.sec";
    }
    
    private String getAppSecret() {
        
        // get app secret from settings and return it;  
        return "97d6fc7c788463e2de89f1571385cc75";    
    }
}