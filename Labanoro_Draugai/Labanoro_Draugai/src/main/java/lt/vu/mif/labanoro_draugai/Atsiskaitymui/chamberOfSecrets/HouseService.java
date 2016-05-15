package lt.vu.mif.labanoro_draugai.Atsiskaitymui.chamberOfSecrets;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Type;

/**
 * Created not by donat not on 2016-04-13.
 */
@Stateless
public class HouseService {
    @PersistenceContext(synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;
    
    @Inject 
    DatabaseManager dbm;

    public House create(House house) {
        
        
        String typeInternalName = "House.Penthouse";
        String title = "Devils house";
        String address = "Hell";
        String houseReg = "HouseReg-667";
        
        Type type = (Type) dbm.getEntity("Type", "Internalname", typeInternalName);
        
        if(type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }
        
        house.setTitle(title);
        house.setAddress(address);
        house.setHousereg(houseReg);
        house.setTypeid(type);
        
        if(dbm.entityExists("House", "Housereg", houseReg)) {
            System.out.println(String.format("House with registration '%s' already exists", houseReg));
            return null;
        }
        
        em.persist(house);
        return house;
    }
}
