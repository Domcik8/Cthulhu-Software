/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.beans.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Ernest B
 */
@Named
@Stateless
public class indexManager implements Serializable {
    private String title1;
    private String description1;
    private String title2;
    private String description2;
    private String title3;
    private String description3;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init() {
        title1 = ((Systemparameter) dbm.getEntity("Systemparameter", "Internalname", "SystemParameter.Index.Title1")).getValue();
        description1 = ((Systemparameter) dbm.getEntity("Systemparameter", "Internalname", "SystemParameter.Index.Description1")).getValue();
        title2 = ((Systemparameter) dbm.getEntity("Systemparameter", "Internalname", "SystemParameter.Index.Title2")).getValue();
        description2 = ((Systemparameter) dbm.getEntity("Systemparameter", "Internalname", "SystemParameter.Index.Description2")).getValue();
        title3 = ((Systemparameter) dbm.getEntity("Systemparameter", "Internalname", "SystemParameter.Index.Title3")).getValue();
        description3 = ((Systemparameter) dbm.getEntity("Systemparameter", "Internalname", "SystemParameter.Index.Description3")).getValue();
    }
    
    public String getTitle1() {
        return title1;
    }
    
    public String getTitle2() {
        return title2;
    }
    
    public String getTitle3() {
        return title3;
    }
    
    public String getDescription1() {
        return description1.replace("#", "<br/>");
    }
    
    public String getDescription2() {
        return description2.replace("#", "<br/>");
    }
    
    public String getDescription3() {
        return description3.replace("#", "<br/>");
    }
}
