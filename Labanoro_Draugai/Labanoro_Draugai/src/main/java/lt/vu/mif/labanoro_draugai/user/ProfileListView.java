/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
import lt.vu.mif.labanoro_draugai.mailService.EmailBean;
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

    private List<Person> persons;
    private Person selectedPerson;
    private Map<String, String> registrationForm;

    @Inject
    private DatabaseManager dbm;

    @Inject
    private EmailBean email;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {

        this.registrationForm = new HashMap();

        if (persons == null || persons.isEmpty()) {
            this.persons = em.createNamedQuery("Person.findAll").getResultList();
        }
        List<Formattribute> attributes = (List<Formattribute>) dbm.getAllEntities("Formattribute");
        if (attributes == null) {
            return;
        }
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

        this.registrationForm = new HashMap();

        JSONParser parser = new JSONParser();

        if (person != null) {
            try {
                Personregistrationform regForm = person.getPersonregistrationform();
                JSONObject json = (JSONObject) parser.parse(regForm.getFormvalue());
                this.registrationForm = parseJson(json, this.registrationForm);
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

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        String receiverEmail = getParameter("personEmail");
        String requestorEmail = request.getUserPrincipal().toString();

        // check if requestor have enought recommendationToSend limit
        // if yes add recommendation and return pop-up information about succesfully send email
        Recommendation recommendation = (Recommendation) dbm.addRecommendation(receiverEmail, requestorEmail, "Recommendation");

        if (recommendation != null) {
            email.sendCandidateRecommendationRequestMessage(receiverEmail, requestorEmail);
            System.out.println("Tokios rekomendacijos nebuvo. Rekomendacija sukurta. Laiskas zmogui issiustas");
        } else {
            System.out.println("Rekomendacijos prasymas jau buvo issiustas");
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

}
