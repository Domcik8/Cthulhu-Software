/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Karolis
 */
@ManagedBean(name = "houseFilter", eager = true)
@ViewScoped
public class HouseFilter implements Serializable{
    private static final long serialVersionUID = 1L;
    
    //Service checkbox list
    private List<String> availableFilters;
    public String[] selectedFilters;

    @PostConstruct
    public void init(){
        //Papulint servisus is db
        availableFilters = new ArrayList<>();
        availableFilters.add("Service1");
        availableFilters.add("Service2");
        availableFilters.add("Service3");
        availableFilters.add("Service4");
        availableFilters.add("Service5");
        System.out.println(toString() + " constructed.");
        
        setMaxHousePrice(1000);
        setPriceTo(getMaxHousePrice());
    }
    
    public String[] getSelectedFilters() {
        return selectedFilters;
    }

    public void setSelectedFilters(String[] selectedFilters) {
        if(selectedFilters!=null){
            System.out.println("Selected services:");
            for(String str:selectedFilters)
                System.out.println("*"+str);
        }
        this.selectedFilters = selectedFilters;
    }
    
    public List<String> getAvailableFilters() {
        return availableFilters;
    }

    //dropdown selection
    public void setAvailableFilters(List<String> availableFilters) {
        this.availableFilters = availableFilters;
    }
    
    private String ordering; 

    public String getOrdering() {
        return ordering;
    }

    public void setOrdering(String ordering) {
        this.ordering = ordering;
        System.out.println("Ordering by "+ ordering);
    }
    
    //place count spinner
    private int placeCount;

    public int getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int placeCount) {
        System.out.println("Place count:  "+ placeCount);
        this.placeCount = placeCount;
    }
    
    //Price slider
    private float maxHousePrice;

    public float getMaxHousePrice() {
        return maxHousePrice;
    }

    public void setMaxHousePrice(float maxHousePrice) {
        this.maxHousePrice = maxHousePrice;
    }
    private float priceFrom = 0;

    public float getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(float priceFrom) {
        this.priceFrom = priceFrom;
    }

    public float getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(float priceTo) {
        this.priceTo = priceTo;
    }
    private float priceTo;
    
    //Datepicker
    
    private Date dateFrom = getToday();
    private Date dateTo;

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
    public Date getToday(){
        return new Date();
    }
    
    //Sezono pradžia čia turi but
    public Date getEndOfSeason(){
        Calendar today = Calendar.getInstance(); 
        today.add(Calendar.YEAR, 1);
        return today.getTime();
    }
    
    public void onDateSelect() {
        if(getDateTo()==null || getDateFrom()==null) return;
        if(getDateTo().before(getDateFrom()))
            setDateTo(null);
    }
}
