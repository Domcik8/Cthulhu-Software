package lt.vu.mif.labanoro_draugai.administration;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
//import javax.faces.bean.ViewScoped;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
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
import lt.vu.mif.labanoro_draugai.entities.Type;
import org.primefaces.model.UploadedFile;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;

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

    //======================= FIELDS ===========================
    private List<House> houses;
    
    private House house;
    
    private Map<String, String> houseTypes;
    private String houseType;
    
    @PersistenceContext(type=PersistenceContextType.EXTENDED, synchronization=SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;

    @Inject
    DatabaseManager dbm;
    
    private List<String> images;
    
    private UploadedFile file;
    
    //private FileUpload file;

    //======================= CONSTRUCT ===========================
    
    @PostConstruct
    public void init() {
        house = getEditableHouse();
        initImages();
        initTypes();
    }
    
    public EditableHouse() {
        //house = new House();
        //house = getEditableHouse();
    }
    
    //======================= HOUSE INFO ===========================
    
    public House getEditableHouse() {
        try {
            String houseId = getParameter("houseId");
            int id = Integer.parseInt(houseId);
            houses = em.createNamedQuery("House.findById").setParameter("id",  id).getResultList();
            house = houses.get(0);
            
            //house = (House) dbm.getEntity("House", "Id", houseId);
        }
        catch (Exception ex) {
            house = new House();
        }
        finally {
            return house;
        }
    }
    
    /*private boolean updateHouse(House h) {
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
    }*/
    
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
            savingSuccess = dbm.updateHouse(house);
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
            boolean savingSuccess = dbm.setHouseIsDeletedTrue(house);
            
            //if (!savingSuccess)
                //return error page
            //else
            return "houses";
        }
        catch (Exception ex) {
            return ""; //error page
        }
    }
    
    /*private boolean setIsDeletedTrue(House h) {
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
    }*/
    
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
    
    //======================= GETTERS SETTERS ===========================
    
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
    
    public List<String> getImages() {
        return images;
    }
    
    public House getHouse() {
        return house;
    }
    
    public Map<String, String> getHouseTypes() {
        return houseTypes;
    }
    
    public String getHouseType() {
        return houseType;
    }
    
    public void setHouseType(String ht) {
        houseType = ht;
    }
    
    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Klaida!", "Įvyko klaida."));
    }
    
    //======================= IMAGES ===========================
    
    private void initImages() {
        images = getImagesList();
    }
    
    public List<String> getImagesList() {
        List<String> imgs = new ArrayList<String>();
        
        try {
            if(house == null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) 
                return imgs;
        }
        catch (Exception ex) {
            
        }
        
        /*List<Houseimage> result = house.getHouseimageList();
         
        for (Houseimage img : result) {
            imgs.add(img.getInternalname());
        }*/
         
        return imgs;
    }
    
    //========================== TYPES ==============================
    
    private void initTypes() {
        houseTypes = new LinkedHashMap<String, String>();
        //List<Type> allTypes = em.createNamedQuery("Type.findAll").getResultList();
        List<Type> allTypes = dbm.retrieveTypes("House");
        
        if (house.getId() != null) {
            for (Type t : allTypes) {
                if (house.getTypeid() != null && t.getId() == house.getTypeid().getId())
                    houseTypes.put(t.getId().toString(), t.getTitle());
            }
            
            for (Type t : allTypes) {
                if (house.getTypeid() != null && t.getId() != house.getTypeid().getId())
                    houseTypes.put(t.getId().toString(), t.getTitle());
            }
        }
        else {
            for (Type t : allTypes) {
                houseTypes.put(t.getId().toString(), t.getTitle());
            }
        }
    }
    
    public void changeHouseType() {
        List<Type> newTypes = em.createNamedQuery("Type.findById").setParameter("id", Integer.parseInt(houseType)).getResultList();
        Type newType = newTypes.get(0);
        //Type newType = (Type) dbm.getEntity("Type", "Id", houseType);
        house.setTypeid(newType);
    }
    
    //======================= FILE UPLOAD ===========================
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void upload(FileUploadEvent event) {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        System.out.println(file.getFileName());

        byte[] foto = IOUtils.toByteArray(file.getInputstream());
        System.out.println(foto);
     //application code
    }
}
