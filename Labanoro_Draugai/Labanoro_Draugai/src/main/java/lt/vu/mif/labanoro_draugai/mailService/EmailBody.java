package lt.vu.mif.labanoro_draugai.mailService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author Ernest J
 */
@Stateless
public class EmailBody {

    @Inject
    private ConfirmationLink link;

    @Inject
    private DatabaseManager dbm;

    public EmailBody() {
    }

    public String getRegistrationConfirmationMessage(Person receiver) {

        validateEmailConfirmationKey(receiver);
        validateUniqueKey(receiver);

        String message = "Sveiki, " + receiver.getFirstname();
        message += "<br/> Sveikiname uzsiregistravus <b>Labanoro draugai</b> klube";

        message += "<br/> Noredami patvirtinti savo registracija paspauskite zemiau esancia nuoroda";
        message += "<br/> " + getContextPath() + "/confirm?key=" + receiver.getEmailconfirmation();

        message += "<br/> Dabar jums reikia surinkti bent " + getMinimumRecommendationNumber() + " rekomendaciju is esamu nariu, kad galetume tapti pilnaverciu klubo nariu!";
        message += "<br/> Maksimalus rekomendaciju skaicius - " + getMaximumRecommendationNumber();
        message += "<br/> Noredami paprasyti esamu nariu, kad jus rekomenduotu, pereikite zemiau pateikta nuoroda ir pasirinkite kam issiusti prasyma";
        message += "<br/> Link: " + getContextPath() + "/profiles";

        message += "<br/> Jeigu jus nevykdete sitos registracijos - ignoruokite laiska";
        message += "<br/><br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getEmailConfirmationMessage(Person receiver) {

        validateEmailConfirmationKey(receiver);

        String message = "Sveiki, " + receiver.getFirstname();

        if (receiver.getEmailconfirmation().equals("validated")) {
            message += "<br/> Jusu el.pastas jau yra patvirtintas";
        } else {
            message += "<br/> Noredami patvirtinti savo el pasta, paspauskite zemiau esancia nuoroda <br/>";
            message += "<br/> " + getContextPath() + "/confirm?key=" + receiver.getEmailconfirmation();
        }

        message += "<br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getCandidateApprovalMessage(Person receiver) {

        String message = "Sveiki, " + receiver.getFirstname();
        message += "<br/> Sveikiname tapus pilnaverciu klubo nariu!";
        message += "<br/> Jus surinkote pakankamai rekomendaciju, todel jums buvo suteiktas <b>Nuolatinio</b> vartotojo statusas";
        message += "<br/> Sumokejus metini narystes mokesti, jums bus suteikta atlikti rezervavimus isskirtinemis salygomis pasirinktu laikotarpiu";

        message += "<br/><br/> Linkime sekmes,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getDeactivationAccountMessage(Person receiver) {

        String message = "Sveiki, " + receiver.getFirstname();
        message += "<br/> Norime informuoti, kad jus buvot pasirinke issiregistravima is \"Labanoro draugai\" klubo";
        message += "<br/> Jusu paskyra " + receiver.getEmail() + " taps nepasiekiama po" + " 5 dienu";

        message += "<br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getCandidateRecommendationRequestMessage(Person receiver, Person requestor) {

        validateUniqueKey(requestor);

        String message = "Sveiki, " + receiver.getFirstname();
        message += "<br/> Neseniai uzsiregistravo naujas klubo narys " + requestor.getFirstname() + " " + requestor.getLastname();
        message += "<br/> Jeigu norite suteikti sitam kandidatui savo rekomendacija, prasom pereiti zemiau esancia nuoroda";
        message += "<br/>" + getContextPath() + "/recommend?user=" + requestor.getUniquekey();

        message += "<br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    private void validateEmailConfirmationKey(Person person) {

        String emailKey = person.getEmailconfirmation();
        if (emailKey == null || emailKey.equals("")) {
            person.setEmailconfirmation(generateConfirmationLink());
            dbm.updateEntity(person);
        }
    }

    private void validateUniqueKey(Person person) {

        String reqKey = person.getUniquekey();
        if (reqKey == null || reqKey.equals("")) {
            person.setUniquekey(generateConfirmationLink());
            dbm.updateEntity(person);
        }
    }

    private String generateConfirmationLink() {

        return link.generateUniqueKey();
    }

    private String getMinimumRecommendationNumber() {

        return dbm.getSystemParameter("SystemParameter.RequiredRecommendations").getValue();
    }

    private String getMaximumRecommendationNumber() {

        return dbm.getSystemParameter("SystemParameter.MaxRecommendations").getValue();
    }

    private String getContextPath() {

        return dbm.getSystemParameter("SystemParameter.General.ContextPath").getValue();
    }
}
