/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.priorityGroup.Manager;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
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
public class ReservationDaysPriorityGroupManager implements IPriorityGroupManager {
    
    @Inject
    DatabaseManager dbm;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void countPriorities() {
        Systemparameter seasonLengthSysParam = dbm.getSystemParameter("SystemParameter.priorityGroup.SeasonLength");
        int seasonLength = Integer.parseInt(seasonLengthSysParam.getValue());
        int priorityGroupThreshhold = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -seasonLength);
        
        List<Reservation> reservations = dbm.getEntityList("Reservation", "startdate", calendar.getTime());
        List<Person> people = dbm.getAllEntities("Person");
        
        Systemparameter numberOfPriorityGroupsSysParam = dbm.getSystemParameter("SystemParameter.priorityGroup.SeasonLength");
        int numberOfPriorityGroups = Integer.parseInt(numberOfPriorityGroupsSysParam.getValue());
        
        if (numberOfPriorityGroups > 0)
            priorityGroupThreshhold = reservations.size() / numberOfPriorityGroups;
        
        for (Person person : people) {
            if (priorityGroupThreshhold > 0)
                person.setPriority(person.getReservationList().size() / priorityGroupThreshhold);
            else person.setPriority(0);
        }
    }
}
