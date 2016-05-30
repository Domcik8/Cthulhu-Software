/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author Karolis
 */
@FacesValidator("simpleEmailValidator")
public class SimpleEmailValidator implements Validator {
    @Inject
    DatabaseManager dbm;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String email = (String) value;
        
        if (email == null) {
            return; // Just ignore and let required="true" do its job.
        }
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(!emailValidator.isValid(email)) 
            throw new ValidatorException(new FacesMessage("Netinkmas elektroninis paštas."));
    }
}