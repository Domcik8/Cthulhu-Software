/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Recommendation;
import lt.vu.mif.labanoro_draugai.mailService.EmailBean;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Ernest J
 */
@Named("profileListView")
@ViewScoped
public class ProfileListView implements Serializable {

    private List<Person> persons;

    @Inject
    private DatabaseManager dbm;

    @Inject
    private EmailBean email;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        if (persons == null || persons.isEmpty()) {
            this.persons = em.createNamedQuery("Person.findAll").getResultList();
        }
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public String viewProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        String personEmail = getParameter("personEmail");

        Person person = (Person) dbm.getEntity("Person", "Email", personEmail);

        Integer personId = person.getId();

        // Call page with "personId" to open
//        request.sendRedirect(request.getContextPath() + "/index");
        return (dbm.getSystemParameter("SystemParameter.General.ContextPath").getValue() + "/user/profile/" + personId);
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

}
