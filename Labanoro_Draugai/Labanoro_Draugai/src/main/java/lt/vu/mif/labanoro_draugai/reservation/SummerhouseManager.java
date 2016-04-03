/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lt.vu.mif.entities.House;

/**
 *
 * @author Karolis
 */
@Named
@Stateful
@ViewScoped
public class SummerhouseManager {
    private List<House> summerhouses;

    @PersistenceContext
    EntityManager em;
    
    @PostConstruct
    public void init() {
        List<House> houses = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            Query query = em.createNamedQuery("House.findAll");
            houses = query.getResultList();
        }
        summerhouses = houses;
        System.out.println(toString() + " constructed.");
    }

    public List<House> getSummerhouses() {
        return summerhouses;
    }
    
    
}
