/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lt.vu.mif.labanoro_draugai.entities.House;

/**
 *
 * @author Karolis
 */
@ManagedBean(name="summerhouseManager")
@ViewScoped
public class SummerhouseManager implements Serializable{
    private List<House> summerhouses;
    private List<House> filteredSummerhouses;
    private House selectedHouse;
    private List<String> houseImages;
    
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
        
        houseImages = new ArrayList<>();
        houseImages.add("http://www.atostogoskaime.lt/uploads/Sodybos/images/galleries/1633/zoom/6-vietis-namelis.JPG");
        houseImages.add("http://sodyboskaime.lt/sites/sodyboskaime.lt/files/bilvinu_sodyba_1_6792.jpg");
        houseImages.add("http://g4.dcdn.lt/images/pix/sodyba-61450842.jpg");
        
        System.out.println(toString() + " constructed.");
    }
    
    public List<House> getFilteredSummerhouses() {
        return filteredSummerhouses;
    }
    //TODO availability filter
    public void filter(){
        System.out.println("This is filter:"+filter.getOrdering());
        
        filteredSummerhouses = new ArrayList<>();
        for(House house:summerhouses){
            if((filter.getPlaceCount() == 0 || house.getNumberofplaces()>=filter.getPlaceCount()) &&
                    (filter.getPriceFrom() <= house.getWeekprice() && house.getWeekprice() <= filter.getPriceTo()))
            filteredSummerhouses.add(house);
        }
        
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
    }

    public House getSelectedHouse() {
        return selectedHouse;
    }

    public void setSelectedHouse(House selectedHouse) {
        this.selectedHouse = selectedHouse;
    }

    public List<String> getHouseImages() {
        return houseImages;
    }

    public void setHouseImages(List<String> houseImages) {
        this.houseImages = houseImages;
    }
        public HouseFilter getFilter() {
        return filter;
    }

    public void setFilter(HouseFilter filter) {
        this.filter = filter;
    }
    
}
