package com.maximus.chatclientjavafx.controller;

import com.maximus.chatclientjavafx.model.UserCred;
import com.maximus.chatclientjavafx.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProxyController {

    private final RestTemplate rest = new RestTemplate();
    private String proxyURL = "http://localhost:8888";

    //public ProxyController(RestTemplate rest) { this.rest = rest; }

    public String sayHello(String str){

        String uri = proxyURL + "/hello";

        HttpEntity<String> httpEntity = new HttpEntity<>(str );

        ResponseEntity<String> response = rest.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        return response.getBody();
    }

    public boolean checkCredentials(UserCred userCred){

        String uri = proxyURL + "/login";

        HttpEntity<UserCred> httpEntity = new HttpEntity<>(userCred);

        ResponseEntity<Boolean> response = rest.exchange(uri, HttpMethod.POST, httpEntity, Boolean.class);
        return response.getBody();
    }

    public boolean createUser(User user){

        String uri = proxyURL + "/register";

        HttpEntity<User> httpEntity = new HttpEntity<>(user);

        ResponseEntity<Boolean> response = rest.exchange(uri, HttpMethod.POST, httpEntity, Boolean.class);
        return response.getBody();
    }

}
