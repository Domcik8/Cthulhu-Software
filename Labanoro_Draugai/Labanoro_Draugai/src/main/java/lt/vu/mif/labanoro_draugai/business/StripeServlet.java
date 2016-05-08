/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.business;

/**
 *
 * @author werezz
 */
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/Stripe"})
public class StripeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Darau dopost");
        String token = request.getParameter("stripeToken");
        System.out.println(token);
        System.out.println(request.getParameter("Price1"));

        Stripe.apiKey = "sk_test_GkbxvPwRpIG9T4aIiruw0TWl";

// Create the charge on Stripe's servers - this will charge the user's card
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount",request.getParameter("Price1")); // amount in cents, again
            chargeParams.put("currency", "eur");
            chargeParams.put("source", token);
            chargeParams.put("description", "Example charge");

            Charge charge;
            charge = Charge.create(chargeParams);
        } catch (CardException e) {
            // The card has been declined
        } catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException ex) {
            Logger.getLogger(StripeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("/Labanoro_Draugai/index.html");
    }   
}
