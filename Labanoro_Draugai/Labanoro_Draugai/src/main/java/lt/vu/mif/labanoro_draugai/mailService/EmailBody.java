package lt.vu.mif.labanoro_draugai.mailService;

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
        message += "<br/><br/> Sveikiname užsiregistravus <b>Labanoro draugai</b> klube";

        message += "<br/> Norėdami patvirtinti savo registraciją paspauskite žemiau esančią nuorodą";
        message += "<br/> " + getContextPath() + "/confirm?key=" + receiver.getEmailconfirmation();

        message += "<br/> Dabar jums reikia surinkti bent " + getMinimumRecommendationNumber() + " rekomendacijų iš esamų narių, kad galėtumėte tapti pilnaverčiu klubo nariu!";
        message += "<br/> Maksimalus rekomendacijų skaičius - " + getMaximumRecommendationNumber();
        message += "<br/> Norėdami paprašyti esamų narių, kad jus rekomenduotu, pereikite žemiau pateiktą nuorodą ir pasirinkite kam išsiusti prašymą";
        message += "<br/> Link: " + getContextPath() + "/profiles";

        message += "<br/> Jeigu jus nevykdėte šitos registracijos - ignoruokite laišką";
        message += "<br/><br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getEmailConfirmationMessage(Person receiver) {

        validateEmailConfirmationKey(receiver);

        String message = "Sveiki, " + receiver.getFirstname();

        if (receiver.getEmailconfirmation().equals("validated")) {
            message += "<br/><br/> Jūsų el. paštas jau yra patvirtintas";
        } else {
            message += "<br/> Norėdami patvirtinti savo el. paštą, paspauskite žemiau esančia nuorodą <br/>";
            message += "<br/> " + getContextPath() + "/confirm?key=" + receiver.getEmailconfirmation();
        }

        message += "<br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getCandidateApprovalMessage(Person receiver) {

        String message = "Sveiki, " + receiver.getFirstname();
        message += "<br/><br/> Sveikiname tapus pilnaverčiu klubo nariu!";
        message += "<br/> Jus surinkote pakankamai rekomendacijų, todėl jums buvo suteiktas <b>Nuolatinio</b> vartotojo statusas";
        message += "<br/> Sumokėjus metinį narystės mokestį, jums bus suteikta teisė atlikti rezervacijas išskirtinėmis sąlygomis";

        message += "<br/><br/> Linkime sėkmės,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getDeactivationAccountMessage(Person receiver) {

        String message = "Sveiki, " + receiver.getFirstname();
        message += "<br/><br/> Norime informuoti, kad jus buvote pasirinkę išsiregistravimą iš \"Labanoro draugai\" klubo";
        message += "<br/> Jusu paskyra " + receiver.getEmail() + " taps nepasiekiama po" + " 5 dienu";

        message += "<br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getCandidateRecommendationRequestMessage(Person receiver, Person requestor) {

        validateUniqueKey(requestor);

        String message = "Sveiki, " + receiver.getFirstname();
        message += "<br/> Neseniai užsiregistravo naujas klubo narys <b>" + requestor.getFirstname() + " " + requestor.getLastname() + "</b>";
        message += "<br/> Jeigu norite suteikti šitam kandidatui savo rekomendaciją, prašome pereiti į žemiau esančią nuorodą";
        message += "<br/>" + getContextPath() + "/recommend?user=" + requestor.getUniquekey();

        message += "<br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getCandidateInvitationMessage(String receiver, Person requestor) {

        validateUniqueKey(requestor);

        String message = "Sveiki, ";
        message += "<br/><br/> Kviečiame prisijungti prie \"Labanoro draugai\" klubo";
        message += "<br/> Jus rekomendavo <b>" + requestor.getFirstname() + " " + requestor.getLastname() + "</b>";
        message += "<br/> Norėdami prisijungti, pereikite žemiau esančią nuorodą ir užpildykite registracijos anketą.";

        message += "<br/><br/>" + getContextPath() + "/register.html?referral=" + requestor.getUniquekey();

        message += "<br/><br/> Jeigu nepažįstate aukščiau minėto žmogaus, ignoruokite šį laišką.";

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
