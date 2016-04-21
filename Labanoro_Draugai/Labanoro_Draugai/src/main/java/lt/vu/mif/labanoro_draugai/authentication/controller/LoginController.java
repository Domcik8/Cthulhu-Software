/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
/**
 *
 * @author NecrQ
 */
@Stateless
public class LoginController {
    
    @Inject 
    private DatabaseManager db;
    
    public LoginController() {}
    
    public Boolean isUser(String email) {
          
        if (db.entityExists("Person", "Email", email)) {
            return true; 
        } 
        return false;
    }
    
    public void RegisterUser(String email) {
        
        db.addPerson(email, null, null, "Person.Candidate");
    }
    
    public void RegisterUser(String email, String firstName, String lastName) {
        
        db.addPerson(email, firstName, lastName, "Person.Candidate");
    }
    
}
