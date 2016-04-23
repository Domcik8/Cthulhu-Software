package lt.vu.mif.labanoro_draugai.administration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;

/**
 *
 * @author ErnestB
 */
/*@Named
@Stateful
@ViewScoped*/
@Named
@ConversationScoped
@Stateful
public class EditableHouse implements Serializable {
    
    private static final String PAGE_INDEX          = "index?faces-redirect=true";
    private static final String PAGE_CREATE_STUDENT = "createStudent?faces-redirect=true";
    private static final String PAGE_CONFIRM        = "confirm?faces-redirect=true";
    
    private int id;
    private List<House> houses;
    
    private House house;
    
    @PersistenceContext(type = PersistenceContextType.EXTENDED, synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;
    
    @Inject
    private Conversation conversation;

    public Conversation getConversation() {
        return conversation;
    }

    @Inject
    DatabaseManager dbm;

    @PostConstruct
    public void init() { 
        //houses = em.createNamedQuery("House.findByIsdeleted").setParameter("isdeleted",  false).getResultList();
        if (!conversation.isTransient()) {
            conversation.end();
        }

        conversation.begin();
        house = getEditableHouse();
    }
    
    public EditableHouse() {
        //house = new House();
    }
    
    EditableHouse(int id) {
        this();
        this.id = id;
    }
    
    public House getHouse() {
        /*if (house == null) {
            house = getEditableHouse();
        }*/
        
        return house;
    }
    
    public House getEditableHouse() {
        try {
            String houseId = getParameter("houseId");
            id = Integer.parseInt(houseId);
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
    
    public String saveHouse() {
        conversation.end();
        dbm.persistAndFlush(house);
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
