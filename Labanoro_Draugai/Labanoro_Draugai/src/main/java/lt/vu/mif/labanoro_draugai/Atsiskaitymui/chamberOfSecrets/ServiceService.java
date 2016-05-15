package lt.vu.mif.labanoro_draugai.Atsiskaitymui.chamberOfSecrets;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;
import lt.vu.mif.labanoro_draugai.entities.Service;

/**
 * Created not by donat not on 2016-04-13.
 */
@Stateless
public class ServiceService {
    @PersistenceContext(synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;

    public void create(Service service) {
        em.persist(service);
    }
}
