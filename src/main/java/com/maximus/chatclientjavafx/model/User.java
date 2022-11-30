package com.maximus.chatclientjavafx.model;

import java.time.LocalDate;

public class User {

    private UserInfo userInfo;

    private String password;
    private String email;

    public User(UserInfo userInfo, String password, String email){
        this.userInfo = userInfo;
        this.password = password;
        this.email = email;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
