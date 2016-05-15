package lt.vu.mif.labanoro_draugai.authentication;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;

@Named("loginPageCode")
@SessionScoped
public class LoginPageCode implements Serializable {

    private static final long serialVersionUID = -1611162265998907599L;

    private String appId = null;
    private String redirectUrl = null;
    
    @Inject
    private DatabaseManager dbm;
    
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
        
        this.appId = dbm.getSystemParameter("SystemParameter.Facebook.AppId").getValue();
    }

    private void setRedirectUrl() {
        
        this.redirectUrl = dbm.getSystemParameter("SystemParameter.Facebook.Redirect").getValue();
    }
}
