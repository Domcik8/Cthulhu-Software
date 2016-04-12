/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import lt.vu.mif.entities.Person;
import lt.vu.mif.labanoro_draugai.authentication.service.UserService;

import org.apache.commons.lang3.StringUtils;
/*
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
*/
/**
 *
 * @author AlaNote
 */
public class RegisterValidator {//implements Validator{
    UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    //@Override
    public boolean supports(Class<?> clazz) {
        return RegisterForm.class.equals(clazz);
    }
/*
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
