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
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
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

        //editPeople();
        //editHouses();
        //editServices();
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
        addType("Service.Other", "Kitos paslaugos");
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            addPerson("admin@labanorai.lt", "admin", "admin", "Person.Administrator", "admin", "admin", "10000", 0, sdf.parse("3000-05-1"));
            addPerson("candidate@labanorai.lt", "candidate", "candidate", "Person.Candidate", "candidate", "candidate", "0", 0, new Date());
            addPerson("user@labanorai.lt", "user", "user", "Person.User", "user", "user", "1000", 0, sdf.parse("2017-05-1"));
            addPerson("Domcik8@gmail.com", "Jonas", "Jonaitis", "Person.User", "user", "Vilnius, Jonaitiškių 3", "1000", 0, sdf.parse("2017-05-1"));
            addPerson("rudzas.com@gmail.com", "Paulius", "Paulaitis", "Person.User", "user", "Vilnius, Pauliškių 3", "1000", 0, sdf.parse("2017-05-1"));
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates new person and flushes it to database. Returns person entity if
     * created sucessfully
     */
    public Person addPerson(String email, String firstName, String lastName, String typeInternalName, String password, String address, String points, int priority, Date membershipDue) {
        Person person = addPerson(email, firstName, lastName, typeInternalName);
        if (person != null) {
            String hashedPassoword = Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
            hashedPassoword = Hashing.sha256().hashString(hashedPassoword, Charsets.UTF_8).toString();
            person.setPassword(hashedPassoword);
            person.setAddress(address);
            person.setPoints(new BigDecimal(points));
            person.setPriority(priority);
            person.setMembershipdue(membershipDue);
        }

        return person;
    }

    /**
     * Fills database with basic houses
     */
    private void fillBasicHouses() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            addHouse("Namelis „Trolių Lūšna“", "Labanoro giria, 1", "HouseReg-1", "House.Penthouse",
                    "Jaukus namelis labanoro miško širdyje", "120", sdf.parse("2016-05-1"), sdf.parse("2016-12-30"), 5);
            addHouse("Namelis „Eglių Lūšna“", "Labanoro giria, 2", "HouseReg-2", "House.Penthouse",
                    "Jaukus namelis labanoro miško pakraštyje", "100", sdf.parse("2016-05-1"), sdf.parse("2016-08-30"), 5);
            addHouse("Namelis „Vandens Guolis“", "Labanoro giria, 3", "HouseReg-3", "House.Penthouse",
                    "Jaukus namelis labanoro miško širdyje ir arti ežero", "110", sdf.parse("2016-05-1"), sdf.parse("2016-12-30"), 4);
            addHouse("Namelis „Miškinio Draugas“", "Labanoro giria, 4", "HouseReg-4", "House.Penthouse",
                    "Jaukus namelis labanoro miško širdyje, kuris arti miškininko trobelės", "150", sdf.parse("2016-05-1"), sdf.parse("2016-08-30"), 6);
            addHouse("Namelis „Pelėdos Urvas“", "Labanoro giria, 5", "HouseReg-5", "House.Penthouse",
                    "Jaukus namelis labanoro miško širdyje, kur naktimis skraido arti pelėdos", "120", sdf.parse("2016-05-1"), sdf.parse("2016-12-30"), 4);
            addHouse("Namelis „Barsuko Guolis“", "Labanoro giria, 6", "HouseReg-6", "House.Penthouse",
                    "Jaukus namelis labanoro miško širdyje ir dar arti barsukai", "80", sdf.parse("2016-05-1"), sdf.parse("2016-08-30"), 3);
            addHouse("Namelis „Samanų Paklodė“", "Labanoro giria, 7", "HouseReg-7", "House.Penthouse",
                    "Jaukus namelis labanoro miško širdyje", "90", sdf.parse("2016-05-1"), sdf.parse("2016-12-30"), 3);
            addHouse("Namelis „Didysis Urvas“", "Labanoro giria, 8", "HouseReg-8", "House.Penthouse",
                    "Jaukus namelis labanoro miško pakraštyje, prie kurio dar randasi urvai", "160", sdf.parse("2016-05-1"), sdf.parse("2016-08-30"), 8);
            addHouse("Namelis „Namiškių Lūšna“", "Labanoro giria, 9", "HouseReg-9", "House.Penthouse",
                    "Jaukus namelis labanoro miško šone, kuris primins senus laikus", "120", sdf.parse("2016-05-1"), sdf.parse("2016-12-30"), 4);
            addHouse("Namelis „Kaimiška  Dvasia“", "Labanoro giria, 10", "HouseReg-10", "House.Penthouse",
                    "Jaukus namelis labanoro miško širdyje, kuris primena senovinį kaimą", "115", sdf.parse("2016-05-1"), sdf.parse("2016-08-30"), 5);
            addHouse("Namelis „Meilės Guolis“", "Labanoro giria, 11", "HouseReg-11", "House.Penthouse",
                    "Jaukus namelis labanoro miško širdyje ir tiems kam patinka romantika", "105", sdf.parse("2016-05-1"), sdf.parse("2016-12-30"), 2);
            addHouse("Namelis „Gerasis Sapnas“", "Labanoro giria, 12", "HouseReg-12", "House.Penthouse",
                    "Jaukus namelis labanoro miško širdyje", "120", sdf.parse("2016-05-1"), sdf.parse("2016-08-30"), 4);
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fills database with legit houses
     */
    public House addHouse(String title, String address, String houseReg, String typeInternalName, String description, String weekPrice, Date seasonStart, Date seasonEnd, int numberOfPlaces) {
        House house = addHouse(title, address, houseReg, typeInternalName);
        if (house != null) {

            house.setDescription(description);
            house.setWeekprice(new BigDecimal(weekPrice));
            house.setSeasonenddate(seasonEnd);
            house.setSeasonstartdate(seasonStart);
            house.setNumberofplaces(numberOfPlaces);
            house.setIsactive(Boolean.TRUE);
        }

        return house;
    }

    /**
     * Fills database with legit services
     */
    public Service addService(String title, String serviceReg, String houseReg, String typeInternalName, String description, String weekPrice) {
        Service service = addService(title, serviceReg, houseReg, typeInternalName);
        if (service != null) {
            service.setDescription(description);
            service.setWeekprice(new BigDecimal(weekPrice));
        }

        return service;
    }

    /**
     * Fills database with basic services
     */
    private void fillBasicServices() {
        Service service = addService("Vandens pramogos", "ServiceReg-1", "HouseReg-1", "Service.Other", "Papildomos vandens pramogos", "30");
        House house = (House) getEntity("House", "HouseReg", "HouseReg-2");
        house.getServiceList().add(service);
        house = (House) getEntity("House", "HouseReg", "HouseReg-3");
        house.getServiceList().add(service);
        
        service = addService("Dviračiai pasivažinėjimui", "ServiceReg-2", "HouseReg-1", "Service.Vehicle.Bike", "Dviračiai išsinuomavimui", "10");
        house = (House) getEntity("House", "HouseReg", "HouseReg-2");
        house.getServiceList().add(service);
        house = (House) getEntity("House", "HouseReg", "HouseReg-3");
        house.getServiceList().add(service);
        house = (House) getEntity("House", "HouseReg", "HouseReg-4");
        house.getServiceList().add(service);
        house = (House) getEntity("House", "HouseReg", "HouseReg-5");
        house.getServiceList().add(service);
        house = (House) getEntity("House", "HouseReg", "HouseReg-6");
        house.getServiceList().add(service);
        
        service = addService("Pažintinė kelionė aplink regioną", "ServiceReg-3", "HouseReg-1", "Service.Other", "Kelionė aplink labanoro apylinkes", "40");
        house = (House) getEntity("House", "HouseReg", "HouseReg-7");
        house.getServiceList().add(service);
        house = (House) getEntity("House", "HouseReg", "HouseReg-8");
        house.getServiceList().add(service);

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
        addSystemParameter("SystemParameter.priorityGroup.LastCountDate", "Prioriteto grupės, paskutinio grupių perskačiavimo laikas", "0", "Metai, mėnuo, mėnesio diena bei valanda, kada paskutini karta buvo perskaičiuotos prioriteto grupės. Atsižvelgiant į šią datą yra nustatomą einamoji sistemos prioriteto grupė", "SystemParameter");

        addSystemParameter("SystemParameter.StripeTestSecretKey", "Stripe testinis slaptas raktas", "sk_test_GkbxvPwRpIG9T4aIiruw0TWl", "SystemParameter");
        addSystemParameter("SystemParameter.StripeTestPublishableKey", "Stripe testinis viešas raktas", "pk_test_NuMXQ4crxg12CBo9NxrjSO63", "SystemParameter");
        addSystemParameter("SystemParameter.StripeLiveSecretKey", "Stripe tikras slaptas raktas", "sk_live_nSRfu4JPpcgYkpSlOjknfBk7", "SystemParameter");
        addSystemParameter("SystemParameter.StripeLivePublishableKey", "Stripe tikras viešas raktas", "pk_live_Q7f9zGXVZ1877SD3DfsCbsgd", "SystemParameter");

        addSystemParameter("SystemParameter.TermsAndConditions", "Nuostatos ir sąlygos", "Naudodamiesi mūsų sistema jūs sutinkate, kad „Labanoro draugai“ nėra atsakingi už sistmos nesklandumus ar kitus žalingus incidentus.", "SystemParameter");

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
        addSystemParameter("SystemParameter.Reservation.LastReservationDay", "Kiek dienų į priekį po paskutinio prioriteto grupių skaičiavimo galima rezervuoti vasarnamius", "185", "", "SystemParameter");

        addSystemParameter("SystemParameter.Mail.Address", "Gmail el.paštas", "labanorai@gmail.com", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Password", "Gmail el.pašto slaptažodis", "LabanoroDraugas", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.host", "Smtp hostas", "smtp.gmail.com", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.port", "Smtp portas", "587", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.auth", "Smtp autentifikacija", "true", "Ar reikalinga autentifikacija prisijungimui", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.starttls.enable", "Smtp TLS", "true", "Ar TLS turi būti įjungtas", "SystemParameter");
        addSystemParameter("SystemParameter.Mail.Smtp.ssl.trust", "Smtp Trust", "smtp.gmail.com", "Ar patikimas hostas", "SystemParameter");

        addSystemParameter("SystemParameter.Houseimage.Width", "Nuotraukos plotis", "600", "Namelio nuotraukų plotis įkėlimo metu. Jeigu reikšmė 0, tai nuotraukos plotis bus nustatytas pagal jos aukštį.", "SystemParameter");
        addSystemParameter("SystemParameter.Houseimage.Height", "Nuotraukos aukštis", "0", "Namelio nuotraukų aukštis įkėlimo metu. Jeigu reikšmė 0, tai nuotraukos aukštis bus nustatytas pagal jos plotį.", "SystemParameter");

        addSystemParameter("SystemParameter.Index.Title1", "Pagrindinio puslapio antraštė", "Klubas „Labanoro Draugai“", "Pagrindinio puslapio pirmosios sekcijos antraštė.", "SystemParameter");
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

    /**
     * Fills database with basic house pictures
     */
    private void fillBasicHouseImages() {
        addHouseImage("Picture.HouseReg-1_1", "Images/House/Housereg-1.jpg", 1, "HouseReg-1", "Picture.House");
        addHouseImage("Picture.HouseReg-1_2", "Images/House/Housereg-2.jpg", 2, "HouseReg-1", "Picture.House");
        addHouseImage("Picture.HouseReg-1_3", "Images/House/Housereg-3.jpg", 3, "HouseReg-1", "Picture.House");
        addHouseImage("Picture.HouseReg-2_1", "Images/House/Housereg-2.jpg", 1, "HouseReg-2", "Picture.House");
        addHouseImage("Picture.HouseReg-2_2", "Images/House/Housereg-3.jpg", 2, "HouseReg-2", "Picture.House");
        addHouseImage("Picture.HouseReg-3_1", "Images/House/Housereg-3.jpg", 1, "HouseReg-3", "Picture.House");
        addHouseImage("Picture.HouseReg-4_1", "Images/House/Housereg-4.jpg", 1, "HouseReg-4", "Picture.House");
        addHouseImage("Picture.HouseReg-5_1", "Images/House/Housereg-5.jpg", 1, "HouseReg-5", "Picture.House");
        addHouseImage("Picture.HouseReg-6_1", "Images/House/Housereg-6.jpg", 1, "HouseReg-6", "Picture.House");
        addHouseImage("Picture.HouseReg-7_1", "Images/House/Housereg-7.jpg", 1, "HouseReg-7", "Picture.House");
        addHouseImage("Picture.HouseReg-8_1", "Images/House/Housereg-8.jpg", 1, "HouseReg-8", "Picture.House");
        addHouseImage("Picture.HouseReg-9_1", "Images/House/Housereg-9.jpg", 1, "HouseReg-9", "Picture.House");
        addHouseImage("Picture.HouseReg-10_1", "Images/House/Housereg-10.jpg", 1, "HouseReg-10", "Picture.House");
        addHouseImage("Picture.HouseReg-11_1", "Images/House/Housereg-10.jpg", 1, "HouseReg-11", "Picture.House");
        addHouseImage("Picture.HouseReg-11_2", "Images/House/Housereg-11.jpg", 2, "HouseReg-11", "Picture.House");
        addHouseImage("Picture.HouseReg-11_3", "Images/House/Housereg-12.jpg", 3, "HouseReg-11", "Picture.House");
        addHouseImage("Picture.HouseReg-12_1", "Images/House/Housereg-12.jpg", 1, "HouseReg-12", "Picture.House");
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
        newHouse.setIsdeleted(Boolean.FALSE);

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

        house.getHouseimageList().add(newHouseimage);

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
            for (int i = 0; i < 10; i++) {
                System.out.println(String.format("Vidinė klaida: Kažkas jau modifikavo objektą."));
            }
        }
        return null;
    }

    public Object updateEntity(Object obj) {

        try {
            Object result = em.merge(obj);
            return result;
        } catch (OptimisticLockException ol) {
            for (int i = 0; i < 10; i++) {
                System.out.println(String.format("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX Kažkas jau modifikavo objektą. REIKIA ISMESTI INTERNAL ERROR. XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"));
            }

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            try {
                em.clear();
                facesContext.getExternalContext().redirect(request.getContextPath() + "/conflict.html");
                return null;
            } catch (Exception e) {
            }
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

    public void setLastCountDate() {
        int tryCounter = 3;
        Calendar calendar = Calendar.getInstance();

        while (tryCounter > 0) {
            Systemparameter lastCountDateSysParam = getSystemParameter("SystemParameter.priorityGroup.LastCountDate");
            lastCountDateSysParam.setValue(calendar.get(Calendar.YEAR) + "," + (calendar.get(Calendar.MONTH) + 1) + ","
                    + calendar.get(Calendar.DAY_OF_MONTH) + "," + calendar.get(Calendar.HOUR_OF_DAY));
            if (updateEntity(lastCountDateSysParam, false) != null) {
                break;
            }
            tryCounter--;
        }
    }
}
