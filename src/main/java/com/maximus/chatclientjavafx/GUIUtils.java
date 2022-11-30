package com.maximus.chatclientjavafx;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GUIUtils {

    public static HBox addRoomTile(String roomName, String lastMessage, String imageName, int countUnread, boolean visible){
        HBox roomBox = new HBox();
        roomBox.setPrefWidth(305);
        roomBox.setPrefHeight(70);
        roomBox.setAlignment(Pos.CENTER_LEFT);
        roomBox.setFillHeight(true);
        VBox.setMargin(roomBox, new Insets(10,0,0,0));
        roomBox.getStylesheets().add("dia.css");
        roomBox.getStyleClass().add("tile-bar");

        ImageView avatar = loadImage(imageName, 60, 60);
        avatar.setFitWidth(60);
        avatar.setFitHeight(60);
        HBox.setMargin(avatar, new Insets(0,0,0,15));


        VBox boxName = new VBox();
        boxName.setPrefWidth(180);
        boxName.setPrefHeight(70);
        boxName.setAlignment(Pos.CENTER_LEFT);
        boxName.setFillWidth(true);
        boxName.setPadding(new Insets(0,10,0,10));


        Label lbRoomName = new Label(roomName);
        lbRoomName.setFont(Font.font("Ebrima", FontWeight.BOLD, 19));
        lbRoomName.setTextFill(Color.WHITE);

        Label lbLastMessage = new Label(lastMessage);
        lbLastMessage.setFont(Font.font("Ebrima", 18));
        lbLastMessage.setTextFill(Color.web("#aba28e"));


        StackPane pnStatus = new StackPane();
        pnStatus.setAlignment(Pos.CENTER);
        pnStatus.setVisible(visible);
        HBox.setHgrow(pnStatus, Priority.ALWAYS);
        HBox.setMargin(pnStatus, new Insets(0,5,0,0));

        Circle crCountUnread = new Circle();
        crCountUnread.setRadius(11);
        crCountUnread.setFill(Color.web("#ffd88f"));
        crCountUnread.setSmooth(true);
        crCountUnread.setStroke(Color.WHITE);
        crCountUnread.setStrokeWidth(2);
        crCountUnread.setStrokeType(StrokeType.OUTSIDE);

        Label lblCountUnread = new Label(Integer.toString(countUnread));
        lblCountUnread.setFont(Font.font("Ebrima", FontWeight.NORMAL, 12));
        StackPane.setAlignment(lblCountUnread,Pos.CENTER);

        pnStatus.getChildren().add(crCountUnread);
        pnStatus.getChildren().add(lblCountUnread);

        boxName.getChildren().add(lbRoomName);
        boxName.getChildren().add(lbLastMessage);

        roomBox.getChildren().add(avatar);
        roomBox.getChildren().add(boxName);
        roomBox.getChildren().add(pnStatus);


        return roomBox;
    }

    public static HBox addMenuTile(String tileText, String imageName){


        HBox menuBox = new HBox();
        menuBox.setPrefWidth(305);
        menuBox.setPrefHeight(40);
        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setFillHeight(true);
        VBox.setMargin(menuBox, new Insets(10,0,0,0));
        menuBox.getStylesheets().add("chatstyle.css");
        menuBox.getStyleClass().add("tile-bar");

        ImageView imgTools = loadImage(imageName, 40, 40);
        imgTools.setFitWidth(40);
        imgTools.setFitHeight(40);
        HBox.setMargin(imgTools, new Insets(0,0,0,15));

        Label tileName = new Label(tileText);
        tileName.setFont(Font.font("Ebrima", FontWeight.BOLD, 19));
        tileName.setTextFill(Color.WHITE);
        HBox.setMargin(tileName, new Insets(0,0,0,10));

        menuBox.getChildren().add(imgTools);
        menuBox.getChildren().add(tileName);
        return menuBox;
    }



    public static Label getStub(String text){
        Label stub = new Label(text);
        stub.setFont(Font.font("Ebrima", 18));
        stub.setTextFill(Color.web("#aba28e"));
        return stub;
    }

    public static ImageView loadImage(String name, double width, double height){
        Image image = new Image(Utils.getImagesPath() + name);
        ImageView picture = new ImageView(image);
        picture.setFitWidth(width);
        picture.setFitHeight(height);

        return picture;
    }

    public static String searchRoomName(HBox tile){
        ObservableList<Node> hBoxList = tile.getChildren();
        for(Node node : hBoxList){
            if(node instanceof VBox){
                VBox vBox = (VBox) node;
                ObservableList<Node> vBoxList = vBox.getChildren();
                Label roomName = (Label) vBoxList.get(0);
                return roomName.getText();
            }
        }
       return null;
    }

    public static Label createRoomHeaderName(String text){
        Label name = new Label(text);
        name.setFont(Font.font("Ebrima", FontWeight.BOLD, 24));
        name.setTextFill(Color.web("#9da7a7"));
        name.setMinWidth(Region.USE_PREF_SIZE);
        name.setMaxWidth(300);
        HBox.setMargin(name, new Insets(0,0,0,20));
        return name;
    }


    public static HBox changeStatus(String roomStatus){
        HBox statusBox = new HBox();
        statusBox.setFillHeight(true);
        statusBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(statusBox, new Insets(0,0,0,10));

        Label status = new Label("Online");
        status.setFont(Font.font("Ebrima", 16));
        status.setTextFill(Color.web("#9da7a7"));
        status.setMinWidth(Region.USE_PREF_SIZE);
        status.setMaxWidth(100);

        if(roomStatus.equals("Online")){
            Circle circle = new Circle(6, Color.web("#3ead7d"));
            circle.setStroke(Color.WHITE);
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStrokeWidth(1);

            HBox.setMargin(status, new Insets(0,0,0,5));
            statusBox.getChildren().addAll(circle, status);
            return statusBox;
        }else{

            statusBox.getChildren().add(status);
            return statusBox;
        }

    }


    public static HBox createMessage(String imageName, String text, String messageType){

        HBox messageBox = new HBox();
        messageBox.setFillHeight(true);
        HBox.setMargin(messageBox, new Insets(2,0,0,0));

        ImageView avatar = loadImage(imageName, 50, 50);
        avatar.setFitWidth(50);
        avatar.setFitHeight(50);
        HBox.setMargin(avatar, new Insets(0,0,0,20));

        Label textMessage = new Label(text);
        textMessage.setFont(Font.font("Ebrima", 16));
        textMessage.getStylesheets().add("chatstyle.css");
        HBox.setMargin(textMessage, new Insets(0,0,0,20));

        if(messageType.equals("incoming")){
            messageBox.setAlignment(Pos.CENTER_LEFT);
            textMessage.setTextFill(Color.web("#aba28e"));
            textMessage.getStyleClass().add("incoming-bubble");
            messageBox.getChildren().addAll(avatar,textMessage);
        }
        if(messageType.equals("outgoing")){
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            textMessage.setTextFill(Color.WHITE);
            textMessage.getStyleClass().add("outgoing-bubble");
            messageBox.getChildren().addAll(textMessage,avatar);
        }

        return messageBox;

    }



}
