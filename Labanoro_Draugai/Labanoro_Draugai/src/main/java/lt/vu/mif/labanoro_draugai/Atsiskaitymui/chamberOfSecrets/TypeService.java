package lt.vu.mif.labanoro_draugai.Atsiskaitymui.chamberOfSecrets;

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
import lt.vu.mif.labanoro_draugai.entities.Type;

/**
 * Created not by donat not on 2016-04-13.
 */
@Stateless
public class TypeService {
    @PersistenceContext(type = PersistenceContextType.TRANSACTION,
                        synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;
    
    @Inject 
    DatabaseManager dbm;

    public Type create(Type type) {
        System.out.println(this + ": gavau EntityManager = " + em.getDelegate());
        String title = type.getTitle();
        String internalName = "2Atsiskaitymas." +  title;
        
        Type newType = new Type();
        newType.setInternalname(internalName);
        newType.setTitle(title);

        if (dbm.entityExists("Type", "Internalname", internalName)) {
            return (Type) dbm.getEntity("Type", "internalname", internalName);
        }

        em.persist(newType);
        return newType;
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
