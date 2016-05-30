/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.priorityGroup;

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
import lt.vu.mif.labanoro_draugai.priorityGroup.Manager.IPriorityGroupManager;

/**
 *
 * @author Dominik Lisovski
 */

@Named
@Stateless
public class priorityGroupUtils {
    
   /* @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    public Boolean canPersonReserveHouses(Person person) {
       
       String lastCountDateSysParam = dbm.getSystemParameter("SystemParameter.priorityGroup.LastCountDate").getValue();
       
       
       
       
       
       
       String[] lastCountDateParts = lastCountDateSysParam.split(",");
       int[] dateParts = new int[lastCountDateParts.length];
       
       if (dateParts.)
       
       
       for (int datePart : monthParts) {
           if (datePart < Calendar.MONTH)
               dateParts.
       }
       
       
    }*/
}