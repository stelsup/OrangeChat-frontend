package com.maximus.chatclientjavafx.fxcontroller;

import com.maximus.chatclientjavafx.displaymanager.DisplayManager;
import com.maximus.chatclientjavafx.displaymanager.ECurrentPageTile;
import com.maximus.chatclientjavafx.utils.GUIUtils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatdto.RoomInfo;
import com.maximus.chatdto.UserInfo;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@FxmlView("newroom.fxml")
public class NewRoomController extends GUIController {

    @FXML
    TextField roomNameTextField;

    @FXML
    ImageView avatarImage;

    @FXML
    HBox membersHbox;

    @FXML
    Button addMembersBtn;

    @FXML
    TextArea descriptionArea;

    ////////////////////////////////////////////////
    private final DisplayManager displayManager;
    ////////////////////////////////////////////////

    private Set<UserInfo> members;


    public NewRoomController(DisplayManager displayManager){
        this.displayManager = displayManager;
        this.members = new HashSet<>();
    }


    @Override
    public void onShow() {
        displayManager.getDisplayNavigator().setCurrentPageTile(ECurrentPageTile.E_TILE_CHATS_NEW_ROOM);
    }

    @FXML
    protected void initialize(){

        addMembersBtn.setGraphic(GUIUtils.loadImage("add.png", 38, 38));
        avatarImage = GUIUtils.loadImage("orange_group_big2.png", 100, 100);
    }


    @FXML
    protected void newRoomOkButtonOnClick(){
        ////Создание комнаты

        members = displayManager.getSearchUsers();

        RoomInfo newRoom = new RoomInfo();
        newRoom.setName(roomNameTextField.getText());
        ///newRoom.setAvatar();
        newRoom.setOwnerId(displayManager.getCurrentPrincipal().getId());
        newRoom.setMembers(members);

        displayManager.requestCreateRoom(newRoom);

        displayManager.getDisplayNavigator().setCurrentPageTile(ECurrentPageTile.E_TILE_NONE);
        this.closeWindow();
    }

    @FXML
    protected void newRoomCancelButtonOnClick(){
        displayManager.getDisplayNavigator().setCurrentPageTile(ECurrentPageTile.E_TILE_NONE);
        this.closeWindow();
    }

    @FXML
    protected void addMembersBtnOnClick(){

        //// Вызывать окно добавления участников

        /// Test
        displayManager.requestUserById(3L);
        displayManager.requestUserById(displayManager.getCurrentPrincipal().getId());

        String name = "Вован";
        String avatar = "orange_avatar1.png";
        /////

        HBox member = GUIUtils.addMember(name,avatar);
        member.getChildren().add(createDeleteBtn());

        Pane pane = new Pane();
        pane.setPrefWidth(10);
        HBox.setHgrow(pane, Priority.ALWAYS);

        membersHbox.getChildren().add(member);
        membersHbox.getChildren().add(pane);

        //// Добавить универсальную при users больше 5!
    }

    protected Button createDeleteBtn(){
        Button deleteMember = new Button();
        deleteMember.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        deleteMember.setAlignment(Pos.CENTER);
        deleteMember.getStyleClass().add("delete-btn");
        deleteMember.setPrefWidth(25);
        deleteMember.setPrefHeight(25);
        deleteMember.minWidth(Region.USE_PREF_SIZE);
        deleteMember.minHeight(Region.USE_PREF_SIZE);
        deleteMember.maxWidth(Region.USE_PREF_SIZE);
        deleteMember.maxHeight(Region.USE_PREF_SIZE);
        HBox.setMargin(deleteMember, new Insets(0,3,0,0));
        deleteMember.setGraphic(GUIUtils.loadImage("delete_gray.png", 23, 23));
        deleteMember.setOnMouseClicked(event -> {
            deleteMember(deleteMember.getParent());});
        return deleteMember;
    }

    protected void deleteMember(Parent parent){
        membersHbox.getChildren().remove(parent);
    }

    protected void TimerFunc() {

    }

}
