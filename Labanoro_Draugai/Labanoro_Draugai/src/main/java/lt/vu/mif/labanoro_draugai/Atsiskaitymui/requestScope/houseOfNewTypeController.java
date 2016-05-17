package lt.vu.mif.labanoro_draugai.Atsiskaitymui.requestScope;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.transaction.TransactionSynchronizationRegistry;
import lt.vu.mif.labanoro_draugai.Atsiskaitymui.chamberOfSecrets.*;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Service;
import lt.vu.mif.labanoro_draugai.entities.Type;

/**
 * Created not by donat not on 2016-04-13.
 */
@Named
@Stateful
@RequestScoped
public class houseOfNewTypeController implements Serializable {

    private static final String PAGE_INDEX = "chambertOfAtsiskaitymas?faces-redirect=true";

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private TypeService typeService;
    
    @Inject
    private HouseService houseService;
    
    private Type type = new Type();
    private House house = new House();
    
    public Type getType() {
        return type;
    }

    public House getHouse() {
        return house;
    }

    public String createHouse() {
        em.isOpen(); 
        System.out.println(this + ": gavau EntityManager = " + em.getDelegate());
        type = typeService.create(type);
        house = houseService.create(house, type);
        return PAGE_INDEX;
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