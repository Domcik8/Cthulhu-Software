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
public class test {
    
    @Inject
    DatabaseManager dbm;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void countPriorities() {
    List<Person> people = dbm.getAllEntities("Person");
        for (Person person : people) {
            person.setPriority(666);
            dbm.updateEntity(person);
        }
    }
}
