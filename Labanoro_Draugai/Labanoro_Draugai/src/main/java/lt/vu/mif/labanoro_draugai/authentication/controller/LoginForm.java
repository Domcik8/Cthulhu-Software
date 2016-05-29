/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Ernest J
 */
@Named("auth")
@Stateful
@ViewScoped
public class LoginForm {

    private String username;
    private String password;
    private String url;
//    private String loginStatus;

    @Inject
    private LoginController controller;

    @Inject
    private DatabaseManager dbm;

    public LoginForm() {
    }

//    public void setLoginStatus(String text) {
//        this.loginStatus = text;
//    }
//
//    public String getLoginStatus() {
//        return this.loginStatus;
//    }
    public void setUrl(String param) {
        this.url = param;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PostConstruct
    public void init() {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ex = context.getExternalContext();

        String dispatcherUrl = (String) ex.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        String dispatcherParam = (String) ex.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

        System.out.println("dispatcherUrl: " + dispatcherUrl);
        System.out.println("dispatcherUrlParam: " + dispatcherParam);

        // For redirect purposes (url with params and w/o)
        if (dispatcherUrl != null && dispatcherParam != null) {
            this.url = dispatcherUrl + "?" + dispatcherParam;
        } else {
            this.url = dispatcherUrl;
        }
    }

    public String login() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        try {
            if (request.getUserPrincipal() == null) {   // check if not already logged in
                if (controller.isUser(this.username)) {     // check if user already exists
                    String passwordHash = Hashing.sha256().hashString(this.password, Charsets.UTF_8).toString();
                    request.login(this.username, passwordHash);

                    if (request.getUserPrincipal().getName().equals(this.username)) {
                        System.out.println("Internal login worked");
                        if (!this.url.isEmpty() || this.url != null) {
                            try {
                                facesContext.getExternalContext().redirect(this.url);
                            } catch (IOException ex) {
                                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            return "";
                        } else {
                            return (dbm.getSystemParameter("SystemParameter.Redirect.LoginSuccess").getValue() + "?faces-redirect=true");
                        }
                    }
                } else {
                    requestContext.execute("PF('ShowNotExistingUserStatus').show();");
                    return "";
                }
            }
        } catch (ServletException e) {
            System.out.println("Failed to log in user!");
            this.password = "";
            requestContext.execute("PF('showIncorrectCredentials').show(); ");
            return "";
        }

        return (dbm.getSystemParameter("SystemParameter.Redirect.LoginSuccess").getValue() + "?faces-redirect=true");
    }

    public String logout() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        String destination = (dbm.getSystemParameter("SystemParameter.Redirect.Login").getValue() + "?faces-redirect=true");
        try {
            request.logout();
            System.out.println("Logout worked");
        } catch (ServletException e) {
            System.out.println("Failed to logout user!");
        }

        return destination;
    }

}
