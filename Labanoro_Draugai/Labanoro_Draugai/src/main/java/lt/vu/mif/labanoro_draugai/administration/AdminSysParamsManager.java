/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.beans.*;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;

/**
 *
 * @author Ernest B
 */
@Named
@Stateful
@ViewScoped
public class AdminSysParamsManager implements Serializable {
    private List<Systemparameter> params;
    private List<Systemparameter> filteredParams;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init() {
        if (params == null || params.isEmpty()) {
            params = em.createNamedQuery("Systemparameter.findAll").getResultList();
        }
    }
    
    public String getType(Systemparameter prm) {
        return prm.getTypeid().getTitle();
    }
    
    public void saveChanges() {
        
    }
    
    
    public List<Systemparameter> getParams() {
        return params;
    }
    
    public void setParams(List<Systemparameter> prms) {
        params = prms;
    }
    
    public List<Systemparameter> getFilteredPayments() {
        return filteredParams;
    }
    
    public void setFilteredUsers(List<Systemparameter> filtUsers) {
        filteredParams = filtUsers;
    }
}
