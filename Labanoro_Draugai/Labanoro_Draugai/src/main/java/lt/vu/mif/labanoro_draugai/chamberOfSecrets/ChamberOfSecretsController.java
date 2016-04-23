package lt.vu.mif.labanoro_draugai.chamberOfSecrets;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Service;

/**
 * Created not by donat not on 2016-04-13.
 */
@Named
@ConversationScoped
@Stateful
public class ChamberOfSecretsController implements Serializable {

    private static final String PAGE_INDEX          = "chamberOfSecrets?faces-redirect=true";
    private static final String PAGE_CONFIRM        = "confirm?faces-redirect=true";

    @PersistenceContext(type = PersistenceContextType.EXTENDED, synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;

    @Inject
    private Conversation conversation;

    @Inject
    private HouseService houseService;
    
    @Inject
    private ServiceService serviceService;

    private House house = new House();
    private Service service = new Service();

    public Conversation getConversation() {
        return conversation;
    }

    public House getHouse() {
        return house;
    }

    public Service getService() {
        return service;
    }

    public String createHouse() {
        if (!conversation.isTransient()) {
            conversation.end();
            return PAGE_INDEX;
        }

        conversation.begin();
        house = houseService.create(house);

        return PAGE_CONFIRM;
    }

    public String ok() {
        try {
            conversation.end();
            em.joinTransaction();
            em.flush();
            return PAGE_INDEX;
        } catch (OptimisticLockException ole) {
            // Kažkas kitas buvo greitesnis...
            return null;
        } catch (PersistenceException pe) {
            // Kitokios bėdos su DB
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Oi ai ajajai.", "Finita la commedia")
            );
            return null;
        }
    }

    public String cancel() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return PAGE_INDEX;
    }

}
