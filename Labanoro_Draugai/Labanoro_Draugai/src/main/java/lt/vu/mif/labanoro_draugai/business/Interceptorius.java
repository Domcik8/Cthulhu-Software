/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.inject.Named;
import javax.interceptor.InterceptorBinding;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author werezz
 */
@Named
@ViewScoped
@InterceptorBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})

public @interface Interceptorius {

}
