package com.maximus.chatclientjavafx.model;

import java.util.HashMap;
import java.util.Map;

public class RoomInfo {

    private String uniqueID;
    private String name;
    private String avatar;
    private String lastMessagePreview;
    private Map<String, ChatStatus> users = new HashMap<>();  // String - Id юзеров

    enum ChatStatus {
        IDLE,
        TYPING
    }

    public String getId() {
        return uniqueID;
    }

    public void setId(String id) {
        this.uniqueID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLastMessagePreview() {
        return lastMessagePreview;
    }

    public void setLastMessagePreview(String lastMessagePreview) {
        this.lastMessagePreview = lastMessagePreview;
    }

    public Map<String, ChatStatus> getUsers() {
        return users;
    }

    public void setUsers(Map<String, ChatStatus> users) {
        this.users = users;
    }


}
