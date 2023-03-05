package com.maximus.chatclientjavafx.service;

import com.maximus.chatclientjavafx.controller.SocketController;
import com.maximus.chatclientjavafx.storage.ChatStorage;
import com.maximus.chatdto.ProfileInfo;
import org.springframework.stereotype.Service;

@Service
public class OutcomingMessageService {

    private final SocketController socketController;
    private final ChatStorage chatStorage;


    public OutcomingMessageService(SocketController socketController, ChatStorage chatStorage){
        this.socketController = socketController;
        this.chatStorage = chatStorage;
    }



    public void getMyProfile(){


        socketController.getMyProfile();
    }


    public void editMyUserInfo(ProfileInfo info) {
        socketController.editMyProfile(info);
    }



}
