/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.mailService;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.math.BigInteger;
import java.security.SecureRandom;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author NecrQ
 */
@Stateless
public class ConfirmationLink {

    @PersistenceContext
    EntityManager em;

    public ConfirmationLink() {
    }

    public String generateUniqueKey() {

        SecureRandom random = new SecureRandom();
        String key = new BigInteger(130, random).toString(32);

        String keyHash = Hashing.sha256().hashString(key, Charsets.UTF_8).toString();

        return key;
    }
}
