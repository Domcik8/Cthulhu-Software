/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication;

import lt.vu.mif.entities.Person;
/*
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
*/
/**
 *
 * @author AlaNote
 */
public class FacebookProvider {
    //@Value("${facebook.appId}")
    private String apiKey;
    //@Value("${facebook.appSecret}")
    private String appSecret;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    /**
     * Create Facebook template for currently logged-in user
     *
     * @return
     */
 /*   public FacebookTemplate createTemplate(Person user) {
        if (user.getFacebookAccessToken() != null) {
            return new FacebookTemplate(user.getFacebookAccessToken());
        } else {
            return null;
        }
    }*/
}
