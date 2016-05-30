/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import lt.vu.mif.labanoro_draugai.entities.Person;

/**
 *
 * @author werezz
 */
@Stateless
public class BuyConfirmationImpl implements BuyConfirmationInterface {

    @Override
    public long getDiscountPrice(long price, Person user,String typeInternalName) {
        return price;
    }

    @Override
    public BigDecimal getDiscountPrice(BigDecimal price, Person user,String typeInternalName) {
        return price;
    }

    @Override
    public BigDecimal getIncreasedPoints(BigDecimal points, Person user,String typeInternalName) {
        return points;
    }
    
}
