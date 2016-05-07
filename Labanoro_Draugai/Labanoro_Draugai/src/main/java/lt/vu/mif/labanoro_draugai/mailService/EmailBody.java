package lt.vu.mif.labanoro_draugai.mailService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author Ernest J
 */
@Stateless
public class EmailBody {

    @Inject
    private ConfirmationLink link;

    public EmailBody() {
    }

    public String getRegistrationConfirmationMessage(String confirmationLink) {

        String message = "Sveiki,";
        message += "<br/> Sveikiname uzsiregistravus <b>Labanoro draugai</b> klube";
        message += "<br/> Dabar jums reikia surinkti bent " + getNeededConfirmationNumber() + " rekomendacijos is esamu nariu, kad galetume tapti pilnaverciu klubo nariu!";
        message += "<br/> Noredami patvirtinti savo registracija paspauskite zemiau esancia nuoroda";
        message += "<br/> http://localhost:8080/Labanoro_Draugai/confirm?user=admin" + "&confirm=" + confirmationLink;
        message += "<br/> Jeigu jus nevykdete sitos registracijos - ignoruokite laiska";      
        message += "<br/> Pagarbiai,";
        message += "<br/> \"Labanoro draugai\" administracija";
        
        return message;
    }
    
    public String getEmailConfirmationMessage(Person person) {
        
        String message = "Sveiki,";
        message += "<br/> Noredami patvirtinti savo el pasta, paspauskite zemiau esancia nuoroda <br/>";
        message += generateConfirmationLink();
        message += "<br/> ";
        
        return message;
    }

    public String getCandidateConfirmationMessage() {

        String message = "";

        return message;
    }

    public String getCandidateRecommendationRequestMessage() {

        String message = "";

        return message;
    }

    private String generateConfirmationLink() {

        return link.generateUniqueKey();
    }
    
    private String getNeededConfirmationNumber() {
        
        String number = (String) "2";
        
        return number;
    }
}
