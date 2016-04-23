package lt.vu.mif.labanoro_draugai.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.TransactionSynchronizationRegistry;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import lt.vu.mif.labanoro_draugai.entities.Service;
import lt.vu.mif.labanoro_draugai.entities.Serviceimage;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import lt.vu.mif.labanoro_draugai.entities.Type;

@Named
@Stateless
public class DatabaseManager {
    
    public DatabaseManager() {}
    
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
        fillBasicSystemParameters();
        //fillBasicHouseImages();
        //fillBasicServiceImages();
        
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
        addType("Picture", "Picture");
        addType("Picture.House", "House picture");
        addType("Picture.Service", "Service picture");
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
        addHouse("New small house", "Vilnius", "HouseReg-18", "House.Penthouse");
        addHouse("New small house", "Vilnius", "HouseReg-19", "House.Penthouse");
        addHouse("Old small house", "Vilnius", "HouseReg-20", "House.Penthouse");
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
        try {
            List<String> services = new ArrayList<String>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            services.add("ServiceReg-1");
            services.add("ServiceReg-2");
            
            addReservation("ReservationReg-1", "HouseReg-1", "Reservation", "doli@test.com", null,format.parse("2016-06-06"),format.parse("2016-06-12"));
            addReservation("ReservationReg-2", "HouseReg-1", "Reservation", "doli@test.com", services,format.parse("2016-06-27"),format.parse("2016-07-10"));
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Fills database with basic system parameters
     */
    private void fillBasicSystemParameters() {
        addSystemParameter("ServiceParameter.Test", "Test", "SystemParameter");
    }
    
     /**
     * Fills database with basic house pictures
     */
    private void fillBasicHouseImages() {
        addHouseImage("Picture.HouseReg-1.1", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-1_1.JPG", 1, "HouseReg-1", "Picture.House");
        addHouseImage("Picture.HouseReg-1.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-1_2.JPG", 2, "HouseReg-1", "Picture.House");
        addHouseImage("Picture.HouseReg-2.1", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_1.JPG", 1, "HouseReg-2", "Picture.House");
        addHouseImage("Picture.HouseReg-2.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-2", "Picture.House");
        addHouseImage("Picture.HouseReg-2.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 3, "HouseReg-2", "Picture.House");
        addHouseImage("Picture.HouseReg-3.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-3", "Picture.House");
        addHouseImage("Picture.HouseReg-3.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-3", "Picture.House");
        addHouseImage("Picture.HouseReg-4.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-4", "Picture.House");
        addHouseImage("Picture.HouseReg-4.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-4", "Picture.House");
        addHouseImage("Picture.HouseReg-5.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-5", "Picture.House");
        addHouseImage("Picture.HouseReg-5.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-5", "Picture.House");
        addHouseImage("Picture.HouseReg-6.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-6", "Picture.House");
        addHouseImage("Picture.HouseReg-6.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-6", "Picture.House");
        addHouseImage("Picture.HouseReg-7.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-7", "Picture.House");
        addHouseImage("Picture.HouseReg-7.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-7", "Picture.House");
        addHouseImage("Picture.HouseReg-8.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-8", "Picture.House");
        addHouseImage("Picture.HouseReg-8.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-8", "Picture.House");
        addHouseImage("Picture.HouseReg-9.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-9", "Picture.House");
        addHouseImage("Picture.HouseReg-9.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-9", "Picture.House");
        addHouseImage("Picture.HouseReg-10.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-10", "Picture.House");
        addHouseImage("Picture.HouseReg-10.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-10", "Picture.House");
        addHouseImage("Picture.HouseReg-11.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-11", "Picture.House");
        addHouseImage("Picture.HouseReg-11.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-11", "Picture.House");
        addHouseImage("Picture.HouseReg-12.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-12", "Picture.House");
        addHouseImage("Picture.HouseReg-12.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-12", "Picture.House");
        addHouseImage("Picture.HouseReg-13.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-13", "Picture.House");
        addHouseImage("Picture.HouseReg-13.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-13", "Picture.House");
        addHouseImage("Picture.HouseReg-14.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-14", "Picture.House");
        addHouseImage("Picture.HouseReg-14.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-14", "Picture.House");
        addHouseImage("Picture.HouseReg-15.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-15", "Picture.House");
        addHouseImage("Picture.HouseReg-15.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-15", "Picture.House");
        addHouseImage("Picture.HouseReg-16.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-16", "Picture.House");
        addHouseImage("Picture.HouseReg-16.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-16", "Picture.House");
        addHouseImage("Picture.HouseReg-17.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-17", "Picture.House");
        addHouseImage("Picture.HouseReg-17.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-17", "Picture.House");
        addHouseImage("Picture.HouseReg-18.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-18", "Picture.House");
        addHouseImage("Picture.HouseReg-18.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-18", "Picture.House");
        addHouseImage("Picture.HouseReg-19.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-19", "Picture.House");
        addHouseImage("Picture.HouseReg-19.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-19", "Picture.House");
        addHouseImage("Picture.HouseReg-20.2", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_2.JPG", 2, "HouseReg-20", "Picture.House");
        addHouseImage("Picture.HouseReg-20.3", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\House\\House-2_3.JPG", 1, "HouseReg-20", "Picture.House");
        
    }
    
    private void fillBasicServiceImages() {
        addServiceImage("Picture.ServiceReg-1.1", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\Service\\Service-1_1.JPG", 1, "ServiceReg-1", "Picture.Service");
        addServiceImage("Picture.ServiceReg-2.1", "Labanoro_Draugai\\Labanoro_Draugai\\src\\main\\resources\\Images\\Service\\Service-2_1.JPG", 1, "ServiceReg-2", "Picture.Service");

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
            System.out.println(String.format("Type '%s' created successfully", internalName));
        else
            return null;
        return newType;
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
            System.out.println(String.format("Person '%s' created successfully", firstName + ":" + lastName));
        else
            return null;
        return newPerson;
    }
    
    /**
     * Creates new house and flushes it to database.
     * Returns person entity if created sucessfully
     * 
     * @param firstName
     * @param lastName
     * @param internalName 
     */
    public House addHouse(String title, String address, String houseReg, String typeInternalName) {
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
            System.out.println(String.format("House '%s' created successfully", title));
        else
            return null;
        return newHouse;
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
            System.out.println(String.format("Service '%s' created successfully", title));
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
    private Reservation addReservation(String reservationReg, String houseReg, String typeInternalName, String personEmail, List<String> services, Date dateFrom, Date dateTo) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        House house = (House) getEntity("House", "Housereg", houseReg);
        Person person = (Person) getEntity("Person", "Email", personEmail);
        
        Reservation newReservation = new Reservation();
        newReservation.setReservationreg(reservationReg);
        newReservation.setTypeid(type);
        newReservation.setHouseid(house);
        newReservation.setPersonid(person);
        newReservation.setStartdate(dateFrom);
        newReservation.setEnddate(dateTo);
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
            System.out.println(String.format("Reservation '%s' created successfully", reservationReg));
        else
            return null;
       
        return newReservation;
    }
    
     /***
      * Creates new system parameter and flushes it to database.
      * Returns system parameter entity if created sucessfully
      * 
      * @param internalName
      * @param title
      * @param typeInternalName
      * @return 
      */
    private Systemparameter addSystemParameter(String internalName, String title, String typeInternalName){
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        
        if(type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }
        
        Systemparameter newSystemParameter = new Systemparameter();
        
        newSystemParameter.setInternalname(internalName);
        newSystemParameter.setTitle(title);
        newSystemParameter.setTypeid(type);
        
        if(entityExists("Systemparameter", "Internalname", internalName)) {
            System.out.println(String.format("System parameter with internal name '%s' already exists", internalName));
            return null;
        }
        
        if(persistAndFlush(newSystemParameter))
            System.out.println(String.format("System parameter '%s' created successfully", internalName));
        else
            return null;
        return newSystemParameter;
    }
    
    /***
     * Creates new house image and flushes it to database.
     * Returns house image entity if created sucessfully
     * 
     * @param internalName
     * @param path
     * @param sequence
     * @param houseReg
     * @param typeInternalName
     * @return 
     */
    private Houseimage addHouseImage(String internalName, String path, int sequence, String houseReg, String typeInternalName){
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
        
        Houseimage newHouseimage = new Houseimage();
        
        newHouseimage.setInternalname(internalName);
        newHouseimage.setPath(path);
        newHouseimage.setSequence(sequence);
        newHouseimage.setHouseid(house);
        newHouseimage.setTypeid(type);
        
        if(entityExists("Houseimage", "Internalname", internalName)) {
            System.out.println(String.format("House image with internal name '%s' already exists", internalName));
            return null;
        }
        
        if(persistAndFlush(newHouseimage))
            System.out.println(String.format("House image '%s' created successfully", internalName));
        else
            return null;
        return newHouseimage;
    }

    /***
     * Creates new service image and flushes it to database.
     * Returns service image entity if created sucessfully
     * 
     * @param internalName
     * @param path
     * @param sequence
     * @param serviceReg
     * @param typeInternalName
     * @return 
     */
    private Serviceimage addServiceImage(String internalName, String path, int sequence, String serviceReg, String typeInternalName){
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        Service service = (Service) getEntity("Service", "Servicereg", serviceReg);
        
        if(type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }  
        
        if(service == null) {
            System.out.println(String.format("Service with registration '%s' does not exist", serviceReg));
            return null;
        }
        
        Serviceimage newServiceimage = new Serviceimage();
        
        newServiceimage.setInternalname(internalName);
        newServiceimage.setPath(path);
        newServiceimage.setSequence(sequence);
        newServiceimage.setServiceid(service);
        newServiceimage.setTypeid(type);
        
        if(entityExists("Serviceimage", "Internalname", internalName)) {
            System.out.println(String.format("Service image with internal name '%s' already exists", internalName));
            return null;
        }
        
        if(persistAndFlush(newServiceimage))
            System.out.println(String.format("Service image '%s' created successfully", internalName));
        else
            return null;
        return newServiceimage;
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
    public boolean persistAndFlush(Object newEntity) {
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
