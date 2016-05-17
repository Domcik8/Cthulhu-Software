package lt.vu.mif.labanoro_draugai.Atsiskaitymui.chamberOfSecrets;

import java.util.ArrayList;
import java.util.List;
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
import lt.vu.mif.labanoro_draugai.entities.Service;
import lt.vu.mif.labanoro_draugai.entities.Type;

/**
 * Created not by donat not on 2016-04-13.
 */
@Stateless
public class ServiceService {
    @PersistenceContext(type = PersistenceContextType.TRANSACTION,
                        synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;
    
    @Inject 
    DatabaseManager dbm;

    public Service create(House house, Service service) {
        String typeInternalName = "Service.Vehicle.Bike";
        String title = service.getTitle();
        String serviceReg = service.getServicereg();
        
        Type type = (Type) dbm.getEntity("Type", "Internalname", typeInternalName);

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }

        Service newService = new Service();
        newService.setTitle(title);
        newService.setServicereg(serviceReg);
        newService.setTypeid(type);

        
        /*if (dbm.entityExists("Service", "Servicereg", serviceReg)) {
            System.out.println(String.format("Service with registration '%s' already exists", serviceReg));
            return null;
        }*/

        em.persist(newService);

        if (house.getServiceList() == null)
            house.setServiceList(new ArrayList<Service>());
        house.getServiceList().add(newService);

        return newService;
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
