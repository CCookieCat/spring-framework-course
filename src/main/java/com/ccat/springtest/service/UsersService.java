package com.ccat.springtest.service;

import com.ccat.springtest.dao.Message;
import com.ccat.springtest.dao.MessagesDAO;
import com.ccat.springtest.dao.User;
import com.ccat.springtest.dao.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("usersService")
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private MessagesDAO messagesDAO;

    public void create(User user) {
        usersDAO.create(user);
    }

    public boolean exists(String username) {
        return usersDAO.exists(username);
    }

    @Secured("ROLE_ADMIN")
    public List<User> getAllUsers() {
        return usersDAO.getAllUsers();
    }

    //Sending messages:
    public void sendMessage(Message message) {
        messagesDAO.saveUpdateMessage(message);
    }

    public User getUser(String username) {
        return usersDAO.getUser(username);
    }

    public List<Message> getMessages(String username) {
        return messagesDAO.getMessages(username);
    }
}
