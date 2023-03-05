//package com.maximus.chatclientjavafx.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.messaging.simp.stomp.StompSessionHandler;
//import org.springframework.web.socket.client.WebSocketClient;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketClientConfig {
//
//    @Bean
//    public WebSocketStompClient webSocketClient() {
//        final WebSocketClient client = new StandardWebSocketClient();
//
//        final WebSocketStompClient stompClient = new WebSocketStompClient(client);
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        //final StompSessionHandler sessionHandler = new MyStompSessionHandler();
//        //stompClient.connect("ws://localhost:8080", sessionHandler);
//        return stompClient;
//    }
//
//
//}
