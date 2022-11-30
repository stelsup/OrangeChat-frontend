package com.maximus.chatclientjavafx.model;

import java.time.LocalDateTime;

public class MessageInfo {

    private String uniqueID;
    private String roomId;
    private String senderId;
    private LocalDateTime timestamp;
    private String text;
    private String content;


    public String getId() {
        return uniqueID;
    }

    public void setId(String id) {
        this.uniqueID = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
