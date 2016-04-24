package lt.vu.mif.labanoro_draugai.administration;

import java.io.Serializable;
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
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Type;

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
    //@PersistenceContext(type=PersistenceContextType.EXTENDED, synchronization=SynchronizationType.UNSYNCHRONIZED) 

    private int id;
    private List<House> houses;
    
    private House house;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private Conversation conversation;

    public Conversation getConversation() {
        return conversation;
    }
    
    //@Inject
    //HouseFilter houseFilter;

    @Inject
    DatabaseManager dbm;

    @PostConstruct
    public void init() { 
        //houses = em.createNamedQuery("House.findByIsdeleted").setParameter("isdeleted",  false).getResultList();
        if (!conversation.isTransient()) {
            conversation.end();
        }
        //houseFilter = new HouseFilter();
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
        Type type = (Type) dbm.getEntity("Type", "Internalname", "House");
        house.setTypeid(type);
        
        conversation.end();
        em.joinTransaction();
        //try {
            //house = dbm.addHouse("Old small house", "Vilnius", "HouseReg-99", "House.Penthouse");
            /*house = new House();
            house.setTitle("Test title");
            house.setAddress("Vilniuuusas");
            house.setHousereg("HouseReg-TEST55");  //<--- UNIQUE
            Type type = (Type) dbm.getEntity("Type", "Internalname", "House.Penthouse");
            house.setTypeid(type);*/
            boolean isSuccess = dbm.persistAndFlush(house);
        /*}
        catch (Exception ex)
        {
            return "houses";
        }*/
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
    
    public String declineChanges() {
        house = null;
        conversation.end();
        return "houses";
    }
}
