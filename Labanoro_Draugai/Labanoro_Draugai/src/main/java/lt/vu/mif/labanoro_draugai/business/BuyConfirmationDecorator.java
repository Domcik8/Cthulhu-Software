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
    public long getDiscountPrice(long price) {
        price = priceDiscount.getDiscountPrice(price);
        return isJonas() ? price / 2 : price;
    }

    @Override
    public BigDecimal getDiscountPrice(BigDecimal price) {
        price = priceDiscount.getDiscountPrice(price);
        return isJonas() ? price.divide(new BigDecimal(2)) : price;
    }

    @Override
    public BigDecimal getIncreasedPoints(BigDecimal points) {
        points = priceDiscount.getIncreasedPoints(points);
        return isJonas() ? points.multiply(new BigDecimal(2)) : points;
    }

    private boolean isJonas() {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) (ec.getRequest());

        Person user = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());

        return user.getFirstname().equalsIgnoreCase("Jonas");

    }

}
