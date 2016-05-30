/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.validators;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Karolis
 */
@FacesValidator("oldPasswordValidator")
public class OldPasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = (String) value;
        String confirm = (String) component.getAttributes().get("confirm");
        
        if (password == null || confirm == null) {
            return; // Just ignore and let required="true" do its job.
        }
        
        password =  Hashing.sha256().hashString(
            Hashing.sha256().hashString(password, Charsets.UTF_8).toString(), Charsets.UTF_8
        ).toString();
        if (!password.equals(confirm)) {
            throw new ValidatorException(new FacesMessage("Passwords are not equal."));
        }
    }

}
