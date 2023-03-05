package com.maximus.chatclientjavafx.service;

import com.maximus.chatclientjavafx.storage.ChatStorage;
import com.maximus.chatdto.ProfileInfo;
import org.springframework.stereotype.Service;

@Service
public class IncomingMessageService {

    private final ChatStorage chatStorage;

    public IncomingMessageService(ChatStorage chatStorage){
        this.chatStorage =chatStorage;
    }


    public void setMyProfile(ProfileInfo info) {
        System.out.println(info.getLogin());
        System.out.println(info.getUniqueID());

        chatStorage.setProfile(info);

    }

}
