package lt.vu.mif.labanoro_draugai.administration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;

/**
 *
 * @author ErnestB
 */
@Named
@Stateful
@ViewScoped
public class EditableHouse implements Serializable {
    
    private int id;
    private List<House> houses;
    public House house;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    DatabaseManager dbm;

    @PostConstruct
    public void init() { 
        houses = em.createNamedQuery("House.findByIsdeleted").setParameter("isdeleted",  0).getResultList();
    }
    
    public EditableHouse() {
        //house = new House();
    }
    
    EditableHouse(int id) {
        this();
        this.id = id;
    }
    
    public House getHouse() {
        if (house == null) {
            house = getEditableHouse();
        }
        
        return house;
    }
    
    public void setHouse(House h) {
        house = h;
    }
    
    public House getEditableHouse() {
        try {
            String houseId = getParameter("houseId");
            id = Integer.parseInt(houseId);
            //houses = em.createNamedQuery("House.findById").setParameter("id",  id).getResultList();
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
        //dbm.editHouse(house);
        return "houses";
    }

    private String getParameter(String key) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        
        return params.get(key);
    }
    
    public void setTitle(String t) {
        house.setTitle(t);
    }
    
    public String getTitle() {
        return house.getTitle();
    }

}
