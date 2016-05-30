/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.priorityGroup;

import java.util.Calendar;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author Dominik Lisovski
 */

@Named
@Stateless
public class priorityGroupUtils {
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    public Boolean canPersonReserveHouses(Person person) {
       
       String lastCountDateSysParam = dbm.getSystemParameter("SystemParameter.priorityGroup.LastCountDate").getValue();
       
       String[] lastCountDateParts = lastCountDateSysParam.split(",");
       int[] dateParts = new int[lastCountDateParts.length];
       
       if (dateParts.length != 4) {
           return true;
       }
       
       Calendar currentDate = Calendar.getInstance();
       
       Calendar lastCountDate = Calendar.getInstance();
       lastCountDate.set(Calendar.YEAR, dateParts[0]);
       lastCountDate.set(Calendar.MONTH, dateParts[1]);
       lastCountDate.set(Calendar.DAY_OF_MONTH, dateParts[2]);
       lastCountDate.set(Calendar.HOUR_OF_DAY, dateParts[3]);
       
       if (currentDate.compareTo(lastCountDate) <= 0)
           return true;
       
       int weekAfterLastCount = (int) ((currentDate.getTimeInMillis() - lastCountDate.getTimeInMillis()) / 100 / 60 / 60 / 24 / 7);
       
       if (person.getPriority() <= weekAfterLastCount)
           return true;
       return false;
    }
}