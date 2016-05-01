package lt.vu.mif.labanoro_draugai.authentication;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

//import lt.vu.mif.labanoro_draugai.administration.settings
@ManagedBean(name = "loginPageCode")
@SessionScoped
public class LoginPageCode implements Serializable {

    private static final long serialVersionUID = -1611162265998907599L;

    private String appId = null;
    private String redirectUrl = null;

    public String getFacebookUrlAuth() {

        setAppId();
        setRedirectUrl();

        if (appId == null || redirectUrl == null) {
            // call error handler saying that
            System.err.println("Custom error handler called: appId or redirectUrl = null");
            return "";
        }

        HttpSession session
                = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String sessionId = session.getId();
        String returnValue = "https://www.facebook.com/dialog/oauth?client_id="
                + appId + "&redirect_uri=" + redirectUrl + "/login"
                + "&scope=email,user_birthday&state=" + sessionId;
        return returnValue;
    }

    private void setAppId() {
        // change to call for db settings
        this.appId = "198659840500311";
    }

    private void setRedirectUrl() {
        // change to call for db settings
        this.redirectUrl = "http://localhost:8080/Labanoro_Draugai";
    }
}
