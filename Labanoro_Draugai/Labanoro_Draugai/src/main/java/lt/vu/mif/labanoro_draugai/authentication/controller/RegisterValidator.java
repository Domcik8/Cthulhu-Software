/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import lt.vu.mif.labanoro_draugai.entities.Person;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Ernest J
 */
public class RegisterValidator {
  
/*
    
    Need to write validation logic for user input ? 
    Like password strength check, etc...
    If user doesn't exist?
    If email is not already registered? 
    
    @Override
    public void validate(Object obj, Errors errors) {
        RegisterForm regForm = (RegisterForm) obj;
        // perform bean validation according to annotations
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "field.required");

        if (StringUtils.isNotBlank(regForm.getUserName())) {
            Person user = null;//userService.findByName(regForm.getUserName());

            if (user != null && StringUtils.isNotEmpty(user.getPassword())) {
                errors.rejectValue("userName", "user.alreadyRegistered");
            }
        }

        if (!StringUtils.equals(regForm.getPassword(), regForm.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "passwordsNotSame");
        }
    }*/
}
