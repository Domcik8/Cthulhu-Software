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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lt.vu.mif.labanoro_draugai.entities.House;
import lt.vu.mif.labanoro_draugai.entities.Payment;
import lt.vu.mif.labanoro_draugai.entities.Person;
import lt.vu.mif.labanoro_draugai.entities.Reservation;
import lt.vu.mif.labanoro_draugai.entities.Systemparameter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebServlet(urlPatterns = {"/Stripe"})
public class StripeServlet extends HttpServlet {

    @Inject
    DatabaseManager dbm;

    private Person user;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Darau dopost");
        String token = request.getParameter("stripeToken");
        System.out.println(token);
        System.out.println(request.getParameter("Price1"));
        String username = request.getUserPrincipal().getName();
        Payment payment;
        Reservation reservation;
        Systemparameter redirectparam = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Redirect.Index");

        //ReservationCreation
        if (request.getParameter("order") != null || !request.getParameter("order").isEmpty()) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(request.getParameter("order"));
                user = (Person) dbm.getEntity("Person", "Email", request.getUserPrincipal().getName());
                switch ((String) json.get("type")) {
                    case "houseReservation":
                        payment = dbm.addPayment(username, BigDecimal.valueOf(Double.parseDouble(request.getParameter("Price1")) / 100), new Date(), "Payment.Reservation", "Currency.Euro");
                        reservation = createHouseReservation(username, json, payment.getPaymentreg());

                        payment.setReservationid(reservation);
                        payment = (Payment) dbm.updateEntity(payment);
                        
                        Person person = (Person) dbm.getEntity("Person", "Email", username);
                        person.getReservationList().add(reservation);
                        person.getPaymentList().add(payment);
                        person = (Person) dbm.updateEntity(person);
                        
                        House house = (House) dbm.getEntity("House", "Housereg", (String) json.get("houseReg"));
                        house.getReservationList().add(reservation);
                        house = (House) dbm.updateEntity(house);

                        dbm.addPaymentLog(user.getEmail(), user.getTypeid().getInternalname(), "lt.vu.mif.labanoro_draugai.business.houseReservation");
                        
                        redirectparam = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Redirect.MyReservations");
                        break;
                    case "buyPoints":
                        payment = dbm.addPayment(username, BigDecimal.valueOf(Double.parseDouble(request.getParameter("Price1")) / 100), new Date(), "Payment.Points", "Currency.Euro");
                        person = (Person) dbm.getEntity("Person", "Email", username);
                        person.getPaymentList().add(payment);
                        
                        person = (Person) dbm.updateEntity(person);
                        
                        dbm.addPaymentLog(user.getEmail(), user.getTypeid().getInternalname(), "lt.vu.mif.labanoro_draugai.business.buyPoints");


                        redirectparam = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Redirect.Buy");
                        break;
                    case "membershipPayment":
                        payment = dbm.addPayment(username, BigDecimal.valueOf(Double.parseDouble(request.getParameter("Price1")) / 100), new Date(), "Payment.Membership", "Currency.Euro");
                        person = (Person) dbm.getEntity("Person", "Email", username);
                        person.getPaymentList().add(payment);
                        
                        person = (Person) dbm.updateEntity(person);
                        
                        dbm.addPaymentLog(user.getEmail(), user.getTypeid().getInternalname(), "lt.vu.mif.labanoro_draugai.business.membershipPayment");


                        redirectparam = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.Redirect.Buy");
                        break;
                }

            } catch (ParseException ex) {
                Logger.getLogger(StripeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.text.ParseException ex) {
                Logger.getLogger(StripeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Stripe.apiKey = dbm.getSystemParameter("SystemParameter.StripeTestSecretKey").getValue();

// Create the charge on Stripe's servers - this will charge the user's card
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", request.getParameter("Price1")); // amount in cents, again
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
//REDIRECT

        if (redirectparam == null) {
            System.out.println("Prašome nustatyti sistemos nukreipimo parametrus");
            response.sendRedirect("/Labanoro_Draugai/index.html");
            return;
        }
        Systemparameter ContextParam = (Systemparameter) dbm.getEntity("SystemParameter", "internalName", "SystemParameter.General.ContextPath");
        if (ContextParam == null) {
            System.out.println("Truksta \"SystemParameter.General.ContextPath\" parametro");
            response.sendRedirect("/Labanoro_Draugai/index.html");
            return;
        }
        response.sendRedirect(ContextParam.getValue() + redirectparam.getValue() + "?faces-redirect=true");
    }

    private Reservation createHouseReservation(String username, JSONObject json, String paymentReg) throws java.text.ParseException {
        List<String> services = new ArrayList();
        JSONArray arr = (JSONArray) json.get("services");
        for (int i = 0; i < arr.size(); i++) {
            services.add((String) arr.get(i));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dbm.addReservation((String) json.get("houseReg"), paymentReg, (String) json.get("reservationTypeInternalName"),
                username, services, sdf.parse((String) json.get("dateFrom")), sdf.parse((String) json.get("dateTo")));
    }

}
