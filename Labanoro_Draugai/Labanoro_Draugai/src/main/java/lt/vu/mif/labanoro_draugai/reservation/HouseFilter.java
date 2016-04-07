/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.reservation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Karolis
 */
@ManagedBean(name = "houseFilter", eager = true)
@SessionScoped
public class HouseFilter implements Serializable{
    private static final long serialVersionUID = 1L;
    
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
    }
    
    public String[] getSelectedFilters() {
        return selectedFilters;
    }

    public void setSelectedFilters(String[] selectedFilters) {
        this.selectedFilters = selectedFilters;
    }
    
    public List<String> getAvailableFilters() {
        return availableFilters;
    }

    public void setAvailableFilters(List<String> availableFilters) {
        this.availableFilters = availableFilters;
    }
}
