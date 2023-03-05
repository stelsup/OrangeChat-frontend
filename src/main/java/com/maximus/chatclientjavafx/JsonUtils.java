package com.maximus.chatclientjavafx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.maximus.chatclientjavafx.model.auth.MessageResponse;
import com.maximus.chatdto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonUtils {

    public static ProfileInfo deserializeProfileInfo(JsonNode jsonNode){
        if(jsonNode == null)  return null;

        ProfileInfo profile = new ProfileInfo();

        profile.setUniqueID(getJsonNodeAsLong(jsonNode, "uniqueID"));
        profile.setLogin(getJsonNodeAsString(jsonNode, "login"));
        profile.setFirstName(getJsonNodeAsString(jsonNode, "firstName"));
        profile.setLastName(getJsonNodeAsString(jsonNode, "lastName"));
        profile.setAvatar(getJsonNodeAsString(jsonNode, "avatar"));
        profile.setDateOfBirth(parseDate(getJsonNodeAsString(jsonNode, "dateOfBirth")));
        profile.setEmail(getJsonNodeAsString(jsonNode, "email"));
        profile.setOnlineStatus(deserializeOnlineStatus(jsonNode.get("onlineStatus")));
        return profile;
    }

    public static UserInfo deserializeUserInfo(JsonNode jsonNode) {

        if(jsonNode == null)  return null;

        UserInfo user = new UserInfo();

        user.setUniqueID(getJsonNodeAsLong(jsonNode, "uniqueID"));
        user.setLogin(getJsonNodeAsString(jsonNode, "login"));
        user.setFirstName(getJsonNodeAsString(jsonNode, "firstName"));
        user.setLastName(getJsonNodeAsString(jsonNode, "lastName"));
        user.setAvatar(getJsonNodeAsString(jsonNode, "avatar"));
        user.setOnlineStatus(deserializeOnlineStatus(jsonNode.get("onlineStatus")));
        return user;
    }

    public static List<UserInfo> deserializeUserInfoList(JsonNode jsonNode) {
        if(jsonNode == null)  return null;
        if(jsonNode.isArray()){

            ArrayNode jsonArray = (ArrayNode) jsonNode;
            List<UserInfo> users = new ArrayList<>();

            for(int i = 0; i < jsonArray.size(); i++){
                JsonNode jsonElement = jsonArray.get(i);
                users.add(deserializeUserInfo(jsonElement));
            }
            return users;
        }

        return null;
    }

    public static UserTile deserializeUserTile(JsonNode jsonNode){

        if(jsonNode == null)  return null;

        UserTile user = new UserTile();

        user.setUniqueID(getJsonNodeAsLong(jsonNode, "uniqueID"));
        user.setLogin(getJsonNodeAsString(jsonNode, "login"));
        user.setFirstName(getJsonNodeAsString(jsonNode, "firstName"));
        user.setLastName(getJsonNodeAsString(jsonNode, "lastName"));
        user.setAvatar(getJsonNodeAsString(jsonNode, "avatar"));
        return user;
    }

    public static OnlineStatusInfo deserializeOnlineStatus(JsonNode jsonNode){

        if(jsonNode == null)  return null;
        OnlineStatusInfo status = new OnlineStatusInfo();

            status.setStatus(parseEOnlineStatus(getJsonNodeAsString(jsonNode, "status")));
            status.setLastTimeOnline(parseDateTime(getJsonNodeAsString(jsonNode, "lastTimeOnline")));
            return status;

    }

    public static LocalDateTime parseDateTime(String dateTime){
        if(dateTime == null) return null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            return  LocalDateTime.parse(dateTime, formatter);
        }
        catch(DateTimeParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static LocalDate parseDate(String date){
        if(date == null) return null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        }
        catch(DateTimeParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static EOnlineStatusInfo parseEOnlineStatus(String status){
        if(status == null) return EOnlineStatusInfo.OFFLINE;

        switch (status){
            case "ONLINE" :
                return  EOnlineStatusInfo.ONLINE;
            case "AWAY" :
                return EOnlineStatusInfo.AWAY;
            default:
                return EOnlineStatusInfo.OFFLINE;
        }
    }

    public static String getJsonNodeAsString(JsonNode node, String name) {
        JsonNode res = node.get(name);
        if(res != null && !res.isNull()) {
            String asText = res.asText();
            if(!asText.isEmpty())       // for single nodes - nodes-values
                return asText;

            return res.toString();     // for nested nodes - nodes-roots
        }
        else
            return null;
    }

    public static Long getJsonNodeAsLong(JsonNode node, String name){
        JsonNode res = node.get(name);
        if(res != null && !res.isNull()){
            return res.asLong();
        }
        return null;
    }

    public static Integer getJsonNodeAsInteger(JsonNode node, String name){
        JsonNode res = node.get(name);
        if(res != null && !res.isNull()){
            return res.asInt();
        }
        return null;
    }

    public static RoomInfo deserializeRoomInfo(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        RoomInfo room = new RoomInfo();

        room.setId(getJsonNodeAsLong(jsonNode,"uniqueID"));
        room.setName(getJsonNodeAsString(jsonNode, "name"));
        room.setAvatar(getJsonNodeAsString(jsonNode, "avatar"));
        room.setDateOfModify(parseDateTime(getJsonNodeAsString(jsonNode, "dateOfModify")));
        room.setOwnerId(getJsonNodeAsLong(jsonNode,"ownerId"));
        room.setMembers(deserializeMembers(jsonNode.get("members")));
        return room;
    }

    public static List<RoomInfo> deserializeRoomInfoList(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        if(jsonNode.isArray()){

            ArrayNode jsonArray = (ArrayNode) jsonNode;
            List<RoomInfo> rooms = new ArrayList<>();

            for(int i = 0; i < jsonArray.size(); i++){
                JsonNode jsonElement = jsonArray.get(i);
                rooms.add(deserializeRoomInfo(jsonElement));
            }
            return rooms;
        }
        return null;

    }

    public static RoomTile deserializeRoomTile(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        RoomTile room = new RoomTile();

        room.setUniqueID(getJsonNodeAsLong(jsonNode,"uniqueID"));
        room.setName(getJsonNodeAsString(jsonNode, "name"));
        room.setAvatar(getJsonNodeAsString(jsonNode, "avatar"));
        room.setLastMessagePreview(getJsonNodeAsString(jsonNode, "lastMessagePreview"));
        room.setUnreadMessageCount(getJsonNodeAsInteger(jsonNode, "unreadMessageCount"));
        return room;
    }

    public static List<RoomTile> deserializeRoomTileList(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        if(jsonNode.isArray()){

            ArrayNode jsonArray = (ArrayNode) jsonNode;
            List<RoomTile> rooms = new ArrayList<>();

            for(int i = 0; i < jsonArray.size(); i++){
                JsonNode jsonElement = jsonArray.get(i);
                rooms.add(deserializeRoomTile(jsonElement));
            }
            return rooms;
        }
        return null;


    }

    public static MessageInfo deserializeMessageInfo(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        MessageInfo message = new MessageInfo();

        message.setId(getJsonNodeAsLong(jsonNode, "uniqueID"));
        message.setRoomId(getJsonNodeAsLong(jsonNode, "roomId"));
        message.setSenderId(getJsonNodeAsLong(jsonNode, "senderId"));
        message.setTimestamp(parseDateTime(getJsonNodeAsString(jsonNode, "timestamp")));
        message.setText(getJsonNodeAsString(jsonNode, "text"));
        message.setContent(getJsonNodeAsString(jsonNode, "content"));
        return message;
    }

    public static List<MessageInfo> deserializeMessageInfoList(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        if(jsonNode.isArray()){

            ArrayNode jsonArray = (ArrayNode) jsonNode;
            List<MessageInfo> messages = new ArrayList<>();

            for(int i = 0; i < jsonArray.size(); i++){
                JsonNode jsonElement = jsonArray.get(i);
                messages.add(deserializeMessageInfo(jsonElement));
            }
            return messages;
        }
        return null;
    }

    public static MessageResponse deserializeMessageResponse(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(getJsonNodeAsString(jsonNode, "message"));

        return messageResponse;
    }

    public static Set<UserInfo> deserializeMembers(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        if(jsonNode.isArray()){

            ArrayNode jsonArray = (ArrayNode) jsonNode;
            Set<UserInfo> members = new HashSet<>();

            for(int i = 0; i < jsonArray.size(); i++){
                JsonNode jsonElement = jsonArray.get(i);
                members.add(deserializeUserInfo(jsonElement));
            }
            return members;
        }
        return null;
    }

    public static ProfilePassword deserializePassword(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        ProfilePassword password = new ProfilePassword();
        password.setUserId(getJsonNodeAsLong(jsonNode, "userId"));
        password.setPassword(getJsonNodeAsString(jsonNode, "password"));
        return password;
    }

    public static ProfileEmail deserializeEmail(JsonNode jsonNode){
        if(jsonNode == null)  return null;
        ProfileEmail email = new ProfileEmail();
        email.setUserId(getJsonNodeAsLong(jsonNode, "userId"));
        email.setEmail(getJsonNodeAsString(jsonNode, "email"));
        return email;
    }


}
