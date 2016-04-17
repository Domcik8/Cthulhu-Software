/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.service;

import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
*/

/**
 *
 * @author AlaNote
 */
public class UserServiceImpl {

   // UserDao userDao;
    DatabaseManager databaseManager;

    //@Override
/*    public Person findByName(String userName) {
        return userDao.findByName(userName);
    }

    /**
     * method to support UserDetailsService interface
     *
     */
    //@Override
/*    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
       Person user = userDao.findByName(userName);

        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }

        org.springframework.security.core.userdetails.User userDetails
                = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                        true, true, true, true,
                        new ArrayList<GrantedAuthority>());

        return userDetails;

    }*/


/*  public void createNewUser(String userName, String password) {
        Person user = new Person();
        user.setUsername(userName);
        user.setPassword(password);

        userDao.saveOrUpate(user);
    }

    /**
     * Update Facebook connection information
     *
     * @param userName
     * @param accessToken
     * @param facebookUserId
     */
/*    public void updateFacebookAuthentication(String userName, String accessToken, String facebookUserId) {
        Person user = userDao.findByUsername(userName);
        user.setFacebookAccessToken(accessToken);
        user.setFacebookid(facebookUserId);

        userDao.saveOrUpate(user); //userDao need to change to "DatabaseManager" call handler to entities.
    }*/

    
}
