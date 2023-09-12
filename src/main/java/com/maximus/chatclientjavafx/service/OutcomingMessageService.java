package com.maximus.chatclientjavafx.service;

import com.maximus.chatclientjavafx.controller.SocketController;
import com.maximus.chatclientjavafx.storage.ChatStorage;
import com.maximus.chatdto.*;
import org.springframework.stereotype.Service;

@Service
public class OutcomingMessageService {

    private final SocketController socketController;
    private final ChatStorage chatStorage;


    public OutcomingMessageService(SocketController socketController, ChatStorage chatStorage){
        this.socketController = socketController;
        this.chatStorage = chatStorage;
    }
    //------------------------------------------------------------
    public void requestMyProfile() {  socketController.getMyProfile();  }
    public void requestEditMyProfile(ProfileInfo info) { socketController.editMyProfile(info);}
    public void requestChangeMyPassword(ProfilePassword password) { socketController.changeMyProfilePassword(password); }
    public void requestChangeMyEmail(ProfileEmail email) { socketController.changeMyEmail(email); }
    public void requestGetUserById(Long uniqueId) {socketController.getUserById(uniqueId);}
    public void requestGetUserByLogin(String login) {socketController.getUserByLogin(login);}
    public void requestCreateRoom(RoomInfo newRoom) {socketController.createRoom(newRoom);}
    public void requestRoomTiles(Long uniqueId) { socketController.requestRoomTiles(uniqueId); }
    public void requestSearch(String text) { socketController.generalSearch(text);}
    public void requestRoomInfo(Long uniqueId) { socketController.requestRoomInfo(uniqueId); }

    public void sendMessage(MessageInfo message) { socketController.sendMessage(message); }
    public void requestMessages(MessagesReq req) { socketController.requestMessages(req); }
}
