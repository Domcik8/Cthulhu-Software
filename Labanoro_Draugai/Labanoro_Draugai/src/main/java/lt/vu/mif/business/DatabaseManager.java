package lt.vu.mif.business;

import java.util.Date;
import java.util.List;
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
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.TransactionSynchronizationRegistry;
import lt.vu.mif.entities.House;
import lt.vu.mif.entities.Person;
import lt.vu.mif.entities.Service;
import lt.vu.mif.entities.Type;

@Named
@Stateful
public class DatabaseManager {
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    private void gimiau() {
        System.out.println(this + " has been created.");
    }
    
    @PreDestroy
    private void tuojMirsiu() {
        System.out.println(this + " has been destroyed.");
    }

    public String fillDataBase() {
        fillBasicTypes();
        fillBasicPeople();
        fillBasicHouses();
        fillBasicServices();
        
        return "DataBase has been filled";
    }

    /**
     * Fills database with basic types
     */
    public void fillBasicTypes() {
        addType("Person", "Person");
        addType("Person.Administrator", "Administrator");
        addType("Person.User", "User");
        addType("Person.Candidate", "Candidate");
        addType("House", "House");
        addType("House.Penthouse", "Penthouse");
        addType("Service", "Service");
        addType("Service.Vehicle", "Vehicle");
        addType("Service.Vehicle.Car", "Car");
        addType("Service.Vehicle.Bike", "Bike");
    }

    /**
     * Fills database with basic people
     */
    private void fillBasicPeople() {
        addPerson("doli", "Dominik", "Lisovski", "Person.Administrator");
        addPerson("erba", "Ernest", "Barkovski", "Person.Administrator");
        addPerson("erja", "Ernest", "Jascanin", "Person.Administrator");
        addPerson("kauz", "Karolis", "Uždanavičius", "Person.Administrator");
        addPerson("paru", "Paulius", "Rudzinskas", "Person.Administrator");
    }
    
    /**
     * Fills database with basic houses
     */
    private void fillBasicHouses() {
        addHouse("Old small house", "Vilnius", "HouseReg-1", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-2", "House.Penthouse");
    }
    
    /**
     * Fills database with basic services
     */
    private void fillBasicServices() {
        addService("New red lamborghini", "ServiceReg-1", "HouseReg-1", "Service.Vehicle.Car");
        addService("New blue bike", "ServiceReg-2", "HouseReg-1", "Service.Vehicle.Bike");
    }
    
    /**
     * Creates new entity type and flushes it to database.
     * Returns type entity if created sucessfully
     * 
     * @param internalName 
     */
    private void addType(String internalName) {
        addType(internalName, null, null, null);
    }
    
    /**
     * Creates new entity type and flushes it to database.
     * Returns type entity if created sucessfully
     * 
     * @param internalName
     * @param title 
     */
    private void addType(String internalName, String title) {
        addType(internalName, title, null, null);
    }
    
    /**
     * Creates new entity type and flushes it to database.
     * Returns type entity if created sucessfully
     * 
     * @param internalName
     * @param title
     * @param description 
     */
    private void addType(String internalName, String title, String description) {
        addType(internalName, title, description, null);
    }
    
    /**
     * Creates new entity type and flushes it to database.
     * Returns type entity if created sucessfully
     * 
     * @param internalName
     * @param title
     * @param description
     * @param isDeleted 
     */
    private Type addType(String internalName, String title, String description, Integer isDeleted){
        Type newType = new Type(internalName, title, description, isDeleted);
        
        if(entityExists("Type", "Internalname", internalName)) {
            System.out.println(String.format("Type with internal name '%s' already exists", internalName));
            return null;
        }
        
        if(persistAndFlush(newType))
        {
            System.out.println(String.format("Type '%s' created successfully", internalName));
            return newType;
        }
        else
            return null;
    }
    
    /**
     * Creates new person and flushes it to database.
     * Returns person entity if created sucessfully
     * 
     * @param firstName
     * @param lastName
     * @param internalName 
     */
    private Person addPerson(String userName, String firstName, String lastName, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        
        if(type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }
        
        Person newPerson = new Person();
        newPerson.setUsername(userName);
        newPerson.setFirstname(firstName);
        newPerson.setLastname(lastName);
        newPerson.setTypeid(type);
        
        if(entityExists("Person", "Username", userName)) {
            System.out.println(String.format("Person with username '%s' already exists", userName));
            return null;
        }
        
        if(persistAndFlush(newPerson))
        {
            System.out.println(String.format("Person '%s' created successfully", firstName + lastName));
            return newPerson;
        }
        else
            return null;
    }
    
    /**
     * Creates new house and flushes it to database.
     * Returns person entity if created sucessfully
     * 
     * @param firstName
     * @param lastName
     * @param internalName 
     */
    private House addHouse(String title, String address, String houseReg, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        
        if(type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }
        
        House newHouse = new House();
        newHouse.setTitle(title);
        newHouse.setAddress(address);
        newHouse.setHousereg(houseReg);
        newHouse.setTypeid(type);
        
        if(entityExists("House", "Housereg", houseReg)) {
            System.out.println(String.format("House with registration '%s' already exists", houseReg));
            return null;
        }
        
        if(persistAndFlush(newHouse))
        {
            System.out.println(String.format("House '%s' created successfully", title));
            return newHouse;
        }
        else
            return null;
    }
    
    /**
     * Creates new service and flushes it to database.
     * Returns person entity if created sucessfully
     * 
     * @param firstName
     * @param lastName
     * @param internalName 
     */
    private Service addService(String title, String serviceReg, String houseReg, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        House house = (House) getEntity("House", "Housereg", houseReg);
        
        if(type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }
        
        if(house == null) {
            System.out.println(String.format("House with registration '%s' does not exist", houseReg));
            return null;
        }
        
        Service newService = new Service();
        newService.setTitle(title);
        newService.setServicereg(serviceReg);
        newService.setTypeid(type);
        
        if(entityExists("Service", "Servicereg", serviceReg)) {
            System.out.println(String.format("Service with registration '%s' already exists", serviceReg));
            return null;
        }
        
        if(persistAndFlush(newService))
        {
            System.out.println(String.format("Service '%s' created successfully", title));
        }
        else
            return null;
        
        house.getServiceList().add(newService);
        
        return newService;
    }

     /**
      * Returns true if specified entity exists with specified parameter
      * 
      * @param className - name of entity class, should always start with upper case letter and other letters be lower case
      * @param findBy - search criteria, should always start with upper case letter and other letters be lower case
      * @param parameter - parameter value
      * @return 
      */
    private boolean entityExists(String className, String findBy, String parameter) {
        Object entity = getEntity(className, findBy, parameter);
        return entity != null ? true : false;
    }
    
    /**
      * Returns entity if specified entity exists with specified parameter
      * 
      * @param className - name of entity class, should always start with upper case letter and other letters be lower case
      * @param findBy - search criteria, should always start with upper case letter and other letters be lower case
      * @param parameter - parameter value
      * @return 
      */
    private Object getEntity(String className, String findBy, String parameter){
        Query query = em.createNamedQuery(className + ".findBy" + findBy).setParameter(decapitalize(findBy), parameter);
        
        return query.getResultList().isEmpty() ? null : query.getResultList().get(0);
    }

    /**
     * Persists and flushes entity to database.
     * Returns true if operation was successful, false otherwise.
     * Before using this method check if newEntity does not already exist in database.
     * 
     * @param newEntity 
     */
    private boolean persistAndFlush(Object newEntity) {
        try {
            em.persist(newEntity);
            em.flush();
        } catch(PersistenceException pe) {
            System.out.println(String.format("Failed to insert '%s' to database", newEntity.toString()));
            em.clear();
            return false;
        } 
        return true;
    }
    
    public static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }
}