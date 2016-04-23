/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;

/**
 *
 * @author ErnestB
 */
@Named
@Stateful
@ViewScoped
public class AdminHouseManager implements Serializable {
    
    private List<House> houses;
    private House house;
    private String editableHouseId;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init() { 
        houses = em.createNamedQuery("House.findAll").setParameter("isdeleted",  false).getResultList();
    }
    
    public List<House> getHouses() {
        return houses;
    }
    
    public String setHouse() {
        
        return "house";
    }
    
    public AdminHouseManager() {
    }
    
    /*private String getParameter(String key) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        
        return params.get(key);
    }
     
     public House getHouse() {
        try {
            String houseId = getParameter("houseId");
            int id = Integer.parseInt(houseId);
            houses = em.createNamedQuery("House.findById").setParameter("id",  id).getResultList();
            house = houses.get(0);
        }
        catch (Exception ex) {
            house = new House();
        }
        finally {
            return house;
        }
    }
     
     public String saveHouse(House h) {
        //em.getTransaction().begin();
        //em.persist(house);
        //em.getTransaction().commit();
        //em.flush();
        //house.setAddress("Vilniussss");
        //house = getHouse();
        
        dbm.editHouse(h);
        return "houses";
    }*/
}
