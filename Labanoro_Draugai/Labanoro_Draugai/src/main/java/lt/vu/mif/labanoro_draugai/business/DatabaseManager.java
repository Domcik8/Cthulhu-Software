package lt.vu.mif.labanoro_draugai.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.TransactionSynchronizationRegistry;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import lt.vu.mif.labanoro_draugai.entities.Service;
import lt.vu.mif.labanoro_draugai.entities.Type;

@Named
@Stateful
@SessionScoped
public class DatabaseManager {
    
    @Resource
    private TransactionSynchronizationRegistry tx;
    
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    private void onCreation() {
        System.out.println(this + " has been created.");
    }
    
    @PreDestroy
    private void onDeletion() {
        System.out.println(this + " has been destroyed.");
    }

    public String fillDataBase() {
        fillBasicTypes();
        fillBasicPeople();
        fillBasicHouses();
        fillBasicServices();
        fillBasicReservations();
        
        return "DataBase has been filled";
    }

    /**
     * Fills database with basic types
     */
    public void fillBasicTypes() {
        addType("SystemParameter", "SystemParameter");
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
        addType("Reservation", "Reservation");
    }

    /**
     * Fills database with basic people
     */
    private void fillBasicPeople() {
        addPerson("doli@test.com", "Dominik", "Lisovski", "Person.Administrator");
        addPerson("erba@test.com", "Ernest", "Barkovski", "Person.Administrator");
        addPerson("erja@test.com", "Ernest", "Jascanin", "Person.Administrator");
        addPerson("kauz@test.com", "Karolis", "Uždanavičius", "Person.Administrator");
        addPerson("paru@test.com", "Paulius", "Rudzinskas", "Person.Administrator");
    }
    
    /**
     * Fills database with basic houses
     */
    private void fillBasicHouses() {
        addHouse("Old small house", "Vilnius", "HouseReg-1", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-2", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-3", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-4", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-5", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-6", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-7", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-8", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-9", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-10", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-11", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-12", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-13", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-14", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-15", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-16", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-17", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-19", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-20", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-21", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-22", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-23", "House.Penthouse");
    }
    
    /**
     * Fills database with basic services
     */
    private void fillBasicServices() {
        addService("New red lamborghini", "ServiceReg-1", "HouseReg-1", "Service.Vehicle.Car");
        addService("New blue bike", "ServiceReg-2", "HouseReg-1", "Service.Vehicle.Bike");
    }
    
    /**
     * Fills database with basic reservations
     */
    private void fillBasicReservations() {
        List<String> services = new ArrayList<String>();
        services.add("ServiceReg-1");
        services.add("ServiceReg-2");
        
        addReservation("ReservationReg-1", "HouseReg-1", "Reservation", "doli@test.com", null);
        addReservation("ReservationReg-2", "HouseReg-1", "Reservation", "doli@test.com", services);
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
    private Type addType(String internalName, String title){
        Type newType = new Type();
        
        newType.setInternalname(internalName);
        newType.setTitle(title);
        
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
    public Person addPerson(String email, String firstName, String lastName, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        
        if(type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }
        
        Person newPerson = new Person();
        newPerson.setEmail(email);
        newPerson.setFirstname(firstName);
        newPerson.setLastname(lastName);
        newPerson.setTypeid(type);
        
        if(entityExists("Person", "Email", email)) {
            System.out.println(String.format("Person with email '%s' already exists", email));
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
    
    /***
     * Creates new service and flushes it to database.
     * Returns entity if created sucessfully
     * 
     * @param title
     * @param serviceReg
     * @param houseReg
     * @param typeInternalName
     * @return 
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
    
    /***
     * Creates new service and flushes it to database.
     * Returns entity if created sucessfully
     * 
     * @param reservationReg
     * @param houseReg
     * @param typeInternalName
     * @param services
     * @return 
     */
    private Reservation addReservation(String reservationReg, String houseReg, String typeInternalName, String personEmail, List<String> services) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        House house = (House) getEntity("House", "Housereg", houseReg);
        Person person = (Person) getEntity("Person", "Email", personEmail);
        
        Reservation newReservation = new Reservation();
        newReservation.setReservationreg(reservationReg);
        newReservation.setTypeid(type);
        newReservation.setHouseid(house);
        newReservation.setPersonid(person);
        newReservation.setServiceList(new ArrayList<Service>());
        
        if(type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }
        
        if(house == null) {
            System.out.println(String.format("House with registration '%s' does not exist", houseReg));
            return null;
        }
        
        if(person == null) {
            System.out.println(String.format("Person with email '%s' does not exist", personEmail));
            return null;
        }
        
        if(services != null)
            for(String serviceReg: services){
                Service service = (Service) getEntity("Service", "Servicereg", serviceReg);
                if(service == null)
                {
                    System.out.println(String.format("Service with registration '%s' does not exist", serviceReg));
                    return null;  
                }
                else 
                {
                    newReservation.getServiceList().add(service);
                }
            }
        
        if(entityExists("Reservation", "Reservationreg", reservationReg)) {
            System.out.println(String.format("Reservation with registration '%s' already exists", reservationReg));
            return null;
        }
        
        if(persistAndFlush(newReservation))
        {
            System.out.println(String.format("Reservation '%s' created successfully", reservationReg));
        }
        else
            return null;
       
        return newReservation;
    }

     /**
      * Returns true if specified entity exists with specified parameter
      * 
      * @param className - name of entity class, should always start with upper case letter and other letters be lower case
      * @param findBy - search criteria, should always start with upper case letter and other letters be lower case
      * @param parameter - parameter value
      * @return 
      */
    public boolean entityExists(String className, String findBy, String parameter) {
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
    public Object getEntity(String className, String findBy, String parameter){
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
    
    public void editHouses(){
        Random rand = new Random();
        Query query = em.createNamedQuery("House.findAll");
        List<House> houses=query.getResultList();
        for(House house:houses){
            house.setTitle(house.getTitle()+rand.nextInt(1000));
            house.setIsactive(true);
            //house.setSeasonstartdate(new Date());
            //house.setSeasonenddate(new Date(2017,04,11));
            house.setIsdeleted(false);
            house.setNumberofplaces(rand.nextInt(30));
            house.setWeekprice(rand.nextInt(800));
            persistAndFlush(house);
        }
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