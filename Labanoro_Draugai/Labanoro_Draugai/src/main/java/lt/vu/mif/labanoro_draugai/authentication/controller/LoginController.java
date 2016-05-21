package lt.vu.mif.labanoro_draugai.authentication.controller;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.MessageFormat;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lt.vu.mif.labanoro_draugai.authentication.FBGraph;

import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author Ernest J
 */
@Stateless
public class LoginController {

    @Inject
    private DatabaseManager db;
    
    public LoginController() {
    }

    public Boolean login(String email, String facebookId, String firstName, String lastName) {

        if (isFbUser(email, facebookId)) {
            return true;
        } else {
            registerUser(email, facebookId, firstName, lastName);

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

    // Facebook user registration [check by email and facebookId] 
    public void registerUser(String email, String facebookId, String firstName, String lastName) {

        if (!isUser(email)) {
            Person person = db.addPerson(email, null, null, "Person.Candidate");

            SecureRandom random = new SecureRandom();
            String password = new BigInteger(130, random).toString(32);

            String passwordHash = Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
            String output = MessageFormat.format("{0} hashed to: {1}", password, passwordHash);
            System.out.println(output);

            person.setFacebookid(facebookId);
            person.setFacebookpassword(password);
            person.setPassword(passwordHash);
            person.setFirstname(firstName);
            person.setLastname(lastName);
        }
    }

    public String getRegisteredUserP(String email, String facebookId) {

        if (isFbUser(email, facebookId)) {
            Person user = (Person) db.getEntity("Person", "Email", email);
            return user.getFacebookpassword();
        }
        return null;
    }

}
