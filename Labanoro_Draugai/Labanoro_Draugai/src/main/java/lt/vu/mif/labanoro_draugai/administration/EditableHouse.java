package lt.vu.mif.labanoro_draugai.administration;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.ApplicationException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import org.omnifaces.cdi.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import lt.vu.mif.labanoro_draugai.entities.Type;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.IOUtils;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;
import org.primefaces.context.RequestContext;
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
    
    private String image;

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
        
        if (house.getSeasonenddate().before(house.getSeasonstartdate())) {
            showError("Netinkamai nustatytos sezono datos!");
            return "";
        }
        else {
            boolean savingSuccess = true;
        
            if (house.getId() != null) {
                savingSuccess = dbm.updateHouse(house);
            }
            else {
                house.setTypeid((Type)dbm.getEntity("Type", "id", Integer.parseInt(houseType)));
                savingSuccess = insertHouse(house);
            }

            return "houses";
        }
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
            return "houses";
        }
        catch (Exception ex) {
            return "";
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
    
    //======================= IMAGES ===========================
    
    private void initImages() {
        images = getImagesList();
    }
    
    public List<String> getImagesList() {
        List<String> imgs = new ArrayList<String>();
        
        List<Houseimage> result = dbm.getEntityList("Houseimage", "Houseid", house);
         
        for (Houseimage img : result) {
            imgs.add(img.getInternalname());
        }
         
        return imgs;
    }
    
    //========================== TYPES ==============================
    
    private void initTypes() {
        houseTypes = new LinkedHashMap<String, String>();
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
            String extension = getFileExtension(file.getFileName());
            if (extension.equals("JPG") || extension.equals("PNG")) {
                byte[] blobFile = file.getContents();
                if (blobFile.length < 2097152) {  // 2097152 B == 2 MB
                    byte[] resizedBlobFile = scale(blobFile, getImageWidthParameter(), getImageHeightParameter());
                    insertHouseImage(resizedBlobFile, extension);
                }
                else {
                    showError("Bandote įkelti per didelį failą!");
                }
            }
            else {
                showError("Bandote įkelti netinkamo formato failą!");
            }
        }
    }
    
    public void showError(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Klaida!", message));
    }
    
    private String getFileExtension(String fileName) {
        String[] devidedName = fileName.split("\\.");
        String lastPart = devidedName[devidedName.length-1];
        return lastPart.toUpperCase();
    }
    
    private int getImageWidthParameter() {
        Systemparameter widthParam = dbm.getSystemParameter("SystemParameter.Houseimage.Width");
        return Integer.parseInt(widthParam.getValue());
    }
    
    private int getImageHeightParameter() {
        Systemparameter heightParam = dbm.getSystemParameter("SystemParameter.Houseimage.Height");
        return Integer.parseInt(heightParam.getValue());
    }
    
    private byte[] scale(byte[] fileData, int width, int height) {
    	ByteArrayInputStream in = new ByteArrayInputStream(fileData);
    	try {
    		BufferedImage img = ImageIO.read(in);
    		if(height == 0) {
    			height = (width * img.getHeight())/ img.getWidth(); 
    		}
    		if(width == 0) {
    			width = (height * img.getWidth())/ img.getHeight();
    		}
    		Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    		BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    		imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

    		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    		ImageIO.write(imageBuff, "jpg", buffer);

    		return buffer.toByteArray();
    	} catch (IOException e) {
                return null;
    	}
    }
    
    public boolean insertHouseImage(Serializable image, String mimeType) throws IOException {
        if (!mimeType.equals("JPG") && !mimeType.equals("PNG")) {
            return false;
        }
        
        try {
            Houseimage img = new Houseimage();
            img.setImage(image);
            img.setTypeid((Type)dbm.getEntity("Type", "Id", 15));
            img.setHouseid(house);
            int sequence = getLastImageSequence(house);
            String internalName = "Picture." + house.getHousereg() + "_" + sequence;
            img.setInternalname(internalName);
            img.setSequence(sequence);
            
            img.setMimetype(mimeType);

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
        List<Houseimage> imgs = dbm.getEntityList("Houseimage", "Houseid", h);//h.getHouseimageList();
        
        for (Houseimage img : imgs) {
            if (img.getSequence() > count) {
                count = img.getSequence();
            }
        }
        
        return (count+1);
    }
    
    public void setImage(String img) {
        image = img;
    }
    
    public void setImageForDialog(String img) {
        image = img;
    }
    
    public String getImage() {
        return image;
    }
    
    public void removeImage() throws IOException {
        dbm.deleteHouseimageByInternalname(image);
        //Close the dialog:
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("PF('deletionDialog').hide();");

        //Reload the page:
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()  + "?houseId=" + house.getId());
    }
    
    public void closeDialogs() throws IOException {
        //Close dialogs:
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("PF('deletionDialog').hide();");
        requestContext.execute("PF('deletionHouseDialog').hide();");
    }
}
