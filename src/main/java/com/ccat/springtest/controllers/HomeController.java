package com.ccat.springtest.controllers;

import com.ccat.springtest.dao.Offer;
import com.ccat.springtest.service.OffersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private OffersService offersService;

    //Logging:
    private static Logger logger = Logger.getLogger(HomeController.class);

    @RequestMapping("/")
    public String showHome(Model model, Principal principal) {

        //log on debug-level:
//        logger.debug(" -- Showing Home page ... --");

        //log on info-level: (higher level)
        logger.info(" -- Showing Home page ... --");

        List<Offer> offers = offersService.getCurrent();
        model.addAttribute("offers", offers);

        boolean hasOffer = false;

        if(principal != null) {
            hasOffer = offersService.hasOffer(principal.getName());
        }

        model.addAttribute("hasOffer", hasOffer);

        return "home";
    }
}
