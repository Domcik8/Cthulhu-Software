/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author Karolis
 */
@FacesValidator("emailValidator")
public class MyEmailValidator implements Validator {

    @Inject
    DatabaseManager dbm;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String email = (String) value;

        if (email == null) {
            return; // Just ignore and let required="true" do its job.
        }

        Person reciever = (Person) dbm.getEntity("Person", "Email", email);
        if (reciever == null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_FATAL, "Klubo nario su tokiu elektroniniu paštu nėra.", "Klubo nario su tokiu elektroniniu paštu nėra."));
        }
        
        if(reciever.getTypeid().getInternalname().equalsIgnoreCase("Person.Candidate")){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_FATAL, "Kandidatas.", "Elektroninis paštas turi būti klubo nario, o ne kandidato."));
        }
    }
}
