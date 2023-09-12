package com.maximus.chatclientjavafx.storage;

import com.maximus.chatclientjavafx.model.UserPrincipal;
import com.maximus.chatdto.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ChatStorage {

    private UserPrincipal principal;
    //----------- Incoming messages & rooms ------------//
    private Map<Long, Queue<MessageInfo>> messages = new HashMap<>(); // база сообщений {id_комнаты, сообщение}
    private Map<Long, RoomTile> roomTiles = new LinkedHashMap<>(); // база комнат-тайлов {id_комнаты, тайл комнаты}
    private Map<Long, LocalDateTime> roomPositions = new LinkedHashMap<>(); // база позиций для каждой комнаты {id_комнаты, позиция=время}

    private StorageSignal globalMessagesSignal = new StorageSignal();
    private StorageSignal globalRoomTilesSignal = new StorageSignal();
    //----------- Requested profile ------------//
    private ProfileInfo profile;
    private StorageSignal profileInfoSignal = new StorageSignal();
    //----------- Requested room ------------//
    private Map<Long, RoomInfo> rooms = new LinkedHashMap<>();
    private StorageSignal roomInfoSignal = new StorageSignal();
    //----------- Search ------------//
    private Set<SearchTile> searchItems = new HashSet<>();
    private StorageSignal searchItemsSignal = new StorageSignal();
    //-------------------------------//
    private Set<UserInfo> searchUsers = new HashSet<>();  // ???
    //-------------------------------//


    //=================================//
    public StorageSignal getGlobalMessagesSignal() { return globalMessagesSignal; }
    public StorageSignal getGlobalRoomTilesSignal() { return globalRoomTilesSignal; }

    public StorageSignal getSearchItemsSignal() { return searchItemsSignal; }
    public StorageSignal getProfileInfoSignal() { return profileInfoSignal; }

    public StorageSignal getRoomInfoSignal() { return roomInfoSignal; }
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
    //--------------------------------------
    public synchronized void setRoomInfo(RoomInfo info) {
        if(rooms.containsKey(info.getUniqueID()))
            rooms.replace(info.getUniqueID(), info);
        else
            rooms.put(info.getUniqueID(), info);

        roomInfoSignal.emitSignalAll();
    }
    public synchronized RoomInfo getRoomInfo(Long uniqueId) { return rooms.get(uniqueId); }
    //--------------------------------------
    public synchronized Set<UserInfo> getSearchUsers() {
        return searchUsers;
    }
    public synchronized void setSearchUsers(Set<UserInfo> searchUsers) {
        this.searchUsers = searchUsers;
    }
    public synchronized void setSearchUserAtSet(UserInfo user) {
        this.searchUsers.add(user);
    }
    //--------------------------------------
    public synchronized void setRoomTileAtList(RoomTile room) {
        if(roomTiles.containsKey(room.getUniqueID()))
            roomTiles.replace(room.getUniqueID(), room);
        else
            roomTiles.put(room.getUniqueID(), room);
        globalRoomTilesSignal.emitSignalAll();
    }
    public synchronized  void setRoomTiles(List<RoomTile> rooms) {
        //this.roomTiles = rooms;
        if(rooms.isEmpty())
            return;
        roomTiles.clear();
//        roomTiles = rooms.stream().sorted(Comparator.comparing(RoomTile::getUniqueID, Comparator.reverseOrder()))
//                .collect(Collectors.toMap(RoomTile::getUniqueID, Function.identity(), (roomTile, roomTile2)));
        //Collections.reverse(rooms);
        for(RoomTile room : rooms) {
            roomTiles.put(room.getUniqueID(), room);
        }

        globalRoomTilesSignal.emitSignalAll();
    }
    public Map<Long, RoomTile> getRoomTiles() { return roomTiles; }
    //--------------------------------------
    public Set<SearchTile> getSearchItems() { return searchItems; }
    public void setSearchItems(Set<SearchTile> searchItems) {
        this.searchItems = searchItems;
        searchItemsSignal.emitSignalAll();
    }
    //--------------------------------------
    public LocalDateTime getRoomPosition(Long roomId) {
        if(roomPositions.containsKey(roomId))
            return roomPositions.get(roomId);

        return LocalDateTime.now();
    }
    public void setRoomPosition(Long roomId, LocalDateTime pos) {
        roomPositions.put(roomId, pos);
    }
    public void setMessage(MessageInfo message) {
        Long roomId = message.getRoomId();

        if(messages.containsKey(roomId)) {
            messages.get(roomId).add(message);
        } else {
            Queue<MessageInfo> msgList = new ArrayDeque<>();
            msgList.add(message);
            messages.put(roomId, msgList);
        }

        globalMessagesSignal.emitSignalAll();
    }
    public void setMessages(List<MessageInfo> messages) {
        if(messages.isEmpty())
            return;

        Queue<MessageInfo> msgList = new ArrayDeque<>();
        msgList.addAll(messages);
        this.messages.put(messages.get(0).getRoomId(), msgList);

        globalMessagesSignal.emitSignalAll();
    }

    public Queue<MessageInfo> getMessages(Long roomId) {
        if(messages.containsKey(roomId)) {
            return messages.get(roomId);
        }
        return null;
    }
//    public List<MessageInfo> getMessages(Long roomId) {
//        if(messages.containsKey(roomId)) {
//            return messages.get(roomId);
//        }
//
//        return null;
//    }
}
