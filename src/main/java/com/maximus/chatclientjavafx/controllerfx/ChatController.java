package com.maximus.chatclientjavafx.controllerfx;


import com.maximus.chatclientjavafx.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;



@Component
@FxmlView("modernchat.fxml")
public class ChatController extends GUIController {

    @FXML
    private Button siderButton1;

    @FXML
    private Button siderButton2;

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


    @Override
    public void onShow() {

    }


    @FXML
    protected void initialize(){

        siderButton1.setGraphic(Utils.loadImage("orange_message.png", 40, 40));
        siderButton2.setGraphic(Utils.loadImage("orange_user1.png", 40, 40));
        siderButton3.setGraphic(Utils.loadImage("conference.png", 40, 40));
        siderButton4.setGraphic(Utils.loadImage("orange_settings.png", 40, 40));
        siderButton5.setGraphic(Utils.loadImage("orange_exit1.png", 40, 40));
        interface_btn_menu.setGraphic(Utils.loadImage("orange_menu.png", 30, 30));
        interface_btn_send.setGraphic(Utils.loadImage("orange_send.png", 40, 40));
        interface_btn_add_picture.setGraphic(Utils.loadImage("orange_picture.png", 40, 40));
        interface_btn_smile.setGraphic(Utils.loadImage("orange_smile.png", 40, 40));
        search_btn_add.setGraphic(Utils.loadImage("add.png", 30, 30));
        search_btn_delete.setGraphic(Utils.loadImage("minus.png", 30, 30));

    }


}
