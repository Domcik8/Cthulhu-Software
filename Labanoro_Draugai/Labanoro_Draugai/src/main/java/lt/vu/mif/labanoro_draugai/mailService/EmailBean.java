package lt.vu.mif.labanoro_draugai.mailService;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import javax.mail.*;
import javax.mail.internet.*;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

//import lt.vu.mif.labanoro_draugai.administration.settings;
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

//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//        Person person = (Person) dbm.getEntity("Person", "Email", "user");
//        Person requestor = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());
//        sendRegisterConfirmationMessage(person);
//        sendCandidateRecommendationRequestMessage(person, requestor);
//        sendEmailConfirmationMessage(person);
//        sendCandidateApprovalMessage(person);
//        sendAccountDeactivationMessage(person);
//        sendCandidateInvitationMessage("necrqlt@gmail.com", person);
    }

    public void sendRegisterConfirmationMessage(Person person) {

        String to = person.getEmail();
        String subject = "\"Labanoro draugai\" klubo registracija";
        sendEmail(to, subject, body.getRegistrationConfirmationMessage(person));

        //start time out for confirmation link
    }

    public void sendEmailConfirmationMessage(Person person) {

        sendEmail(person.getEmail(), "Elektroninio pašto patvirtinimas", body.getEmailConfirmationMessage(person));
    }

    public void sendCandidateApprovalMessage(Person person) {

        sendEmail(person.getEmail(), "Kandidato patvirtinimas", body.getCandidateApprovalMessage(person));
    }

    public void sendAccountDeactivationMessage(Person person) {

        sendEmail(person.getEmail(), "Paskyros deaktivavimas", body.getDeactivationAccountMessage(person));
    }

    public void sendCandidateRecommendationRequestMessage(Person receiver, Person requestor) {

        sendEmail(receiver.getEmail(), "Rekomendacijos prašymas", body.getCandidateRecommendationRequestMessage(receiver, requestor));
    }

    public void sendCandidateRecommendationRequestMessage(String receiver, String requestor) {

        Person receiverPerson = (Person) dbm.getEntity("Person", "Email", receiver);
        Person requestorPerson = (Person) dbm.getEntity("Person", "Email", requestor);

        if (receiverPerson != null && requestorPerson != null) {
            sendEmail(receiver, "Rekomendacijos prašymas", body.getCandidateRecommendationRequestMessage(receiverPerson, requestorPerson));
        }
    }

    public void sendCandidateInvitationMessage(String receiver, Person requestor) {
        sendEmail(receiver, "Kvietimas prisijungti prie \"Labanoro draugai\" klubo", body.getCandidateInvitationMessage(receiver, requestor));
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
            MimeMessage mailMessage = new MimeMessage(mailSession);

            mailMessage.setFrom(new InternetAddress(fromEmail));
            InternetAddress[] toAddresses = {new InternetAddress(to)};            // sitoje vietoje galima nurodyti kiek nori addresatu
            mailMessage.setRecipients(Message.RecipientType.TO, toAddresses);

            mailMessage.setSubject(subject, "utf-8");
            mailMessage.setSentDate(new Date());
            mailMessage.setContent(body, "text/html; charset=utf-8");

            Transport.send(mailMessage);

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
