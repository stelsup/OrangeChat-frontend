package com.maximus.chatclientjavafx.controllerfx;


import com.maximus.chatclientjavafx.GUIUtils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    private Button siderButton3;

    @FXML
    private Button siderButton4;

    @FXML
    private Button siderButton5;

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

    private HBox currentTile;

    @Override
    public void onShow() {

    }




    @FXML
    protected void initialize(){

        sidebarSearchBtn.setGraphic(GUIUtils.loadImage("search_big.png", 40, 40));
        sidebarMessageBtn.setGraphic(GUIUtils.loadImage("orange_message.png", 40, 40));
        siderButton3.setGraphic(GUIUtils.loadImage("orange_user1.png", 40, 40));
        siderButton4.setGraphic(GUIUtils.loadImage("orange_settings.png", 40, 40));
        siderButton5.setGraphic(GUIUtils.loadImage("orange_exit1.png", 40, 40));
        interface_btn_menu.setGraphic(GUIUtils.loadImage("orange_menu.png", 30, 30));
        interface_btn_send.setGraphic(GUIUtils.loadImage("orange_send.png", 40, 40));
        interface_btn_add_picture.setGraphic(GUIUtils.loadImage("orange_picture.png", 40, 40));
        interface_btn_smile.setGraphic(GUIUtils.loadImage("orange_smile.png", 40, 40));
        search_btn_add.setGraphic(GUIUtils.loadImage("add.png", 30, 30));
        search_btn_delete.setGraphic(GUIUtils.loadImage("minus.png", 30, 30));

    }


    @FXML
    protected void onBtnAddClick() {

        HBox newRoom = GUIUtils.addRoom("New User", "Last message",
                "orange_avatar1.png", 10, true );
        newRoom.setOnMouseClicked(event -> {currentTile = newRoom;});
        vboxLocalRooms.getChildren().add(newRoom);

    }

    @FXML
    protected void onBtnDeleteClick(){
        if(currentTile == null){
            return;
        }
        vboxLocalRooms.getChildren().remove(currentTile);
        currentTile = null;

    }

    @FXML
    protected void onBtnSearchClick(){

        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.CENTER);
        vboxLocalRooms.getChildren().add(GUIUtils.getStub("Поиск временно недоступен"));

    }


    @FXML
    protected void onBtnMessageClick(){
        HBox vovan = GUIUtils.addRoom("Вован", "Last message", "orange_avatar1.png",0, false );
        vovan.setOnMouseClicked(event -> {currentTile = vovan;});
        HBox potash = GUIUtils.addRoom("Поташ", "Last message", "orange_avatar1.png", 28, true );
        potash.setOnMouseClicked(event -> {currentTile = potash;});

        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);
        vboxLocalRooms.getChildren().addAll(vovan, potash);

    }


    @FXML
    protected  void onBtnProfileClick(){



    }

    @FXML
    protected  void onBtnSettingsClick(){

    }


}
