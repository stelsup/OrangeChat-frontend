package com.maximus.chatclientjavafx.fxcontroller;

import com.maximus.chatclientjavafx.GUIUtils;
import com.maximus.chatclientjavafx.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.service.IncomingMessageService;
import com.maximus.chatclientjavafx.service.OutcomingMessageService;
import com.maximus.chatclientjavafx.storage.ChatStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;



@Component
@FxmlView("profile.fxml")
public class ProfileController extends GUIController {

    @FXML
    private Label serviceLabel;

    @FXML
    private ImageView avatarImage;

    @FXML
    private TextField lastNameTextfield;

    @FXML
    private TextField firstNameTextfield;

    @FXML
    private TextField loginTextfield;

    @FXML
    private TextField dateOfBirthTextfield;

    @FXML
    private TextField emailTextfield;

    @FXML
    private Button editLastNameBtn;

    @FXML
    private Button editFirstNameBtn;

    @FXML
    private Button editLoginBtn;

    @FXML
    private Button editDateofBirthBtn;

    private final OutcomingMessageService outcomingMessageService;
    private final IncomingMessageService incomingMessageService;
    private final ChatStorage chatStorage;

    private boolean edited = false;


    public ProfileController(OutcomingMessageService outcomingMessageService, IncomingMessageService incomingMessageService,
                             ChatStorage chatStorage){
        this.outcomingMessageService = outcomingMessageService;
        this.incomingMessageService = incomingMessageService;
        this.chatStorage = chatStorage;
    }



    @Override
    public void onShow() {
        outcomingMessageService.getMyProfile();
    }

    @FXML
    protected void initialize(){

        editLastNameBtn.setGraphic(GUIUtils.loadImage("editPen-40.png", 30, 30));
        editFirstNameBtn.setGraphic(GUIUtils.loadImage("editPen-40.png", 30, 30));
        editLoginBtn.setGraphic(GUIUtils.loadImage("editPen-40.png", 30, 30));
        editDateofBirthBtn.setGraphic(GUIUtils.loadImage("editPen-40.png", 30, 30));
        lastNameTextfield.setEditable(false);
        firstNameTextfield.setEditable(false);
        loginTextfield.setEditable(false);
        dateOfBirthTextfield.setEditable(false);
        emailTextfield.setEditable(false);

    }



    @FXML
    protected void profileOkButtonOnClick(){

        if(edited){
            Utils.MessageBox( "Внимание", "Сохранить изменения?",
                    "Данные вашего профиля были изменены. Сохранить изменения?",
                    Alert.AlertType.INFORMATION, getClass());
            //// послать на сервер
        }
        this.closeWindow();
    }

    @FXML
    protected void profileCancelButtonOnClick(){
        this.closeWindow();
    }

    @FXML
    protected void editLastNameBtnOnClick(){
        this.edited = true;
        this.lastNameTextfield.setEditable(true);
    }

    @FXML
    protected void editFirstNameBtnOnClick(){
        this.edited = true;
        this.firstNameTextfield.setEditable(true);
    }

    @FXML
    protected void editLoginBtnOnClick(){
        this.edited = true;
        this.loginTextfield.setEditable(true);
    }

    @FXML
    protected void editDateofBirthBtnOnClick(){
        this.edited = true;
        this.dateOfBirthTextfield.setEditable(true);
    }


    public void setProfileInformation(){

        /////// Вывести информацию о пользвателе

    }


}
