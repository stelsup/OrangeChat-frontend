package com.maximus.chatclientjavafx;

import com.maximus.chatclientjavafx.controllerfx.ChatController;
import com.maximus.chatclientjavafx.controllerfx.LoginController;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.fxcore.GUIParam;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    private FxWeaver fxWeaver;
    private GUIController controller;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(SpringBootExampleApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        fxWeaver = applicationContext.getBean(FxWeaver.class);

        controller = applicationContext.getBean(LoginController.class);
        Utils.showWindow(LoginController.class, fxWeaver, controller, new GUIParam(Modality.NONE, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT,
                335, 530), "Авторизация пользователя" );

        LoginController loginController = (LoginController) controller;
        if(loginController.getBtnResult() == ButtonType.CANCEL){
            return;
        }

        controller = applicationContext.getBean(ChatController.class);
        Utils.showWindow(ChatController.class, fxWeaver, controller, new GUIParam(Modality.NONE, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT,
                702, 1006), "Orange chat" );

    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

}
