/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.labanoro_draugai.entities.Person;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author NecrQ
 */
@Named("profileListView")
@ViewScoped
public class ProfileListView implements Serializable {

    private List<Person> persons;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        if (persons == null || persons.isEmpty()) {
            this.persons = em.createNamedQuery("Person.findAll").getResultList();
        }
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

}
