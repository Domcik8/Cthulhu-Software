/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lt.vu.mif.labanoro_draugai.business.DatabaseManager;

/**
 *
 * @author Ernest J
 */
@Named
@Stateless
public class LoginForm {
    private String username;
    private String password;

    @Inject
    LoginController controller;
    
    public LoginForm() {}
    
    public String getUsername() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword() {
        this.password = password;
    }
    
    public String login() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//        try {
            
            // check user if exist in db and if correct credentials
                System.out.println("Login worked"); 
              //  System.out.println(username + " : " + password);
            //if (exist)         
//            request.login(this.username, this.password);
            
//        } catch (ServletException e) {
//           
//            context.addMessage(null, new FacesMessage("Login failed."));
//            return "error";
//        }
        return "/index.html";
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
    }
    
    
}
