/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.beans.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Person;

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
    private Payment payment;
    private Date calendar;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init() {
        if (payments == null || payments.isEmpty()) {
            payments = em.createNamedQuery("Payment.findAll").getResultList();
        }
    }
    
    public String getType(Payment paym) {
        return paym.getTypeid().getTitle();
    }
    
    public void removeChecked() {
        
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
}
