/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Karolis
 */
@Named
@ViewScoped
public class MyReservationsView implements Serializable {

    List<Reservation> reservations;
    List<Reservation> filteredReservations;
    private Reservation selectedReservation;
    private Person user;
    private double exchangeRate;
    private String currency;
    @Inject
    DatabaseManager dbm;

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) (ec.getRequest());
        if (request == null || request.getUserPrincipal() == null || request.getUserPrincipal().getName() == null) {
            try {
                ec.redirect("/Labanoro_Draugai/login.html");
                return;
            } catch (IOException ex) {
                Logger.getLogger(ReservationConfirmationManager.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        user = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());

        reservations = new ArrayList<>();
        for (Reservation reserv : user.getReservationList()) {
            if (!reserv.getIsdeleted()) {
                reservations.add(reserv);
            }
        }

        Systemparameter param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.ExchangeRate.Euro");
        if (param == null) {
            System.out.println("Truksta \"SystemParameter.ExchangeRate.Euro\" parametro");
            exchangeRate = -1;
            currency = "?";
            return;
        }
        exchangeRate = Double.parseDouble(param.getValue());
        param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Currency.Euro");
        if (param == null) {
            System.out.println("Truksta \"SystemParameter.Currency.Euro\" parametro");
            currency = "?";
            return;
        }
        currency = param.getValue();
        System.out.println(toString() + " constructed.");
    }

    public void cancelReservation() {
        if (selectedReservation != null && selectedReservation.getIsdeleted() && canBeCanceled(selectedReservation)) {
            return;
        }
        selectedReservation.setIsdeleted(Boolean.TRUE);
        user.setPoints(user.getPoints().add(BigDecimal.valueOf(reservationPointPrice(selectedReservation))));
        dbm.updatePersonPoints(user);
        selectedReservation = (Reservation) dbm.updateEntity(selectedReservation);
        reservations.remove(selectedReservation);
        filteredReservations.remove(selectedReservation);
        selectedReservation = null;
    }

    public boolean canBeCanceled(Reservation reservation) {
        Systemparameter cancelParam = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Reservation.MinDaysBeforeCancel");
        if (cancelParam == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, Integer.parseInt(cancelParam.getValue()));
        if (reservation.getStartdate().before(calendar.getTime())) {
            return false;
        }
        return true;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public double reservationPointPrice(Reservation reservation) {
        return reservation.getPaymentid().getPaymentprice().doubleValue() * exchangeRate;
    }

    public boolean filterFromDate(Object value, Object filter, Locale locale) {
        if (filter == null) {
            return true;
        }
        Date date = (Date) value;
        Date filterDate = (Date) filter;
        return date.after(filterDate);
    }

    public boolean filterToDate(Object value, Object filter, Locale locale) {
        if (filter == null) {
            return true;
        }
        Date date = (Date) value;
        Date filterDate = (Date) filter;
        return date.before(filterDate);
    }

    public List<Reservation> getFilteredReservations() {
        return filteredReservations;
    }

    public void setFilteredReservations(List<Reservation> filteredReservations) {
        this.filteredReservations = filteredReservations;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Reservation getSelectedReservation() {
        return selectedReservation;
    }

    public void setSelectedReservation(Reservation selectedReservation) {
        this.selectedReservation = selectedReservation;
    }
}
