package lt.vu.mif.labanoro_draugai.mailService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;

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
        message += "<br/> Norėdami paprašyti esamų narių, kad jus rekomenduotų, pereikite į žemiau pateiktą nuorodą ir pasirinkite kam išsiųsti prašymą";
        message += "<br/> Link: " + getContextPath() + "/profiles";

        message += "<br/> Jeigu jūs nevykdėte šitos registracijos - ignoruokite laišką";
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
            message += "<br/> Norėdami patvirtinti savo el. paštą, paspauskite žemiau esančią nuorodą <br/>";
            message += "<br/> " + getContextPath() + "/confirm?key=" + receiver.getEmailconfirmation();
        }

        message += "<br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getCandidateApprovalMessage(Person receiver) {

        String message = "Sveiki, " + receiver.getFirstname();
        message += "<br/><br/> Sveikiname tapus pilnaverčiu klubo nariu!";
        message += "<br/> Jūs surinkote pakankamai rekomendacijų, todėl jums buvo suteiktas <b>Nuolatinio</b> vartotojo statusas";
        message += "<br/> Sumokėjus metinį narystės mokestį, jums bus suteikta teisė atlikti rezervacijas išskirtinėmis sąlygomis";

        message += "<br/><br/> Linkime sėkmės,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    public String getDeactivationAccountMessage(Person receiver) {

        String message = "Sveiki, " + receiver.getFirstname();
        message += "<br/><br/> Norime informuoti, kad jūs buvote pasirinkę išsiregistravimą iš \"Labanoro draugai\" klubo";
        message += "<br/> Jūsų paskyra " + receiver.getEmail() + " taps nepasiekiama po" + " 5 dienų";

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
        message += "<br/> Norėdami prisijungti pereikite į žemiau esančią nuorodą ir užpildykite registracijos anketą.";

        message += "<br/><br/>" + getContextPath() + "/register.html?referral=" + requestor.getUniquekey();

        message += "<br/><br/> Jeigu nepažįstate aukščiau minėto žmogaus, ignoruokite šį laišką.";

        message += "<br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }
    
    public String getPaymentApprovementMessage(String receiver, Payment payment) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currency;
        Systemparameter param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Currency.Euro");
        if (param == null) {
            currency = "?";
        }
        else {
            currency = param.getValue();
        }
        
        String message = "Sveiki, ";
        message += "<br/><br/> Norime pranešti, kad gavome jūsų mokėjimą!";
        message += "<br/><br/> Mokėjimo suma: <b>" + payment.getPaymentprice() + " " + currency + " </b>";
        message += "<br/> Mokėjimo data ir laikas: <b>" + dateFormat.format(payment.getPaymentdate()) + " </b>";
        
        message += "<br/><br/> Džiaugiamės, kad naudojatės mūsų paslaugomis!";

        message += "<br/><br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";

        return message;
    }

    private void validateEmailConfirmationKey(Person person) {

        String emailKey = person.getEmailconfirmation();
        if (emailKey == null || emailKey.equals("")) {
            person.setEmailconfirmation(generateConfirmationLink());
            //dbm.updateEntity(person); Optimistic lock exception
        }
    }

    private void validateUniqueKey(Person person) {

        String reqKey = person.getUniquekey();
        if (reqKey == null || reqKey.equals("")) {
            person.setUniquekey(generateConfirmationLink());
            //dbm.updateEntity(person); Optimistic lock exception
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
