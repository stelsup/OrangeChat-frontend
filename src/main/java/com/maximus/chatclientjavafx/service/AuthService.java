package com.maximus.chatclientjavafx.service;

import com.maximus.chatclientjavafx.controller.AuthController;
import com.maximus.chatclientjavafx.controller.SocketController;
import com.maximus.chatclientjavafx.model.UserPrincipal;
import com.maximus.chatclientjavafx.model.auth.JwtResponse;
import com.maximus.chatclientjavafx.model.auth.MessageResponse;
import com.maximus.chatclientjavafx.model.auth.RegisterRequest;
import com.maximus.chatclientjavafx.model.auth.LoginRequest;
import com.maximus.chatclientjavafx.storage.ChatStorage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {

    private final ConfigurableApplicationContext applicationContext;
    private final AuthController authController;
    private String authToken;
    private MessageResponse errorMessage;


    public AuthService(AuthController controller, ConfigurableApplicationContext context) {
        this.authController = controller;
        this.applicationContext = context;

    }

    public boolean checkCredentials(String name, String password){

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(name);
        loginRequest.setPassword(password);

        if(authController.login(loginRequest)){
            JwtResponse jwtResponse = authController.getJwtResponse();
            this.authToken = jwtResponse.getToken();
            applicationContext.getBean(SocketController.class).setAuthToken(authToken);
            //applicationContext.getBean(RestController.class).setAuthToken(authToken);
            applicationContext.getBean(ChatStorage.class).setPrincipal(new UserPrincipal(jwtResponse));
            return true;
        }else{
            this.errorMessage = authController.getErrorMessage();
            return false;
        }
    }

    public boolean registerUser(String lastName, String firstName, String login,
                                String password, String email, LocalDate dateOfBirth) {

        RegisterRequest registerReq = new RegisterRequest(login, firstName, lastName, dateOfBirth, password, email);

         return authController.createUser(registerReq);

    }


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public MessageResponse getErrorMessage() {
        return errorMessage;
    }

}
