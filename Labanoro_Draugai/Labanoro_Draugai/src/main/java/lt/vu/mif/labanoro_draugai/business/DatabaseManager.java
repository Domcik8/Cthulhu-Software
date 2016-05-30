package lt.vu.mif.labanoro_draugai.business;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.TransactionSynchronizationRegistry;
import lt.vu.mif.labanoro_draugai.data_models.AdminUserFormProperty;
import lt.vu.mif.labanoro_draugai.entities.*;
import org.apache.commons.io.IOUtils;

@Named
@Stateless
public class DatabaseManager {

    public DatabaseManager() {
    }

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
        fillBasicPayments();
        fillBasicReservations();
        fillBasicSystemParameters();
        fillBasicHouseImages();
        fillBasicRecommendations();

        editPeople();
        editHouses();
        editServices();
        return "DataBase has been filled and have been houses edited";
    }

    /**
     * Fills database with basic types
     */
    public void fillBasicTypes() {
        addType("SystemParameter", "Sistemos parametras");
        addType("Person", "Asmuo");
        addType("Person.Administrator", "Administratorius");
        addType("Person.User", "Vartotojas");
        addType("Person.Candidate", "Kandidatas");
        addType("Recommendation", "Rekomentacija");
        addType("House", "Namas");
        addType("House.Penthouse", "Mansarda");
        addType("Service", "Paslauga");
        addType("Service.Vehicle", "Transporto priemonė");
        addType("Service.Vehicle.Car", "Automobilis");
        addType("Service.Vehicle.Bike", "Dviratis");
        addType("Reservation", "Rezervacija");
        addType("Picture", "Nuotrauka");
        addType("Picture.House", "Namo nuotrauka");
        addType("FormElement", "Formos elementas");
        addType("FormElement.Calendar", "Kalendorius");
        addType("FormElement.Input", "Teksto laukas");
        addType("FormElement.Select", "Pasirinkti vieną");
        addType("FormElement.Textarea", "Didelis teksto laukas");
        addType("FormElement.Number", "Skaičius");
        addType("Form.Person", "Forma");
        addType("Payment", "Mokėjimas");
        addType("Payment.Reservation", "Rezervacija");
        addType("Payment.Points", "Taškų pirkimas");
        addType("Payment.Membership", "Klubo narystės mokestis");
        addType("Currency", "Valiuta");
        addType("Currency.Points", "Taškai");
        addType("Currency.Euro", "Eurai");
    }

    /**
     * Fills database with basic people
     */
    public void fillBasicPeople() {
        addPerson("System", "admin", "System", "System", "Person.Administrator");
        addPerson("doli@test.com", "admin", "Dominik", "Lisovski", "Person.Administrator");
        addPerson("erba@test.com", "admin", "Ernest", "Barkovski", "Person.Administrator");
        addPerson("erja@test.com", "admin", "Ernest", "Jascanin", "Person.Administrator");
        addPerson("kauz@test.com", "admin", "Karolis", "Uždanavičius", "Person.Administrator");
        addPerson("paru@test.com", "admin", "Paulius", "Rudzinskas", "Person.Administrator");
        addPerson("admin", "admin", "admin", "admin", "Person.Administrator");
        addPerson("can", "admin", "can", "can", "Person.Candidate");
        addPerson("user", "admin", "user", "user", "Person.User");
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
        for (int i = 2; i <= 20; i++) {
            addService("coolSerice" + i, "ServiceReg-" + i, "HouseReg-" + i, "Service.Vehicle.Bike");
        }
    }

    /**
     * Fills database with basic reservations
     */
    private void fillBasicReservations() {
        if (getAllEntities("Reservation").isEmpty()) {
            try {
                List<String> services = new ArrayList<String>();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                services.add("ServiceReg-1");
                services.add("ServiceReg-2");

                addReservation("HouseReg-1", "DefaultPayment", "Reservation", "doli@test.com", null, format.parse("2016-06-06"), format.parse("2016-06-12"));
                addReservation("HouseReg-1", "DefaultPayment", "Reservation", "doli@test.com", services, format.parse("2016-06-27"), format.parse("2016-07-10"));
                addReservation("HouseReg-1", "DefaultPayment", "Reservation", "doli@test.com", null, format.parse("2016-07-11"), format.parse("2016-07-17"));
                addReservation("HouseReg-2", "DefaultPayment", "Reservation", "doli@test.com", null, format.parse("2016-06-06"), format.parse("2016-06-12"));
                addReservation("HouseReg-2", "DefaultPayment", "Reservation", "doli@test.com", services, format.parse("2016-06-27"), format.parse("2016-07-10"));
                addReservation("HouseReg-2", "DefaultPayment", "Reservation", "doli@test.com", null, format.parse("2016-07-11"), format.parse("2016-07-17"));
                addReservation("HouseReg-3", "DefaultPayment", "Reservation", "erba@test.com", null, format.parse("2016-07-11"), format.parse("2016-07-17"));

            } catch (ParseException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No reservations were created, because system already has some");
        }
    }

    /**
     * Fills database with basic system parameters
     */
    private void fillBasicSystemParameters() {
        addSystemParameter("SystemParameter.BuyPoints", "Taškų kainos eurais", "5;10;15;20", "Esamos sistemos taškų kainos, kurios yra nesusijųsios su gaunamu taškų kiekiu. Naujos įvesties pvz.: (5;)", "SystemParameter");

        addSystemParameter("SystemParameter.ExchangeRate.Euro", "Taškų kursas lyginant su euru", "1", "Pinigai * Kursas = Taškai", "SystemParameter");
        addSystemParameter("SystemParameter.Currency.Euro", "Euro valiutos simbolis", "€", "SystemParameter");
        addSystemParameter("SystemParameter.Membership.Price", "Narystės mokesčio kaina eurais.", "20.00", "SystemParameter");
        addSystemParameter("SystemParameter.Membership.YearLimit", "Narystės metų limitas", "0", "Keliems metams į priekį galima turėti narystės mokestį.(0 - galima užsisakyti tik pasibaigus narystei, 1 - galima užsisakyti turint ne daugiau kaip metus narystės ir t.t.)", "SystemParameter");

        addSystemParameter("SystemParameter.General.ContextPath", "Pagrindinis kelias", "http://localhost:8080/Labanoro_Draugai", "Pagrindines puslapio URL'as", "SystemParameter");

        addSystemParameter("SystemParameter.RequiredRecommendations", "Reikalingų rekomendacijų skaičius", "2", "SystemParameter");
        addSystemParameter("SystemParameter.MaxRecommendations", "Maksimalus rekomendacijų užklausų skaičius", "5", "SystemParameter");

        addSystemParameter("SystemParameter.priorityGroup.Month", "Prioriteto grupės, perskaičiavimo laikas: mėnuo", "3,6,9,12", "SystemParameter");
        addSystemParameter("SystemParameter.priorityGroup.DayOfTheMonth", "Prioriteto grupės, perskaičiavimo laikas: mėnesio diena", "1", "SystemParameter");
        addSystemParameter("SystemParameter.priorityGroup.HourOfTheDay", "Prioriteto grupės, perskaičiavimo laikas: dienos valanda", "22", "SystemParameter");
        addSystemParameter("SystemParameter.priorityGroup.SeasonLength", "Prioriteto grupės, suskirstimo laikotarpis mėnesiais", "3", "SystemParameter");
        addSystemParameter("SystemParameter.priorityGroup.Quantity", "Prioriteto grupės, grupių skaičius", "12", "SystemParameter");

        addSystemParameter("SystemParameter.StripeTestSecretKey", "Stripe testinis slaptas raktas", "sk_test_GkbxvPwRpIG9T4aIiruw0TWl", "SystemParameter");
        addSystemParameter("SystemParameter.StripeTestPublishableKey", "Stripe testinis viešas raktas", "pk_test_NuMXQ4crxg12CBo9NxrjSO63", "SystemParameter");
        addSystemParameter("SystemParameter.StripeLiveSecretKey", "Stripe tikras slaptas raktas", "sk_live_nSRfu4JPpcgYkpSlOjknfBk7", "SystemParameter");
        addSystemParameter("SystemParameter.StripeLivePublishableKey", "Stripe tikras viešas raktas", "pk_live_Q7f9zGXVZ1877SD3DfsCbsgd", "SystemParameter");

        addSystemParameter("SystemParameter.TermsAndConditions", "Nuostatos ir sąlygos", "Naudodamiesi mūsų sistema jūs sutinkate, kad \"Labanoro draugai\" nėra atsakingi už sistmos nesklandumus ar kitus žalingus incidentus.", "SystemParameter");

        addSystemParameter("SystemParameter.Facebook.AppId", "FB aplikacijos kodas", "198659840500311", "Facebook užregistruotos aplikacijos kodas ", "SystemParameter");
        addSystemParameter("SystemParameter.Facebook.AppSecret", "FB aplikacijos slaptas kodas", "97d6fc7c788463e2de89f1571385cc75", "Kodas skirtas autentifikuotis užregistruotoje Facebook aplikacijoje", "SystemParameter");
        addSystemParameter("SystemParameter.Facebook.Redirect", "FB autentifikacija", "http://localhost:8080/Labanoro_Draugai", "Nukreipimas į puslapį facebook autentifikacijos metu", "SystemParameter");

        addSystemParameter("SystemParameter.Redirect.Login", "Sėkmingas prisijungimas", "/index.html", "Nukreipimas į puslapį po sėkmingo prisijungimo", "SystemParameter");
        addSystemParameter("SystemParameter.Redirect.Register", "Registracijos forma", "/register.html", "Nukreipimas į paskyros sūkurimo bei kandidato anketos pildymo puslapį", "SystemParameter");
        addSystemParameter("SystemParameter.Redirect.LoginError", "Klaidingas prisijungimas", "/loginError.html", "Nukreipimas į puslapį po nesėkmingo prisijungimo", "SystemParameter");
        addSystemParameter("SystemParameter.Redirect.LoginSuccess", "Sėkmingas prisijungimas", "/index.html", "Nukreipimas į puslapį po sėkmingo prisijungimo", "SystemParameter");
        addSystemParameter("SystemParameter.Redirect.GlobalError", "Globali klaida", "/WEB-INF/other_pages/someError.html", "Nukreipimas į puslapį po globalios klaidos", "SystemParameter");
        addSystemParameter("SystemParameter.Redirect.MyReservations", "Sėkminga rezervacija", "/Reservation/myReservations.html", "Nukreipimas į puslapį po sėkmingos rezervacijos", "SystemParameter");
        addSystemParameter("SystemParameter.Redirect.Buy", "Sėkmingas taškų pirkimas", "/user/profile.html", "Nukreipimas į puslapį po sėkmingo taškų pirkimo", "SystemParameter");
        addSystemParameter("SystemParameter.Redirect.Index", "Nukreipimas į pagrindinį puslapį", "/index.html", "Nukreipimas į puslapį po sėkmingos operacijos", "SystemParameter");

        addSystemParameter("SystemParameter.Reservation.MinDaysBeforeCancel", "Minimalus skaičius dienų, kai dar galima atšaukti rezervaciją.", "7", "", "SystemParameter");

        addSystemParameter("SystemParameter.Mail.Address", "Gmail el.paštas", "labanorai@gmail.com", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Password", "Gmail el.pašto slaptažodis", "LabanoroDraugas", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.host", "Smtp hostas", "smtp.gmail.com", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.port", "Smtp portas", "587", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.auth", "Smtp autentifikacija", "true", "Ar reikalinga autentifikacija prisijungimui", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.starttls.enable", "Smtp TLS", "true", "Ar TLS turi būti įjungtas", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.ssl.trust", "Smtp Trust", "smtp.gmail.com", "Ar patikimas hostas", "SystemParameter");

        addSystemParameter("SystemParameter.Houseimage.Width", "Nuotraukos plotis", "600", "Namelio nuotraukų plotis įkėlimo metu. Jeigu reikšmė 0, tai nuotraukos plotis bus nustatytas pagal jos aukštį.", "SystemParameter");
        addSystemParameter("SystemParameter.Houseimage.Height", "Nuotraukos aukštis", "0", "Namelio nuotraukų aukštis įkėlimo metu. Jeigu reikšmė 0, tai nuotraukos aukštis bus nustatytas pagal jos plotį.", "SystemParameter");

        addSystemParameter("SystemParameter.Index.Title1", "Pagrindinio puslapio antraštė", "Klubas \"Labanoro Draugai\"", "Pagrindinio puslapio pirmosios sekcijos antraštė.", "SystemParameter");
        addSystemParameter("SystemParameter.Index.Description1", "Pagrindinio puslapio pirmos sekcijos turinys", "Labanoro regioninis parkas – regioninis parkas rytinėje Lietuvos dalyje, Švenčionių, Molėtų ir Utenos rajonuose. Parko direkcija įsikūrusi Labanoro miestelyje. #Parko teritorija užima 55344 ha (kitais duomenimis – 52848 ha) plotą.", "Pagrindinio puslapio pirmosios sekcijos turinys. Jis rodomas po pirmos antraštės. Naujos eilutės simbolis: #", "SystemParameter");
        addSystemParameter("SystemParameter.Index.Title2", "Pagrindinio antros sekcijos antraštė", "Apie mus", "Pagrindinio puslapio pirmosios sekcijos antraštė.", "SystemParameter");
        addSystemParameter("SystemParameter.Index.Description2", "Pagrindinio puslapio antros sekcijos turinys", "Mūsų klubo nariai - gamtos mėgėjai, kurie noriai savo poilsiui renkasi Labanoro girią! Šis puslapis surenka bendraminčius ir palengviną jų poilsio organizavimą.", "Pagrindinio puslapio pirmosios sekcijos turinys. Jis rodomas po antros antraštės. Naujos eilutės simbolis: #", "SystemParameter");
        addSystemParameter("SystemParameter.Index.Title3", "Pagrindinio trečios sekcijos antraštė", "Kontaktai", "Pagrindinio puslapio pirmosios sekcijos antraštė.", "SystemParameter");
        addSystemParameter("SystemParameter.Index.Description3", "Pagrindinio puslapio trečios sekcijos turinys", "Mūsų būstinės adresas: Būstinės g. 111 #Telefonas: +37060000000 #Administratoriaus el. pašto adresas: labanorai@gmail.com", "Pagrindinio puslapio pirmosios sekcijos turinys. Jis rodomas po trečios antraštės. Naujos eilutės simbolis: #", "SystemParameter");
    }

    /**
     * Fills database with basic recommendations
     */
    private void fillBasicRecommendations() {
        addRecommendation("doli@test.com", "erba@test.com", new Date(), "Recommendation");
        addRecommendation("doli@test.com", "erba@test.com", "Recommendation");
    }

    public void fillBasicRecommendations1() {
        addRecommendation("admin", "doli@test.com", "Recommendation");
        addRecommendation("admin", "erba@test.com", "Recommendation");
        addRecommendation("admin", "erja@test.com", "Recommendation");
        addRecommendation("admin", "kauz@test.com", "Recommendation");
        addRecommendation("admin", "paru@test.com", "Recommendation");
        addRecommendation("admin", "can", "Recommendation");
        addRecommendation("admin", "user", "Recommendation");
        addRecommendation("can", "doli@test.com", "Recommendation");
        addRecommendation("can", "erba@test.com", "Recommendation");
    }

    /**
     * Fills database with basic house pictures
     */
    private void fillBasicHouseImages() {
        for (int i = 2; i <= 20; i++) {
            addHouseImage("Picture.HouseReg-" + i + "_1", "Images/House/House-1_1.JPG", 1, "HouseReg-" + i, "Picture.House");
        }
        addHouseImage("Picture.HouseReg-1_1", "Images/House/House-2_1.JPG", 1, "HouseReg-1", "Picture.House");
        addHouseImage("Picture.HouseReg-1_2", "Images/House/House-2_2.JPG", 2, "HouseReg-1", "Picture.House");
        addHouseImage("Picture.HouseReg-1_3", "Images/House/House-2_3.JPG", 3, "HouseReg-1", "Picture.House");
        addHouseImage("Picture.HouseReg-2_2", "Images/House/House-1_2.JPG", 2, "HouseReg-2", "Picture.House");
    }

    /**
     * Fills database with basic payments
     */
    private void fillBasicPayments() {
        addPayment("DefaultPayment", "System", BigDecimal.ZERO, null, "Payment.Points", "Currency.Points");
    }

    /**
     * Creates new entity type and flushes it to database. Returns type entity
     * if created sucessfully
     */
    private Type addType(String internalName, String title) {
        Type newType = new Type();

        newType.setInternalname(internalName);
        newType.setTitle(title);

        if (entityExists("Type", "Internalname", internalName)) {
            System.out.println(String.format("Type with internal name '%s' already exists", internalName));
            return null;
        }

        if (persistAndFlush(newType)) {
            System.out.println(String.format("Type '%s' created successfully", internalName));
        } else {
            return null;
        }
        return newType;
    }

    /**
     * Creates new person and flushes it to database. Returns person entity if
     * created sucessfully
     */
    public Person addPerson(String email, String password, String firstName, String lastName, String typeInternalName) {
        Person person = addPerson(email, firstName, lastName, typeInternalName);
        if (person != null) {
            String hashedPassoword = Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
            hashedPassoword = Hashing.sha256().hashString(hashedPassoword, Charsets.UTF_8).toString();
            person.setPassword(hashedPassoword);
        }

        return person;
    }

    /**
     * Creates new person and flushes it to database. Returns person entity if
     * created sucessfully
     */
    public Person addPerson(String email, String firstName, String lastName, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }

        Person newPerson = new Person();
        newPerson.setEmail(email);
        newPerson.setFirstname(firstName);
        newPerson.setLastname(lastName);
        newPerson.setTypeid(type);

        if (entityExists("Person", "Email", email)) {
            System.out.println(String.format("Person with email '%s' already exists", email));
            return null;
        }

        if (persistAndFlush(newPerson)) {
            System.out.println(String.format("Person '%s' created successfully", firstName + " " + lastName));
        } else {
            return null;
        }
        return newPerson;
    }

    /**
     * Creates new house and flushes it to database. Returns person entity if
     * created sucessfully
     */
    public House addHouse(String title, String address, String typeInternalName) {
        return addHouse(title, address, generateReg("HouseReg"), typeInternalName);
    }

    /**
     * Creates new house and flushes it to database. Returns person entity if
     * created sucessfully
     */
    public House addHouse(String title, String address, String houseReg, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }

        House newHouse = new House();
        newHouse.setTitle(title);
        newHouse.setAddress(address);
        newHouse.setHousereg(houseReg);
        newHouse.setTypeid(type);

        if (entityExists("House", "Housereg", houseReg)) {
            System.out.println(String.format("House with registration '%s' already exists", houseReg));
            return null;
        }

        if (persistAndFlush(newHouse)) {
            System.out.println(String.format("House '%s' created successfully", title));
        } else {
            return null;
        }
        return newHouse;
    }

    /**
     * Creates new service and flushes it to database. Returns entity if created
     * sucessfully
     */
    private Service addService(String title, String houseReg, String typeInternalName) {
        return addService(title, generateReg("ServiceReg"), houseReg, typeInternalName);
    }

    /**
     * Creates new service and flushes it to database. Returns entity if created
     * sucessfully
     */
    private Service addService(String title, String serviceReg, String houseReg, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        House house = (House) getEntity("House", "Housereg", houseReg);

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }

        if (house == null) {
            System.out.println(String.format("House with registration '%s' does not exist", houseReg));
            return null;
        }

        Service newService = new Service();
        newService.setTitle(title);
        newService.setServicereg(serviceReg);
        newService.setTypeid(type);

        if (entityExists("Service", "Servicereg", serviceReg)) {
            System.out.println(String.format("Service with registration '%s' already exists", serviceReg));
            return null;
        }

        if (persistAndFlush(newService)) {
            System.out.println(String.format("Service '%s' created successfully", title));
        } else {
            return null;
        }

        house.getServiceList().add(newService);

        return newService;
    }

    /**
     * Creates new service and flushes it to database. Returns entity if created
     * sucessfully
     */
    public Reservation addReservation(String houseReg, String paymentReg, String typeInternalName, String personEmail, List<String> services, Date dateFrom, Date dateTo) {
        return addReservation(generateReg("ResReg"), houseReg, paymentReg, typeInternalName, personEmail, services, dateFrom, dateTo);
    }

    /**
     * Creates new service and flushes it to database. Returns entity if created
     * sucessfully
     */
    public Reservation addReservation(String reservationReg, String houseReg, String paymentReg, String typeInternalName, String personEmail, List<String> services, Date dateFrom, Date dateTo) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        House house = (House) getEntity("House", "Housereg", houseReg);
        Person person = (Person) getEntity("Person", "Email", personEmail);
        Payment payment = (Payment) getEntity("Payment", "Paymentreg", paymentReg);

        Reservation newReservation = new Reservation();
        newReservation.setReservationreg(reservationReg);
        newReservation.setTypeid(type);
        newReservation.setHouseid(house);
        newReservation.setPersonid(person);
        newReservation.setStartdate(dateFrom);
        newReservation.setEnddate(dateTo);
        newReservation.setServiceList(new ArrayList<Service>());
        newReservation.setPaymentid(payment);
        newReservation.setIsdeleted(Boolean.FALSE);

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }

        if (house == null) {
            System.out.println(String.format("House with registration '%s' does not exist", houseReg));
            return null;
        }

        if (person == null) {
            System.out.println(String.format("Person with email '%s' does not exist", personEmail));
            return null;
        }

        if (payment == null) {
            System.out.println(String.format("Payment with registration '%s' does not exist", paymentReg));
            return null;
        }

        if (services != null) {
            for (String serviceReg : services) {
                Service service = (Service) getEntity("Service", "Servicereg", serviceReg);
                if (service == null) {
                    System.out.println(String.format("Service with registration '%s' does not exist", serviceReg));
                    return null;
                } else {
                    newReservation.getServiceList().add(service);
                }
            }
        }

        if (entityExists("Reservation", "Reservationreg", reservationReg)) {
            System.out.println(String.format("Reservation with registration '%s' already exists", reservationReg));
            return null;
        }

        if (persistAndFlush(newReservation)) {
            System.out.println(String.format("Reservation '%s' created successfully", reservationReg));
        } else {
            return null;
        }

        return newReservation;
    }

    /**
     * Creates new system parameter and flushes it to database. Returns system
     * parameter entity if created sucessfully
     */
    private Systemparameter addSystemParameter(String internalName, String title, String value, String description, String typeInternalName) {
        Systemparameter newSystemParameter = addSystemParameter(internalName, title, value, typeInternalName);
        if (newSystemParameter != null) {
            newSystemParameter.setDescription(description);
        }
        return newSystemParameter;
    }

    /**
     * Creates new system parameter and flushes it to database. Returns system
     * parameter entity if created sucessfully
     */
    private Systemparameter addSystemParameter(String internalName, String title, String value, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }

        Systemparameter newSystemParameter = new Systemparameter();

        newSystemParameter.setInternalname(internalName);
        newSystemParameter.setTitle(title);
        newSystemParameter.setValue(value);
        newSystemParameter.setTypeid(type);

        if (entityExists("Systemparameter", "Internalname", internalName)) {
            System.out.println(String.format("System parameter with internal name '%s' already exists", internalName));
            return null;
        }

        if (persistAndFlush(newSystemParameter)) {
            System.out.println(String.format("System parameter '%s' created successfully", internalName));
        } else {
            return null;
        }
        return newSystemParameter;
    }

    /**
     * Creates new house image and flushes it to database. Returns house image
     * entity if created sucessfully
     */
    public Houseimage addHouseImage(String internalName, String path, int sequence, String houseReg, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        House house = (House) getEntity("House", "Housereg", houseReg);

        Houseimage newHouseimage = new Houseimage();

        newHouseimage.setInternalname(internalName);
        newHouseimage.setImage(getImageFromResources(path));
        newHouseimage.setSequence(sequence);
        newHouseimage.setHouseid(house);
        newHouseimage.setTypeid(type);
        newHouseimage.setMimetype(getImageMimeType(path));

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }

        if (house == null) {
            System.out.println(String.format("House with registration '%s' does not exist", houseReg));
            return null;
        }

        if (newHouseimage.getImage() == null) {
            System.out.println(String.format("Could't find file at '%s'", path));
            return null;
        }

        if (newHouseimage.getMimetype() == null) {
            System.out.println(String.format("Could not define Mime type of file '%s'", path));
            return null;
        }

        if (entityExists("Houseimage", "Internalname", internalName)) {
            System.out.println(String.format("House image with internal name '%s' already exists", internalName));
            return null;
        }

        if (persistAndFlush(newHouseimage)) {
            System.out.println(String.format("House image '%s' created successfully", internalName));
        } else {
            return null;
        }

        return newHouseimage;
    }

    /**
     * Creates new recommendation and flushes it to database. Returns
     * recommendation entity if created sucessfully
     */
    public Recommendation addRecommendation(String recommenderEmail, String recommendedEmail, String typeInternalName) {
        return addRecommendation(recommenderEmail, recommendedEmail, null, typeInternalName);
    }

    /**
     * Creates new recommendation and flushes it to database. Returns
     * recommendation entity if created sucessfully
     */
    public Recommendation addRecommendation(String recommenderEmail, String recommendedEmail, Date date, String typeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        Person recommender = (Person) getEntity("Person", "Email", recommenderEmail);
        Person recommended = (Person) getEntity("Person", "Email", recommendedEmail);

        Recommendation newRecommendation = new Recommendation();

        newRecommendation.setTypeid(type);
        newRecommendation.setRecommendedid(recommended);
        newRecommendation.setRecommenderid(recommender);
        newRecommendation.setRecommendationdate(date);

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }

        if (recommender == null) {
            System.out.println(String.format("Person with email '%s' does not exist", recommenderEmail));
            return null;
        }

        if (recommended == null) {
            System.out.println(String.format("Person with email '%s' does not exist", recommendedEmail));
            return null;
        }

        Query query = em.createQuery("SELECT r FROM Recommendation r WHERE r.recommendedid = :recommendedID AND r.recommenderid = :recommenderID");
        query.setParameter("recommendedID", recommended);
        query.setParameter("recommenderID", recommender);

        if (!query.getResultList().isEmpty()) {
            System.out.println(String.format("Recommendation from '%s' to '%s' already exists", recommenderEmail, recommendedEmail));
            return null;
        }

        if (persistAndFlush(newRecommendation)) {
            System.out.println(String.format("Recommendation from '%s' to '%s' created successfully", recommenderEmail, recommendedEmail));
        } else {
            return null;
        }

        return newRecommendation;
    }

    public Recommendation getRecommendation(String recommenderEmail, String recommendedEmail) {

        Recommendation recommendation = null;

        Person recommender = (Person) getEntity("Person", "Email", recommenderEmail);
        Person recommended = (Person) getEntity("Person", "Email", recommendedEmail);

        Query query = em.createQuery("SELECT r FROM Recommendation r WHERE r.recommendedid = :recommendedID AND r.recommenderid = :recommenderID");
        query.setParameter("recommendedID", recommended);
        query.setParameter("recommenderID", recommender);

        if (!query.getResultList().isEmpty()) {
            System.out.println(String.format("Recommendation from '%s' to '%s' already exists, returning result", recommenderEmail, recommendedEmail));
            return (Recommendation) query.getResultList().get(0);
        }

        return recommendation;
    }

    /**
     * Creates new payment and flushes it to database. Returns payment entity if
     * created sucessfully
     */
    public Payment addPayment(String payerEmail, BigDecimal paymentPrice, Date paymentDate, String typeInternalName, String currencyTypeInternalName) {
        return addPayment(generateReg("PaymentReg"), payerEmail, paymentPrice, paymentDate, typeInternalName, currencyTypeInternalName);
    }

    /**
     * Creates new payment and flushes it to database. Returns payment entity if
     * created sucessfully
     */
    public Payment addPayment(String paymentReg, String payerEmail, BigDecimal paymentPrice, Date paymentDate, String typeInternalName, String currencyTypeInternalName) {
        Type type = (Type) getEntity("Type", "Internalname", typeInternalName);
        Type currnecyType = (Type) getEntity("Type", "Internalname", currencyTypeInternalName);
        Person payer = (Person) getEntity("Person", "Email", payerEmail);

        Payment newPayment = new Payment();

        newPayment.setPaymentreg(paymentReg);
        newPayment.setPersonid(payer);
        newPayment.setPaymentprice(paymentPrice);
        newPayment.setPaymentdate(new Date());
        newPayment.setTypeid(type);
        newPayment.setCurrencytypeid(currnecyType);

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", typeInternalName));
            return null;
        }

        if (currnecyType == null) {
            System.out.println(String.format("There is no currnecy type '%s'", currencyTypeInternalName));
            return null;
        }

        if (payer == null) {
            System.out.println(String.format("Person with email '%s' does not exist", payerEmail));
            return null;
        }

        if (entityExists("Payment", "paymentReg", paymentReg)) {
            System.out.println(String.format("Payment with registration'%s' already exists", paymentReg));
            return null;
        }

        if (persistAndFlush(newPayment)) {
            System.out.println(String.format("Payment '%s' created successfully", paymentReg));
        } else {
            return null;
        }

        return newPayment;
    }

    public Paymentlog addPaymentLog(String payerEmail, String payerType, String method) {

        if (payerEmail == null || payerType == null || method == null) {
            System.out.println("Payment log parameters can not be null");
            return null;
        }

        Type type = (Type) getEntity("Type", "Internalname", payerType);

        Paymentlog newPaymentLog = new Paymentlog();

        newPaymentLog.setPersonemail(payerEmail);
        newPaymentLog.setDate(new Date());
        newPaymentLog.setMethod(method);

        if (type == null) {
            System.out.println(String.format("There is no type '%s'", payerType));
            return null;
        }

        newPaymentLog.setPersontype(type.getTitle());

        if (persistAndFlush(newPaymentLog)) {
            System.out.println("PaymentLog created successfully");
        } else {
            return null;
        }

        return newPaymentLog;
    }
    
     public Object updateEntity(Object obj, Boolean throwInternalError) {
        
        if (throwInternalError) {
            return updateEntity(obj);
        }
         
        try {
            Object result = em.merge(obj);
            return result;
        } catch (OptimisticLockException ol) {
            for (int i = 0; i < 10; i++)
                System.out.println(String.format("Vidinė klaida: Kažkas jau modifikavo objektą."));
        }
        return null;
    }

    public Object updateEntity(Object obj) {
        
        try {
            Object result = em.merge(obj);
            return result;
        } catch (OptimisticLockException ol) {
            for (int i = 0; i < 10; i++)
                System.out.println(String.format("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX Kažkas jau modifikavo objektą. REIKIA ISMESTI INTERNAL ERROR. XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"));
        }
        return null;
    }

    /**
     * Gets byte[] of image in given path
     */
    private byte[] getImage(String path) {
        FileInputStream fis = null;
        byte[] imageInBytes = null;
        File image = null;

        try {
            image = new File(path);
            fis = new FileInputStream(image);
            imageInBytes = new byte[(int) image.length()];
            fis.read(imageInBytes);
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
        return imageInBytes;
    }

    /**
     * *
     * Gets byte[] of image in given resource path
     */
    private byte[] getImageFromResources(String path) {
        byte[] imageInBytes = null;
        InputStream in = null;
        try {
            in = this.getClass().getClassLoader().getResourceAsStream(path);
            if (in == null) {
                System.out.println("input stream is null");
            }
            imageInBytes = IOUtils.toByteArray(in);
        } catch (Exception e) {
            System.out.println("Error reading image from resources");
            e.printStackTrace();
        }
        return imageInBytes;
    }

    /**
     * *
     * Returns MimeType of file in given path
     */
    private String getImageMimeType(String path) {
        String[] pathParts = path.split("\\.");

        return pathParts[pathParts.length - 1];
    }

    public Systemparameter getSystemParameter(String internalName) {
        Systemparameter param = (Systemparameter) getEntity("Systemparameter", "Internalname", internalName);
        return param;
    }

    /**
     * Returns true if specified entity exists with specified parameter
     */
    public boolean entityExists(String className, String findBy, String parameter) {
        Object entity = getEntity(className, findBy, parameter);
        return entity != null ? true : false;
    }

    /**
     * Returns true if specified entity exists with specified parameter
     */
    public boolean entityExists(String className, String findBy, int parameter) {
        Object entity = getEntity(className, findBy, parameter);
        return entity != null ? true : false;
    }

    /**
     * Returns true if specified entity exists with specified parameter
     */
    public boolean entityExists(String className, String findBy, Calendar parameter) {
        Object entity = getEntity(className, findBy, parameter);
        return entity != null ? true : false;
    }

    /**
     * Returns true if specified entity exists with specified parameter
     */
    public boolean entityExists(String className, String findBy, Object parameter) {
        Object entity = getEntity(className, findBy, parameter);
        return entity != null ? true : false;
    }

    /**
     * Returns entity if specified entity exists with specified parameter
     */
    public Object getEntity(String className, String findBy, String parameter) {
        List entityList = getEntityList(className, findBy, parameter);
        return entityList.isEmpty() ? null : entityList.get(0);
    }

    /**
     * Returns entity if specified entity exists with specified parameter
     */
    public Object getEntity(String className, String findBy, Calendar parameter) {
        List entityList = getEntityList(className, findBy, parameter);
        return entityList.isEmpty() ? null : entityList.get(0);
    }

    /**
     * Returns entity if specified entity exists with specified parameter
     */
    public Object getEntity(String className, String findBy, int parameter) {
        List entityList = getEntityList(className, findBy, parameter);
        return entityList.isEmpty() ? null : entityList.get(0);
    }

    /**
     * Returns entity if specified entity exists with specified parameter
     */
    public Object getEntity(String className, String findBy, Object parameter) {
        List entityList = getEntityList(className, findBy, parameter);
        return entityList.isEmpty() ? null : entityList.get(0);
    }

    /**
     * Returns entity if specified entity exists with specified parameter
     */
    public List getEntityList(String className, String findBy, String parameter) {
        className = className.toLowerCase();
        findBy = findBy.toLowerCase();
        Query query = em.createNamedQuery(capitalize(className) + ".findBy" + capitalize(findBy)).setParameter(findBy, parameter);

        return query.getResultList();
    }

    /**
     * Returns entity if specified entity exists with specified parameter
     */
    public List getEntityList(String className, String findBy, int parameter) {
        className = className.toLowerCase();
        findBy = findBy.toLowerCase();
        Query query = em.createNamedQuery(capitalize(className) + ".findBy" + capitalize(findBy)).setParameter(findBy, parameter);

        return query.getResultList();
    }

    /**
     * Returns entity if specified entity exists with specified parameter
     */
    public List getEntityList(String className, String findBy, Date parameter) {
        className = className.toLowerCase();
        findBy = findBy.toLowerCase();

        Query query = em.createQuery("SELECT e FROM " + capitalize(className) + " e WHERE e." + findBy + " = :parameter");
        query.setParameter("parameter", parameter);

        return query.getResultList();
    }

    /**
     * Returns entity if specified entity exists with specified parameter
     */
    public List getEntityList(String className, String findBy, Object parameter) {
        className = className.toLowerCase();
        findBy = findBy.toLowerCase();

        Query query = em.createQuery("SELECT e FROM " + capitalize(className) + " e WHERE e." + findBy + " = :parameter");
        query.setParameter("parameter", parameter);

        return query.getResultList();
    }

    /**
     * Returns all entities of selected class name.
     */
    public List getAllEntities(String className) {
        className = className.toLowerCase();
        Query query = em.createNamedQuery(capitalize(className) + ".findAll");

        return query.getResultList();
    }

    /**
     * Persists and flushes entity to database. Returns true if operation was
     * successful, false otherwise. Before using this method check if newEntity
     * does not already exist in database.
     */
    public boolean persistAndFlush(Object newEntity) {
        try {
            em.persist(newEntity);
            em.flush();
        } catch (PersistenceException pe) {
            System.out.println(String.format("Failed to insert '%s' to database", newEntity.toString()));
            em.clear();
            return false;
        }
        return true;
    }

    /**
     * Persists and flushes entity to database. Returns true if operation was
     * successful, false otherwise. Before using this method check if newEntity
     * does not already exist in database.
     */
    public boolean persistAndFlushList(List<Object> newEntities) {
        try {
            for (Object newEntity : newEntities) {
                em.persist(newEntity);
            }
            em.flush();
        } catch (PersistenceException pe) {
            System.out.println(String.format("Failed to insert list'%s' to database", newEntities.toString()));
            em.clear();
            return false;
        }
        return true;
    }

    public boolean saveFormAttributes(List<AdminUserFormProperty> properties) {
        //drop table
        em.createQuery("DELETE FROM Formattribute e").executeUpdate();
        //insert
        for (AdminUserFormProperty prop : properties) {
            Formattribute attr = new Formattribute();
            attr.setName(prop.getLabelName());
            attr.setInternalname(prop.getLabelName());
            attr.setIsdeleted(Boolean.FALSE);
            attr.setIsrequired(prop.isRequired());
            attr.setListitems(prop.getSelectionValues());
            Type type = (Type) getEntity("Type", "internalname", prop.getSelectedType());
            attr.setTypeid(type);
            if (!persistAndFlush(attr)) {
                return false;
            }
        }
        return true;
    }

    public void editHouses() {
        Random rand = new Random();
        Query query = em.createNamedQuery("House.findAll");
        List<House> houses = query.getResultList();
        for (House house : houses) {
            house.setTitle(house.getTitle() + rand.nextInt(1000));
            house.setIsactive(true);
            //house.setSeasonstartdate(new Date());
            //house.setSeasonenddate(new Date(2017,04,11));
            house.setIsdeleted(false);
            house.setNumberofplaces(rand.nextInt(30));
            house.setWeekprice(new BigDecimal(rand.nextInt(1000)));
            em.persist(house);
        }
    }

    public void editServices() {
        Random rand = new Random();
        Query query = em.createNamedQuery("Service.findAll");
        List<Service> services = query.getResultList();
        for (Service service : services) {
            service.setWeekprice(new BigDecimal(rand.nextInt(800)));
            em.persist(service);
        }
    }

    public void editPeople() {
        Random rand = new Random();
        Query query = em.createNamedQuery("Person.findAll");
        List<Person> people = query.getResultList();
        for (Person person : people) {
            person.setPriority(rand.nextInt(7));
            person.setPoints(new BigDecimal(rand.nextInt(1000)));
            person.setAddress(person.getFirstname() + rand.nextInt(1000));
            person.setMembershipdue(new Date(2017, 01, 01));
            em.persist(person);
        }
    }

    /**
     * Returns Type objects belonging to specific type class
     *
     * @param typeClass
     */
    public List<Type> retrieveTypes(String typeClass) {
        Query query = em.createQuery("SELECT t FROM Type t WHERE (t.internalname LIKE :typeClassLike) OR (t.internalname = :typeClass)").setParameter("typeClassLike", typeClass + ".%").setParameter("typeClass", typeClass);
        return query.getResultList();
    }

    public static String capitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        char c[] = string.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        return new String(c);
    }

    public boolean updateHouse(House h) {
        try {
            Query q = em.createQuery("UPDATE House h SET h.title = :title, h.typeid = :typeid, "
                    + "h.description = :description, h.housereg = :housereg, h.address = :address, "
                    + "h.seasonstartdate = :startdt, h.seasonenddate = :enddt, "
                    + "h.weekprice = :price, h.numberofplaces = :places "
                    + "WHERE h.id = :id");
            q.setParameter("title", h.getTitle());
            q.setParameter("typeid", h.getTypeid());
            q.setParameter("description", h.getDescription());
            q.setParameter("housereg", h.getHousereg());
            q.setParameter("address", h.getAddress());
            q.setParameter("startdt", h.getSeasonstartdate());
            q.setParameter("enddt", h.getSeasonenddate());
            q.setParameter("price", h.getWeekprice());
            q.setParameter("places", h.getNumberofplaces());
            q.setParameter("id", h.getId());
            em.detach(h);
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            updateHouseIsActive(h.getId(), h.getIsactive());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateHouseIsActive(int houseId, boolean newValue) {
        try {
            Query q = em.createNativeQuery("UPDATE House h SET h.isactive = " + newValue + " WHERE h.id = " + houseId);

            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateEntityIsDeletedTrue(String className, int id) {
        try {
            Query q = em.createNativeQuery("UPDATE " + className + " e SET e.isdeleted = true WHERE e.id = " + id);
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updatePersonIsDeletedTrue(Person p) {
        try {
            Query q = em.createQuery("UPDATE Person p SET p.isdeleted = :isdeleted "
                    + "WHERE p.id = :id");
            q.setParameter("isdeleted", true);
            q.setParameter("id", p.getId());
            em.detach(p);
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updatePersonPoints(Person p) {
        try {
            Query q = em.createQuery("UPDATE Person p SET p.points = :points "
                    + "WHERE p.id = :id");
            q.setParameter("points", p.getPoints());
            q.setParameter("id", p.getId());
            em.detach(p);
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updatePersonMembershipDue(Person p) {
        try {
            Query q = em.createQuery("UPDATE Person p SET p.membershipdue = :membershipdue "
                    + "WHERE p.id = :id");
            q.setParameter("membershipdue", p.getMembershipdue());
            q.setParameter("id", p.getId());
            em.detach(p);
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updatePaymentApprovalDate(Payment p) {
        try {
            Query q = em.createQuery("UPDATE Payment p SET p.approveddate = :approvedate "
                    + "WHERE p.id = :id");
            q.setParameter("approvedate", new Date());
            q.setParameter("id", p.getId());
            em.detach(p);
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteHouseimageByInternalname(String internalName) {
        try {
            Query q = em.createQuery("DELETE FROM Houseimage h WHERE h.internalname = :internalName");
            q.setParameter("internalName", internalName);
            em.joinTransaction();
            int deleted = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateSystemParameterValue(Systemparameter param) {
        try {
            Query q = em.createQuery("UPDATE Systemparameter p SET p.value = :value "
                    + "WHERE p.id = :id");
            q.setParameter("value", param.getValue());
            q.setParameter("id", param.getId());
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String generateReg(String desiredReg) {
        Random rand = new Random();
        return desiredReg + "-" + System.currentTimeMillis() % 10000 + rand.nextInt(1000);
    }

    public Houseimage getFirstImage(House house) {
        List<Houseimage> imgs = getEntityList("Houseimage", "Houseid", house);

        for (Houseimage img : imgs) {
            if (img.getSequence() == 1) {
                return img;
            }
        }

        return null;
    }

    public boolean updateImageSequence(Houseimage img, int seq) {
        try {
            Query q = em.createQuery("UPDATE Houseimage p SET p.sequence = :sequence "
                    + "WHERE p.id = :id");
            q.setParameter("sequence", seq);
            q.setParameter("id", img.getId());
            em.detach(img);
            em.joinTransaction();
            int updated = q.executeUpdate();
            em.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean swapImageSequences(Houseimage img1, Houseimage img2) {
        try {
            int seq1 = img1.getSequence();
            updateImageSequence(img1, img2.getSequence());
            updateImageSequence(img2, seq1);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
