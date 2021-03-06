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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.business.BuyConfirmationImpl;
import lt.vu.mif.labanoro_draugai.business.BuyConfirmationInterface;
import lt.vu.mif.labanoro_draugai.entities.Houseimage;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Service;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
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
public class SummerhouseManager implements Serializable {

    enum Ordering {
        Cheap, Expensive, Old, New
    }

    private String currency;
    private static final long serialVersionUID = 1L;
    private List<House> summerhouses;
    private List<House> filteredSummerhouses;
    private boolean isFiltered = false;
    List<String> selectedHouseImages;
    private boolean showOnlyFree;
    
    //Decorator
    @Inject
    private BuyConfirmationInterface priceService;
    
    //Dialog
    private House selectedHouse;
    private Date selectedDateFrom;
    private Date selectedDateTo;
    private String selectedHouseReservedDays;
    private List<Service> selectedHouseAvailableServices;
    private List<String> selectedHouseSelectedServices;

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
    private Map<String, Ordering> availableOrderings;

    //Service checkbox list
    private List<String> availableFilters;
    private String[] selectedFilters;

    @Inject
    DatabaseManager dbm;

    private Person user;
    
    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) (ec.getRequest());
        if (request == null || request.getUserPrincipal() == null || request.getUserPrincipal().getName() == null && request.getUserPrincipal().getName().isEmpty()) {
            try {
                ec.redirect("/Labanoro_Draugai/login.html");
                return;
            } catch (IOException ex) {
                Logger.getLogger(ReservationConfirmationManager.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        user = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());
        
        
        summerhouses = new ArrayList<>();;
        filteredSummerhouses = new ArrayList<>();
        for(House house:(List<House>) dbm.getAllEntities("House")){
            if(house.getIsactive()!= null &&  house.getIsactive()){
                summerhouses.add(house);
                filteredSummerhouses.add(house);
            }
        }
//        filteredSummerhouses = (List<House>) dbm.getAllEntities("House");
//        Collections.reverse(summerhouses);
//        Collections.reverse(filteredSummerhouses);
        System.out.println("summerhouses size:" + summerhouses.size());
        //filter
        //Papulint servisus is db
        List<Service> services = (List<Service>) dbm.getAllEntities("Service");

        availableFilters = new ArrayList<>();
        for (Service service : services) {
            availableFilters.add(service.getTitle());
        }
        ////////////////////////

        availableOrderings = new LinkedHashMap<>();
        availableOrderings.put("Naujausi", Ordering.New);
        availableOrderings.put("Seniausi", Ordering.Old);
        availableOrderings.put("Pigiausi", Ordering.Cheap);
        availableOrderings.put("Brangiausi", Ordering.Expensive);
        ordering = Ordering.Expensive;
        sortHouses();
        setMaxHousePrice(getWeekPriceHouse(filteredSummerhouses.get(0)));
        setPriceTo(getMaxHousePrice());
        ordering = Ordering.New;
        sortHouses();
        Systemparameter param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Currency.Euro");
        if (param == null) {
            System.out.println("Truksta \"SystemParameter.Currency.Euro\" parametro");
            currency = "?";
            return;
        }
        currency = param.getValue();
        System.out.println(toString() + " constructed.");
    }

    public boolean userMembershipPayed(){
        return user.getMembershipdue().after(new Date());
    }
    
    public String firstImageName(House house) {
        if (house == null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) {
            return null;
        }
        Predicate condition = new Predicate() {
            public boolean evaluate(Object sample) {
                return ((Houseimage) sample).getSequence().equals(1);
            }
        };
        Houseimage result = (Houseimage) CollectionUtils.select(house.getHouseimageList(), condition).iterator().next();
        return result.getInternalname();
    }

    public List<String> HouseImageNames(House house) {
        if (house == null || house.getHouseimageList() == null || house.getHouseimageList().isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (Houseimage img : house.getHouseimageList()) {
            result.add(img.getInternalname());
        }
        return result;
    }

    //TODO availability filter
    public void filter() {
        System.out.println("This is filter:" + getOrdering());

        filteredSummerhouses = new ArrayList<>();
        if (showOnlyFree) {
            for (House house : summerhouses) {
                if ((getPlaceCount() == 0 || house.getNumberofplaces() >= getPlaceCount())
                        && (getPriceFrom().compareTo(getWeekPriceHouse(house)) <= 0 && getWeekPriceHouse(house).compareTo(getPriceTo()) <= 0)
                        && isHouseAvailable(house, getDateFrom(), getDateTo()) && hasSelectedServices(house, getSelectedFilters())) {
                    filteredSummerhouses.add(house);
                }
            }
        } else {
            for (House house : summerhouses) {
                if ((getPlaceCount() == 0 || house.getNumberofplaces() >= getPlaceCount())
                        && (getPriceFrom().compareTo(getWeekPriceHouse(house)) <= 0 && getWeekPriceHouse(house).compareTo(getPriceTo()) <= 0)
                        && isHouseAvailableInPeriod(house, getDateFrom(), getDateTo()) && hasSelectedServices(house, getSelectedFilters())) {
                    filteredSummerhouses.add(house);
                }
            }
        }

        sortHouses();
        if(showOnlyFree)isFiltered = true;
        else isFiltered = false;
    }

    private boolean hasSelectedServices(House house, String[] serviceNames) {
        
        boolean found = false;
        
        if (serviceNames == null || serviceNames.length == 0) {
            return true;
        }
        if (house.getServiceList() == null || house.getServiceList().isEmpty()) {
            return false;
        }
        for (String name : serviceNames) {
            for (Service service : house.getServiceList()) {
                if (service.getTitle().equalsIgnoreCase(name)) {
                    found=true;
                    break;
                } else {
                    found=false;
                }
            }
            if(!found) return false;
        }
        return true;
    }

    private boolean isHouseAvailableInPeriod(House house, Date dateFrom, Date dateTo) {
        house = (House) dbm.getEntity("House", "Housereg", house.getHousereg());
        if (house.getReservationList() == null || dateFrom == null || dateTo == null) {
            return true;
        }
        if (house.getSeasonstartdate() != null && house.getSeasonenddate() != null && (dateTo.before(house.getSeasonstartdate()) || dateFrom.after(house.getSeasonenddate()))) {
            return false;
        }
        return true;
    }

    private boolean isHouseAvailable(House house, Date dateFrom, Date dateTo) {
        house = (House) dbm.getEntity("House", "Housereg", house.getHousereg());
        if (house.getReservationList() == null || dateFrom == null || dateTo == null) {
            return true;
        }
        if (house.getSeasonstartdate() != null && house.getSeasonenddate() != null && (dateTo.before(house.getSeasonstartdate()) || dateFrom.after(house.getSeasonenddate()))) {
            return false;
        }
        for (Reservation reservation : house.getReservationList()) {
            if ((dateFrom.equals(reservation.getStartdate()) || dateTo.equals(reservation.getEnddate())) || !(dateTo.before(reservation.getStartdate()) || dateFrom.after(reservation.getEnddate()))) {
                return false;
            }
        }
        return true;
    }

    private void sortHouses() {
        switch (getOrdering()) {
            case Cheap:
                Collections.sort(filteredSummerhouses, new Comparator<House>() {
                    @Override
                    public int compare(House h1, House h2) {
                        return getWeekPriceHouse(h1).compareTo(getWeekPriceHouse(h2));
                    }
                });
                break;
            case Expensive:
                Collections.sort(filteredSummerhouses, new Comparator<House>() {
                    @Override
                    public int compare(House h1, House h2) {
                        return getWeekPriceHouse(h2).compareTo(getWeekPriceHouse(h1));
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

    public void showAll() {
        filteredSummerhouses = new ArrayList<>();
        for (House house : summerhouses) {
            filteredSummerhouses.add(house);
        }
        sortHouses();
        isFiltered = false;
    }

    public Date getEndOfSeason() {
        String lastReservationDayStr = dbm.getSystemParameter("SystemParameter.Reservation.LastReservationDay").getValue();
        String lastCountDateStr = dbm.getSystemParameter("SystemParameter.priorityGroup.LastCountDate").getValue();
        
        Calendar today = Calendar.getInstance();
        
        if (lastCountDateStr.equals("")) {
            today.add(Calendar.YEAR, 1);
            return today.getTime();
        }
        
        int lastReservationDay = Integer.parseInt(lastReservationDayStr);
            
        if (lastReservationDay < 0) {
            today.add(Calendar.YEAR, 1);
            return today.getTime();
        }
                
        String[] lastCountDateParts = lastCountDateStr.split(",");
        int[] dateParts = new int[lastCountDateParts.length];

        if (dateParts.length != 4) {
            today.add(Calendar.YEAR, 1);
            return today.getTime();
        }

        for (int i = 0; i < lastCountDateParts.length; i++) {
            dateParts[i] = Integer.parseInt(lastCountDateParts[i]);
        }

        Calendar lastCountDate = Calendar.getInstance();
        lastCountDate.set(Calendar.YEAR, dateParts[0]);
        lastCountDate.set(Calendar.MONTH, (dateParts[1] - 1));
        lastCountDate.set(Calendar.DAY_OF_MONTH, dateParts[2]);
        lastCountDate.set(Calendar.HOUR_OF_DAY, dateParts[3]);

        lastCountDate.add(Calendar.DAY_OF_MONTH, lastReservationDay);
                
        return lastCountDate.getTime();
    }

    public Date getNextMonday() {
        Calendar now = Calendar.getInstance();
        int weekday = now.get(Calendar.DAY_OF_WEEK);
        if (weekday != Calendar.MONDAY) {
            // calculate how much to add
            // the 2 is the difference between Saturday and Monday
            int days = (Calendar.SATURDAY - weekday + 2) % 7;
            now.add(Calendar.DAY_OF_YEAR, days);
        }
        // now is the date you want
        return now.getTime();
    }

    public void handleDialogClose(CloseEvent event) {
        selectedHouse = null;
        selectedDateFrom = null;
        selectedDateTo = null;
        selectedHouseReservedDays = null;
        selectedHouseAvailableServices = null;
        selectedHouseSelectedServices = null;
    }

    public Date selectedHouseMinDate() {
        Date mon = getNextMonday();
        if (selectedHouse == null || selectedHouse.getSeasonstartdate() == null) {
            return mon;
        }
        return selectedHouse.getSeasonstartdate().after(mon) ? selectedHouse.getSeasonstartdate() : mon;
    }

    public Date selectedHouseMaxDate() {
        if (selectedHouse == null || selectedHouse.getSeasonenddate() == null) {
            return getEndOfSeason();
        }
        return selectedHouse.getSeasonenddate().before(getEndOfSeason()) ? selectedHouse.getSeasonenddate() : getEndOfSeason();
    }

    public Date selectedHouseMaxDateTo() {
        Date maxDate = selectedHouseMaxDate();
        if (selectedHouse == null || selectedDateFrom == null || selectedHouse.getReservationList() == null) {
            return maxDate;
        }
        for (Reservation reservation : selectedHouse.getReservationList()) {
            if (reservation.getStartdate().before(maxDate) && reservation.getStartdate().after(selectedDateFrom)) {
                maxDate = reservation.getStartdate();
            }
        }
        return maxDate;
    }

    public void onDateSelect() {
        if (getSelectedDateTo() == null || getSelectedDateFrom() == null) {
            return;
        }
        if (getSelectedDateTo().before(getSelectedDateFrom())) {
            setSelectedDateTo(null);
        }
    }

    public String getSelectedHouseReservedDays() {
        if (selectedHouse == null || selectedHouse.getReservationList() == null) {
            return "[]";
        }
        List<Date> dates;
        String prefix = "";
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("M-d-yyyy");
        StringBuilder builder = new StringBuilder();
        for (Reservation reservation : selectedHouse.getReservationList()) {
            dates = getDaysBetweenDates(reservation.getStartdate(), reservation.getEnddate());
            for (Date date : dates) {
                builder.append(prefix);
                prefix = ",";
                builder.append(formatter.format(date));
            }
        }

        selectedHouseReservedDays = builder.toString();
        return selectedHouseReservedDays;
    }

    //Modded setters/getters 
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

    public BigDecimal selectedHousePeriodPrice() {
        if (selectedDateFrom == null || selectedDateTo == null || selectedHouse == null) {
            return new BigDecimal(0);
        }
        int dayCount = getDaysBetweenDates(selectedDateFrom, selectedDateTo).size() + 1;
        BigDecimal price = getWeekPriceHouse(selectedHouse).multiply(new BigDecimal(dayCount / 7));
        if (selectedHouseSelectedServices != null && selectedHouseAvailableServices != null) {
            for (String serviceReg : selectedHouseSelectedServices) {
                for (Service service : selectedHouseAvailableServices) {
                    if (service.getServicereg().equals(serviceReg)) {
                        price = price.add(servicePeriodPrice(service));
                        break;
                    }
                }
            }
        }
        return price;
    }

    public BigDecimal servicePeriodPrice(Service service) {
        if (selectedDateFrom == null || selectedDateTo == null || selectedHouse == null || service == null) {
            return new BigDecimal(0);
        }
        int dayCount = getDaysBetweenDates(selectedDateFrom, selectedDateTo).size() + 1;

        return getWeekPriceService(service).multiply(new BigDecimal(dayCount / 7));
    }

    public String confirmSelectedHouse() {
        if (user == null|| user.getTypeid().getInternalname().equalsIgnoreCase("Person.Candidate") || selectedHouse == null || selectedDateFrom == null || selectedDateTo == null
                || !isHouseAvailable(selectedHouse, selectedDateFrom, selectedDateTo) || (selectedHousePeriodPrice().compareTo(new BigDecimal(0)) == 0) || !userMembershipPayed()) {
            return "";
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("house", selectedHouse);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dateFrom", selectedDateFrom);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dateTo", selectedDateTo);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedServices", selectedHouseSelectedServices);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("price", selectedHousePeriodPrice().doubleValue());

        return "reservationConfirmation?faces-redirect=true";
    }

    public void setSelectedFilters(String[] selectedFilters) {
        if (selectedFilters != null) {
            System.out.println("Selected services:");
            for (String str : selectedFilters) {
                System.out.println("*" + str);
            }
        }
        this.selectedFilters = selectedFilters;
    }
    
    public boolean isCandidate(){
        return user.getTypeid().getInternalname().equalsIgnoreCase("Person.Candidate");
    } 
    public void setSelectedHouse(House selectedHouse) {
        selectedHouseAvailableServices = new ArrayList<>();
        if (selectedHouse != null) {
            for (Service service : selectedHouse.getServiceList()) {
                selectedHouseAvailableServices.add(service);
            }
        }
        this.selectedHouse = selectedHouse;
    }

    public Date getSelectedDateTo() {
        if (selectedDateTo == null && isFiltered) {
            selectedDateTo = getDateTo();
        }
        return selectedDateTo;
    }

    public Date getSelectedDateFrom() {
        if (selectedDateFrom == null) {
            if (isFiltered) {
                selectedDateFrom = getDateFrom();
            }
//            else selectedDateFrom = getNextMonday();
        }
        return selectedDateFrom;
    }
    
    public BigDecimal getWeekPriceHouse(House house){
        if(house == null) return new BigDecimal(Integer.MAX_VALUE);
        return priceService.getDiscountPrice(house.getWeekprice(), user, "Payment.Reservation");
    }
    
    public BigDecimal getWeekPriceService(Service service){
        if(service == null) return new BigDecimal(Integer.MAX_VALUE);
        return priceService.getDiscountPrice(service.getWeekprice(), user, "Payment.Reservation");
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

    public List<Service> getSelectedHouseAvailableServices() {
        return selectedHouseAvailableServices;
    }

    public void setSelectedHouseAvailableServices(List<Service> selectedHouseAvailableServices) {
        this.selectedHouseAvailableServices = selectedHouseAvailableServices;
    }

    public List<String> getSelectedHouseSelectedServices() {
        return selectedHouseSelectedServices;
    }

    public void setSelectedHouseSelectedServices(List<String> selectedHouseSelectedServices) {
        this.selectedHouseSelectedServices = selectedHouseSelectedServices;
    }

    public void setSelectedHouseReservedDays(String selectedHouseReservedDays) {
        this.selectedHouseReservedDays = selectedHouseReservedDays;
    }

    public List<House> getFilteredSummerhouses() {
        return filteredSummerhouses;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isShowOnlyFree() {
        return showOnlyFree;
    }

    public void setShowOnlyFree(boolean showOnlyFree) {
        this.showOnlyFree = showOnlyFree;
    }

    public Person getUser() {
        return user;
    }
}
