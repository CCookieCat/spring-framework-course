package com.ccat.springtest.tests.tests;

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

//Use "dev" Profile for config-files (not production):
@ActiveProfiles("dev")
@ContextConfiguration(locations={
        "classpath:config/dao-context.xml",
        "classpath:config/security-context.xml",
        "classpath:config/datasource.xml"
})
@RunWith(SpringJUnit4ClassRunner.class) //how to run Tests
public class UserDaoTests {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private DataSource dataSource;

    //Dummy Users:
    private User user1 = new User("testusr","Test Person",
            "12345","test@test.com", true, "ROLE_USER");
    private User user2 = new User("someusr","Some One",
            "42690","some@email.com", true, "ROLE_USER");
    private User user3 = new User("usertest","User Test",
            "@4hLrf2&8MNkesM","usertest@ca.com", true, "ROLE_USER");


    @Before //run before all tests
    public void init() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DELETE FROM offers");
        jdbcTemplate.execute("DELETE FROM messages");
        jdbcTemplate.execute("DELETE FROM users");
    }

    @Test
    public void testCreateRetrieve() {
        usersDAO.create(user1);
        List<User> users1 = usersDAO.getAllUsers();
        assertEquals("Number of users should be 1.", 1, users1.size());
        assertEquals("Inserted user should match retrieved", user1, users1.get(0));

        assertTrue("User should exist." , usersDAO.exists(user1.getUsername()));

        usersDAO.create(user2);
        usersDAO.create(user3);
        assertEquals("Retrieved users should be 3.",3 ,usersDAO.getAllUsers().size());

        assertFalse("User should not exist." , usersDAO.exists("xlwjiq"));
    }

//    @Test
    public void testCreateUser() {
        User user = new User("coffeecom","Coffee Com", "12345","coffee@c.com", true, "ROLE_USER");

        usersDAO.create(user);
        assertTrue("User should exist." , usersDAO.exists(user.getUsername()));

        List<User> users = usersDAO.getAllUsers();
        assertEquals("Number of users should be 1", 1, users.size());

        assertEquals("Create User should be identical to retrieved User.", user, users.get(0));
    }
}
