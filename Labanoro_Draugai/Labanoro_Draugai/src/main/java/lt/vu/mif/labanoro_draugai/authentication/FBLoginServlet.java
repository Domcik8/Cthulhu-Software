package lt.vu.mif.labanoro_draugai.authentication;

import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import lt.vu.mif.labanoro_draugai.authentication.controller.LoginController;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;

@WebServlet(urlPatterns = {"/login"})
public class FBLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 8071426090770097330L;
    private String appId = "";
    private String redirectUrl = "";

    @Inject
    private FBGraph fbGraph;
    @Inject
    private LoginController loginController;
    @Inject
    private DatabaseManager dbm;

    public FBLoginServlet() {
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

        String facebookId = (String) fbGraph.getIdEmail(accessToken).get("id");
        String email = fbGraph.getEmail(accessToken);
        String firstname = fbGraph.getFirstName(accessToken);
        String lastname = fbGraph.getLastName(accessToken);
        String sessionID = httpSession.getId();

//        if (state.equals(sessionID)) {
        try {

            if (request.getUserPrincipal() == null) {
                if (loginController.login(email, facebookId, firstname, lastname)) {
                    String password = loginController.getRegisteredUserP(email, facebookId);
                    request.login(email, password);
                    System.out.println("User logged");
                }
            } else {
                System.out.println("User has already logged: " + request.getUserPrincipal().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Auth error caught: " + request.getContextPath());
            response.sendRedirect(request.getContextPath() + dbm.getSystemParameter("SystemParameter.Redirect.LoginError").getValue());
            return;
        }

        response.sendRedirect(request.getContextPath() + dbm.getSystemParameter("SystemParameter.Redirect.Login").getValue());

//        } else {
//            System.err.println("CSRF protection validation");
////             URL doesn't change
//            RequestDispatcher rd = getServletContext().getRequestDispatcher("/ErrorHandler");
//            rd.forward(request, response);
////             URL changes
//            response.sendRedirect(request.getContextPath() + "/error");
////        }
    }

    private String getFacebookAccessToken(String faceCode) throws IOException {

        String token = null;
        if (faceCode != null && !"".equals(faceCode)) {

            String faceAppSecret = getAppSecret();
            String newUrl = "https://graph.facebook.com/oauth/access_token?client_id="
                    + appId + "&redirect_uri=" + redirectUrl + "/login" + "&client_secret="
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

        this.appId = dbm.getSystemParameter("SystemParameter.Facebook.AppId").getValue();
    }

    private void setRedirectUrl() {

        this.redirectUrl = dbm.getSystemParameter("SystemParameter.Facebook.Redirect").getValue();
    }

    private String getAppSecret() {

        return dbm.getSystemParameter("SystemParameter.Facebook.AppSecret").getValue();
    }
}
