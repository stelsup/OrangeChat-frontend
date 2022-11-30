package com.maximus.chatclientjavafx.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserInfo {

    private String uniqueID;
    private String login;
    private String firstName;
    private String lastName;
    private String avatar;
    private LocalDate dateOfBirth;

    private OnlineStatus onlineStatus;

    enum OnlineStatus {
        ONLINE,
        AWAY,
        OFFLINE;

        private LocalDateTime lastTimeOnline;

        public LocalDateTime getLastTimeOnline() {
            return lastTimeOnline;
        }
        public void setLastTimeOnline(LocalDateTime lastTimeOnline) {this.lastTimeOnline = lastTimeOnline; }
    }

    public UserInfo(String login, String firstName, String lastName, LocalDate dateOfBirth){

        this.uniqueID = UUID.randomUUID().toString();
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = "default user avatar";
        this.dateOfBirth = dateOfBirth;
        this.onlineStatus = OnlineStatus.OFFLINE;

    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
