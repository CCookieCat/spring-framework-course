package com.ccat.springtest.tests.tests;

import com.ccat.springtest.dao.Message;
import com.ccat.springtest.dao.MessagesDAO;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ActiveProfiles("dev")
@ContextConfiguration(locations={
        "classpath:config/dao-context.xml",
        "classpath:config/security-context.xml",
        "classpath:config/datasource.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageDaoTests {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private MessagesDAO messagesDAO;
    @Autowired
    private DataSource dataSource;

    private User user1 = new User("testusr","Test Person",
            "12345","test@test.com", true, "ROLE_USER");
    private User user2 = new User("someusr","Some One",
            "42690","some@email.com", true, "ROLE_USER");
    private User user3 = new User("usertest","User Test",
            "@4hLrf2&8MNkesM","usertest@ca.com", true, "ROLE_USER");

    @Before
    public void init() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DELETE FROM messages");
        jdbcTemplate.execute("DELETE FROM offers");
        jdbcTemplate.execute("DELETE FROM users");
    }

    @Test
    public void createTestMessage() {
        //create Receivers:
        usersDAO.create(user1);

        Message message1 = new Message("Chuck Srly", "srlych@web.mail", user1.getUsername(),
                "Test Msg","This is a test Message, please don't respond to it.");
        messagesDAO.saveUpdateMessage(message1);
    }

    @Test
    public void retrieveMessages() {
        //create Receivers:
        usersDAO.create(user1);
        usersDAO.create(user2);

        Message message1 = new Message("Chuck Srly", "srlych@web.mail", user1.getUsername(),
                "Test MS","This is the first message");
        messagesDAO.saveUpdateMessage(message1);

        assertEquals("One message should be retrieved from the Database",
                1, messagesDAO.getMessages().size());

        Message message2 = new Message("Chuck Srly", "srlych@web.mail", user1.getUsername(),
                "Test Msg","This another test");
        Message message3 = new Message("Chuck Srly", "srlych@web.mail", user2.getUsername(),
                "Test Msg","This another test");

        messagesDAO.saveUpdateMessage(message2);
        messagesDAO.saveUpdateMessage(message3);

        //Retrieve List of messages directed to User1:
        assertEquals("Two messages should be retrieved for user1",
                2, messagesDAO.getMessages(user1.getUsername()).size());

        //Retrieve single Message via Id:
        Message retrievedMessage = messagesDAO.getMessage(message3.getId());
        assertEquals("Existing and retrieved messages should be identical", message3, retrievedMessage);
    }

    @Test
    public void testDeleteMessage() {
        usersDAO.create(user2);
        Message message2 = new Message("Delete This", "delete@this.mail", user2.getUsername(),
                "Msg to delete.","This message will delete itself!");
        messagesDAO.saveUpdateMessage(message2);
        assertNotNull("The message should exist.", messagesDAO.getMessage(message2.getId()));

        messagesDAO.delete(message2.getId());
        Message deletedMessage = messagesDAO.getMessage(message2.getId());
        assertNull("The message should no longer exist.", deletedMessage);
    }
}
