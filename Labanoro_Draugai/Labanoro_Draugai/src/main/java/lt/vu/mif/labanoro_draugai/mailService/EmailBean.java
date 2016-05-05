package lt.vu.mif.labanoro_draugai.mailService;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.NamingException;

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

    public void sendRegisterConfirmationMessage() {

        String to = "";             // nurodyti adresat'a, kam bus nusiustas emailas
        String subject = "test";    // nurodyti laisko "Title"
        sendEmail(to, subject, body.getRegistrationConfirmationMessage());
    }

    private void sendEmail(String to, String subject, String body) {

        final String fromEmail = "";            // get from administration settings
        final String emailPassword = "";        // get from administration settings

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
}
