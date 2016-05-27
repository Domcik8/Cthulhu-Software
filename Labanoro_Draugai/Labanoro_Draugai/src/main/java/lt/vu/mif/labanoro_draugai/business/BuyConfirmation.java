/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import net.sf.json.JSONObject;

/**
 *
 * @author werezz
 */
@ManagedBean
@ViewScoped
public class BuyConfirmation implements Serializable {

    @Inject
    DatabaseManager dbm;

    private String[] prices;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
                System.out.println("price updated:"+price);
        this.price = price;
    }

    @PostConstruct
    public void init() {

        Systemparameter parameter = dbm.getSystemParameter("SystemParameter.BuyPoints");
        prices = parameter.getValue().split(";");
        System.out.println("Kainos");
        for (String i : prices) {
            System.out.println(i);
        }
        if (prices.length != 0) {
            price = prices[0];
        }
    }

    public long getPriceInCents() {
        System.out.println("cents updated:"+new Date());
        if (price == null || price.isEmpty()) {
            return -1;
        }
        System.out.println("price:"+price);
        double temp = Double.valueOf(price);
        temp *= 100;
        return Math.round(temp);

    }

    public String createBuyJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("type", "buyPoints");
        return jsonObject.toString();
    }

    public String[] getPrices() {
        return prices;
    }

}
