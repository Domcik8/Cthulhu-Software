/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import static javax.interceptor.Interceptor.Priority.APPLICATION;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.reservation.ReservationConfirmationManager;

/**
 *
 * @author werezz
 */
@Interceptorius
@Dependent
@Interceptor
@Priority(APPLICATION)
public class FullInterceptor implements Serializable {

    @Inject
    DatabaseManager dbm;

    private Person user;
    private String clas;
    private String method;

    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) (ec.getRequest());
        if (request == null || request.getUserPrincipal() == null || request.getUserPrincipal().getName() == null && request.getUserPrincipal().getName().isEmpty()) {
            try {
                ec.redirect("/Labanoro_Draugai/Reservation/buy.html");
            } catch (IOException ex) {
                Logger.getLogger(ReservationConfirmationManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        user = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());
        clas = ctx.getTarget().getClass().getSuperclass().getName();
        method = ctx.getMethod().getName();
        clas = clas.concat(".");
        clas = clas.concat(method);

        dbm.addPaymentLog(user.getEmail(), user.getTypeid().getInternalname(), clas);
        return ctx.proceed();
    }

}
