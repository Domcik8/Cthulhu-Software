/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.BuyConfirmationInterface;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
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
    private String currency;

    @Inject
    BuyConfirmationInterface discountService;

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

        Systemparameter param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Currency.Euro");
        if (param == null) {
            currency = "?";
            return;
        }
        currency = param.getValue();
    }

    public String getType(Payment paym) {
        return paym.getTypeid().getTitle();
    }

    public void approveChecked() throws IOException {
        for (Payment p : selectedPayments) {
            if (p.getApproveddate() == null) {
                boolean approvementSuccess = dbm.updatePaymentApprovalDate(p);
                if (approvementSuccess) {
                    if (p.getTypeid().getInternalname().equalsIgnoreCase("Payment.Points")) {
                        addPoints(p);
                    } else if (p.getTypeid().getInternalname().equalsIgnoreCase("Payment.Membership")) {
                        addMembership(p);
                    }
                    sendPaymentApprovementEmail(p);
                }
            }
        }

        //Reload the page:
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    private void addPoints(Payment paym) {
        Systemparameter param = (Systemparameter) dbm.getEntity("Systemparameter", "Internalname", "SystemParameter.ExchangeRate.Euro");
        BigDecimal rate = new BigDecimal(param.getValue());
        BigDecimal points = paym.getPaymentprice().multiply(rate);
        Person person = paym.getPersonid();
        person.setPoints(person.getPoints().add((discountService.getIncreasedPoints(points, person, paym.getTypeid().getInternalname()))));
        dbm.updatePersonPoints(person);
    }

    public void addMembership(Payment paym) {

        Person person = paym.getPersonid();

        Calendar cal = Calendar.getInstance();
        if (new Date().after(person.getMembershipdue())) {
            cal.setTime(new Date());
        } else {
            cal.setTime(person.getMembershipdue());
        }
        cal.add(Calendar.YEAR, 1);
        person.setMembershipdue(cal.getTime());

        dbm.updatePersonMembershipDue(person);
    }

    private void sendPaymentApprovementEmail(Payment paym) {
        Person person = paym.getPersonid();
        String receiver = person.getEmail();
        emailBean.sendPaymentApprovementMessage(receiver, person, paym);
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

    public String getCurrency() {
        return currency;
    }

    public boolean isApproved(Payment p) {
        if (p.getApproveddate() != null) {
            return true;
        } else {
            return false;
        }
    }
}
