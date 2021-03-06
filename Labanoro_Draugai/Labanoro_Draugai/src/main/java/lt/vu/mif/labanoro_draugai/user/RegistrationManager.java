/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.AssertTrue;
import lt.vu.mif.labanoro_draugai.authentication.FBGraph;
import lt.vu.mif.labanoro_draugai.authentication.controller.RegistrationController;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.data_models.UserFormProperty;
import lt.vu.mif.labanoro_draugai.entities.Formattribute;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Type;
import lt.vu.mif.labanoro_draugai.entities.Personregistrationform;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import lt.vu.mif.labanoro_draugai.mailService.EmailBean;

import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.simple.JSONObject;

/**
 *
 * @author Karolis
 */
@Named
@ViewScoped
public class RegistrationManager implements Serializable {

    @Inject
    private FBGraph fbGraph;
    
    private DynaFormModel displayModel;
    private String email;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String termsAndConditions;
    private String referral;
    private String accessToken;

    @AssertTrue
    private boolean isAgreeingToTerms;

    @Inject
    DatabaseManager dbm;

    @Inject
    EmailBean emailBean;

    @Inject
    RegistrationController regController;

    @PostConstruct
    public void init() {

        referral = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referral");
        accessToken = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("accessToken");

        if (referral != null) {
            System.out.print("Opened with referral: referral=" + referral);
        }

        if (accessToken != null) {
            email = fbGraph.getEmail(accessToken);
            firstName = fbGraph.getFirstName(accessToken);
            lastName = fbGraph.getLastName(accessToken);
            System.out.print("Opened after facebook registration=" + accessToken);
        } else {
            System.out.print("[REG MANAGER] Facebook token =" + accessToken);
        }

        displayModel = new DynaFormModel();
        List<Formattribute> attributes = (List<Formattribute>) dbm.getAllEntities("Formattribute");
        if (attributes == null) {
            return;
        }

        termsAndConditions = ((Systemparameter) dbm.getEntity("Systemparameter", "Internalname", "SystemParameter.TermsAndConditions")).getValue();

        for (Formattribute attribute : attributes) {
            DynaFormRow row = displayModel.createRegularRow();
            DynaFormLabel label = row.addLabel(attribute.getName());
            DynaFormControl control = row.addControl(new UserFormProperty(attribute.getName(), attribute.getIsrequired(), parseSelectValues(attribute.getListitems())),
                    attribute.getTypeid().getInternalname());
            label.setForControl(control);
        }
        System.out.println(toString() + " constructed.");
    }

    public String submitRegistration() {

        if (!password.equals(passwordConfirm)) {
            return "";
        }

//        EmailValidator emailValidator = EmailValidator.getInstance();
//
//        if (!emailValidator.isValid(email)) {
//            return null;
//        }

        JSONObject jsonObject = new JSONObject();
        for (DynaFormControl control : displayModel.getControls()) {
            UserFormProperty ufp = (UserFormProperty) control.getData();
            if (ufp.getValue() instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jsonObject.put(ufp.getName(), sdf.format(ufp.getValue()));
            } else {
                jsonObject.put(ufp.getName(), ufp.getValue());
            }
        }
        String facebookId = (String) fbGraph.getIdEmail(accessToken).get("id");
        Person person = regController.registerUser(email, facebookId, firstName, lastName, password);
        //Person person = dbm.addPerson(email, password, firstName, lastName, "Person.Candidate");
        person.setPoints(new BigDecimal(0));
        person.setPriority(0);
        person.setMembershipdue(new Date());
        person.setRecommendationstosend(0);
        Personregistrationform regInfo = new Personregistrationform();
        regInfo.setFormvalue(jsonObject.toString());
        regInfo.setInternalname(email);
        regInfo.setIsdeleted(false);
        regInfo.setPersonid(person);
        regInfo.setTypeid((Type) dbm.getEntity("Type", "internalname", "Form.Person"));
        person.setPersonregistrationform(regInfo);

        if (referral != null) {
            Person referringUser = (Person) dbm.getEntity("Person", "UniqueKey", referral);
            if (referringUser != null) {
                dbm.addRecommendation(referringUser.getEmail(), person.getEmail(), new Date(), "Recommendation");
                person.setRecommendationsreceived(person.getRecommendationsreceived() + 1);
            }
        }
        if (!dbm.persistAndFlush(regInfo)) {
            return null;
        }

        emailBean.sendRegisterConfirmationMessage(person);
        person = (Person) dbm.updateEntity(person, false); //Pakeitimai del optimistic locking
        
        return (dbm.getSystemParameter("SystemParameter.Redirect.Login").getValue() + "?faces-redirect=true");
    }

    private List<SelectItem> parseSelectValues(String selectionValues) {

        if (selectionValues == null || selectionValues.isEmpty()) {
            return null;
        }
        List<SelectItem> result = new ArrayList<>();
        String[] values = selectionValues.split(",");
        for (String str : values) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
