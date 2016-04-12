/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import lt.vu.mif.entities.Person;
import lt.vu.mif.labanoro_draugai.authentication.FacebookProvider;
import lt.vu.mif.labanoro_draugai.authentication.service.UserService;

import javax.validation.Valid;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
*/


/**
 *
 * @author AlaNote
 */
//@Controller
public class SocialController {

    //@Autowired
    FacebookProvider facebookProvider;

    //@Autowired
    UserService userService;

    /**
     * Callback from Facebook on success login
     *
     * @param accessToken
     * @param facebookUserId
     * @return
     */ /*
    @RequestMapping(value = "/connect/facebook", method = RequestMethod.POST)
    public String connectAccountToFacebook(String accessToken, String facebookUserId) {
        if (facebookUserId != null && accessToken != null) {
            // store facebook information
            String userName = getCurrentUser().getUsername();
            userService.updateFacebookAuthentication(userName, accessToken, facebookUserId);
        }
        return "redirect:/status";
    }

    /**
     * Display user status page
     *
     * @param model
     * @return
     */ /*
    @RequestMapping("/status")
    public String showStatus(Model model) {
        Person user = getCurrentUser();

  // ! uncomment !     FacebookTemplate facebookTemplate = facebookProvider.createTemplate(user);
 
        // put facebook info into page model
        if (facebookTemplate != null) {
            model.addAttribute("connectedToFacebook", true);
            //model.addAttribute("facebookProfile", facebookTemplate.getUserProfile());
            model.addAttribute("facebookProfile", facebookTemplate.userOperations().getUserProfile());
        } else {
            model.addAttribute("connectedToFacebook", false);
        }


        // put empty status form
        model.addAttribute("statusForm", new StatusForm());

        return "status";
    } */

    /**
     * Send status to supported services
     *
     * @param statusForm
     * @param result
     * @param modelMap
     * @return
     */ /*
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public String sendStatus(@Valid StatusForm statusForm, BindingResult result, ModelMap modelMap) {
        Person user = getCurrentUser();

  
   // ! uncomment !        FacebookTemplate facebookTemplate = facebookProvider.createTemplate(user);


        // send message to Facebook
       if (facebookTemplate != null) {
            //facebookTemplate.updateStatus(statusForm.getStatus());
            facebookTemplate.feedOperations().updateStatus(statusForm.getStatus());
        }

        return "redirect:/status";
    } */

    /**
     * Returns current user
     *
     * @return
     */ /*
    protected Person getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        //Person user = userService.findByUsername(userName);

        return null;
    }*/
}
