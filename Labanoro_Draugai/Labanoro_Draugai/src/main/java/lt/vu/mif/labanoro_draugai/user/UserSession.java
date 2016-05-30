/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author werezz
 */
@Stateful
@Named
@SessionScoped
public class UserSession {

    @Inject
    DatabaseManager dbm;

    private Person person;

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) (ec.getRequest());
        person = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());
        System.out.println(request.getUserPrincipal().getName());
    }

    public boolean isAdmin() {
        if (person == null) {
            return false;
        }
        return (person.getTypeid().getInternalname().equalsIgnoreCase("Person.Administrator"));
    }

}
