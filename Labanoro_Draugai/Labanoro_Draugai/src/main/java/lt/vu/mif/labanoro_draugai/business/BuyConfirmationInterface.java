/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import java.math.BigDecimal;

/**
 *
 * @author werezz
 */
public interface BuyConfirmationInterface {

    public long getDiscountPrice(long price);
    public BigDecimal getDiscountPrice(BigDecimal price);
    public BigDecimal getIncreasedPoints(BigDecimal points);

}
