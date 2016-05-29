/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author werezz
 */
@Decorator
public class BuyConfirmationDecorator implements Serializable, BuyConfirmationInterface {

    @Inject
    DatabaseManager dbm;

    @Inject
    @Delegate
    BuyConfirmationInterface priceDiscount;

    @Override
    public long getDiscountPrice(long price, Person user) {
        price = priceDiscount.getDiscountPrice(price, user);
        return isJonas(user) ? price / 2 : price;
    }

    @Override
    public BigDecimal getDiscountPrice(BigDecimal price, Person user) {
        price = priceDiscount.getDiscountPrice(price, user);
        return isJonas(user) ? price.divide(new BigDecimal(2)) : price;
    }

    @Override
    public BigDecimal getIncreasedPoints(BigDecimal points, Person user) {
        points = priceDiscount.getIncreasedPoints(points, user);
        return isJonas(user) ? points.multiply(new BigDecimal(2)) : points;
    }

    private boolean isJonas(Person user) {

        return user.getFirstname().equalsIgnoreCase("Jonas");

    }

}
