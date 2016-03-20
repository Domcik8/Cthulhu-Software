/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import lt.vu.mif.entities.House;

/**
 *
 * @author Karolis
 */
@Named
@Stateful
@ViewScoped
public class SummerhouseManager {
    private List<House> summerhouses;

    @PostConstruct
    public void init() {
        List<House> houses = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            houses.add(new House(Long.valueOf(i),"reg"+i,1,new Date(),new Date(),50));
        }
        summerhouses = houses;
        System.out.println(toString() + " constructed.");
    }

    public List<House> getSummerhouses() {
        return summerhouses;
    }
    
    
}
