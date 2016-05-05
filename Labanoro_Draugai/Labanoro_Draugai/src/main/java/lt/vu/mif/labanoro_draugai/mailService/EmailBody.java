package lt.vu.mif.labanoro_draugai.mailService;

import javax.ejb.Stateless;

/**
 *
 * @author Ernest J
 */
@Stateless
public class EmailBody {

    public EmailBody() {
    }

    public String getRegistrationConfirmationMessage() {

        String message = "Sveiki";
        message += "<br/> Sveikiname uzsiregistravus <b>Labanoro draugai</b> klube <br/>";
        message += "Dabar jums reikia surinkti bent 2 rekomendacijos esamu nariu, kad galetume tapti pilnaverciu klubo nariu!";

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
}
