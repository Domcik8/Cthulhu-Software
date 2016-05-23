/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.MessageFormat;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author Ernest J
 */
@Stateless
public class RegistrationController {

    @Inject
    private DatabaseManager db;

    public void RegistrationController() {
    }

    public Person registerUser(String email, String facebookId, String firstName, String lastName) {

        return registerUser(email, facebookId, firstName, lastName, null);
    }

    public Person registerUser(String email, String facebookId, String firstName, String lastName, String password) {

        if (!isUser(email)) {
            Person person = db.addPerson(email, null, null, "Person.Candidate");

//            SecureRandom random = new SecureRandom();
//            String password = new BigInteger(130, random).toString(32);
            if (password != null) {
                String passwordHash = Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
                String output = MessageFormat.format("{0} hashed to: {1}", password, passwordHash);
                System.out.println(output);
                person.setPassword(passwordHash);
            }

            if (facebookId != null) {
                person.setFacebookid(facebookId);
                person.setFacebookpassword(password);
            }

            person.setFirstname(firstName);
            person.setLastname(lastName);
            person.setRecommendationstosend(0);

            return person;
        }

        return null;
    }

    private Boolean isUser(String email) {

        if (db.entityExists("Person", "Email", email)) {
            return true;
        }
        return false;
    }
}
