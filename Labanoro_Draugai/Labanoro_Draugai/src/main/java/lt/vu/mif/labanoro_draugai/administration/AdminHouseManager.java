/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Type;

/**
 *
 * @author ErnestB
 */
@Named
@Stateful
@ViewScoped
public class AdminHouseManager implements Serializable {
    private List<House> houses;
    
    private int id;
    
    @PersistenceContext(type=PersistenceContextType.EXTENDED, synchronization=SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init() { 
        houses = em.createNamedQuery("House.findAll").getResultList();
    }
    
    public AdminHouseManager() {
    }
    
    public List<House> getHouses() {
        return houses;
    }
    
    public String setEditableHouse() {
        return "house";
    }
}
