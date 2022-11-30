package com.maximus.chatclientjavafx.fxcontroller;


import com.maximus.chatclientjavafx.GUIUtils;
import com.maximus.chatclientjavafx.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;



@Component
@FxmlView("modernchat.fxml")
public class ChatController extends GUIController {

    @FXML
    private Button sidebarSearchBtn;

    @FXML
    private Button sidebarMessageBtn;

    @FXML
    private Button sidebarProfileBtn;

    @FXML
    private Button sidebarSettingsBtn;

    @FXML
    private Button sidebarExitBtn;

    @FXML
    private MenuButton interface_btn_menu;

    @FXML
    private Button interface_btn_send;

    @FXML
    private Button interface_btn_add_picture;

    @FXML
    private MenuButton interface_btn_smile;

    @FXML
    private Button search_btn_add;

    @FXML
    private Button search_btn_delete;

    @FXML
    private VBox vboxLocalRooms;

    @FXML
    private HBox hboxSearchBar;

    @FXML
    private HBox hboxRoomHeader;


    private HBox currentTile;

    @Override
    public void onShow() {

    }




    @FXML
    protected void initialize(){

        sidebarSearchBtn.setGraphic(GUIUtils.loadImage("search_big.png", 40, 40));
        sidebarMessageBtn.setGraphic(GUIUtils.loadImage("orange_message.png", 40, 40));
        sidebarProfileBtn.setGraphic(GUIUtils.loadImage("orange_user1.png", 40, 40));
        sidebarSettingsBtn.setGraphic(GUIUtils.loadImage("orange_settings.png", 40, 40));
        sidebarExitBtn.setGraphic(GUIUtils.loadImage("orange_exit1.png", 40, 40));
        interface_btn_menu.setGraphic(GUIUtils.loadImage("orange_menu.png", 30, 30));
        interface_btn_send.setGraphic(GUIUtils.loadImage("orange_send.png", 40, 40));
        interface_btn_add_picture.setGraphic(GUIUtils.loadImage("orange_picture.png", 40, 40));
        interface_btn_smile.setGraphic(GUIUtils.loadImage("orange_smile.png", 40, 40));
        search_btn_add.setGraphic(GUIUtils.loadImage("add.png", 30, 30));
        search_btn_delete.setGraphic(GUIUtils.loadImage("minus.png", 30, 30));

    }


    @FXML
    protected void onBtnAddClick() {

        HBox newRoom = GUIUtils.addRoomTile("New User", "Last message",
                "orange_avatar1.png", 10, true );
        newRoom.setOnMouseClicked(event -> {createChatRoom(newRoom);});
        vboxLocalRooms.getChildren().add(newRoom);

    }

    @FXML
    protected void onBtnDeleteClick(){
        if(currentTile == null){
            return;
        }
        vboxLocalRooms.getChildren().remove(currentTile);
        currentTile = null;
        //TODO Clear RoomHeader!

    }

    @FXML
    protected void onBtnSearchClick(){
        hboxSearchBar.setDisable(false);
        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.CENTER);
        vboxLocalRooms.getChildren().add(GUIUtils.getStub("Поиск временно недоступен"));

    }


    @FXML
    protected void onBtnMessageClick(){
        HBox vovan = GUIUtils.addRoomTile("Вован", "Last message", "orange_avatar1.png",0, false );
        vovan.setOnMouseClicked(event -> {createChatRoom(vovan);});
        HBox potash = GUIUtils.addRoomTile("Поташ", "Last message", "orange_avatar1.png", 28, true );
        potash.setOnMouseClicked(event -> {createChatRoom(potash);});

        hboxSearchBar.setDisable(false);
        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);
        vboxLocalRooms.getChildren().addAll(vovan, potash);

    }


    @FXML
    protected  void onBtnProfileClick(){
        HBox myProfile = GUIUtils.addMenuTile("Мой профиль", "profile.png");
        myProfile.setOnMouseClicked(event -> {currentTile = myProfile;});
        HBox notification = GUIUtils.addMenuTile("Уведомления", "notification.png");
        notification.setOnMouseClicked(event -> {currentTile = notification;});
        HBox security = GUIUtils.addMenuTile("Безопасность", "security.png");
        security.setOnMouseClicked(event -> {currentTile = security;});
        HBox statistic = GUIUtils.addMenuTile("Статистика", "statistics.png");
        statistic.setOnMouseClicked(event -> {currentTile = statistic;});

        hboxSearchBar.setDisable(true);
        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);
        vboxLocalRooms.getChildren().addAll(myProfile, notification, security, statistic);

    }

    @FXML
    protected  void onBtnSettingsClick(){
        HBox programSettings = GUIUtils.addMenuTile("Настройки программы", "program_settings.png");
        programSettings.setOnMouseClicked(event -> {currentTile = programSettings;});
        HBox securitySettings = GUIUtils.addMenuTile("Настройки безопасности", "security_settings.png");
        securitySettings.setOnMouseClicked(event -> {currentTile = securitySettings;});

        hboxSearchBar.setDisable(true);
        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);
        vboxLocalRooms.getChildren().addAll(programSettings, securitySettings);

    }

    @FXML
    protected void onBtnExitClick(){

        ButtonType result = Utils.MessageBox("Предупреждение", "Выйти из профиля",
                "Вы уверены, что хотите выйти из профиля ?", Alert.AlertType.CONFIRMATION);

        if(result == ButtonType.OK)
            this.closeWindow();
    }

    protected void createChatRoom(HBox currentRoom){

        currentTile = currentRoom;
        String roomName = GUIUtils.searchRoomName(currentRoom);
        hboxRoomHeader.getChildren().set(0,GUIUtils.createRoomHeaderName(roomName));
        hboxRoomHeader.getChildren().set(1,GUIUtils.changeStatus("Online"));


    }





}
