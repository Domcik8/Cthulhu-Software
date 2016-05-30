/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.priorityGroup.Manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;

/**
 *
 * @author Dominik Lisovski
 */

@Named
@Stateless
@Alternative
public class priorityGroupManagerByReservationDays implements IPriorityGroupManager {
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void countPriorities() {
        int tryCount;
        
        List<Reservation> reservations = getReservationsForPriorityCount(); 
        List<Person> people = dbm.getAllEntities("Person");
        
        float priorityGroupThreshhold = getPriorityGroupThreshhold(reservations);
        
        if (priorityGroupThreshhold < 1)
            for (Person person : people) {
                tryCount = 3;
                
                while (tryCount > 0) {
                    person.setPriority(0);
                    if(dbm.updateEntity(person) == null) {
                        person = (Person) dbm.getEntity("Person", "Email", person.getEmail());
                    } else
                        break;
                    tryCount--;
                }
            }
        else
            for (Person person : people) {
                float reservedDaysCount;
                tryCount = 3;
                int personPriority;
                
                while (tryCount > 0) {
                    personPriority = 0;
                    reservedDaysCount = getReservedDaysCount(person.getReservationList());
                    while(reservedDaysCount > 0) {
                        reservedDaysCount -= priorityGroupThreshhold;
                        personPriority++;
                    } 
                    person.setPriority(personPriority);
                    
                    if(dbm.updateEntity(person) == null) {
                        person = (Person) dbm.getEntity("Person", "Email", person.getEmail());
                    } else
                        break;
                    tryCount--;
                }
            }
    }
    
    private List<Reservation> getReservationsForPriorityCount() {
        
        Systemparameter seasonLengthSysParam = dbm.getSystemParameter("SystemParameter.priorityGroup.SeasonLength");
        int seasonLength = Integer.parseInt(seasonLengthSysParam.getValue());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -seasonLength);
        
        List<Reservation> allReservations = dbm.getAllEntities("Reservation");
        List<Reservation> resultReservations = new ArrayList();
        
        for (Reservation reservation : allReservations) {
            if (reservation.getStartdate().compareTo(calendar.getTime()) >= 0)
                resultReservations.add(reservation);
        }
        
        return resultReservations; 
    }
    
    private float getPriorityGroupThreshhold(List<Reservation> reservations) {
        
        float priorityGroupThreshhold = 0;
        
        Systemparameter numberOfPriorityGroupsSysParam = dbm.getSystemParameter("SystemParameter.priorityGroup.Quantity");
        int numberOfPriorityGroups = Integer.parseInt(numberOfPriorityGroupsSysParam.getValue());
        
        if (numberOfPriorityGroups > 0) {
            priorityGroupThreshhold = (float) getReservedDaysCount(reservations) / (float) numberOfPriorityGroups;
        }
        
        return priorityGroupThreshhold;
    }
    
    private int getReservedDaysCount(List<Reservation> reservations) {
        
        int reservationDays = 0;
        
        for (Reservation reservation : reservations) {
                reservationDays += (reservation.getEnddate().getTime() - reservation.getStartdate().getTime()) / (1000 * 60 * 60 * 24);
            }
        
        return reservationDays;
    }
}
