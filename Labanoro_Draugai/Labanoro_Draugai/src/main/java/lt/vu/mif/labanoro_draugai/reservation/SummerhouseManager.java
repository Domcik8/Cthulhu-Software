/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lt.vu.mif.entities.House;

/**
 *
 * @author Karolis
 */
@ManagedBean(name="summerhouseManager")
@ViewScoped
public class SummerhouseManager implements Serializable{
    private List<House> summerhouses;
    private List<House> filteredSummerhouses;
      
    @PersistenceContext
    EntityManager em;
    
    @PostConstruct
    public void init() {
        
        Query query = em.createNamedQuery("House.findAll");
        summerhouses = query.getResultList();
        filteredSummerhouses = query.getResultList();

        System.out.println(toString() + " constructed.");
    }
    
    public List<House> getFilteredSummerhouses() {
        return filteredSummerhouses;
    }
    
    public void filter(){
    }
}
