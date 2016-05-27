/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

import java.util.Arrays;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author werezz
 */
public class Interceptor {

    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception {
        System.out.println("*** Intercepting call to StripeServlet method: "
                + ctx.getMethod().getName());        
         System.out.println("*** Intercepting call to StripeServlet method: "
                + ctx.getTarget().getClass().getSuperclass().getName());
        return ctx.proceed();
    }
}
