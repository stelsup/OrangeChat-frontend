package com.maximus.chatclientjavafx.fxcontroller;


import com.maximus.chatclientjavafx.displaymanager.DisplayManager;
import com.maximus.chatclientjavafx.displaymanager.ECurrentPageTile;
import com.maximus.chatclientjavafx.utils.GUIUtils;
import com.maximus.chatclientjavafx.utils.JsonUtils;
import com.maximus.chatclientjavafx.utils.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatdto.ProfileEmail;
import com.maximus.chatdto.ProfileInfo;
import com.maximus.chatdto.ProfilePassword;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
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

    ////////////////////////////////////////////////
    private final DisplayManager displayManager;
    ////////////////////////////////////////////////

    private boolean passEdited = false;
    private boolean emailEdited = false;

    public ProfileSecurityController(DisplayManager displayManager) {
        this.displayManager = displayManager;
    }


    @Override
    public void onShow() {
        displayManager.getDisplayNavigator().setCurrentPageTile(ECurrentPageTile.E_TILE_PROFILE_SECURITY);
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
            resetNotification();

            if(!inputValidation()) {
                return;
            }

            if(Utils.MessageBox( "Внимание", "Сохранить изменения?",
                    "Данные были изменены. Сохранить изменения?",
                    Alert.AlertType.INFORMATION, getClass()) == ButtonType.OK ) {

                Long curProfileId = displayManager.getCurrentPrincipal().getId();

                if (passEdited) {
                    ProfilePassword newPassword = new ProfilePassword();
                    newPassword.setUserId(curProfileId);
                    newPassword.setPassword(repeatPasswordField.getText());

                    displayManager.requestChangeMyPassword(newPassword);
                }

                if (emailEdited) {
                    ProfileEmail newEmail = new ProfileEmail();
                    newEmail.setUserId(curProfileId);
                    newEmail.setEmail(emailField.getText());

                    displayManager.requestChangeMyEmail(newEmail);
                }
            }
        }
        displayManager.getDisplayNavigator().setCurrentPageTile(ECurrentPageTile.E_TILE_NONE);
        this.closeWindow();
    }

    @FXML
    protected void onBtnCancelClick(){
        displayManager.getDisplayNavigator().setCurrentPageTile(ECurrentPageTile.E_TILE_NONE);
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

    protected void resetNotification(){
        newPassServiceLabel.setText("");
        repeatPassServiceLabel.setText("");
        emailServiceLabel.setText("");
    }

    protected boolean inputValidation(){

        boolean result = true;

        if (passEdited) {
            if (newPasswordField.getText().length() > 50 || newPasswordField.getText().isEmpty()) {
                newPassServiceLabel.setText("Поле пустое или содержит более 50 символов!");
                result = false;
            }
            if (!newPasswordField.getText().equals(repeatPasswordField.getText())) {
                repeatPassServiceLabel.setText("Введенные пароли не совпадают!");
                result = false;
            }
        }

        if (emailEdited) {
            if (emailField.getText().isEmpty() || !emailField.getText().contains("@") || !emailField.getText().contains(".")) {
                emailServiceLabel.setText("Данный email не существует!");
                result = false;
            }
        }

        return result;
    }

    protected void TimerFunc() {

    }

}
