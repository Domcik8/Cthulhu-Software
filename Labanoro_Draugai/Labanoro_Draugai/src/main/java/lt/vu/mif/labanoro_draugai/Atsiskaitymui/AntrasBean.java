package lt.vu.mif.labanoro_draugai.Atsiskaitymui;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import static javax.persistence.PersistenceContextType.EXTENDED;
import static javax.persistence.PersistenceContextType.TRANSACTION;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionSynchronizationRegistry;

@Stateful
public class AntrasBean {
    @PersistenceContext(type=TRANSACTION)
    //@PersistenceContext(type=EXTENDED)
    private EntityManager em;
    
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

    //---------------------------------

    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String sayKuku() {
        System.out.println(this + " Vykdau dalykinÄ¯ funkcionalumÄ…, raÅ¡au/skaitau DB...");
        System.out.println(this + ": gavau EntityManager = " + em.getDelegate());
        return "AntrasBean sako: " + new Date() + " " + toString();
    }
    
    //---------------------------------

    @AfterBegin
    private void afterBeginTransaction() {
        System.out.println(this + " Transakcija: " + tx.getTransactionKey());
    }

    @AfterCompletion
    private void afterTransactionCompletion(boolean commited) {
        System.out.println(this + " Transakcija pasibaigÄ—; commited: " + commited);
    }

}