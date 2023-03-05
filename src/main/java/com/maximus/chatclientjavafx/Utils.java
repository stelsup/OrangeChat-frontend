package com.maximus.chatclientjavafx;

import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.fxcore.GUIParam;
import com.maximus.chatclientjavafx.model.auth.MessageResponse;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {


    public static String getImagesPath(){
        String path = System.getProperty("user.dir");
        return "file:" + path + "/img/";
    }

    public static String getEtcPath() {
        String path = System.getProperty("user.dir");
        return path + "/etc/";

    }

    public static void showWindow(Class<?> controllerClass, FxWeaver fxWeaver, GUIController controller, GUIParam guiParam, String title){

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.getIcons().add(new Image(getImagesPath() + "orange 1.png"));
        Parent parent = fxWeaver.loadView(controllerClass);
        Scene scene = new Scene(parent);
        stage.initModality(guiParam.modality);
        stage.initOwner(guiParam.ownerParent);

        stage.setMinHeight(guiParam.minHeight);
        stage.setMinWidth(guiParam.minWidth);
        stage.setScene(scene);

        controller.setScene(scene);
        controller.onShow();

        if(guiParam.showType == GUIParam.ShowType.SHOWTYPE_SHOWNORMAL)
            stage.show();
        else if(guiParam.showType == GUIParam.ShowType.SHOWTYPE_SHOWWAIT)
            stage.showAndWait();

    }


    public static ButtonType MessageBox(String title, String text, String textDetails, Alert.AlertType type, Class<?> invokeClass) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.setContentText(textDetails);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(invokeClass.getResource("/stylesheets/dialogpanestyle.css").toExternalForm());
        dialogPane.getStyleClass().add("my-dialog-pane");
        Stage dialog = (Stage) dialogPane.getScene().getWindow();
        dialog.getIcons().add(new Image(getImagesPath() + "orange 1.png"));


        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                alert.close();
            }else{
                alert.close();
            }
        });
        return alert.getResult();
    }


    public static String passToHash(String rawPass) {
        String stringHash = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(rawPass.getBytes());
            stringHash = new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException ex) {
            // TODO add Logger message
            //DiagnosticMessage.logging("Hashing failed: ", ex, Utils.class, DiagnosticMessage.LoggerType.ERROR);
        }
        return stringHash;

    }

    public static MessageResponse createResponseStatusMessage(int statusCode){
        switch(statusCode){

            case (401) :
                return new MessageResponse("Неверный логин или пароль");
            case (403) :
                return new MessageResponse("Отказано в доступе");
            case (503) :
                return new MessageResponse("Сервер недоступен");
            default:
                return new MessageResponse("Неизвестная ошибка");
        }
    }


//    public static Object deserializePayload(LinkedHashMap<String, String> payload) {
//
//        MessageType objType = MessageType.fromString(payload.get("msgType"));
//
//        switch (objType){
//
//            case USER_INFO_TYPE:
//                UserInfo userInfo = new UserInfo();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                //userInfo.setMsgType(objType);
//                userInfo.setUniqueID(payload.get("uniqueID"));
//                userInfo.setLogin(payload.get("login"));
//                userInfo.setFirstName(payload.get("firstName"));
//                userInfo.setLastName(payload.get("lastName"));
//                userInfo.setAvatar(payload.get("avatar"));
//                //userInfo.setDateOfBirth( LocalDate.parse(payload.get("dateOfBirth"), formatter));
//                //userInfo.setOnlineStatus();
//                return userInfo;
//
//            default:
//                return null;
//        }
//    }



}
