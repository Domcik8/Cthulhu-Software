package lt.vu.mif.labanoro_draugai.mailService;

import java.util.Date;
import javax.annotation.Resource;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingException;

/**
 *
 * @author Ernest J
 */
@Named
@RequestScoped
public class EmailBean {

    @Resource(lookup = "EMailME")
    private Session mailSession;

    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = new MimeMessage(mailSession);
        try {

            message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
