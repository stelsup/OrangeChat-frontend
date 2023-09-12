package com.maximus.chatclientjavafx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.maximus.chatclientjavafx.config.ChatStompSessionHandler;
import com.maximus.chatdto.*;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class SocketController {

    private WebSocketStompClient stompClient;

    private final StompSessionHandler sessionHandler;
    private StompSession session;
    private final String proxyURL = "ws://127.0.0.1:8888/chat-server";

    private String authToken;

    public SocketController(){
        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.sessionHandler = new ChatStompSessionHandler();
    }

    public void connect() throws ExecutionException, InterruptedException, TimeoutException {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(
                LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        javaTimeModule.addSerializer(
                LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ISO_DATE));

        ObjectMapper mapper = JsonMapper.builder()   // for LocalDate
                .addModule(javaTimeModule)
                .build();
        converter.setObjectMapper(mapper);
        //-------
        converter.setContentTypeResolver(resolver);
        stompClient.setMessageConverter( converter );


        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.setBearerAuth(authToken);
        ListenableFuture<StompSession> sessionAsync = stompClient.connect(proxyURL, headers, sessionHandler);
        session = sessionAsync.get(10, TimeUnit.SECONDS);
        session.subscribe("/queue", sessionHandler);

        System.out.println("Connected and subscribed to /queue");

    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    //------------------------------------------------------------
    public void getMyProfile(){ session.send("/chat-app/profile", "null"); }
    public void editMyProfile(ProfileInfo info) {
        session.send("/chat-app/editProfile", info);
    }
    public void changeMyProfilePassword(ProfilePassword password) { session.send("/chat-app/changePassword", password); }
    public void changeMyEmail(ProfileEmail email) { session.send("/chat-app/changeEmail", email); }
    //-------------------------------------------------------------
    public void getUserById(Long uniqueId){
        session.send("/chat-app/getUserByID", uniqueId);
    }
    public void getUserByLogin(String login){
        session.send("/chat-app/getUserByLogin", login);
    }
    //-------------------------------------------------------------
    public void createRoom(RoomInfo newRoom){ session.send("/chat-app/createRoom", newRoom);}
    public void requestRoomInfo(Long uniqueId) { session.send("/chat-app/getRoomInfo", uniqueId);}
    public void requestRoomTiles(Long uniqueId) { session.send("/chat-app/getRooms", uniqueId); }
    //-------------------------------------------------------------
    public void generalSearch(String text) { session.send("/chat-app/generalSearch", text); }
    //-------------------------------------------------------------
    public void sendMessage(MessageInfo message) { session.send("/chat-app/sendMessage", message); }
  //  public void requestMessages(Long roomId) { session.send("/chat-app/getLastMessages", roomId); }
    public void requestMessages(MessagesReq req) { session.send("/chat-app/getMessages", req); }


}

