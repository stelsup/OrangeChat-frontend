package com.maximus.chatclientjavafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    public static HBox addRoom(String roomName, String lastMessage, String imageName, int countUnread, boolean visible){
        HBox roomBox = new HBox();
        roomBox.setPrefWidth(305);
        roomBox.setPrefHeight(70);
        roomBox.setAlignment(Pos.CENTER_LEFT);
        roomBox.setFillHeight(true);
        VBox.setMargin(roomBox, new Insets(10,0,0,0));
        roomBox.getStylesheets().add("chatstyle.css");
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


        Label lbRoomName = new Label();
        lbRoomName.setText(roomName);
        lbRoomName.setFont(Font.font("Ebrima", FontWeight.BOLD, 19));
        lbRoomName.setTextFill(Color.WHITE);

        Label lbLastMessage = new Label();
        lbLastMessage.setText(lastMessage);
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

}
