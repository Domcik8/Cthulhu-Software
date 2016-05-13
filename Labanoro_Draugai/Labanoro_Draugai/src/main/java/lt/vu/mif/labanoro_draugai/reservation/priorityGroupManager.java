/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.util.List;
import java.util.Random;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Reservation;

/**
 *
 * @author Dominik Lisovski
 */

@Named
@Stateless
public class priorityGroupManager {
    
    @Inject
    DatabaseManager dbm;
    
    @PersistenceContext
    EntityManager em;
   
    //@Schedule(second="20, 40, 60", minute="*", hour="*")
    public void priorityService() {
        System.out.println("Kuku");
        
        List<Person> people = dbm.getAllEntities("Person");
        for (Person person : people) {
            person.setPriority(666);
        }
        System.out.println("Kuku2");
    }
    /*
   Every weekday at 20:15: minute=”15″, hour=”20″, dayOfWeek=”Mon-Fri”
Every friday at midnight: dayOfWeek=”Fri”
Every five minutes: minute=”5″, hour=”*”
Every twenty seconds starting at second 10: second=”10/20″, minute = “*”, hour = “*”*/
 
    public void countPriorities() {List<Person> people = dbm.getAllEntities("Person");
        if(people != null)
            for (Person person : people) {
                countPriority(person);
            }
    }
    
    private void countPriority(Person person) {
        List<Reservation> reservations = dbm.getEntityList("Reservation", "PersonID", person);
        /*String dateFrom = dbm.getSystemParameter("TEST", "Date");
        String dateTo = dbm.getSystemParameter("TEST", "Date");
        
        if(reservations != null)
            for(Reservation reservation : reservations) {
                
                //System.out.println(reservation.getId());
                if(reservation.getStartdate() >  && reservation.getStartdate() < ) {
                    
                }
            }*/
                
        
        Random random = new Random();
        person.setPriority(random.nextInt(7));
        em.persist(person);
    }
}