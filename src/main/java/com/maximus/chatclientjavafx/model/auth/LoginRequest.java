package com.maximus.chatclientjavafx.model.auth;

public class LoginRequest {

    //private MessageType msgType = MessageType.USER_CRED_TYPE;
    private String userName;
    private String password;

    /*
    public MessageType getMsgType() {
        return msgType;
    }

    public void setMsgType(MessageType msgType) {
        this.msgType = msgType;
    }


     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
