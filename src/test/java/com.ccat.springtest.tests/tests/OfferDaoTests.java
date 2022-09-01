package com.ccat.springtest.tests.tests;

import com.ccat.springtest.dao.Offer;
import com.ccat.springtest.dao.OffersDAO;
import com.ccat.springtest.dao.User;
import com.ccat.springtest.dao.UsersDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("dev")
@ContextConfiguration(locations={
        "classpath:config/dao-context.xml",
        "classpath:config/security-context.xml",
        "classpath:config/datasource.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class OfferDaoTests {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private OffersDAO offersDAO;
    @Autowired
    private DataSource dataSource;

    @Before
    public void init() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DELETE FROM offers");
        jdbcTemplate.execute("DELETE FROM messages");
        jdbcTemplate.execute("DELETE FROM users");
    }

    @Test
    public void testOfferCreation() {
        //First User offers:
        User user1 = new User("testUser","Max Muster", "12345","test@test.com", true, "ROLE_USER");
        usersDAO.create(user1);
        // -> create 2 offers for User1:
        Offer offer1 = new Offer( user1,"U1 : This is a test Offer.");
        Offer offer2 = new Offer( user1,"U1 : This is a second Offer.");
        offersDAO.saveUpdateOffer(offer1);
        offersDAO.saveUpdateOffer(offer2);

        //Second User offers:
        User user2 = new User("anotherUser","Not Muster", "12345","another@test.com", true, "ROLE_USER");
        usersDAO.create(user2);
        // -> create test offer for User2:
        Offer offer3 = new Offer( user2,"U2 : This is a test Offer.");
        offersDAO.saveUpdateOffer(offer3);

        //Query DB - Tests:
        List<Offer> offersList = offersDAO.getOffers();
        assertEquals("Database should contain 3 offers.",3,offersList.size());
        assertEquals("User1 should have 2 offers.", 2, offersDAO.getOffers(user1.getUsername()).size());
        assertEquals("User2 should have 1 offer.", 1, offersDAO.getOffers(user2.getUsername()).size());

        //disabled User test:
        User userDisabled = new User("usrDisable","Not Enabled", "5235323","test@disbl.com", false, "ROLE_USER");
        usersDAO.create(userDisabled);
        Offer offerDisabled = new Offer(userDisabled, "This offer shouldn't be visible.");
        offersDAO.saveUpdateOffer(offerDisabled);

        assertEquals("Database should still only contain 3 offers", 3, offersList.size());
        assertEquals("Disabled User should not have any visible Offers.", 0, offersDAO.getOffers(userDisabled.getUsername()).size());

        //retrieve Offer by Id:
        Offer retrievedOffer = offersDAO.getOffer(offer2.getId());
        assertEquals("Retrieved Offers by Id and Offer should match.", offer2 ,retrievedOffer);

        //delete Offer by Id:
        offersDAO.deleteById(offer2.getId());
        Offer offerDeleted = offersDAO.getOffer(offer2.getId());
        assertNull("The retrieved Offer should be deleted and null", offerDeleted);
    }
//    @Test
    public void testCreateOffer() {
        User user = new User("testUser","Max Muster", "12345","test@test.com", true, "ROLE_USER");

        usersDAO.create(user);

        Offer offer = new Offer( user,"This is a test Offer.");
        offersDAO.saveUpdateOffer(offer);

        List<Offer> allOffers = offersDAO.getOffers();
        assertEquals("One Offer should exist in the Database.",1,allOffers.size());

        //assign correct ID from DB increment:
        offer.setId(allOffers.get(0).getId());

        //update Offer Information:
        offer.setInformation("This is the updated Offer Information.");

        offersDAO.saveUpdateOffer(offer); //overwrite
        Offer updatedOffer = offersDAO.getOffer(offer.getId());
        assertEquals("Updated Offer should match.", offer, updatedOffer);

        offersDAO.deleteById(offer.getId());
        assertEquals("No offer Objects should exist in the Database.", 0, offersDAO.getOffers().size());

    }
}
