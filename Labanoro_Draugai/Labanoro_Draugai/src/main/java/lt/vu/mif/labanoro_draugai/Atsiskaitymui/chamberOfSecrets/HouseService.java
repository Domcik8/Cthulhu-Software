package lt.vu.mif.labanoro_draugai.Atsiskaitymui.chamberOfSecrets;

import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import javax.transaction.TransactionSynchronizationRegistry;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Type;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionSynchronizationRegistry;

/**
 * Created not by donat not on 2016-04-13.
 */
@Stateless
public class HouseService {
    @PersistenceContext(type = PersistenceContextType.TRANSACTION,
                        synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;
    
    @Inject 
    DatabaseManager dbm;
    
    Random rand = new Random();

    public House create(House house) {
        return create(house, "House.Penthouse");
    }
    
    public House create(House house, Type type) {
        return create(house, type.getInternalname());
    }
    
    public House create(House house, String typeInternalName) {
        System.out.println(this + ": gavau EntityManager = " + em.getDelegate());
        String title = house.getTitle();
        String address = house.getAddress();
        String houseReg = title;//"HouseReg-" + rand.nextInt();
        
        Type type = (Type) dbm.getEntity("Type", "Internalname", typeInternalName);
        
        /*if(type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }*/
        
        house.setTitle(title);
        house.setAddress(address);
        house.setHousereg(houseReg);
        house.setTypeid(type);
        
        /*if(dbm.entityExists("House", "Housereg", houseReg)) {
            System.out.println(String.format("House with registration '%s' already exists", houseReg));
            return null;
        }*/
        
        em.persist(house);
        return house;
    }
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    @PostConstruct
    private void gimiau() {
        System.out.println(this + " gimiau.");
    }
    
    @PreDestroy
    private void tuojMirsiu() {
        System.out.println(this + " tuoj mirsiu.");
    }

    @AfterBegin
    private void afterBeginTransaction() {
        System.out.println(this + " Transakcija: " + tx.getTransactionKey());
    }

    @AfterCompletion
    private void afterTransactionCompletion(boolean commited) {
        System.out.println(this + " Transakcija pasibaigė commited: " + commited);
    }
}
