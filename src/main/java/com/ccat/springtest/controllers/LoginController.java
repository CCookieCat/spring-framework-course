package com.ccat.springtest.controllers;

import com.ccat.springtest.dao.Message;
import com.ccat.springtest.dao.Offer;
import com.ccat.springtest.dao.User;
import com.ccat.springtest.service.UsersService;
import com.ccat.springtest.validation.FormValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UsersService usersService;

    //SpringMail
    @Autowired
    private MailSender mailSender;

    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }

    @RequestMapping("/loggedout")
    public String showLoggedOut() {
        return "loggedout";
    }

    @RequestMapping("/newaccount")
    public String showCreateAccount(Model model) {

        model.addAttribute("user", new User());

        return "newaccount";
    }

    //CREATE USER ACCOUNT:
    @RequestMapping(value="/createaccount", method= RequestMethod.POST)
    public String showAccountCreated(@Validated(FormValidationGroup.class) User user, BindingResult result) {

        //validate Form:
        if(result.hasErrors()) {
            //stay on same page if failed:
            return "newaccount";
        }

        user.setAuthority("ROLE_USER");
        user.setEnabled(true);

        //Check if User is a duplicate
        if(usersService.exists(user.getUsername())) {
            System.out.println(">>>> DUPLICATE USERNAME CAUGHT <<<<");
            result.rejectValue("username", "DuplicateKey.user.username");
            return "newaccount";
        }
        try {
            usersService.create(user);
        } catch(DuplicateKeyException d) {
            //path-control, properties-key, message
            result.rejectValue("username", "DuplicateKey.user.username");
            return "newaccount";
        }


        return "accountcreated";
    }

    //Admin Page:
    @RequestMapping("/admin")
    public String showAdmin(Model model) {

        List<User> users = usersService.getAllUsers();
        model.addAttribute("users", users);


        return "admin";
    }

    //Access-Denied Page:
    @RequestMapping("/denied")
    public String showDenied() {
        return "denied";
    }

    //JSON:
    @RequestMapping(value="/getmessages", method=RequestMethod.GET, produces="application/json")
    @ResponseBody //process return Data into appropriate(JSON) format
    public Map<String, Object> getMessages(Principal principal) {

        List<Message> messages = null;

        if(principal == null) {
            messages= new ArrayList<Message>();
        }
        else {
            String username = principal.getName();
            messages = usersService.getMessages(username);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("messages", messages);
        data.put("amount", messages.size()); //amount of messages

        return data;
    }

    //send reply - JSON:
    @RequestMapping(value="/sendmessage", method=RequestMethod.POST, produces="application/json")
    @ResponseBody //process return Data into appropriate(JSON) format
    //RequestBody for receiving Data in appropriate type <- interpret data as JSON
    public Map<String, Object> sendMessage(Principal principal, @RequestBody Map<String, Object> data) {

        String name = (String)data.get("name");
        String email = (String)data.get("email");
        String text = (String)data.get("text");

        Integer target = (Integer)data.get("target");
        System.out.println(name + " " + email + " " + text);

        //MailSender Setup:
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@coffeecat.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Re: " + name + ", your message");
        mailMessage.setText(text);

        //send the prepared Message:
        try{
            mailSender.send(mailMessage);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Unable to send the message.");
        }

        //send data back to messages -> access via : data
        Map<String, Object> retValue = new HashMap<>();
        retValue.put("success", true);
        retValue.put("target", target);

        return retValue;
    }

    @RequestMapping("/messages")
    public String showMessages() {
        return "messages";
    }
}
