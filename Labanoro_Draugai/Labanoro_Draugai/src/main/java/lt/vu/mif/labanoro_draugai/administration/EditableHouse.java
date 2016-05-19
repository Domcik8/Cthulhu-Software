package lt.vu.mif.labanoro_draugai.administration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import org.omnifaces.cdi.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import lt.vu.mif.labanoro_draugai.entities.Type;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.IOUtils;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author ErnestB
 */
@Named
@ViewScoped
@Stateful
public class EditableHouse implements Serializable {

    //======================= FIELDS ===========================
    private List<House> houses;
    
    private House house;
    
    private Map<String, String> houseTypes;
    private String houseType;
    
    @PersistenceContext(type=PersistenceContextType.TRANSACTION, synchronization=SynchronizationType.UNSYNCHRONIZED)
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
        
        List<Houseimage> result = dbm.getEntityList("Houseimage", "Houseid", house);
         
        for (Houseimage img : result) {
            imgs.add(img.getInternalname());
        }
         
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
    
    public void upload() throws IOException {
        if(file != null) {
            //FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            //FacesContext.getCurrentInstance().addMessage(null, message);
            byte[] blobFile = file.getContents();
            insertHouseImage(blobFile);
        }
    }
    
    public boolean insertHouseImage(Serializable image) throws IOException {
        try {
            Houseimage img = new Houseimage();
            img.setImage(image);
            img.setTypeid((Type)dbm.getEntity("Type", "Id", 15));
            img.setHouseid(house);
            int sequence = getLastImageSequence(house);
            String internalName = "Picture." + house.getHousereg() + "_" + sequence;
            img.setInternalname(internalName);
            img.setSequence(sequence);
            img.setMimetype("JPG");

            em.joinTransaction();
            dbm.persistAndFlush(img);

            return true;
        }
        catch (Exception ex) {
            return false;
        }
        finally {
            //Reload the page:
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI() + "?houseId=" + house.getId());
        }
    }
    
    private int getLastImageSequence(House h) {
        int count = 0;
        List<Houseimage> imgs = h.getHouseimageList();
        
        for (Houseimage img : imgs) {
            if (img.getSequence() > count) {
                count = img.getSequence();
            }
        }
        
        return (count+1);
    }
}
