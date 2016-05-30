/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 *
 * @author ErnestB
 */
@Named
@ViewScoped
public class AdminHouseManager implements Serializable {
    private List<House> houses;
    
    private int id;
    private String currency;
    
    @PersistenceContext(type=PersistenceContextType.EXTENDED, synchronization=SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init() { 
        //houses = em.createNamedQuery("House.findAll").getResultList();
        houses = (List<House>) dbm.getEntityList("House", "Isdeleted", false);
        
        Systemparameter param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Currency.Euro");
        if (param == null) {
            currency = "?";
            return;
        }
        currency = param.getValue();
    }
    
    public AdminHouseManager() {
    }
    
    public List<House> getHouses() {
        return houses;
    }
    
    public String setEditableHouse() {
        return "house";
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public String firstImageName(House house) {
        //if(house == null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) 
            //return null;
        
        Predicate condition = new Predicate() {
            public boolean evaluate(Object sample) {
                 return ((Houseimage)sample).getSequence().equals(1);
            }
         };
        
        try {
            List<Houseimage> imgs = dbm.getEntityList("Houseimage", "Houseid", house);
            Houseimage result = (Houseimage)CollectionUtils.select(imgs, condition).iterator().next();
            return result.getInternalname();
        }
        catch (Exception ex) {
            return "Nepavyko užkrauti nuotraukos! Siūlome įeiti į sistemą iš naujo.";
        }
    }
}
