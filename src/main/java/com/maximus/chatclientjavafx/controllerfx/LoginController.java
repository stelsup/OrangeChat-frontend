package com.maximus.chatclientjavafx.controllerfx;

import com.maximus.chatclientjavafx.Utils;
import com.maximus.chatclientjavafx.WeatherService;
import com.maximus.chatclientjavafx.controller.ProxyController;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.fxcore.GUIParam;
import com.maximus.chatclientjavafx.model.UserCred;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@FxmlView("login.fxml")
public class LoginController extends GUIController {

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Button loginOkButton;

    @FXML
    private Button loginCancelButton;

    @FXML
    private Hyperlink loginHyperLink;

    @FXML
    private Label loginResultLabel;

    private ConfigurableApplicationContext applicationContext;
    private ProxyController proxyController;
    private UserCred userCred;


    @Autowired
    public LoginController(ProxyController controller, ConfigurableApplicationContext context) {
        this.applicationContext = context;
        this.proxyController = controller;
    }


    @Override
    public void onShow() {

    }

    @FXML
    protected void initialize(){
        loginTextField.setPromptText("Имя пользователя");
        loginPasswordField.setPromptText("Пароль");
    }

    @FXML
    protected void loginOkButtonOnClick(){
        userCred = new UserCred();
        userCred.setUserName(loginTextField.getText());
        userCred.setPassword(loginPasswordField.getText());
        // TODO Maybe on Backend?
        //userCred.setPassword(Utils.passToHash(loginPasswordField.getText()));

        if(proxyController.checkCredentials(userCred)){
            loginResultLabel.setText("Accept");
            loginResultLabel.setTextFill(Color.web("#23AF05"));
        }else{
            loginResultLabel.setText("Пользователь или пароль введены неверно!");
            loginResultLabel.setTextFill(Color.web("#AD0505"));
            /*Utils.MessageBox( "Ошибка", "Ошибка","Пользователь или пароль введены неверно!",
                    Alert.AlertType.WARNING);*/
        }

    }

    @FXML
    protected void loginCancelButtonOnClick(){
        this.closeWindow();
    }

    @FXML
    protected void loginRegisterButtonOnClick(){
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        GUIController controller = applicationContext.getBean(RegisterController.class);
        Utils.showWindow(RegisterController.class, fxWeaver, controller, new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT,
                600, 500), "Регистрация пользователя" );
    }


}
