/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import lt.vu.mif.labanoro_draugai.entities.Service;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.primefaces.event.CloseEvent;
 
/**
 *
 * @author Karolis
 */
@ManagedBean(name="summerhouseManager")
@ViewScoped
public class SummerhouseManager implements Serializable{
    private List<House> summerhouses;
    private List<House> filteredSummerhouses;
    private boolean isFiltered = false;
    List<String> selectedHouseImages; 
    
    @ManagedProperty(value="#{houseFilter}")
    private HouseFilter filter;
    
    @PersistenceContext
    EntityManager em;
    
    @PostConstruct
    public void init() {
        Query query = em.createNamedQuery("House.findAll");
        
        summerhouses = query.getResultList();
        filteredSummerhouses = query.getResultList();
        Collections.reverse(summerhouses);
        Collections.reverse(filteredSummerhouses);
        
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
        System.out.println("This is filter:"+filter.getOrdering());
        
        filteredSummerhouses = new ArrayList<>();
        for(House house:summerhouses){
            if((filter.getPlaceCount() == 0 || house.getNumberofplaces()>=filter.getPlaceCount()) &&
                    (filter.getPriceFrom() <= house.getWeekprice() && house.getWeekprice() <= filter.getPriceTo()) &&
                    isHouseAvailable(house, filter.getDateFrom(), filter.getDateTo()) && hasSelectedServices(house, filter.getSelectedFilters()))
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
        switch(filter.getOrdering()){
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

    public List<House> getFilteredSummerhouses() {
        return filteredSummerhouses;
    }

    public HouseFilter getFilter() {
        return filter;
    }
    public void setFilter(HouseFilter filter) {
        this.filter = filter;
    }
    
    
    
    //Dialog
    private House selectedHouse;
    private Date selectedDateFrom;
    private Date selectedDateTo;
    private String selectedHouseReservedDays;
    private List<String> selectedHouseAvailableServices;
    public String[] selectedHouseSelectedServices;

    public void handleDialogClose(CloseEvent event){
        selectedHouse = null;
        selectedDateFrom = null;
        selectedDateTo= null;
        selectedHouseReservedDays = null;
        selectedHouseAvailableServices = null;
        selectedHouseSelectedServices = null;
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
    
    public Date selectedHouseMinDate(){
        Date mon = getNextMonday();
        if(selectedHouse == null || selectedHouse.getSeasonstartdate()==null) return mon;
        return selectedHouse.getSeasonstartdate().after(mon)? selectedHouse.getSeasonstartdate():mon;
    }
    
    public Date selectedHouseMaxDate(){
        if(selectedHouse == null || selectedHouse.getSeasonenddate()==null) return filter.getEndOfSeason();
        return selectedHouse.getSeasonenddate().before(filter.getEndOfSeason())? selectedHouse.getSeasonenddate():filter.getEndOfSeason();
    }
    
    public Date selectedHouseMaxDateTo(){
        Date maxDate = selectedHouseMaxDate();
        if(selectedHouse == null || selectedHouse.getReservationList() == null) return maxDate;
        for(Reservation reservation:selectedHouse.getReservationList()){
            if(reservation.getStartdate().before(maxDate) && reservation.getStartdate().after(selectedDateFrom)) maxDate = reservation.getStartdate();
        }
        return maxDate;
    }
    
    public Date getSelectedDateFrom() {
        if(selectedDateFrom == null){
            if(isFiltered) selectedDateFrom = filter.getDateFrom();
            else selectedDateFrom = getNextMonday();
        }
        return selectedDateFrom;
    }
    public void setSelectedDateFrom(Date selectedDateFrom) {
        this.selectedDateFrom = selectedDateFrom;
    }
    public Date getSelectedDateTo() {
        if(selectedDateTo == null && isFiltered){
            selectedDateTo = filter.getDateTo();
        }
        return selectedDateTo;
    }
    public void setSelectedDateTo(Date selectedDateTo) {
        this.selectedDateTo = selectedDateTo;
    }    
    public House getSelectedHouse() {
        return selectedHouse;
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
    
    
    private List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {
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
    
    public int selectedHousePeriodPrice(){
        if(selectedDateFrom==null || selectedDateTo== null || selectedHouse==null) return 0;
        int dayCount = getDaysBetweenDates(selectedDateFrom, selectedDateTo).size()+1;
        return selectedHouse.getWeekprice() * (dayCount/7);
    }
    
    private Date getNextMonday(){
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
    
    public String confirmSelectedHouse(){
        if(selectedHouse== null || selectedDateFrom == null || selectedDateTo==null
                || !isHouseAvailable(selectedHouse, selectedDateFrom, selectedDateTo)|| selectedHousePeriodPrice() == 0){
           return "";
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("house", selectedHouse);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dateFrom", selectedDateFrom);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dateTo", selectedDateTo);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("serviceList", selectedHouseSelectedServices);
        
        
        return "reservationConfirmation.html";
    }

    public List<String> getSelectedHouseImages() {
        return selectedHouseImages;
    }

    public void setSelectedHouseImages(List<String> selectedHouseImages) {
        this.selectedHouseImages = selectedHouseImages;
    }
    
}
