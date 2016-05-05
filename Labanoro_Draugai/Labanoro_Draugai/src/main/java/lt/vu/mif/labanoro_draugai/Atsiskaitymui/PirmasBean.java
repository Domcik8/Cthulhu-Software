package lt.vu.mif.labanoro_draugai.Atsiskaitymui;

import java.util.Date;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static javax.persistence.PersistenceContextType.EXTENDED;
import static javax.persistence.PersistenceContextType.TRANSACTION;
import javax.transaction.TransactionSynchronizationRegistry;
import lt.vu.mif.labanoro_draugai.entities.Type;

@Named
@Stateful
public class PirmasBean {
    @PersistenceContext(type=TRANSACTION)
    //@PersistenceContext(type=EXTENDED)
    private EntityManager em;
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    @Inject
    private AntrasBean antrasBean;
    
    Random random = new Random();
    
    @PostConstruct
    private void gimiau() {
        System.out.println(this + " gimiau.");
    }
    
    @PreDestroy
    private void tuojMirsiu() {
        System.out.println(this + " tuoj mirsiu.");
    }
    
    //---------------------------------

    public String sayKuku() {
        System.out.println(this + " Vykdau dalykinÄ¯ funkcionalumÄ…, raÅ¡au/skaitau DB...");
        System.out.println(this + ": gavau EntityManager = " + em.getDelegate());
        
        Type type = new Type();
        type.setTitle("Test");
        type.setInternalname("TEST" + random.nextInt());
        
        em.persist(type);
        
        return "Kuku " + new Date() + " " + toString() + " | " + antrasBean.sayKuku();
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