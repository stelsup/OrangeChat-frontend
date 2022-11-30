package com.maximus.chatclientjavafx.service;

import com.maximus.chatclientjavafx.controller.ProxyController;
import com.maximus.chatclientjavafx.model.UserCred;
import com.maximus.chatclientjavafx.model.User;
import com.maximus.chatclientjavafx.model.UserInfo;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProxyService {

    private ConfigurableApplicationContext applicationContext;
    private ProxyController proxyController;


    public ProxyService(ProxyController controller, ConfigurableApplicationContext context) {
        this.proxyController = controller;
        this.applicationContext = context;
    }

    public boolean checkCredentials(String name, String password){

        UserCred userCred = new UserCred();
        userCred.setUserName(name);
        userCred.setPassword(password);

        return proxyController.checkCredentials(userCred);

    }

    public boolean registerUser(String lastName, String firstName, String login,
                                String password, String email, LocalDate dateOfBirth) {

        User user = new User(new UserInfo(login, firstName, lastName, dateOfBirth), password, email);

        return proxyController.createUser(user);

    }
}
