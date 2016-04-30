/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author ErnestB
 */
@Named
@Stateful
@ConversationScoped
public class AdminUserManager implements Serializable {
    
    private List<Person> users;
    private List<Person> filteredUsers;
    private Person user;
    private Date calendar;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;
    
    @Inject
    private Conversation conversation;
    
    @PostConstruct
    public void init() { 
        if (!conversation.isTransient()) {
            conversation.end();
        }
        
        conversation.begin();
        
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
            String houseId = getParameter("userId");
            int id = Integer.parseInt(houseId);

            List<Person> usersToDelete = em.createNamedQuery("Person.findById").setParameter("id",  id).getResultList();
            Person userToDelete = usersToDelete.get(0);

            userToDelete.setIsdeleted(true);

            conversation.end();
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
    
     /*public House getHouse() {
        try {
            String houseId = getParameter("houseId");
            int id = Integer.parseInt(houseId);
            houses = em.createNamedQuery("House.findById").setParameter("id",  id).getResultList();
            house = houses.get(0);
        }
        catch (Exception ex) {
            house = new House();
        }
        finally {
            return house;
        }
    }
     
     public String saveHouse(House h) {
        //em.getTransaction().begin();
        //em.persist(house);
        //em.getTransaction().commit();
        //em.flush();
        //house.setAddress("Vilniussss");
        //house = getHouse();
        
        dbm.editHouse(h);
        return "houses";
    }*/
}
