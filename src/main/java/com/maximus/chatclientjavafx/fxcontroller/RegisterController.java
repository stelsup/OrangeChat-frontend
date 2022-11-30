package com.maximus.chatclientjavafx.fxcontroller;

import com.maximus.chatclientjavafx.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.service.ProxyService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


@Component
@FxmlView("register.fxml")
public class RegisterController extends GUIController {

    @FXML
    private TextField registerLastName;

    @FXML
    private TextField registerFirstName;

    @FXML
    private TextField registerLogin;

    @FXML
    private TextField registerPassword;

    @FXML
    private TextField registerPasswordRe;

    @FXML
    private TextField registerEmail;

    @FXML
    private DatePicker registerDatePicker;

    @FXML
    private Label incorrectLastName, incorrectFirstName, incorrectLogin, incorrectDayOfBirth, incorrectPassword, incorrectPasswordRe, incorrectEmail;

    private final ProxyService service;


    public RegisterController(ProxyService service){
        this.service = service;
    }

    @Override
    public void onShow() {

        TextFormatter<String> letterLast, letterFirst;

        UnaryOperator<TextFormatter.Change> filter;
        filter = change -> {
            String str = change.getControlNewText();
            if(Pattern.matches("\\D+",str)) {
                return change;
            }
            return null;
        };
        letterLast = new TextFormatter<>(filter);//можно задать значение по умолчанию
        letterFirst = new TextFormatter<>(filter);
        registerLastName.setTextFormatter(letterLast);
        registerFirstName.setTextFormatter(letterFirst);

    }

    @FXML
    protected void initialize(){

        registerDatePicker.setEditable(false);

        registerLastName.setPromptText("Введите фамилию (не более 80 символов)");
        registerFirstName.setPromptText("Введите имя (не более 80 символов)");
        registerLogin.setPromptText("Введите логин (не более 30 символов)");
        registerPassword.setPromptText("Введите пароль (не более 50 символов)");
        registerPasswordRe.setPromptText("Повторите пароль");
        registerEmail.setPromptText("Введите свой email");
        registerDatePicker.setPromptText("Выберите дату");

    }

    @FXML
    protected void registerOkButtonOnClick(){

        resetNotification();

        if(!inputValidation()){
            return;
        }

        String lastName = registerLastName.getText();
        String firstName = registerFirstName.getText();
        String login = registerLogin.getText();
        String password = registerPassword.getText();
        String email = registerEmail.getText();
        LocalDate dateOfBirth = registerDatePicker.getValue();

        if(service.registerUser(lastName, firstName, login, password, email, dateOfBirth)){
            Utils.MessageBox( "Регистрация", "Подтверждение email",
                    "Регистрация успешно завершена. На Вашу почту выслано письмо с подтверждением",
                    Alert.AlertType.INFORMATION);
            //TODO добавить отправку подтверждения на почту
        }else{
            Utils.MessageBox( "Регистрация", "Регистрация невозможна!",
                    "Пользователь с таким login или email уже существует. Воспользуйтесь восстановлением аккаунта, если забыли свой пароль.",
                    Alert.AlertType.WARNING);
        }
        this.closeWindow();

    }

    @FXML
    protected void registerCancelButtonOnClick(){
        this.closeWindow();
    }


    protected void resetNotification(){
        incorrectLastName.setText("");
        incorrectFirstName.setText("");
        incorrectLogin.setText("");
        incorrectDayOfBirth.setText("");
        incorrectPassword.setText("");
        incorrectPasswordRe.setText("");
        incorrectEmail.setText("");
    }

    protected boolean inputValidation(){

        boolean result = true;

        if(registerLastName.getText().length() > 80 || registerLastName.getText().isEmpty()){
            incorrectLastName.setText("Поле пустое или содержит более 80 символов!");
            result = false;
        }
        if(registerFirstName.getText().length() > 80 || registerFirstName.getText().isEmpty()){
            incorrectFirstName.setText("Поле пустое или содержит более 80 символов!");
            result = false;
        }
        if(registerLogin.getText().length() > 30 || registerLogin.getText().isEmpty()){
            incorrectLogin.setText("Поле пустое или содержит более 30 символов!");
            result = false;
        }
        if(registerDatePicker.getValue() == null){
            incorrectDayOfBirth.setText("Выберите дату рождения");
            result = false;
        }
        if(registerPassword.getText().length() > 50 || registerPassword.getText().isEmpty()){
            incorrectPassword.setText("Поле пустое или содержит более 50 символов!");
            result = false;
        }
        if(!registerPassword.getText().equals(registerPasswordRe.getText())){
            incorrectPasswordRe.setText("Введенные пароли не совпадают!");
            result = false;
        }
        if(registerEmail.getText().isEmpty() || !registerEmail.getText().contains("@") || !registerEmail.getText().contains(".")){
            incorrectEmail.setText("Данный email не существует!");
            result = false;
        }
        return result;
    }

}
