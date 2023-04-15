package com.maximus.chatclientjavafx.storage;

import com.maximus.chatclientjavafx.model.UserPrincipal;
import com.maximus.chatdto.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChatStorage {

    private UserPrincipal principal;
    //----------- Incoming messages & rooms ------------//
    private Map<Long, List<MessageInfo>> messages = new HashMap<>();
    private List<RoomTile> roomTiles = new ArrayList<>();
    private StorageSignal globalMessagesSignal = new StorageSignal();
    private StorageSignal globalRoomTilesSignal = new StorageSignal();
    //----------- Requested profile ------------//
    private ProfileInfo profile;
    private StorageSignal profileInfoSignal = new StorageSignal();
    //----------- Requested room ------------//
    private Set<RoomInfo> rooms = new HashSet<>();
    //----------- Search ------------//
    private Set<UserInfo> searchUsers = new HashSet<>();
    //-------------------------------//
    private Set<SearchTile> searchItems = new HashSet<>();
    private StorageSignal searchItemsSignal = new StorageSignal();
    //-------------------------------//



    //=================================//
    public StorageSignal getGlobalMessagesSignal() { return globalMessagesSignal; }
    public StorageSignal getGlobalRoomTilesSignal() { return globalRoomTilesSignal; }

    public StorageSignal getSearchItemsSignal() { return searchItemsSignal; }
    public StorageSignal getProfileInfoSignal() { return profileInfoSignal; }
    //=================================//
    public synchronized UserPrincipal getPrincipal() { return principal; }
    public synchronized void setPrincipal(UserPrincipal principal) {
        this.principal = principal;
    }
    public synchronized ProfileInfo getProfile() {
        return profile;
    }
    public synchronized void setProfile(ProfileInfo profile) {
        this.profile = profile;
        profileInfoSignal.emitSignalAll();
    }
    public synchronized Set<UserInfo> getSearchUsers() {
        return searchUsers;
    }
    public synchronized void setSearchUsers(Set<UserInfo> searchUsers) {
        this.searchUsers = searchUsers;
    }
    public synchronized void setSearchUserAtSet(UserInfo user) {
        this.searchUsers.add(user);
    }
    public synchronized  void setRoomTileAtList(RoomTile room){
        this.roomTiles.add(room);
        globalRoomTilesSignal.emitSignalAll();
    }
    public synchronized  void setRoomTiles(List<RoomTile> rooms) {
        this.roomTiles = rooms;
        globalRoomTilesSignal.emitSignalAll();
    }

    public List<RoomTile> getRoomTiles() {
        return roomTiles;
    }

    public Set<SearchTile> getSearchItems() {
        return searchItems;
    }

    public void setSearchItems(Set<SearchTile> searchItems) {
        this.searchItems = searchItems;
        searchItemsSignal.emitSignalAll();
    }
}
