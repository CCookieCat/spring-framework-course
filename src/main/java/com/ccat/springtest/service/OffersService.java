package com.ccat.springtest.service;

import com.ccat.springtest.dao.Offer;
import com.ccat.springtest.dao.OffersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("offersService")
public class OffersService {

    @Autowired
    private OffersDAO offersDAO;

    //retrieve Offers to display:
    public List<Offer> getCurrent() {
        return offersDAO.getOffers();
    }

    //method wrapper:
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public void createOffer(Offer offer) {
        offersDAO.saveUpdateOffer(offer);
    }

    public boolean hasOffer(String name) {
        if(name == null) return false;

        List<Offer> offers = offersDAO.getOffers(name);
        if(offers.size() == 0) {
            return false;
        }

        return true;
    }

    public Offer getOffer(String username) {
        if(username == null) {
            return null;
        }

        List<Offer> offers = offersDAO.getOffers(username);

        if(offers.size() == 0) {
            return null;
        }

        return offers.get(0);
    }

    public void saveOrUpdate(Offer offer) {
//        if(offer.getId() != 0) { //Auto-Increment - existing offer will never be 0
//            offersDAO.updateOffer(offer);
//        }
//        else {
        offersDAO.saveUpdateOffer(offer);
    }

    public void delete(int id) {
        offersDAO.deleteById(id);
    }
}
