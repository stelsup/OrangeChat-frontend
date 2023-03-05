package com.maximus.chatclientjavafx.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.maximus.chatclientjavafx.JsonUtils;
import com.maximus.chatclientjavafx.ApplicationContextHolder;
import com.maximus.chatclientjavafx.model.auth.MessageResponse;
import com.maximus.chatclientjavafx.service.IncomingMessageService;
import com.maximus.chatclientjavafx.service.OutcomingMessageService;
import com.maximus.chatdto.*;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;


import java.lang.reflect.Type;
import java.util.List;


public class ChatStompSessionHandler extends StompSessionHandlerAdapter {


//    @Override
//    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//        //logger.info("New session established : " + session.getSessionId());
//        //session.subscribe("/topic", this);
//        //logger.info("Subscribed to /topic/messages");
//        //session.send("/app/chat", getSampleMessage());
//        //logger.info("Message sent to websocket server");
//    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        throw new RuntimeException("Failure in WebSocket handling", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Object.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println("---------------Recvd something!!!!!!!!!!!-------------");
        String messageType = headers.get("message-type").toString();

        ApplicationContext ctx = ApplicationContextHolder.getApplicationContext();
        String jsonString = new Gson().toJson(payload);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode  jsonNode;

        try {
            jsonNode = mapper.readTree(jsonString);
            //dbg
            System.out.println("handleFrame(): got " + messageType);
            //dbg
            switch (messageType){
                case "[PROFILE_INFO_TYPE]":
                    ProfileInfo profile = JsonUtils.deserializeProfileInfo(jsonNode);
                    ctx.getBean(IncomingMessageService.class).setMyProfile(profile);
                    break;
                case "[USER_INFO_TYPE]":
                    UserInfo userInfo = JsonUtils.deserializeUserInfo(jsonNode);
                    break;
                case "[USER_TILE_TYPE]":
                    UserTile userTile = JsonUtils.deserializeUserTile(jsonNode);
                    break;
                case "[ROOM_INFO_TYPE]":
                    RoomInfo roomInfo = JsonUtils.deserializeRoomInfo(jsonNode);
                    break;
                case "[ROOM_TILE_TYPE]":
                    RoomTile roomTile = JsonUtils.deserializeRoomTile(jsonNode);
                    break;
                case "[MESSAGE_INFO_TYPE]":
                    MessageInfo message = JsonUtils.deserializeMessageInfo(jsonNode);
                    break;
                case "[USER_INFO_LIST]":
                    List<UserInfo> users = JsonUtils.deserializeUserInfoList(jsonNode);
                    break;
                case "[ROOM_INFO_LIST]":
                    List<RoomInfo> infoRooms = JsonUtils.deserializeRoomInfoList(jsonNode);
                    break;
                case "[ROOM_TILE_LIST]":
                    List<RoomTile> tileRooms = JsonUtils.deserializeRoomTileList(jsonNode);
                    break;
                case "[MESSAGE_INFO_LIST]":
                    List<MessageInfo> messages = JsonUtils.deserializeMessageInfoList(jsonNode);
                    break;
                case "[MESSAGE_RESPONSE]":
                    MessageResponse messageResponse = JsonUtils.deserializeMessageResponse(jsonNode);
                    System.out.println(messageResponse.getMessage());
                    break;
                case "[PROFILE_PASSWORD_TYPE]":
                    ProfilePassword password = JsonUtils.deserializePassword(jsonNode);
                    break;
                case "[PROFILE_EMAIL_TYPE]":
                    ProfileEmail email = JsonUtils.deserializeEmail(jsonNode);
                    break;
                default:
                    System.out.println("---------------Unknown message type!!!!!!!!!!!-------------");
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("---------------JsonNode exception!!!!!!!!!!!-------------");
        }

    }


}
