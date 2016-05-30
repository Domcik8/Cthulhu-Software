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
import javax.enterprise.inject.Alternative;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;

/**
 *
 * @author Dominik Lisovski
 */

@Named
@Stateless
@Alternative
public class priorityGroupManagerTest implements IPriorityGroupManager {
    
    @Inject
    DatabaseManager dbm;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void countPriorities() {
        List<Person> people = dbm.getAllEntities("Person");
        int tryCount;

        for (Person person : people) {
            tryCount = 3;

            while (tryCount > 0) {
                person.setPriority(666);
                if(dbm.updateEntity(person) == null) {
                    person = (Person) dbm.getEntity("Person", "Email", person.getEmail());
                } else
                    break;
                tryCount--;
            }
        }
        dbm.setLastCountDate();
    }
}
