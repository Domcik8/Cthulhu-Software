/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.beans.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Service;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import lt.vu.mif.labanoro_draugai.entities.Type;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Ernest
 */
@Named
@Stateful
@ViewScoped
public class AdminServiceManager implements Serializable {
    @Inject
    DatabaseManager dbm;
    
    @PersistenceContext
    EntityManager em;
    
    private List<Service> services;
    private List<Service> filteredServices;
    private String currency;
    
    private Map<String, String> serviceTypes;
    private String serviceType;
    
    private List<Service> selectedServices;
    
    @PostConstruct
    public void init() {
        if (services == null || services.isEmpty()) {
            services = em.createNamedQuery("Service.findAll").getResultList();
        }
        
        Systemparameter param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Currency.Euro");
        if (param == null) {
            currency = "?";
            return;
        }
        currency = param.getValue();
        
        initTypes();
    }
    
    public String getType(Service service) {
        return service.getTypeid().getTitle();
    }
    
    private void initTypes() {
        serviceTypes = new LinkedHashMap<String, String>();
        List<Type> allTypes = dbm.retrieveTypes("Service");
        
        for (Type t : allTypes) {
            serviceTypes.put(t.getId().toString(), t.getTitle());
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        Service srv = (Service) event.getObject();
        srv.setTypeid((Type) dbm.getEntity("Type", "Id", Integer.parseInt(serviceType)));
        srv = (Service) dbm.updateEntity(srv);
        dbm.persistAndFlush(srv);
    }
     
    public void onRowCancel(RowEditEvent event) { }
    
    public void deleteChecked() throws IOException {
        for (Service s : selectedServices) {
            s.setIsdeleted(Boolean.TRUE);
            s = (Service) dbm.updateEntity(s);
            dbm.persistAndFlush(s);
        }

        //Reload the page:
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    public void addService() throws IOException {
        services.add(new Service());
        //Reload the page:
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    public List<Service> getServices() {
        return services;
    }
    
    public void setServices(List<Service> svcs) {
        services = svcs;
    }
    
    public List<Service> getFilteredServices() {
        return filteredServices;
    }
    
    public void setFilteredServices(List<Service> svcs) {
        filteredServices = svcs;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public Map<String, String> getServiceTypes() {
        return serviceTypes;
    }
    
    public void setServiceTypes(Map<String, String> types) {
        serviceTypes = types;
    }
    
    public void setServiceType(String type) {
        serviceType = type;
    }
    
    public String getServiceType() {
        return serviceType;
    }
    
    public List<Service> getSelectedServices() {
        return selectedServices;
    }
    
    public void setSelectedServices(List<Service> services) {
        selectedServices = services;
    }
}
