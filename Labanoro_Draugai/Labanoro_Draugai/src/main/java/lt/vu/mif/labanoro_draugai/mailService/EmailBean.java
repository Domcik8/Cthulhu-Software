package lt.vu.mif.labanoro_draugai.mailService;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.NamingException;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

//import lt.vu.mif.labanoro_draugai.administration.settings;
import lt.vu.mif.labanoro_draugai.mailService.EmailBody;

/**
 *
 * @author Ernest J
 */
@Stateless
@Named
public class EmailBean {

    @Inject
    private EmailBody body;

    @Inject
    private ConfirmationLink link;

    @Inject
    private DatabaseManager dbm;

    public void testMethod() {

        Person person = (Person) dbm.getEntity("Person", "Email", "necrqlt@gmail.com");
        sendRegisterConfirmationMessage(person);
    }

    public void sendRegisterConfirmationMessage() {

        String to = "necrqlt@gmail.com";
        String subject = "test";                                // nurodyti laisko "Title"
//        sesndEmail(to, subject, body.getRegistrationConfirmationMessage());

    }

    public void sendRegisterConfirmationMessage(Person person) {

        String to = person.getEmail();
        //to = "necrqlt@gmail.com";
        String subject = "\"Labanoro draugai\" klubo registracija";                                // nurodyti laisko "Title"
        String confirmationLink = generateConfirmationLink();
        person.setEmailconfirmation(confirmationLink);
        sendEmail(to, subject, body.getRegistrationConfirmationMessage(confirmationLink));

        //start time out for confirmation link
    }

    private void sendEmail(String to, String subject, String body) {

        final String fromEmail = "Labanorai@gmail.com";             // get from administration settings
        final String emailPassword = "LabanoroDraugas";             // get from administration settings

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, emailPassword);
            }
        };

        Session mailSession = Session.getInstance(properties, auth);

        try {

            mailSession.setDebug(true);
            Message mailMessage = new MimeMessage(mailSession);

            mailMessage.setFrom(new InternetAddress(fromEmail));
            InternetAddress[] toAddresses = {new InternetAddress(to)};            // sitoje vietoje galima nurodyti kiek nori addresatu
            mailMessage.setRecipients(Message.RecipientType.TO, toAddresses);

            mailMessage.setSubject(subject);
            mailMessage.setSentDate(new Date());
            mailMessage.setContent(body, "text/html");

            Transport.send(mailMessage);

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    private String generateConfirmationLink() {

        return link.generateUniqueKey();
    }
}
