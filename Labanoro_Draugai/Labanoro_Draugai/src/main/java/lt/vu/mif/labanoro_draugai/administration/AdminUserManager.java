/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;
import org.primefaces.context.RequestContext;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author ErnestB
 */
@Named
@Stateful
@ViewScoped
public class AdminUserManager implements Serializable {
    
    private List<Person> users;
    private List<Person> filteredUsers;
    private Date calendar;
    
    //Dialog:
    private Person user;
    private Person userForDeletion;
    private String userEmail;
    private int addedPoints;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init() {        
        if (users == null || users.isEmpty()) {
            users = em.createNamedQuery("Person.findAll").getResultList();
        }
        
        initFilters();
    }
    
    public List<Person> getUsers() {
        return users;
    }
    
    public List<Person> getFilteredUsers() {
        return filteredUsers;
    }
    
    public void setFilteredUsers(List<Person> filtUsers) {
        filteredUsers = filtUsers;
    }
    
    public String removeUser() {
        boolean isSuccess = true;
        
        try {
            //String userId = getParameter("userId");
            //int id = Integer.parseInt(userId);

            int id = userForDeletion.getId();
            
            List<Person> usersToDelete = em.createNamedQuery("Person.findById").setParameter("id",  id).getResultList();
            Person userToDelete = usersToDelete.get(0);

            userToDelete.setIsdeleted(true);

            em.joinTransaction();
            
            isSuccess = dbm.persistAndFlush(userToDelete);
        }
        catch (Exception ex) {
            //return error page
        }

        
        //if (!isSuccess)
            //return error page
        //else
        return "users";
    }
    
    public AdminUserManager() {
    }
    
    private String getParameter(String key) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        
        return params.get(key);
    }
    
    // FILTERS:
    
    private int filter;
    private Map<String, Object> filters;
    private String filterKey;
    
    public int getFilter() {
        return filter;
    }
    
    public void setFilter(int filt) {
        filter = filt;
    }
    
    public Map<String, Object> getFilters() {
        return filters;
    }
    
    public void initFilters() {
        filters = new LinkedHashMap<String, Object>();
        
        filters.put("El. paštas", 0);
        filters.put("Vardas", 1);
        filters.put("Pavardė", 2);
    }
    
    public String getFilterKey() {
        return filterKey;
    }
    
    public void setFilterKey(String filtKey) {
        filterKey = filtKey;
    }
    
    public String filterUsers() {
        if (!filterKey.equals("")) {
            switch (filter) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
            }
        }
        return "users";
    }
    
    public void addPoints() throws IOException {
        Person u = (Person) dbm.getEntity("Person", "Email", userEmail);
        u.setPoints(u.getPoints().add(new BigDecimal(addedPoints)));
        if (dbm.updatePersonPoints(u)) {
            userEmail = "";
            addedPoints = 0;
            
            //Close the dialog:
            RequestContext requestContext = RequestContext.getCurrentInstance();  
            requestContext.execute("PF('addPointsDialog').hide();");
            
            //Reload the page:
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }
    }
    
    public void closeDialogs() throws IOException {
        //Close dialogs:
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("PF('deletionUserDialog').hide();");
    }
    
    /*public void openDialog(Person u) {
        user = u;
        userEmail = u.getEmail();
        
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("PF('addPointsDialog').show();");
    }*/
    
    public Date getCalendar() {
        return calendar;
    }
 
    public void setCalendar(Date date) {
        this.calendar = date;
    }
    
    public Date getCurrentDate() {
        return new Date();
    }
    
    public String getType(Person user) {
        return user.getTypeid().getTitle();
    }
    
    public Person getUser() {
        return user;
    }
    
    public void setUser(Person u) {
        user = u;
    }
    
    public Person getUserForDeletion() {
        return userForDeletion;
    }
    
    public void setUserForDeletion(Person u) {
        userForDeletion = u;
    }
    
    public void setUserEmail(String eml) {
        userEmail = eml;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public int getAddedPoints() {
        return addedPoints;
    }
    
    public void setAddedPoints(int ap) {
        addedPoints = ap;
    }
}
