/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import lt.vu.mif.labanoro_draugai.entities.Service;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.CloseEvent;
 
/**
 *
 * @author Karolis
 */
@Named
@ViewScoped
public class SummerhouseManager implements Serializable{
    enum Ordering{
    Cheap,Expensive,Old,New
    }
    
    private static final long serialVersionUID = 1L;
    private List<House> summerhouses;
    private List<House> filteredSummerhouses;
    private boolean isFiltered = false;
    List<String> selectedHouseImages; 
    
    //Dialog
    private House selectedHouse;
    private Date selectedDateFrom;
    private Date selectedDateTo;
    private String selectedHouseReservedDays;
    private List<String> selectedHouseAvailableServices;
    private String[] selectedHouseSelectedServices;
    
    //Datepicker
    private Date dateFrom = getNextMonday();
    private Date dateTo;
    
    //Price slider
    private BigDecimal maxHousePrice;
    private BigDecimal priceFrom = new BigDecimal(0);
    private BigDecimal priceTo;
    
    //place count spinner
    private int placeCount;

    private Ordering ordering;
    private Map<String,Ordering> availableOrderings;
    
    //Service checkbox list
    private List<String> availableFilters;
    private String[] selectedFilters;
    
    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init() {
        summerhouses = (List<House>)dbm.getAllEntities("House"); 
        filteredSummerhouses = (List<House>)dbm.getAllEntities("House");
        Collections.reverse(summerhouses);
        Collections.reverse(filteredSummerhouses);
        System.out.println("summerhouses size:"+summerhouses.size());
        //filter
        //Papulint servisus is db
        List<Service> services = (List<Service>)dbm.getAllEntities("Service");

        availableFilters = new ArrayList<>();
        for(Service service:services){
            availableFilters.add(service.getTitle());
        }
        ////////////////////////
        
        availableOrderings = new LinkedHashMap<>();
        availableOrderings.put("Naujausi", Ordering.New);
        availableOrderings.put("Seniausi", Ordering.Old);
        availableOrderings.put("Pigiausi", Ordering.Cheap);
        availableOrderings.put("Brangiausi", Ordering.Expensive);
        
        
        System.out.println(toString() + " constructed.");
        
        setMaxHousePrice(new BigDecimal(1000));
        setPriceTo(getMaxHousePrice());
        
        System.out.println(toString() + " constructed.");
    }
    
    public String firstImageName(House house) {
        if(house ==null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) return null;
        Predicate condition = new Predicate() {
            public boolean evaluate(Object sample) {
                 return ((Houseimage)sample).getSequence().equals(1);
            }
         };
         Houseimage result = (Houseimage)CollectionUtils.select( house.getHouseimageList(), condition ).iterator().next();
         return result.getInternalname();
    }
    
    public List<String> HouseImageNames(House house){
        if(house ==null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) return null;
        List<String> result = new ArrayList<>();
        for(Houseimage img : house.getHouseimageList()){
            result.add(img.getInternalname());
        }
         return result;
    }
    
    //TODO availability filter
    public void filter(){
        System.out.println("This is filter:"+getOrdering());
        
        filteredSummerhouses = new ArrayList<>();
        for(House house:summerhouses){
            if((getPlaceCount() == 0 || house.getNumberofplaces()>=getPlaceCount()) &&
                    (getPriceFrom().compareTo(house.getWeekprice()) <= 0 && house.getWeekprice().compareTo(getPriceTo()) <= 0) &&
                    isHouseAvailable(house, getDateFrom(), getDateTo()) && hasSelectedServices(house, getSelectedFilters()))
            filteredSummerhouses.add(house);
        }
        
        sortHouses();
        isFiltered = true;
    }
    
    private boolean hasSelectedServices(House house,String[] serviceNames){
        
        if(serviceNames == null || serviceNames.length==0) return true;
        if(house.getServiceList() == null || house.getServiceList().isEmpty()) return false;
        for(String name:serviceNames){
            for(Service service: house.getServiceList()){
                if(service.getTitle().equalsIgnoreCase(name)) break;
                else return false;
            }  
        }    
        return  true;
    }
    
    private boolean isHouseAvailable(House house,Date dateFrom,Date dateTo){
        if(house.getReservationList()== null || dateFrom == null || dateTo == null) return true;
        if(house.getSeasonstartdate()!=null && house.getSeasonenddate()!=null && !(dateTo.before(house.getSeasonstartdate()) || dateFrom.after(house.getSeasonenddate())))
                    return false;
        for(Reservation reservation : house.getReservationList()){
            if(!(dateTo.before(reservation.getStartdate()) || dateFrom.after(reservation.getEnddate())))
                    return false;
        }
        return true;
    }
    
    private void sortHouses(){
        switch(getOrdering()){
            case Cheap:
                Collections.sort(filteredSummerhouses, new Comparator<House>() {
                    @Override
                    public int compare(House h1, House h2) {
                        return h1.getWeekprice().compareTo(h2.getWeekprice());
                    }
                });
                break;
            case Expensive: 
                Collections.sort(filteredSummerhouses, new Comparator<House>() {
                    @Override
                    public int compare(House h1, House h2) {
                        return h2.getWeekprice().compareTo(h1.getWeekprice());
                    }
                });
                break;
            case New:
                Collections.sort(filteredSummerhouses, new Comparator<House>() {
                    @Override
                    public int compare(House h1, House h2) {
                        return h2.getId().compareTo(h1.getId());
                    }
                });
                break;
            case Old:
                Collections.sort(filteredSummerhouses, new Comparator<House>() {
                    @Override
                    public int compare(House h1, House h2) {
                        return h1.getId().compareTo(h2.getId());
                    }
                });
                break;
            default:
                System.out.println("No Ordering selected");
                break;
        }
    }
    
    public void showAll(){
        filteredSummerhouses = new ArrayList<>();
        for(House house:summerhouses){
            filteredSummerhouses.add(house);
        }
        sortHouses();
        isFiltered = false;
    }

    //TODO Sezono pradžia čia turi but<-------------------------------------------------------
    public Date getEndOfSeason(){
        Calendar today = Calendar.getInstance(); 
        today.add(Calendar.YEAR, 1);
        return today.getTime();
    }
    
    public Date getNextMonday(){
        Calendar now = Calendar.getInstance();
        int weekday = now.get(Calendar.DAY_OF_WEEK);
        if (weekday != Calendar.MONDAY)
        {
            // calculate how much to add
            // the 2 is the difference between Saturday and Monday
            int days = (Calendar.SATURDAY - weekday + 2) % 7;
            now.add(Calendar.DAY_OF_YEAR, days);
        }
        // now is the date you want
        return now.getTime();
    }

    public void handleDialogClose(CloseEvent event){
        selectedHouse = null;
        selectedDateFrom = null;
        selectedDateTo= null;
        selectedHouseReservedDays = null;
        selectedHouseAvailableServices = null;
        selectedHouseSelectedServices = null;
    }
    
    public Date selectedHouseMinDate(){
        Date mon = getNextMonday();
        if(selectedHouse == null || selectedHouse.getSeasonstartdate()==null) return mon;
        return selectedHouse.getSeasonstartdate().after(mon)? selectedHouse.getSeasonstartdate():mon;
    }
    
    public Date selectedHouseMaxDate(){
        if(selectedHouse == null || selectedHouse.getSeasonenddate()==null) return getEndOfSeason();
        return selectedHouse.getSeasonenddate().before(getEndOfSeason())? selectedHouse.getSeasonenddate():getEndOfSeason();
    }
    
    public Date selectedHouseMaxDateTo(){
        Date maxDate = selectedHouseMaxDate();
        if(selectedHouse == null || selectedHouse.getReservationList() == null) return maxDate;
        for(Reservation reservation:selectedHouse.getReservationList()){
            if(reservation.getStartdate().before(maxDate) && reservation.getStartdate().after(selectedDateFrom)) maxDate = reservation.getStartdate();
        }
        return maxDate;
    }
    
    public void onDateSelect() {
        if(getSelectedDateTo()==null || getSelectedDateFrom()==null) return;
        if(getSelectedDateTo().before(getSelectedDateFrom()))
            setSelectedDateTo(null);
    }
    
    public String getSelectedHouseReservedDays(){
        if(selectedHouse == null || selectedHouse.getReservationList() == null) return "[]";
        List<Date> dates;
        String prefix = "";
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("M-d-yyyy");
        StringBuilder builder = new StringBuilder();
        
        for(Reservation reservation:selectedHouse.getReservationList()){
            dates = getDaysBetweenDates(reservation.getStartdate(), reservation.getEnddate());
            for(Date date:dates){
                builder.append(prefix);
                prefix = ",";
                builder.append(formatter.format(date));
            }
        }

        selectedHouseReservedDays = builder.toString();
        return selectedHouseReservedDays;
    }
    
   //Modded setters/getters 
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
    
    public double selectedHousePeriodPrice(){
        if(selectedDateFrom==null || selectedDateTo== null || selectedHouse==null) return 0;
        int dayCount = getDaysBetweenDates(selectedDateFrom, selectedDateTo).size()+1;
        
        return selectedHouse.getWeekprice().doubleValue()*(dayCount / 7);
        
        
    }
      
    public String confirmSelectedHouse(){
        if(selectedHouse == null || selectedDateFrom == null || selectedDateTo == null
                || !isHouseAvailable(selectedHouse, selectedDateFrom, selectedDateTo)|| selectedHousePeriodPrice() == 0){
           return "";
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("house", selectedHouse);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dateFrom", selectedDateFrom);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dateTo", selectedDateTo);
        
        
        return "reservationConfirmation?faces-redirect=true";
    }

    public void setSelectedFilters(String[] selectedFilters) {
        if(selectedFilters!=null){
            System.out.println("Selected services:");
            for(String str:selectedFilters)
                System.out.println("*"+str);
        }
        this.selectedFilters = selectedFilters;
    }
    
    public void setSelectedHouse(House selectedHouse) {
        selectedHouseAvailableServices = new ArrayList<>();
        if(selectedHouse!= null){
            for(Service service:selectedHouse.getServiceList()){
                selectedHouseAvailableServices.add(service.getTitle());
            }
        }
        this.selectedHouse = selectedHouse;
    }
    
    public Date getSelectedDateTo() {
        if(selectedDateTo == null && isFiltered){
            selectedDateTo = getDateTo();
        }
        return selectedDateTo;
    }
        
    public Date getSelectedDateFrom() {
        if(selectedDateFrom == null){
            if(isFiltered) selectedDateFrom = getDateFrom();
            else selectedDateFrom = getNextMonday();
        }
        return selectedDateFrom;
    }
    
    //Basic setters/getters
    public void setSelectedDateFrom(Date selectedDateFrom) {
        this.selectedDateFrom = selectedDateFrom;
    }
    public void setSelectedDateTo(Date selectedDateTo) {
        this.selectedDateTo = selectedDateTo;
    }    
    public House getSelectedHouse() {
        return selectedHouse;
    } 
    public List<String> getSelectedHouseImages() {
        return selectedHouseImages;
    }
    public void setSelectedHouseImages(List<String> selectedHouseImages) {
        this.selectedHouseImages = selectedHouseImages;
    }
    public String[] getSelectedFilters() {
        return selectedFilters;
    }
    public List<String> getAvailableFilters() {
        return availableFilters;
    }
    public void setAvailableFilters(List<String> availableFilters) {
        this.availableFilters = availableFilters;
    }  
    public Map<String, Ordering> getAvailableOrderings() {
        return availableOrderings;
    }
    public void setAvailableOrderings(Map<String, Ordering> availableOrderings) {
        this.availableOrderings = availableOrderings;
    }   
    public Ordering getOrdering() {
        return ordering;
    }
    public void setOrdering(Ordering ordering) {
        this.ordering = ordering;
    }   
    public int getPlaceCount() {
        return placeCount;
    }
    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }   
    public BigDecimal getMaxHousePrice() {
        return maxHousePrice;
    }
    public void setMaxHousePrice(BigDecimal maxHousePrice) {
        this.maxHousePrice = maxHousePrice;
    }   
    public BigDecimal getPriceFrom() {
        return priceFrom;
    }
    public void setPriceFrom(BigDecimal priceFrom) {
        this.priceFrom = priceFrom;
    }
    public BigDecimal getPriceTo() {
        return priceTo;
    }
    public void setPriceTo(BigDecimal priceTo) {
        this.priceTo = priceTo;
    }    
    public Date getDateFrom() {
        return dateFrom;
    }
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }
    public Date getDateTo() {
        return dateTo;
    }
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }  
    public List<String> getSelectedHouseAvailableServices() {
        return selectedHouseAvailableServices;
    }
    public void setSelectedHouseAvailableServices(List<String> selectedHouseAvailableServices) {
        this.selectedHouseAvailableServices = selectedHouseAvailableServices;
    }
    public String[] getSelectedHouseSelectedServices() {
        return selectedHouseSelectedServices;
    }
    public void setSelectedHouseSelectedServices(String[] selectedHouseSelectedServices) {
        this.selectedHouseSelectedServices = selectedHouseSelectedServices;
    }   
    public void setSelectedHouseReservedDays(String selectedHouseReservedDays) {
        this.selectedHouseReservedDays = selectedHouseReservedDays;
    }
    public List<House> getFilteredSummerhouses() {
        return filteredSummerhouses;
    }
}
