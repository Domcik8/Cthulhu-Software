/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.priorityGroup.Manager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Dominik Lisovski
 */

@Named
@Stateless
public class testytest {
    
    @Inject 
    IPriorityGroupManager pgm;
    
    public void start()
    {
        int a = 0;
        pgm.countPriorities();
    }
}
