package com.maximus.chatclientjavafx.displaymanager;

import com.maximus.chatclientjavafx.fxcontroller.ChatController;
import com.maximus.chatclientjavafx.fxcontroller.ProfileController;
import com.maximus.chatclientjavafx.model.UserPrincipal;
import com.maximus.chatclientjavafx.service.OutcomingMessageService;
import com.maximus.chatclientjavafx.storage.ChatStorage;
import com.maximus.chatclientjavafx.storage.StorageSignal;
import com.maximus.chatdto.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DisplayManager {

    private final ChatStorage chatStorage;
    private final OutcomingMessageService outcomingMessageService;
    //private final IncomingMessageService incomingMessageService;

    private ChatController chatController;
    private ProfileController profileController;

    private DisplayNavigator displayNavigator = new DisplayNavigator();

    private DisplayTimer displayGlobalMessagesTimer;
    private DisplayTimer displayGlobalRoomsTimer;
    private DisplayTimer displayCurrentActionTimer;

    //------------------------------------------------------------
    // Конструктор
    //------------------------------------------------------------
    @Lazy
    public DisplayManager(ChatStorage chatStorage, OutcomingMessageService outcomingMessageService, /*IncomingMessageService incomingMessageService,*/
                          ChatController chatController, ProfileController profileController)
    {
        this.chatStorage = chatStorage;
        this.outcomingMessageService = outcomingMessageService;
      //  this.incomingMessageService = incomingMessageService;

        this.chatController = chatController;
        this.profileController = profileController;

        //this.displayGlobalMessagesTimer = new DisplayTimer(chatController, "showProfileInformation", chatStorage.getGlobalMessagesSignal());
        this.displayGlobalRoomsTimer = new DisplayTimer(chatController, "showRoomTiles", chatStorage.getGlobalRoomTilesSignal());
    }
    //-----------------------------------------------------------
    // Navigation & timers
    //------------------------------------------------------------
    public DisplayNavigator getDisplayNavigator() { return this.displayNavigator; }
    public void AbortCurrentTimer() {
        if(displayCurrentActionTimer != null /*&& displayCurrentActionTimer.isBusy()*/)
            displayCurrentActionTimer.stop();
    }
    public void startGlobalTimers() {
        //displayGlobalMessagesTimer.start(0, 100, 5*1000);
        displayGlobalRoomsTimer.start(0, 100);
    }
    //-----------------------------------------------------------
    // ProfileInfo
    //------------------------------------------------------------
    public UserPrincipal getCurrentPrincipal() {   // получить текущее UserPrincipal
        return chatStorage.getPrincipal();
    }

    public ProfileInfo getCurrentProfileInfo() {   // получить текущее (закешированное) ProfileInfo
        return chatStorage.getProfile();
    }

    public void requestMyProfile() {               // запросить новое ProfileInfo
        ProfileInfo info = chatStorage.getProfile();
        if(info == null)  {
            displayCurrentActionTimer = new DisplayTimer(profileController, "showProfileInformation", chatStorage.getProfileInfoSignal());
            displayCurrentActionTimer.startSingleShot(5000);
            outcomingMessageService.requestMyProfile();
        } else {
            profileController.showProfileInformation();
        }
    }
    public void requestChangeMyProfile(ProfileInfo info) {   // запросить изменение ProfileInfo
        outcomingMessageService.requestEditMyProfile(info);
    }


    //------------------------------------------------------------
    // Profile Password and Email
    //------------------------------------------------------------
    public void requestChangeMyPassword(ProfilePassword password) {
        outcomingMessageService.requestChangeMyPassword(password);
        outcomingMessageService.requestMyProfile();
    }
    public void requestChangeMyEmail(ProfileEmail email) {
        outcomingMessageService.requestChangeMyEmail(email);
        outcomingMessageService.requestMyProfile();
    }


    //-----------------------------------------------------------
    // UserInfo
    //------------------------------------------------------------

    public Set<UserInfo> getSearchUsers(){return chatStorage.getSearchUsers();}

    public void requestUserById(Long uniqueId){
        outcomingMessageService.requestGetUserById(uniqueId);
    }

    //-----------------------------------------------------------
    // RoomInfo
    //------------------------------------------------------------

    public void requestCreateRoom(RoomInfo newRoom){
        outcomingMessageService.requestCreateRoom(newRoom);
    }

    //-----------------------------------------------------------
    // RoomTile
    //------------------------------------------------------------
    public boolean IsRoomTileEmpty(){
        return chatStorage.getRoomTiles().isEmpty();
    }
    public List<RoomTile> getRoomTiles(){
        return chatStorage.getRoomTiles();
    }
    public void requestRoomTiles() {
        List<RoomTile> rooms = chatStorage.getRoomTiles();
        if(rooms.isEmpty()) {
            outcomingMessageService.requestRoomTiles(chatStorage.getPrincipal().getId());
        } else {
            chatController.showRoomTiles();
        }
    }
    //-----------------------------------------------------------
    // SearchTile
    //------------------------------------------------------------
    public void requestSearch(String searchTitle) {
        //public DisplayTimer(String name, Object obj, String func, StorageSignal sig, long timeoutMs) {
        displayCurrentActionTimer = new DisplayTimer(chatController, "showSearchResults", chatStorage.getSearchItemsSignal());
        displayCurrentActionTimer.startSingleShot(5000);

        outcomingMessageService.requestSearch(searchTitle);
    }
    public Set<SearchTile> getSearchResults() {
        return chatStorage.getSearchItems();
    }

    //-----------------------------------------------------------
    // Global refresh
    //------------------------------------------------------------
    public void onReceivedGlobal() {

    }

//    public StorageSignal getGlobalSignal() { return chatStorage.getGlobalSignal(); }
//    public StorageSignal getSearchItemsSignal() { return chatStorage.getSearchItemsSignal(); }
}
