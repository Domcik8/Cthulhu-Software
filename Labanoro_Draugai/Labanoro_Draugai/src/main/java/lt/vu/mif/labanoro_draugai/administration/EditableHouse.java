package lt.vu.mif.labanoro_draugai.administration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
//import javax.faces.bean.ViewScoped;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 *
 * @author ErnestB
 */
/*@Named
@Stateful
@ViewScoped*/
@Named
@ViewScoped
@Stateful
public class EditableHouse implements Serializable {

    private List<House> houses;
    
    private House house;
    
    @PersistenceContext(type=PersistenceContextType.EXTENDED, synchronization=SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;

    @Inject
    DatabaseManager dbm;
    
    private List<String> images;

    @PostConstruct
    public void init() {
        house = getEditableHouse();
        initImages();
    }
    
    public EditableHouse() {
        //house = new House();
        //house = getEditableHouse();
    }
    
    public House getEditableHouse() {
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
    
    private boolean updateHouse(House h) {
        try {
            Query q = em.createQuery("UPDATE House h SET h.title = :title, h.typeid = :typeid, "
                    + "h.description = :description, h.housereg = :housereg, h.address = :address, "
                    + "h.isactive = :isactive, h.seasonstartdate = :startdt, h.seasonenddate = :enddt, "
                    + "h.weekprice = :price, h.numberofplaces = :places "
                    + "WHERE h.id = :id");
            q.setParameter("title", h.getTitle());
            q.setParameter("typeid", h.getTypeid());
            q.setParameter("description", h.getDescription());
            q.setParameter("housereg", h.getHousereg());
            q.setParameter("address", h.getAddress());
            q.setParameter("isactive", h.getIsactive());
            q.setParameter("startdt", h.getSeasonstartdate());
            q.setParameter("enddt", h.getSeasonenddate());
            q.setParameter("price", h.getWeekprice());
            q.setParameter("places", h.getNumberofplaces());
            q.setParameter("id", h.getId());
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    private boolean insertHouse(House h) {
        try {
            em.joinTransaction();
            return dbm.persistAndFlush(house);
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public String saveHouse() {
        boolean savingSuccess = true;
        
        if (house.getId() != null)
            savingSuccess = updateHouse(house);
        else
            savingSuccess = insertHouse(house);

        return "houses";
    }

    private String getParameter(String key) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        
        return params.get(key);
    }
    
    public String declineChanges() {
        house = null;
        return "houses";
    }
    
    public String deleteHouse() {
        try {
            em.joinTransaction();
            boolean savingSuccess = setIsDeletedTrue(house);
            
            //if (!savingSuccess)
                //return error page
            //else
            return "houses";
        }
        catch (Exception ex) {
            return ""; //error page
        }
    }
    
    private boolean setIsDeletedTrue(House h) {
        try {
            Query q = em.createQuery("UPDATE House h SET h.isdeleted = :isdeleted "
                    + "WHERE h.id = :id");
            q.setParameter("isdeleted", true);
            q.setParameter("id", h.getId());
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    /*public String firstImageName() {
        if(house == null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) return null;
        Predicate condition = new Predicate() {
            public boolean evaluate(Object sample) {
                 return ((Houseimage)sample).getSequence().equals(1);
            }
         };
         Houseimage result = (Houseimage)CollectionUtils.select(house.getHouseimageList(), condition ).iterator().next();
         return result.getInternalname();
    }*/
    
    private void initImages() {
        images = getImagesList();
    }
    
    public List<String> getImagesList() {
        List<String> imgs = new ArrayList<String>();
        
        if(house == null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) return null;
        List<Houseimage> result = house.getHouseimageList();
        //return result.getInternalname();
         
        for (Houseimage img : result) {
            imgs.add(img.getInternalname());
        }
         
        return imgs;
    }
    
    public boolean getIsActive() {
        try {
            if (house.getId() == null)
                return true;
            else
                return house.getIsactive();
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public void setIsActive(boolean isa) {
        house.setIsactive(isa);
    }
    
    public String getType() {
        return house.getTypeid().getTitle();
    }
    
    public List<String> getImages() {
        return images;
    }
    
    public House getHouse() {
        return house;
    }
    
    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Klaida!", "Įvyko klaida."));
    }
}
