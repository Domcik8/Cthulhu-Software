/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.data_models.UserFormProperty;
import lt.vu.mif.labanoro_draugai.entities.Formattribute;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Personregistrationform;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import lt.vu.mif.labanoro_draugai.entities.Type;
import lt.vu.mif.labanoro_draugai.mailService.EmailBean;
import lt.vu.mif.labanoro_draugai.reservation.ReservationConfirmationManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

/**
 *
 * @author Karolis
 */
@Named
@ViewScoped
public class ProfileView implements Serializable {

    private Person user;
    private DynaFormModel displayModel;

    private String oldPass;
    private String newPass;
    private String newPassConfirm;
    private String friendEmail;

    @Inject
    DatabaseManager dbm;

    @Inject
    EmailBean emailBean;

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) (ec.getRequest());
        if (request == null || request.getUserPrincipal() == null || request.getUserPrincipal().getName() == null && request.getUserPrincipal().getName().isEmpty()) {
            try {
                ec.redirect("/Labanoro_Draugai/login.html");
                return;
            } catch (IOException ex) {
                Logger.getLogger(ReservationConfirmationManager.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        user = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());

        displayModel = new DynaFormModel();
        List<Formattribute> attributes = (List<Formattribute>) dbm.getAllEntities("Formattribute");
        if (attributes == null) {
            return;
        }

        Personregistrationform regForm = user.getPersonregistrationform();

        JSONParser parser = new JSONParser();
        JSONObject json;
        if (regForm != null && regForm.getFormvalue() != null) {
            try {
                json = (JSONObject) parser.parse(regForm.getFormvalue());
            } catch (org.json.simple.parser.ParseException ex) {
                Logger.getLogger(ProfileView.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        } else {
            json = new JSONObject();
        }
        for (Formattribute attribute : attributes) {
            DynaFormRow row = displayModel.createRegularRow();
            DynaFormLabel label = row.addLabel(attribute.getName());
            Object controlValue = null;
            if (attribute.getTypeid().getInternalname().equals("FormElement.Calendar")) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String unparsedDate = (String) json.get(attribute.getName());
                    if (unparsedDate != null && !unparsedDate.isEmpty()) {
                        controlValue = sdf.parse((String) json.get(attribute.getName()));
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(ProfileView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                controlValue = json.get(attribute.getName());
            }

            DynaFormControl control = row.addControl(
                    new UserFormProperty(
                            attribute.getName(), attribute.getIsrequired(),
                            parseSelectValues(attribute.getListitems()), controlValue
                    ), attribute.getTypeid().getInternalname()
            );
            label.setForControl(control);
        }
        System.out.println(toString() + " constructed.");
    }

    public String formattedMembershipEndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(user.getMembershipdue());
    }

    //Recommendation
    public int recommendationsTillMember() {
        Systemparameter par = (Systemparameter) dbm.getEntity("Systemparameter", "internalName", "SystemParameter.RequiredRecommendations");
        int totalrecomendations = Integer.parseInt(par.getValue());
        return user == null ? totalrecomendations : totalrecomendations - user.getRecommendationsreceived();
    }

    public int remainingRecommendationRequests() {
        Systemparameter par = (Systemparameter) dbm.getEntity("Systemparameter", "internalName", "SystemParameter.MaxRecommendations");
        int totalrecomendations = Integer.parseInt(par.getValue());
        return user == null || user.getRecommendationstosend() == null ? totalrecomendations : totalrecomendations - user.getRecommendationstosend();
    }

    public void askForRecommendation() {
        if (user == null || remainingRecommendationRequests() <= 0) {
            return;
        }
        Person reciever = (Person) dbm.getEntity("Person", "Email", friendEmail);
        if (reciever == null) {
            return;
        }
        emailBean.sendCandidateRecommendationRequestMessage(reciever, user);
        user.setRecommendationstosend(user.getRecommendationstosend() + 1);
        dbm.updateEntity(user);
    }

    public void inviteFriend() {
        System.out.println("Invitation not yet implemented.");
    }

    //renderers
    public String renderRecommendtionInfo() {
        if (!user.getTypeid().getInternalname().equals("Person.Candidate")) {
            return "false";
        }
        return "true";
    }

    public String renderNotForCandidate() {
        if (!user.getTypeid().getInternalname().equals("Person.Candidate")) {
            return "true";
        }
        return "false";
    }

    //Save form updates
    public void saveChanges() {
//        if(!password.equals(passwordConfirm)) return null;
//        EmailValidator emailValidator = EmailValidator.getInstance();
//        if(!emailValidator.isValid(email)) return null;
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

        Personregistrationform regInfo = user.getPersonregistrationform();
        if (regInfo == null) {
            regInfo = new Personregistrationform();
            regInfo.setInternalname(user.getEmail());
            regInfo.setIsdeleted(false);
            regInfo.setPersonid(user);
            regInfo.setTypeid((Type) dbm.getEntity("Type", "internalname", "Form.Person"));
            user.setPersonregistrationform(regInfo);
            if (!dbm.persistAndFlush(regInfo)) {
                return;
            }
            dbm.updateEntity(user);
        }
        regInfo.setFormvalue(jsonObject.toString());
        dbm.updateEntity(regInfo);
    }

    public void updateUser() {
        dbm.updateEntity(user);
    }

    public void updatePassword() {
        user.setPassword(Hashing.sha256().hashString(newPass, Charsets.UTF_8).toString());
        dbm.updateEntity(user);
    }

    //Email
    public String isEmailNotConfirmed() {
        if (user.getEmailconfirmation() == null) {
            return "true";
        }
        if (user.getEmailconfirmation().equals("validated")) {
            return "false";
        }
        return "true";
    }

    public void sendEmailConfirmation() {
        emailBean.sendEmailConfirmationMessage(user);
    }

    //Util
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

    //Getters
    public DynaFormModel getDisplayModel() {
        return displayModel;
    }

    public Person getUser() {
        return user;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewPassConfirm() {
        return newPassConfirm;
    }

    public void setNewPassConfirm(String newPassConfirm) {
        this.newPassConfirm = newPassConfirm;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }
}
