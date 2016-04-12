/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lt.vu.mif.business.DatabaseManager;


/**
 *
 * @author AlaNote
 */
@ManagedBean(name="personManager")
public class PersonManager {
    

    @PersistenceContext
    EntityManager em;

}
