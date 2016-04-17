/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.service;

import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author AlaNote
 */
public interface UserService {

    /**
     * Create new user
     *
     * @param userName
     * @param password
     */
    public abstract void createNewUser(String userName, String password);

    /**
     * Update Facebook connection information
     *
     * @param userName
     * @param accessToken
     * @param facebookUserId
     */
    public abstract void updateFacebookAuthentication(String userName, String accessToken, String facebookUserId);

}
