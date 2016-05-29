/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;

/**
 *
 * @author werezz
 */
@Dependent
@Decorator
public class BuyConfirmationDecorator implements Serializable, BuyConfirmationInterface {

    @Inject
    @Delegate
    @Any
    BuyConfirmationInterface priceDiscount;

    @Inject
    DatabaseManager dbm;

    private String price;
    private String ratio;
    private String[] prices;
    private String currency;

    @Override
    @PostConstruct
    public void init() {

        priceDiscount.init();
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

    @Override
    public long getPriceInCents() {
        
        if (price == null || price.isEmpty()) {
            return -1;
        }
        double temp = Double.valueOf(price);
        Systemparameter exchangeratio = dbm.getSystemParameter("SystemParameter.ExchangeRate.Euro");
        ratio = exchangeratio.getValue();
        temp *= Double.valueOf(ratio);
        temp *= 20;
        //priceDiscount.getPriceInCents()=temp;
        System.out.println("Dekoratoriussususususususu");
        return (Math.round(priceDiscount.getPriceInCents()));
    }

}
