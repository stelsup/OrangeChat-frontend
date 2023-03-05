package com.maximus.chatclientjavafx.model;

import com.maximus.chatclientjavafx.model.auth.JwtResponse;

import java.util.List;

public class UserPrincipal {

    private Long id;
    private String token;
    private String login;
    private String email;
    private List<String> roles;

    public UserPrincipal(){}

    public UserPrincipal(Long id, String token, String login, String email, List<String> roles){
        this.id = id;
        this.token = token;
        this.login = login;
        this.email = email;
        this.roles = roles;
    }

    public UserPrincipal(JwtResponse jwtResponse){
        this.id = jwtResponse.getId();
        this.token = jwtResponse.getToken();
        this.login = jwtResponse.getLogin();
        this.email = jwtResponse.getEmail();
        this.roles = jwtResponse.getRoles();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
