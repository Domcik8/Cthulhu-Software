/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author NecrQ
 */
@Stateless
public class LoginController {

    @Inject
    private DatabaseManager db;

    public LoginController() {
    }

    public Boolean login(String email, String facebookId) {

        if (isFbUser(email, facebookId)) {
            return true;
        } else {
            registerUser(email, facebookId);

            if (isFbUser(email, facebookId)) {
                return true;
            }
        }

        return false;
    }

    public Boolean isFbUser(String email, String facebookId) {

        if (isUser(email)) {

            Person user = (Person) db.getEntity("Person", "Email", email);
            if (user.getFacebookid().equals(facebookId)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Boolean isUser(String email) {

        if (db.entityExists("Person", "Email", email)) {
            return true;
        }
        return false;
    }

    // Simple user registration [check by email]
    public void registerUser(String email) {

        if (!isUser(email)) {
            db.addPerson(email, null, null, "Person.Candidate");
        }
    }

    // Facebook user registration [check by email and facebookId] 
    public void registerUser(String email, String facebookId) {

        if (!isUser(email)) {
            Person person = db.addPerson(email, null, null, "Person.Candidate");
            person.setFacebookid(facebookId);
        }
    }

    public void registerUser(String email, String firstName, String lastName) {

        db.addPerson(email, firstName, lastName, "Person.Candidate");
    }

}
