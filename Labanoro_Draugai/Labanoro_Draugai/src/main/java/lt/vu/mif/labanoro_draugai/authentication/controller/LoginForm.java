/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ernest J
 */
@Named("auth")
@Stateless
public class LoginForm {

    private String username;
    private String password;

    @Inject
    LoginController controller;

    public LoginForm() {
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

    public String login() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {

            if (request.getUserPrincipal() == null) {   // check if not already logged in
                if (controller.isUser(this.username)) {     // check if user already exists
                    request.login(this.username, this.password);

                    if (request.getUserPrincipal().getName().equals(this.username)) {
                        System.out.println("Internal login worked");
                        return "/index?faces-redirect=true";
                    }
                }
            }

        } catch (ServletException e) {
            System.out.println("Failed to log in user!");
            context.addMessage(null, new FacesMessage("Login failed."));
            return "/loginError.html?faces-redirect=true";
        }

        return "/loginError.html?faces-redirect=true";
    }

    public String logout() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        String destination = "/index?faces-redirect=true";
        try {
            request.logout();
            System.out.println("Logout worked");
        } catch (ServletException e) {
            System.out.println("Failed to logout user!");
            destination = "/loginError.html?faces-redirect=true";
        }

        return destination;
    }

}
