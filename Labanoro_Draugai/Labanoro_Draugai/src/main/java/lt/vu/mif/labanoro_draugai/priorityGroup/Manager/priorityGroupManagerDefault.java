/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.priorityGroup.Manager;

import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;

/**
 *
 * @author Dominik Lisovski
 */

@Named
@Stateless
public class priorityGroupManagerDefault implements IPriorityGroupManager {
    
    @Inject
    DatabaseManager dbm;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void countPriorities() {
        Random random = new Random();
        int tryCount;
        
        Systemparameter numberOfPriorityGroupsSysParam = dbm.getSystemParameter("SystemParameter.priorityGroup.Quantity");
        int numberOfPriorityGroups = Integer.parseInt(numberOfPriorityGroupsSysParam.getValue());
        
        List<Person> people = dbm.getAllEntities("Person");
        
        if (numberOfPriorityGroups > 0) {
            for (Person person : people) {
                tryCount = 3;
                
                while (tryCount > 0) {
                    person.setPriority(random.nextInt(numberOfPriorityGroups));
                    if(dbm.updateEntity(person) == null) {
                        person = (Person) dbm.getEntity("Person", "Email", person.getEmail());
                    } else
                        break;
                    tryCount--;
                }
            }
        } else {
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
        }
    }
}