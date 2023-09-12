package com.maximus.chatclientjavafx.service;

import com.maximus.chatclientjavafx.displaymanager.DisplayManager;
import com.maximus.chatclientjavafx.storage.ChatStorage;
import com.maximus.chatdto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class IncomingMessageService {

    private final ChatStorage chatStorage;
    private final DisplayManager displayManager;

    public IncomingMessageService(ChatStorage chatStorage, DisplayManager displayManager) {
        this.chatStorage = chatStorage;
        this.displayManager = displayManager;
    }


    public void receivedMyProfile(ProfileInfo info) {
        System.out.println(info.getLogin());
        System.out.println(info.getUniqueID());

        chatStorage.setProfile(info);
    }
    public void receivedSearchUser(UserInfo user){
        chatStorage.setSearchUserAtSet(user);
    }
    public void receivedRoomTile(RoomTile room){ chatStorage.setRoomTileAtList(room); }
    public void receivedRoomTiles(List<RoomTile> rooms) { chatStorage.setRoomTiles(rooms); }
    public void receivedRoomInfo(RoomInfo room){ chatStorage.setRoomInfo(room); }

    public void receivedChatMessage(MessageInfo message) { chatStorage.setMessage(message);}
    public void receivedChatMessageList(List<MessageInfo> messages) { chatStorage.setMessages(messages); }

    public void receivedSearchTiles(Set<SearchTile> tiles) {
        chatStorage.setSearchItems(tiles);
        //displayManager.onReceivedSearchTiles();
    }
}
