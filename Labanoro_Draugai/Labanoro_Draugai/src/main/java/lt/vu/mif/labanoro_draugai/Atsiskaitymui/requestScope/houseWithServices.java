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
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
@ConversationScoped
public class houseWithServices implements Serializable {
    
    private static final String PAGE_INDEX = "chambertOfAtsiskaitymas?faces-redirect=true";
    private static final String PAGE_SERVICE = "serviceCreation?faces-redirect=true";
    
    @PersistenceContext(type = PersistenceContextType.EXTENDED,
                        synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;
    
    @Inject
    private ServiceService serviceService;
    
    @Inject
    private HouseService houseService;
    
    @Inject
    private Conversation conversation;

    public Conversation getConversation() {
        return conversation;
    }
    
    private Service service = new Service();
    private House house = new House();
    
    public Service getService() {
        return service;
    }

    public House getHouse() {
        return house;
    }
    
    public String createHouse() {
        if (!conversation.isTransient()) {
            conversation.end();
            return PAGE_INDEX;
        }
        conversation.begin();
        
        System.out.println(this + ": gavau EntityManager = " + em.getDelegate());
        
        house = houseService.create(house);

        return PAGE_SERVICE;
    }
    
    public String createService() {
         if (conversation.isTransient()) {
            return PAGE_INDEX;
        }
        
        serviceService.create(house, service);
        
        return PAGE_SERVICE;
        
    }

    public String save() {
        try {
            conversation.end();
            em.joinTransaction();
            em.flush();
            return PAGE_INDEX;
        } catch (OptimisticLockException ole) {
            System.out.println(this + " optLost");
            return PAGE_INDEX;
        } catch (PersistenceException pe) {
            // Kitokios bėdos su DB
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Oi ai ajajai.", "Finita la commedia")
            );
            return null;
        }
    }

    public String cancel() {
        if (conversation.isTransient()) {
            conversation.end();
        }
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
