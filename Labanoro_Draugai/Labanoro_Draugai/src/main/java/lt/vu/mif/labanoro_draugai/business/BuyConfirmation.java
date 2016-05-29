/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import net.sf.json.JSONObject;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author werezz
 */
@Named
@ViewScoped
public class BuyConfirmation implements Serializable {

    @Inject
    DatabaseManager dbm;

    @Inject
    private BuyConfirmationInterface priceService;

    private String[] prices;
    private String price;
    private String currency;
    private String ratio;

    @PostConstruct
    public void init() {

        Systemparameter parameter = dbm.getSystemParameter("SystemParameter.BuyPoints");
        if (parameter.getValue() == null || parameter.getValue().isEmpty()) {
            return;
        }
        prices = parameter.getValue().split(";");
        System.out.println("Kainos");
        for (String i : prices) {
            System.out.println(i);
        }
        if (prices.length != 0) {
            price = prices[0];
        }

        Systemparameter param = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Currency.Euro");
        if (param == null) {
            System.out.println("Truksta \"SystemParameter.Currency.Euro\" parametro");
            currency = "?";
            return;
        }
        currency = param.getValue();
    }

    public BigDecimal getPriceInPoints() {
        if (price == null || price.isEmpty()) {
            return new BigDecimal(-1);
        }
        BigDecimal exchangeratio = new BigDecimal(dbm.getSystemParameter("SystemParameter.ExchangeRate.Euro").getValue());
        return exchangeratio.multiply(new BigDecimal(price));
    }

    public BuyConfirmationInterface getPriceService() {
        return priceService;
    }

    public void setPriceService(BuyConfirmationInterface priceService) {
        this.priceService = priceService;
    }

    public long getPriceInCents() {
        System.out.println("cents updated:" + new Date());
        if (price == null || price.isEmpty()) {
            return -1;
        }
        System.out.println("price:" + price);
        double temp = Double.valueOf(price);
        temp *= 100;
        return Math.round(temp);

    }

    @Interceptorius
    public String createBuyJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("type", "buyPoints");
        return jsonObject.toString();
    }

    public String[] getPrices() {
        return prices;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }
}
