package com.maximus.chatclientjavafx.fxcontroller;

import com.maximus.chatclientjavafx.displaymanager.ECurrentPageTile;
import com.maximus.chatclientjavafx.utils.GUIUtils;
import com.maximus.chatclientjavafx.utils.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.displaymanager.DisplayManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import com.maximus.chatdto.ProfileInfo;


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
    private DatePicker dateOfBirthField;

    @FXML
    private TextField emailTextfield;

    @FXML
    private Button editLastNameBtn;

    @FXML
    private Button editFirstNameBtn;

    @FXML
    private Button editLoginBtn;


    ////////////////////////////////////////////////
    private final DisplayManager displayManager;
    ////////////////////////////////////////////////


    private boolean edited = false;


    public ProfileController(DisplayManager displayManager) {
        this.displayManager = displayManager;
    }



    @Override
    public void onShow() {
//        EnableTimer(0, 50);
        displayManager.getDisplayNavigator().setCurrentPageTile(ECurrentPageTile.E_TILE_PROFILE);

        displayManager.requestMyProfile();
    }

    @FXML
    protected void initialize(){

        editLastNameBtn.setGraphic(GUIUtils.loadImage("editPen-40.png", 30, 30));
        editFirstNameBtn.setGraphic(GUIUtils.loadImage("editPen-40.png", 30, 30));
        editLoginBtn.setGraphic(GUIUtils.loadImage("editPen-40.png", 30, 30));
        lastNameTextfield.setEditable(false);
        firstNameTextfield.setEditable(false);
        loginTextfield.setEditable(false);
        dateOfBirthField.setEditable(false);
        emailTextfield.setEditable(false);

    }

    @FXML
    protected void profileOkButtonOnClick(){

        if(edited) {
            resetNotification();

            if(!inputValidation()) {
                return;
            }

            if(Utils.MessageBox( "Внимание", "Сохранить изменения?",
                    "Данные вашего профиля были изменены. Сохранить изменения?",
                    Alert.AlertType.INFORMATION, getClass()) == ButtonType.OK) {

                ProfileInfo info = displayManager.getCurrentProfileInfo();
                info.setLogin(loginTextfield.getText());
                info.setFirstName(firstNameTextfield.getText());
                info.setLastName(lastNameTextfield.getText());
                //info.setAvatar();  // ???
                info.setDateOfBirth(dateOfBirthField.getValue());

                displayManager.requestChangeMyProfile(info);
            }
        }
        displayManager.getDisplayNavigator().setCurrentPageTile(ECurrentPageTile.E_TILE_NONE);
        this.closeWindow();
    }

    @FXML
    protected void profileCancelButtonOnClick(){
        displayManager.getDisplayNavigator().setCurrentPageTile(ECurrentPageTile.E_TILE_NONE);
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
    }

    protected void resetNotification(){
        serviceLabel.setText("");

    }

    protected boolean inputValidation(){

        boolean result = true;

        if(lastNameTextfield.getText().length() > 80 || lastNameTextfield.getText().isEmpty()){
            result = false;
        }
        if(firstNameTextfield.getText().length() > 80 || firstNameTextfield.getText().isEmpty()){
            result = false;
        }
        if(loginTextfield.getText().length() > 30 || loginTextfield.getText().isEmpty()){
            result = false;
        }
        if(!result)
            serviceLabel.setText("Некорректные данные!!!");

        return result;
    }

    //------------
    //  Remote call
    //------------

    public void showProfileInformation() {
        ProfileInfo info = displayManager.getCurrentProfileInfo();
        if(info != null) {
            lastNameTextfield.setText(info.getLastName());
            firstNameTextfield.setText(info.getFirstName());
            loginTextfield.setText(info.getLogin());
            dateOfBirthField.setValue(info.getDateOfBirth());
            emailTextfield.setText(info.getEmail());
        }

    }

    protected void TimerFunc() {
//        while(true) {
//           // waitRefresh();
//            ProfileInfo info = displayManager.getCurrentProfileInfo();
//            if(info != null) {
//                lastNameTextfield.setText(info.getLastName());
//                firstNameTextfield.setText(info.getFirstName());
//                loginTextfield.setText(info.getLogin());
//                dateOfBirthField.setValue(info.getDateOfBirth());
//                emailTextfield.setText(info.getEmail());
//            }
//        }
    }
}
