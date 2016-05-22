/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.priorityGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
public class PriorityGroupManager {
    
    @Inject
    DatabaseManager dbm;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    test test;
    
    @Inject
    BasicPriorityGroupManager basicPgm;
    
    @Schedule(second="0, 30", minute="*", hour="*", dayOfWeek="*", month="*")
    public void checkServices() {
        System.out.println("Checking services");
        
        if(!isRightDate())
            return;
        
        System.out.println("Priority group service started");
        
        basicPgm.countPriorities();
        System.out.println("Priority group service ended");
    }
    
    private boolean isRightDate() {
        Calendar calendar = Calendar.getInstance();
        
        if (checkDatePart("HourOfTheDay") && checkDatePart("DayOfTheWeek") && checkDatePart("Month"))
            return true;
       return false;
    }

    private List<Reservation> getPersonReservations(Person person, List<Reservation> reservations) {
        List<Reservation> personReservations = new ArrayList<Reservation>();
        
        for(Reservation reservation : reservations) {
            if (reservation.getPersonid() == person)
                personReservations.add(reservation);
        }
        
        return personReservations;
    }

    public boolean checkDatePart(String datePart) {
        String datePartStr = dbm.getSystemParameter("SystemParameter.priorityGroup." + datePart).getValue();
        String[] datePartsStr = datePartStr.split(",");
        int[] dateParts = new int[datePartsStr.length];
        
        for(int i = 0; i < datePartsStr.length; i++) {
            dateParts[i] = Integer.parseInt(datePartsStr[i]);
            
            switch (datePart) {
                case "HourOfTheDay": 
                    if (dateParts[i] < 0 || dateParts[i] > 23)
                        return false;
                    break;
                    
                case "DayOfTheWeek": 
                    if (dateParts[i] < 1 || dateParts[i] > 7)
                        return false;
                    break;
                case "Month": 
                    if (dateParts[i] < 1 || dateParts[i] > 31)
                        return false;
            }
        }
            
        Calendar calendar = Calendar.getInstance();
        
        switch (datePart) {
            case "HourOfTheDay": 
                if (contains(dateParts, calendar.get(Calendar.HOUR_OF_DAY)))
                    return true;
                break;
                
            case "DayOfTheWeek": 
                if (contains(dateParts, calendar.get(Calendar.DAY_OF_WEEK)))
                    return true;
                break;
                
            case "Month": 
                if (contains(dateParts, calendar.get(Calendar.MONTH) + 1))
                    return true;
        }
        
        return false;
    }
    
    public boolean contains(int[] container, int item) {
        for(int containterItem : container) {
            if(containterItem == item)
                return true;
        }
        
        return false;
    }
}