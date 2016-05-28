/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.beans.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.mailService.EmailBean;

/**
 *
 * @author Ernest B
 */
@Named
@Stateful
@ViewScoped
public class AdminPaymentManager implements Serializable {
    private List<Payment> payments;
    private List<Payment> filteredPayments;
    private List<Payment> selectedPayments;
    private Payment payment;
    private Date calendar;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @Inject
    EmailBean emailBean;
    
    @PostConstruct
    public void init() {
        if (payments == null || payments.isEmpty()) {
            payments = em.createNamedQuery("Payment.findAll").getResultList();
        }
    }
    
    public String getType(Payment paym) {
        return paym.getTypeid().getTitle();
    }
    
    public void approveChecked() throws IOException {
        for (Payment p : selectedPayments) {
            if (p.getApproveddate() == null) {
                boolean approvementSuccess = dbm.setPaymentApprovalDate(p);
                if (approvementSuccess) {
                    sendPaymentApprovementEmail(p);
                }
            }
        }
        
        //Reload the page:
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    private void sendPaymentApprovementEmail(Payment paym) {
        Person pers = (Person) dbm.getEntity("Person", "Id", paym.getPersonid().getId());
        String receiver = pers.getEmail();
        emailBean.sendPaymentApprovementMessage(receiver, pers, paym);
    }
    
    public List<Payment> getPayments() {
        return payments;
    }
    
    public void setPayments(List<Payment> pmnts) {
        payments = pmnts;
    }
    
    public Payment getPayment() {
        return payment;
    }
    
    public void setPayment(Payment paym) {
        payment = paym;
    }
    
    public List<Payment> getFilteredPayments() {
        return filteredPayments;
    }
    
    public void setFilteredUsers(List<Payment> filtUsers) {
        filteredPayments = filtUsers;
    }
    
    public Date getCurrentDate() {
        return new Date();
    }
    
    public Date getCalendar() {
        return calendar;
    }
 
    public void setCalendar(Date date) {
        this.calendar = date;
    }
    
    public List<Payment> getSelectedPayments() {
        return selectedPayments;
    }
    
    public void setSelectedPayments(List<Payment> p) {
        selectedPayments = p;
    }
    
    public boolean isApproved(Payment p) {
        if (p.getApproveddate() != null)
            return true;
        else
            return false;
    }
}
