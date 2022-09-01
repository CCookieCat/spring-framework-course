package com.ccat.springtest.controllers;

import com.ccat.springtest.dao.Offer;
import com.ccat.springtest.service.OffersService;
import com.ccat.springtest.validation.FormValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class OffersController {

    @Autowired
    private OffersService offersService;

    @RequestMapping(value= "/src/main/test", method=RequestMethod.GET)
    public String showTest(Model model, @RequestParam(value="id") String id) {
        System.out.println("Id passed: " + id);
        return "home";
    }

    /*    //EXCEPTION HANDLING:
    @ExceptionHandler(DataAccessException.class)
    public String handleDatabaseException(DataAccessException ex) {
        return "errorpage";
    }
     */


    //DISPLAY OFFERS MAPPING:
//    @RequestMapping("/offers")
//    public String showOffers(Model model) {
//
////        //testing
////        offersService.throwTestException();
//
//        //retrieve Data, pass List to model:
//        List<Offer> offers = offersService.getCurrent();
//        model.addAttribute("offers", offers);
//
//        return "offers";
//    }

    //CREATE OFFER MAPPING:
    @RequestMapping("/createoffer")
    public String createOffer(Model model, Principal principal) {

        Offer offer = null;

        if(principal != null) {
            String username = principal.getName();
            offer = offersService.getOffer(username);
        }

        //if no offer was retrieved create a blank Offer:
        if(offer == null) {
            offer = new Offer();
        }

        //Add Attribute "offer" for SpringForm to access:
        model.addAttribute("offer", offer);

        return "createoffer";
    }

    @RequestMapping(value="/docreate", method=RequestMethod.POST)
    public String doCreate(Model model,
                           @Validated(value= FormValidationGroup.class) Offer offer,
                           BindingResult result, Principal principal,
                           @RequestParam(value = "delete", required = false) String delete) { //get delete param from submit request

        //validate Form:
        if(result.hasErrors()) {
            //stay on same page if failed:
            return "createoffer";
        }
        //Spring will match params in Offer Bean to the getMethod passed attributes
        System.out.println(offer);


        if(delete == null) { //save offer:
            //use Principal Argument to get currently logged in User Entity:
            String username = principal.getName();
            offer.getUser().setUsername(username); //set username field in blank username

            //create the valid offer:
            offersService.saveOrUpdate(offer);
            return "offercreated";

        } else { //delete existing offer
            offersService.delete(offer.getId());
            return "offerdeleted";
        }
    }
}
