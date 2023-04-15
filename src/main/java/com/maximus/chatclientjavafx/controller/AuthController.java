package com.maximus.chatclientjavafx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maximus.chatclientjavafx.utils.Utils;
import com.maximus.chatclientjavafx.model.auth.JwtResponse;
import com.maximus.chatclientjavafx.model.auth.MessageResponse;
import com.maximus.chatclientjavafx.model.auth.RegisterRequest;
import com.maximus.chatclientjavafx.model.auth.LoginRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthController {


    private String proxyURL = "http://localhost:8888/chat/auth";
    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private MessageResponse errorMessage;
    private JwtResponse jwtResponse;


    public boolean login(LoginRequest loginRequest){

        String uri = proxyURL + "/login";

        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(loginRequest);
        ResponseEntity<String> response;
        try{
             response = rest.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        }catch (HttpClientErrorException ex){
            errorMessage = Utils.createResponseStatusMessage(ex.getRawStatusCode());
            return false;
        }

        try {
            jwtResponse = mapper.readValue(response.getBody(), JwtResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
       return true;
    }

    public boolean createUser(RegisterRequest registerReq){

        String uri = proxyURL + "/register";

        HttpEntity<RegisterRequest> httpEntity = new HttpEntity<>(registerReq);

        ResponseEntity<MessageResponse> response;
        try{
            response = rest.exchange(uri, HttpMethod.POST, httpEntity, MessageResponse.class);
            errorMessage = response.getBody();
        }catch (HttpClientErrorException ex){
            errorMessage = new MessageResponse(ex.getMessage());
            return  false;
        }
        return true;
    }


    public MessageResponse getErrorMessage() {
        return errorMessage;
    }

    public JwtResponse getJwtResponse() {
        return jwtResponse;
    }
}
