package lt.vu.mif.labanoro_draugai.business;

import java.util.Date;
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
import javax.transaction.TransactionSynchronizationRegistry;

@Named
@Stateful
public class PirmasBean {
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private AntrasBean antrasBean;
    
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
        return "Kuku " + new Date() + " " + toString() + " | " + antrasBean.sayKuku();
    }
    public String fillDataBase() {
        /*addType("Person.Administrator");
        addType("Person.User");*/
        
        
        return "DataBase has been filled";
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