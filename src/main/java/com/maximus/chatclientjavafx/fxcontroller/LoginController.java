package com.maximus.chatclientjavafx.fxcontroller;

import com.maximus.chatclientjavafx.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.fxcore.GUIParam;
import com.maximus.chatclientjavafx.service.ProxyService;
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

    private ButtonType btnResult;

    private final ConfigurableApplicationContext applicationContext;
    private final ProxyService service;


    @Autowired
    public LoginController(ConfigurableApplicationContext context, ProxyService service) {
        this.applicationContext = context;
        this.service = service;
        this.btnResult = ButtonType.CANCEL;
    }


    @Override
    public void onShow() {

    }

    public ButtonType getBtnResult(){
        return btnResult;
    }

    @FXML
    protected void initialize(){
        loginTextField.setPromptText("Имя пользователя");
        loginPasswordField.setPromptText("Пароль");
    }

    @FXML
    protected void loginOkButtonOnClick(){

        if(service.checkCredentials(loginTextField.getText(), loginPasswordField.getText())){
            btnResult = ButtonType.APPLY;
            this.closeWindow();
        }else{
            loginResultLabel.setText("Пользователь или пароль введены неверно!");
            loginResultLabel.setTextFill(Color.web("#AD0505"));
            /*Utils.MessageBox( "Ошибка", "Ошибка","Пользователь или пароль введены неверно!",
                    Alert.AlertType.WARNING);*/
        }

    }

    @FXML
    protected void loginCancelButtonOnClick(){
        btnResult = ButtonType.CANCEL;
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
