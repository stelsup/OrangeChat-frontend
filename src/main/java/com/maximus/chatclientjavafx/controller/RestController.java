package com.maximus.chatclientjavafx.controller;


import com.maximus.chatdto.UserInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestController {

    private String proxyURL = "http://localhost:8888/chat/";
    private final RestTemplate rest = new RestTemplate();
    private String authToken;


    public UserInfo getMyProfile(String login){

        String uri = proxyURL + "/profile";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);             ///////// TODO  Сделать глобальным для сокетов и REST
        HttpEntity<String> httpEntity = new HttpEntity<>(login, headers);
        ResponseEntity<UserInfo> response;
        try{
            response = rest.exchange(uri, HttpMethod.POST, httpEntity, UserInfo.class);
            return response.getBody();
        }catch (HttpClientErrorException ex){
            ex.printStackTrace();
            return null;
        }

    }


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
