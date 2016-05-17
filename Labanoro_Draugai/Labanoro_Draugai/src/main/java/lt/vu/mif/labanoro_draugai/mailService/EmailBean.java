package lt.vu.mif.labanoro_draugai.mailService;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
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
    private DatabaseManager dbm;

    public void testMethod() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        Person person = (Person) dbm.getEntity("Person", "Email", "necrqlt@gmail.com");
        Person requestor = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());
//        sendRegisterConfirmationMessage(person);
        sendCandidateRecommendationRequestMessage(person, requestor);
//        sendEmailConfirmationMessage(person);
//        sendCandidateApprovalMessage(person);
//        sendAccountDeactivationMessage(person);
//        sendCandidateInventationMessage(person);
    }

    public void sendRegisterConfirmationMessage(Person person) {

        String to = person.getEmail();
        String subject = "\"Labanoro draugai\" klubo registracija";
        sendEmail(to, subject, body.getRegistrationConfirmationMessage(person));

        //start time out for confirmation link
    }

    public void sendEmailConfirmationMessage(Person person) {

        sendEmail(person.getEmail(), "test email confirm", body.getEmailConfirmationMessage(person));
    }

    public void sendCandidateApprovalMessage(Person person) {

        sendEmail(person.getEmail(), "candidate approved test", body.getCandidateApprovalMessage(person));
    }

    public void sendAccountDeactivationMessage(Person person) {

        sendEmail(person.getEmail(), "account deactivation test", body.getDeactivationAccountMessage(person));
    }

    public void sendCandidateRecommendationRequestMessage(Person receiver, Person requestor) {

        sendEmail(receiver.getEmail(), "recommend newbie test", body.getCandidateRecommendationRequestMessage(receiver, requestor));
    }
    
    public void sendCandidateRecommendationRequestMessage(String receiver, String requestor) {
        
        Person receiverPerson = (Person) dbm.getEntity("Person", "Email", receiver);
        Person requestorPerson = (Person) dbm.getEntity("Person", "Email", requestor);
        
        if (receiverPerson != null && requestorPerson != null) {
            sendEmail(receiver, "recommend newbie test", body.getCandidateRecommendationRequestMessage(receiverPerson, requestorPerson));
        }
    }
    
    public void sendCandidateInventationMessage(Person receiver) {
//        sendEmail(receiver.getEmail(), "recommend newbie test", body.getCandidateInventationMessage(receiver));
    }

    private void sendEmail(String to, String subject, String body) {

        final String fromEmail = dbm.getSystemParameter("SystemParameter.Mail.Address").getValue();
        final String emailPassword = dbm.getSystemParameter("SystemParameter.Mail.Password").getValue();

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", dbm.getSystemParameter("SystemParameter.Mail.Smtp.host").getValue());
        properties.put("mail.smtp.port", dbm.getSystemParameter("SystemParameter.Mail.Smtp.port").getValue());
        properties.put("mail.smtp.auth", dbm.getSystemParameter("SystemParameter.Mail.Smtp.auth").getValue());
        properties.put("mail.smtp.starttls.enable", dbm.getSystemParameter("SystemParameter.Mail.Smtp.starttls.enable").getValue());

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
