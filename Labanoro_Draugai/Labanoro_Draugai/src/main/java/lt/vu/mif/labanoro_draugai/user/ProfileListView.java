/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Formattribute;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Personregistrationform;
import lt.vu.mif.labanoro_draugai.entities.Recommendation;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import lt.vu.mif.labanoro_draugai.entities.Type;
import lt.vu.mif.labanoro_draugai.mailService.EmailBean;
import lt.vu.mif.labanoro_draugai.reservation.ReservationConfirmationManager;
import net.sf.json.JSONString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Ernest J
 */
@Named("profileListView")
@ViewScoped
public class ProfileListView implements Serializable {

    private List<Person> filteredProfiles;
    private List<Person> persons;
    private Person selectedPerson;
    private Map<String, String> registrationForm;
    List<String> userTypes;
    private Person user;
    @Inject
    private DatabaseManager dbm;

    @Inject
    private EmailBean email;

    @PersistenceContext
    private EntityManager em;

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

        this.registrationForm = new LinkedHashMap();

        if (persons == null || persons.isEmpty()) {
            this.persons = em.createNamedQuery("Person.findAll").getResultList();
        }
        List<Formattribute> attributes = (List<Formattribute>) dbm.getAllEntities("Formattribute");
        if (attributes == null) {
            return;
        }

        userTypes = new ArrayList<>();
        List<Type> types = dbm.retrieveTypes("Person");
        for (Type type : types) {
            if(!type.getInternalname().equals("Person"))userTypes.add(type.getTitle());
        }
    }

    public boolean isCandidate(Person person) {
        if (person != null && person.getTypeid().getInternalname().equals("Person.Candidate")) {
            return true;
        }
        return false;
    }

    public boolean isCandidate() {
        return isCandidate(user);
    }

    public boolean renderRecommendation() {
        if (user == null || selectedPerson == null || isCandidate() || !isCandidate(selectedPerson) || alreadyRecommended()) {
            return false;
        }
        return true;
    }

    public boolean alreadyRecommended() {
        if (selectedPerson.getRecommendationList1() == null) {
            return false;
        }
        for (Recommendation reco : selectedPerson.getRecommendationList1()) {
            if (reco.getRecommenderid().getEmail().equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Person getSelectedUser() {
        return this.selectedPerson;
    }

    public void setSelectedUser(Person person) {
        this.selectedPerson = person;
        setRegistrationForm(person);
    }

    private void setRegistrationForm(Person person) {

        this.registrationForm = new LinkedHashMap();

        JSONParser parser = new JSONParser();

        if (person != null) {
            registrationForm.put("Vardas", selectedPerson.getFirstname());
            registrationForm.put("Pavardė", selectedPerson.getLastname());
            registrationForm.put("El. paštas", selectedPerson.getEmail());
            try {
                Personregistrationform regForm = person.getPersonregistrationform();
                JSONObject json = (JSONObject) parser.parse(regForm.getFormvalue());
                this.registrationForm.putAll(parseJson(json, this.registrationForm));
            } catch (ParseException ex) {
                Logger.getLogger(ProfileListView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException eex) {
                Logger.getLogger(ProfileListView.class.getName()).log(Level.SEVERE, null, eex);
            }
        }
    }

    public Map<String, String> getRegistrationForm() {
        return this.registrationForm;
    }

    public void askRecommendation() {
        if (isCandidate()) {
            return;
        }

        String receiverEmail = selectedPerson.getEmail();
        String requestorEmail = user.getEmail();
        Systemparameter param = (Systemparameter) dbm.getEntity("Systemparameter", "InternalName", "SystemParameter.MaxRecommendations");
        // check if requestor have enought recommendationToSend limit
        // if yes add recommendation and return pop-up information about succesfully send email
        if (param == null || user.getRecommendationstosend() >= Integer.parseInt(param.getValue())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nepavyko!", "Pasiektas maksimalus rekomendacijų prašymų skaičius."));
            return;
        }
//        Recommendation recommendation = (Recommendation) dbm.addRecommendation(receiverEmail, requestorEmail, "Recommendation");

//        if (recommendation != null) {
        email.sendCandidateRecommendationRequestMessage(receiverEmail, requestorEmail);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pavyko!", "Rekomendacijos prašymas sėkmingai išsiūstas."));
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nepavyko!", "Šis vartotojas jau gavo jūsų prašymą."));
//        }
    }

    public void giveRecommendation() {
        if (!renderRecommendation()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nepavyko!", "Kandidatas jau turi jūsų rekomandaciją."));
            return;
        }
        String receiverEmail = selectedPerson.getEmail();
        String requestorEmail = user.getEmail();

        Recommendation recommendation = (Recommendation) dbm.addRecommendation(requestorEmail, receiverEmail, new Date(), "Recommendation");
        if (recommendation != null) {
            selectedPerson.setRecommendationsreceived(selectedPerson.getRecommendationsreceived() + 1);
            selectedPerson.getRecommendationList1().add(recommendation);
            int minimumRecommendations = Integer.parseInt(dbm.getSystemParameter("SystemParameter.RequiredRecommendations").getValue());

            // Check if candidate have reached necessary number of recommendation to become a member
            if (selectedPerson.getRecommendationsreceived() >= minimumRecommendations) {

                Type memberType = (Type) dbm.getEntity("Type", "Internalname", "Person.User");

                if (memberType != null) {
                    selectedPerson.setTypeid(memberType);
                    email.sendCandidateApprovalMessage(selectedPerson);
                }
            }

            dbm.updateEntity(selectedPerson);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pavyko!", "Kandidatas sėkmingai rekomenduotas."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nepavyko!", "Kandidatas jau turi jūsų rekomandaciją."));
        }
    }

    private String getParameter(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();

        return params.get(key);
    }

    private Map<String, String> parseJson(JSONObject json, Map<String, String> collection) {

        Set keys = json.keySet();
        Iterator<String> a = keys.iterator();

        while (a.hasNext()) {
            String key = (String) a.next();
            String value = (String) json.get(key);
            collection.put(key, value);
            System.out.print("key: " + key);
            System.out.println(" value: " + value);
        }

        return collection;
    }

    public Person getUser() {
        return user;
    }

    public List<String> getUserTypes() {
        return userTypes;
    }

    public List<Person> getFilteredProfiles() {
        return filteredProfiles;
    }

    public void setFilteredProfiles(List<Person> filteredProfiles) {
        this.filteredProfiles = filteredProfiles;
    }

}
