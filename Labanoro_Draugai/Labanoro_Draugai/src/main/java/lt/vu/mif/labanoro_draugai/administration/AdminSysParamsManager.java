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
@Stateful
@ViewScoped
public class AdminSysParamsManager implements Serializable {
    private List<Systemparameter> params;
    private List<Systemparameter> filteredParams;
    private Systemparameter editedParameter;
    
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
    
    public void saveChanges() throws IOException {
        if (editedParameter != null) {
            if (updateSystemParameterValue(editedParameter)) {
                //Close the dialog:
                RequestContext requestContext = RequestContext.getCurrentInstance();  
                requestContext.execute("PF('confirmDialog').hide();");

                //Reload the page:
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
            }
        }
    }
    
    public void discardChanges() throws IOException {
        editedParameter = null;
        
        //Close the dialog:
        RequestContext requestContext = RequestContext.getCurrentInstance();  
         requestContext.execute("PF('confirmDialog').hide();");

        //Reload the page:
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    public void onCellEdit(CellEditEvent event) {
        String oldValue = (String) event.getOldValue();
        String newValue = (String) event.getNewValue();
        
        FacesContext context = FacesContext.getCurrentInstance();
        editedParameter = context.getApplication().evaluateExpressionGet(context, "#{parameter}", Systemparameter.class);
        
        if(newValue != null && !newValue.equals(oldValue)) {
            RequestContext requestContext = RequestContext.getCurrentInstance();  
            requestContext.execute("PF('confirmDialog').show();");
        }
    }
    
    public boolean updateSystemParameterValue(Systemparameter param) {
        try {
            Query q = em.createQuery("UPDATE Systemparameter p SET p.value = :value "
                    + "WHERE p.id = :id");
            q.setParameter("value", param.getValue());
            q.setParameter("id", param.getId());
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public List<Systemparameter> getParams() {
        return params;
    }
    
    public void setParams(List<Systemparameter> prms) {
        params = prms;
    }
    
    public List<Systemparameter> getFilteredParams() {
        return filteredParams;
    }
    
    public void setFilteredParams(List<Systemparameter> filtParams) {
        filteredParams = filtParams;
    }
    
    public Systemparameter getEditedParameter() {
        return editedParameter;
    }
    
    public void setEditedParameter(Systemparameter param) {
        editedParameter = param;
    }
}
