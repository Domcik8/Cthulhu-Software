/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author Dominik Lisovski
 */

@Named
@Stateless
public class priorityGroupManager {
    
    @Inject
    DatabaseManager databaseManager;
    
    @PersistenceContext
    EntityManager em;
 
    public void countPriorities() {
        Random random = new Random();
        Query query = em.createNamedQuery("Person.findAll");
        List<Person> people = query.getResultList();
        for (Person person : people) {
            person.setPriority(random.nextInt(7));
            em.persist(person);
        }
    }
}