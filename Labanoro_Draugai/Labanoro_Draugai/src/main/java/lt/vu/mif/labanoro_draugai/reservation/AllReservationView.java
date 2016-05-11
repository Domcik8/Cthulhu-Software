/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Karolis
 */
@Named
@ViewScoped
public class AllReservationView implements Serializable{
    List<Reservation> reservations;
    List<Reservation> filteredReservations;

    @Inject
    DatabaseManager dbm;
    
     
   @PostConstruct
    public void init(){
        reservations = dbm.getAllEntities("Reservation");
    }
    public boolean filterFromDate(Object value, Object filter, Locale locale) {
        if(filter == null) return true;
        Date date = (Date) value;
        Date filterDate = (Date) filter;
        return date.after(filterDate);
    }
    
    public boolean filterToDate(Object value, Object filter, Locale locale) {
       if(filter == null) return true;
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
}
