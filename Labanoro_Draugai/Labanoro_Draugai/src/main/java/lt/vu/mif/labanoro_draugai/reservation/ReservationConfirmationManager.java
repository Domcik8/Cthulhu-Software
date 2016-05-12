/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Service;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Karolis
 */
@ManagedBean
@ViewScoped
public class ReservationConfirmationManager implements Serializable{
    
    private House house;
    private Date dateFrom;
    private Date dateTo;
    List<String> selectedServices;
    private Person user;
    private int totalPrice;
    @Inject
    DatabaseManager dbm;
    
    //neveikia redirectas
    @PostConstruct
    public void init(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) (ec.getRequest());
        if(request==null || request.getUserPrincipal()==null || request.getUserPrincipal().getName() == null) try {
            ec.redirect("/Labanoro_Draugai/login.html");
            return;
        } catch (IOException ex) {
            Logger.getLogger(ReservationConfirmationManager.class.getName()).log(Level.SEVERE, null, ex);
            user = new Person();
            return;
        }
        user = (Person)dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());
    }
    
    public String createReservationJSON(){
        if(dateTo==null || dateFrom == null) return "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("type", "houseReservation");
        jsonObject.element("reservationReg", generateReservationReg());
        jsonObject.element("houseReg", house.getHousereg());
        jsonObject.element("reservationTypeInternalName",  "Reservation");
        JSONArray arr = new JSONArray();
        if(selectedServices!=null){ 
            for(String str:selectedServices){
                arr.add(str);
            }
        }
        jsonObject.element("services", arr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonObject.element("dateFrom", sdf.format(dateFrom));
        jsonObject.element("dateTo", sdf.format(dateTo));
        return jsonObject.toString();
    }
    
    public String reserveSummerhouse(){
        //validation
        dbm.addReservation(generateReservationReg(), house.getHousereg(), "Reservation", user.getEmail(), selectedServices, dateFrom, dateTo);
        return "/Labanoro_Draugai/index.html";
    }
    
    private String generateReservationReg(){
        Random rand = new Random();
        return "ReservationReg-"+System.currentTimeMillis() % 1000+rand.nextInt(10000);
    }
    
    public int calculateTotalPrice(){
        if(house == null )return 0;
        totalPrice = 0;
        totalPrice+= periodPrice(house.getWeekprice());
        if(selectedServices == null) return totalPrice;
        for(String str:selectedServices){
            Service service = (Service)dbm.getEntity("Service", "Title", str);
            if(service == null) continue;
            totalPrice+=periodPrice(service.getWeekprice());
        }
        return totalPrice;
    }
    
    public List<String> houseImageNames(){
        if(house ==null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) return null;
        List<String> result = new ArrayList<>();
        for(Houseimage img : house.getHouseimageList()){
            result.add(img.getInternalname());
        }
         return result;
    }
    
    public int periodPrice(int weekPrice){
        int dayCount = getDaysBetweenDates(dateFrom, dateTo).size()+1;
        return weekPrice * (dayCount/7);
    }
    
    private List<Date> getDaysBetweenDates(Date startdate, Date enddate){
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);
        
        while (!calendar.getTime().after(enddate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
    
    public House getHouse() {
        if (house == null) {
            house =  (House) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("house");
        }
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Date getDateFrom() {
        if (dateFrom == null) {
            dateFrom =  (Date) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dateFrom");
        }
        
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        if (dateTo == null) {
            dateTo =  (Date) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dateTo");
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
        return selectedServices;
    }

    public void setSelectedServices(List<String> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {      
        this.totalPrice = totalPrice;
    }
    
}
