package com.maximus.chatclientjavafx.service;

import com.maximus.chatclientjavafx.controller.SocketController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
public class ConnectionService {

    private final SocketController socketController;
    private String authToken;

    public ConnectionService(SocketController socketController){
        this.socketController = socketController;
    }

    public boolean connectToServer(){
        try{
            socketController.connect();
            return true;
        }catch (ExecutionException | InterruptedException | TimeoutException exec){
            exec.printStackTrace();
            return false;
        }
    }


}
