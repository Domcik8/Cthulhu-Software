/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.BuyConfirmationInterface;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.business.Interceptorius;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import lt.vu.mif.labanoro_draugai.entities.Service;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Karolis
 */
@Named
@ViewScoped
public class ReservationConfirmationManager implements Serializable {

    private House house;
    private Date dateFrom;
    private Date dateTo;
    List<String> selectedServices;
    private Person user;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceInPoints;
    private BigDecimal exchangeRate;
    private String currency;
    @Inject
    DatabaseManager dbm;
    
    //Decorator
    @Inject
    private BuyConfirmationInterface priceService;
    
    //neveikia redirectas
    @PostConstruct
    public void init() {
        totalPrice = new BigDecimal(-1);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) (ec.getRequest());
        if (request == null || request.getUserPrincipal() == null || request.getUserPrincipal().getName() == null) {
            try {
                ec.redirect("/Labanoro_Draugai/login.html");
                return;
            } catch (IOException ex) {
                Logger.getLogger(ReservationConfirmationManager.class.getName()).log(Level.SEVERE, null, ex);
                user = new Person();
                return;
            }
        }
        user = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());

        Systemparameter param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.ExchangeRate.Euro");
        if (param == null) {
            System.out.println("Truksta \"SystemParameter.ExchangeRate.Euro\" parametro");
            exchangeRate = new BigDecimal(-1);
            currency = "?";
            return;
        }
        exchangeRate = new BigDecimal(param.getValue());
        param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Currency.Euro");
        if (param == null) {
            System.out.println("Truksta \"SystemParameter.Currency.Euro\" parametro");
            currency = "?";
            return;
        }
        currency = param.getValue();
        System.out.println(toString() + "created.");
    }

    public void onLoad() {
        System.out.println(toString() + "loaded.");
    }

    public List<Service> serviceList() {
        List<Service> services = new ArrayList<>();
        if (getSelectedServices() != null && getHouse().getServiceList() != null) {
            for (String serviceReg : selectedServices) {
                for (Service service : house.getServiceList()) {
                    if (service.getServicereg().equals(serviceReg)) {
                        services.add(service);
                        break;
                    }
                }
            }
        }
        return services;
    }

    public String createReservationJSON() {
        if (dateTo == null || dateFrom == null) {
            return "";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("type", "houseReservation");
        jsonObject.element("houseReg", house.getHousereg());
        jsonObject.element("reservationTypeInternalName", "Reservation");
        JSONArray arr = new JSONArray();
        if (selectedServices != null) {
            for (String str : selectedServices) {
                arr.add(str);
            }
        }
        jsonObject.element("services", arr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonObject.element("dateFrom", sdf.format(dateFrom));
        jsonObject.element("dateTo", sdf.format(dateTo));
        return jsonObject.toString();
    }

    @Interceptorius
    public String reserveSummerhouse() {
        if (user.getPoints().compareTo(totalPriceInPoints) == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nepavyko!", "Jūsų sąskaitoje yra nepakankamai taškų."));
            return null;
        }

        Payment pay = dbm.addPayment(user.getEmail(), totalPrice, new Date(), "Payment.Reservation", "Currency.Points");

        user.setPoints(user.getPoints().subtract(totalPriceInPoints));

        Reservation reservation = dbm.addReservation(house.getHousereg(), pay.getPaymentreg(), "Reservation", user.getEmail(), selectedServices, dateFrom, dateTo);

        pay.setReservationid(reservation);
        pay.setApproveddate(new Date());
        pay = (Payment) dbm.updateEntity(pay);

        user.getReservationList().add(reservation);
        user.getPaymentList().add(pay);
        user = (Person) dbm.updateEntity(user);

        house.getReservationList().add(reservation);
        house = (House) dbm.updateEntity(house);
        Systemparameter param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Redirect.MyReservations");
        if (param == null) {
            System.out.println("Truksta \"SystemParameter.Redirect.MyReservations\" parametro");
            return "/index";
        }

        return param.getValue() + "?faces-redirect=true";
    }

    public BigDecimal calculateTotalPrice() {
        totalPrice = new BigDecimal(0);
        if (house == null) {
            return totalPrice;
        }

        totalPrice = totalPrice.add(periodPrice(getWeekPriceHouse(house)));
        if (selectedServices == null) {
            return totalPrice;
        }
        for (String str : selectedServices) {
            Service service = (Service) dbm.getEntity("Service", "Servicereg", str);
            if (service == null) {
                continue;
            }
            totalPrice = totalPrice.add(periodPrice(getWeekPriceService(service)));
        }
        totalPriceInPoints = totalPrice.multiply(exchangeRate);
        return totalPrice;
    }

    public List<String> houseImageNames() {
        if (house == null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (Houseimage img : house.getHouseimageList()) {
            result.add(img.getInternalname());
        }
        return result;
    }

    public BigDecimal periodPrice(BigDecimal weekPrice) {
        int dayCount = getDaysBetweenDates(dateFrom, dateTo).size() + 1;
        return weekPrice.multiply(new BigDecimal(dayCount / 7));
    }

    public BigDecimal getWeekPriceHouse(House house){
        if(house == null) return new BigDecimal(Integer.MAX_VALUE);
        return priceService.getDiscountPrice(house.getWeekprice(), user, "Payment.Reservation");
    }
    
    public BigDecimal getWeekPriceService(Service service){
        if(service == null) return new BigDecimal(Integer.MAX_VALUE);
        return priceService.getDiscountPrice(service.getWeekprice(), user, "Payment.Reservation");
    }
    
    public long getTotalPriceInCents() {
        return Math.round(totalPrice.doubleValue() * 100);
    }

    private List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (!calendar.getTime().after(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public House getHouse() {
        if (house == null) {
            house = (House) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("house");
        }
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Date getDateFrom() {
        if (dateFrom == null) {
            dateFrom = (Date) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dateFrom");
        }

        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        if (dateTo == null) {
            dateTo = (Date) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dateTo");
        }

        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public List<String> getSelectedServices() {
        if (selectedServices == null) {
            selectedServices = (List<String>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedServices");
        }
        return selectedServices;
    }

    public void setSelectedServices(List<String> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public BigDecimal getTotalPrice() {
        if (totalPrice.compareTo(new BigDecimal(-1)) == 0) {
            totalPrice = new BigDecimal((Double)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("price"));
        }
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPriceInPoints() {
        return totalPriceInPoints;
    }

    public String getCurrency() {
        return currency;
    }

}
