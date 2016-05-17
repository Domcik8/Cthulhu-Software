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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebServlet(urlPatterns = {"/Stripe"})
public class StripeServlet extends HttpServlet {

    @Inject
    DatabaseManager dbm;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Darau dopost");
        String token = request.getParameter("stripeToken");
        System.out.println(token);
        System.out.println(request.getParameter("Price1"));
        String username = request.getUserPrincipal().getName();
        Payment payment;
        Reservation reservation;
        
        //ReservationCreation
        if(request.getParameter("order")!= null || !request.getParameter("order").isEmpty()){          
            try {
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(request.getParameter("order"));
                switch((String)json.get("type")){
                    case "houseReservation":
                        payment = dbm.addPayment(username,BigDecimal.valueOf(Double.parseDouble(request.getParameter("Price1"))), new Date(), "Payment.Reservation","Currency.Euro");
                        reservation = createHouseReservation(username,json,payment.getPaymentreg());
                        payment.setReservationid(reservation);
                        dbm.updateEntity(payment);
                        break;
                }
                
            } catch (ParseException ex) {
                Logger.getLogger(StripeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.text.ParseException ex) {
                Logger.getLogger(StripeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }         
        }

        
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
    
    private Reservation createHouseReservation(String username, JSONObject json, String paymentReg) throws java.text.ParseException{
        List<String> services = new ArrayList();
        JSONArray arr = (JSONArray)json.get("services");
        for (int i = 0; i < arr.size(); i++) {
            services.add((String)arr.get(i));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dbm.addReservation((String) json.get("houseReg"), paymentReg, (String) json.get("reservationTypeInternalName"), 
                username, services, sdf.parse((String) json.get("dateFrom")), sdf.parse((String) json.get("dateTo")));
    }
    
}
