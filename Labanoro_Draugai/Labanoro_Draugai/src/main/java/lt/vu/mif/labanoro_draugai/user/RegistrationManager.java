/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.data_models.UserFormProperty;
import lt.vu.mif.labanoro_draugai.entities.Formattribute;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Type;
import lt.vu.mif.labanoro_draugai.entities.Personregistrationform;
import net.sf.json.JSONObject;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author Karolis
 */
@Named
@ViewScoped
public class RegistrationManager implements Serializable{
    
    private DynaFormModel displayModel;
    private String email;
    private String password;
    private String passwordConfirm;
    private String termsAndConditions;
    @AssertTrue
    private boolean isAgreeingToTerms;
    
    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init(){
        displayModel = new DynaFormModel();
        List<Formattribute> attributes = (List<Formattribute>)dbm.getAllEntities("Formattribute");
        if(attributes == null) return;
        
        termsAndConditions = "Būkite geri ir draugiški!";
        
        for(Formattribute attribute:attributes){
            DynaFormRow row = displayModel.createRegularRow();
            DynaFormLabel label = row.addLabel(attribute.getName()); 
            DynaFormControl control = row.addControl(new UserFormProperty(attribute.getName(),attribute.getIsrequired(),parseSelectValues(attribute.getListitems())),
                    attribute.getTypeid().getInternalname());
             label.setForControl(control);
        }
    } 
    
    public String submitRegistration(){
        if(!password.equals(passwordConfirm)) return null;
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(!emailValidator.isValid(email)) return null;
        if(dbm.getEntity("Person", "Email", email)!=null) return null;
        JSONObject jsonObject = new JSONObject();
        for(DynaFormControl control:displayModel.getControls()){
            jsonObject.element(control.getKey(), control.getData());
        }
        Person person = new Person();
        person.setEmail(email);
        person.setIsdeleted(false);
        person.setPassword(password);
        person.setPoints(0);
        person.setPriority(0);
        person.setTypeid((Type)dbm.getEntity("Type", "internalname", "Person.Candidate"));
        Personregistrationform regInfo = new Personregistrationform();
        regInfo.setFormvalue(jsonObject.toString());
        regInfo.setInternalname(email);
        regInfo.setIsdeleted(false);
        regInfo.setPersonid(person);
        regInfo.setTypeid((Type)dbm.getEntity("Type", "internalname", "Person.Form"));
        person.setPersonregistrationform(regInfo);
        List persistList = new ArrayList<>();
        persistList.add(person);
        persistList.add(regInfo);
        if(!dbm.persistAndFlushList(persistList)) return null;
        return "/user/login?faces-redirect=true";
    }
    
    private List<SelectItem> parseSelectValues(String selectionValues){
        if(selectionValues == null || selectionValues.isEmpty()) return null;
        List<SelectItem> result = new ArrayList<>();
        String[] values = selectionValues.split(",");
        for(String str:values){
            System.out.println(str.trim());
            result.add(new SelectItem(str.trim()));
        }
        return result;
    }
    
    public DynaFormModel getDisplayModel() {
        return displayModel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public boolean isIsAgreeingToTerms() {
        return isAgreeingToTerms;
    }

    public void setIsAgreeingToTerms(boolean isAgreeingToTerms) {
        this.isAgreeingToTerms = isAgreeingToTerms;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }
}