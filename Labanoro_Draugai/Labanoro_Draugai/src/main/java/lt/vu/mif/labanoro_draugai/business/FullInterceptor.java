/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import static javax.interceptor.Interceptor.Priority.APPLICATION;
import javax.interceptor.InvocationContext;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author werezz
 */
@Interceptorius @Dependent @Interceptor @Priority(APPLICATION)
public class FullInterceptor implements Serializable{

    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception {
        System.out.println("*** Intercepting call to StripeServlet method: "
                + ctx.getMethod().getName());
        System.out.println("*** Intercepting call to StripeServlet method: "
                + ctx.getTarget().getClass().getSuperclass().getName());
        System.out.println("lolololololololo");
        return ctx.proceed();
    }

}
