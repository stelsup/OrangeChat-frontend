package com.maximus.chatclientjavafx.fxcontroller;


import com.maximus.chatclientjavafx.GUIUtils;
import com.maximus.chatclientjavafx.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("profile-security.fxml")
public class ProfileSecurityController extends GUIController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Button editNewPassBtn;

    @FXML
    private TextField emailField;

    @FXML
    private Button editEmailBtn;

    @FXML
    private Label newPassServiceLabel;

    @FXML
    private Label repeatPassServiceLabel;

    @FXML
    private Label emailServiceLabel;

    private boolean passEdited = false;
    private boolean emailEdited = false;


    @Override
    public void onShow() {

    }

    @FXML
    protected void initialize(){

        editNewPassBtn.setGraphic(GUIUtils.loadImage("editPen-40.png", 30, 30));
        editEmailBtn.setGraphic(GUIUtils.loadImage("editPen-40.png", 30, 30));
        newPasswordField.setEditable(false);
        repeatPasswordField.setEditable(false);
        emailField.setEditable(false);

    }


    @FXML
    protected void onBtnOkClick(){

        if(passEdited || emailEdited){
            Utils.MessageBox( "Внимание", "Сохранить изменения?",
                    "Данные были изменены. Сохранить изменения?",
                    Alert.AlertType.INFORMATION, getClass());
            //// послать на сервер
        }
        this.closeWindow();

    }

    @FXML
    protected void onBtnCancelClick(){
        this.closeWindow();
    }

    @FXML
    protected void onEditNewPassBtnClick(){
        this.passEdited = true;
        this.newPasswordField.setEditable(true);
        this.repeatPasswordField.setEditable(true);

    }

    @FXML
    protected void onEditNewEmailBtnClick(){
        this.emailEdited = true;
        this.emailField.setEditable(true);
    }



}
