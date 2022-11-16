package com.maximus.chatclientjavafx.fxcore;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class GUIController {

    protected Scene scene;

    public abstract void onShow();

    public void setScene(Scene s){
        this.scene = s;
    }

    public void closeWindow() {
        Stage stage = (Stage)scene.getWindow();
        stage.close();
    }

}
