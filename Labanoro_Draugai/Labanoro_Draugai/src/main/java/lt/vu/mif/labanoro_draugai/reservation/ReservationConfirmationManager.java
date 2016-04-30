/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Service;

/**
 *
 * @author Karolis
 */
@ManagedBean
@ViewScoped
public class ReservationConfirmationManager implements Serializable{
    
    private House house;
    private Date dateFrom;
    private Date dateTo;
    String[] services;
    
    @PostConstruct
    public void init(){
    }

    public House getHouse() {
        if (house == null) {
            house =  (House) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("house");
        }
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Date getDateFrom() {
        if (dateFrom == null) {
            dateFrom =  (Date) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dateFrom");
        }
        
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        if (dateTo == null) {
            dateTo =  (Date) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dateTo");
        }
        
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String[] getServices() {
        if (services == null) {
            services =  (String[]) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("serviceList");
        }
        
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }


    
    
}
