package com.maximus.chatclientjavafx.fxcontroller;


import com.maximus.chatclientjavafx.displaymanager.*;
import com.maximus.chatclientjavafx.utils.GUIUtils;
import com.maximus.chatclientjavafx.utils.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.fxcore.GUIParam;
import com.maximus.chatclientjavafx.service.ConnectionService;
import com.maximus.chatdto.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;


@Component
@FxmlView("modernchat.fxml")
public class ChatController extends GUIController {

    @FXML
    private Button sidebarSearchBtn;

    @FXML
    private Button sidebarMessageBtn;

    @FXML
    private Button sidebarGroupMessageBtn;

    @FXML
    private Button sidebarProfileBtn;

    @FXML
    private Button sidebarSettingsBtn;

    @FXML
    private Button sidebarExitBtn;

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

    @FXML
    private TextField searchGlobal;

    @FXML
    private VBox vboxLocalRooms;

    @FXML
    private HBox hboxSearchBar;

    @FXML
    private HBox hboxRoomHeader;

    @FXML
    private VBox vBoxRoomBody;

    @FXML
    private TextField input_message_field;

    ////////////////////////////////////////
    ////////////   Cursor  /////////////////

    private Button currentSideBtn;

    private HBox currentTile;

    ////////////////////////////////////////

    private final ConfigurableApplicationContext applicationContext;
    private final ConnectionService connectionService;
    private final DisplayManager displayManager;



    public ChatController(ConfigurableApplicationContext context, ConnectionService service, DisplayManager displayManager){
        this.applicationContext = context;
        this.connectionService = service;
        this.displayManager = displayManager;
    }


    @Override
    public void onShow() {

        if(connectionService.connectToServer()){
            this.onBtnMessageClick();
        }else{
            Utils.MessageBox( "Внимание", "Нет подключения к серверу",
                    "Не удалось установить соединение с сервером!",
                    Alert.AlertType.WARNING, getClass());
        }

        displayManager.startGlobalTimers();
    }




    @FXML
    protected void initialize(){

        sidebarSearchBtn.setGraphic(GUIUtils.loadImage("search_big.png", 40, 40));
        sidebarMessageBtn.setGraphic(GUIUtils.loadImage("orange_message.png", 40, 40));
        sidebarGroupMessageBtn.setGraphic(GUIUtils.loadImage("conference.png", 40, 40));
        sidebarProfileBtn.setGraphic(GUIUtils.loadImage("orange_user1.png", 40, 40));
        sidebarSettingsBtn.setGraphic(GUIUtils.loadImage("orange_settings.png", 40, 40));
        sidebarExitBtn.setGraphic(GUIUtils.loadImage("orange_exit1.png", 40, 40));
        interface_btn_menu.setGraphic(GUIUtils.loadImage("orange_menu.png", 30, 30));
        interface_btn_send.setGraphic(GUIUtils.loadImage("orange_send.png", 40, 40));
        interface_btn_add_picture.setGraphic(GUIUtils.loadImage("orange_picture.png", 40, 40));
        interface_btn_smile.setGraphic(GUIUtils.loadImage("orange_smile.png", 40, 40));
    }


    @FXML
    protected void onBtnAddClick() {

        System.out.println("add work!");

        if(displayManager.getDisplayNavigator().isInChatMode()) {

            FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
            GUIController controller = applicationContext.getBean(NewRoomController.class);
            Utils.showWindow(NewRoomController.class, fxWeaver, controller, new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT,
                    350, 550), "Создание чата" );

        }

    }

    @FXML
    protected void onBtnDeleteClick(){
        if(currentTile == null){
            return;
        }
        vboxLocalRooms.getChildren().remove(currentTile);
        currentTile = null;
        //TODO Clear RoomHeader!

    }

    @FXML
    protected void onSearchGlobalKeyPress() {
        String searchText = searchGlobal.getText();
        if(searchText != null && !searchText.isEmpty()) {
            displayManager.requestSearch(searchText);
        }
    }

    @FXML
    protected void onBtnSearchClick(){

        displayManager.getDisplayNavigator().setCurrentPage(ECurrentPage.E_PAGE_SEARCH);
        hboxSearchBar.setDisable(false);
        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.CENTER);
        vBoxRoomBody.getChildren().clear();

    }



    @FXML
    protected void onBtnMessageClick(){

        displayManager.getDisplayNavigator().setCurrentPage(ECurrentPage.E_PAGE_CHATS);
        hboxSearchBar.setDisable(false);

        displayManager.AbortCurrentTimer();

        displayManager.requestRoomTiles();

        showRoomTiles();
    }


    @FXML
    protected  void onBtnProfileClick(){

        displayManager.getDisplayNavigator().setCurrentPage(ECurrentPage.E_PAGE_PROFILE);

        //currentSideBtn = sidebarProfileBtn;

        HBox myProfile = GUIUtils.addMenuTile("Мой профиль", "profile.png");
        myProfile.setOnMouseClicked(event -> {openProfileWindow(myProfile);});
        HBox notification = GUIUtils.addMenuTile("Уведомления", "notification.png");
        notification.setOnMouseClicked(event -> {currentTile = notification;});
        HBox security = GUIUtils.addMenuTile("Безопасность", "security.png");
        security.setOnMouseClicked(event -> {openSecurityWindow(security);});
        HBox statistic = GUIUtils.addMenuTile("Статистика", "statistics.png");
        statistic.setOnMouseClicked(event -> {currentTile = statistic;});

        hboxSearchBar.setDisable(true);
        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);
        vboxLocalRooms.getChildren().addAll(myProfile, notification, security, statistic);

    }

    @FXML
    protected  void onBtnSettingsClick(){

        displayManager.getDisplayNavigator().setCurrentPage(ECurrentPage.E_PAGE_SETTINGS);

        //currentSideBtn = sidebarSettingsBtn;


        HBox programSettings = GUIUtils.addMenuTile("Настройки программы", "program_settings.png");
        programSettings.setOnMouseClicked(event -> {currentTile = programSettings;});
        HBox securitySettings = GUIUtils.addMenuTile("Настройки безопасности", "security_settings.png");
        securitySettings.setOnMouseClicked(event -> {currentTile = securitySettings;});

        hboxSearchBar.setDisable(true);
        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);
        vboxLocalRooms.getChildren().addAll(programSettings, securitySettings);

    }

    @FXML
    protected void onBtnExitClick(){
        ButtonType result = Utils.MessageBox("Предупреждение", "Выйти из профиля",
                "Вы уверены, что хотите выйти из профиля ?", Alert.AlertType.CONFIRMATION, getClass());

        if(result == ButtonType.OK)
            this.closeWindow();
    }

    @FXML
    protected void onBtnSendClick() {
        String text = input_message_field.getText();

        MessageInfo message = new MessageInfo();
        message.setText(text);
        message.setSenderId(displayManager.getCurrentPrincipal().getId());
        message.setRoomId(displayManager.getDisplayNavigator().getCurrentEntityTile().getUniqueId());

        displayManager.sendMessage(message);
    }

    @FXML
    protected void onInputMessageFieldAction() {

    }

    protected void openProfileWindow(HBox currentMenuTile){

        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        GUIController controller = applicationContext.getBean(ProfileController.class);
        Utils.showWindow(ProfileController.class, fxWeaver, controller, new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT,
                600, 500), "Мой профиль" );

    }

    protected void openNotificationWindow(HBox currentMenuTile){


    }

    protected void openSecurityWindow(HBox currentMenuTile){

        currentTile = currentMenuTile;
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        GUIController controller = applicationContext.getBean(ProfileSecurityController.class);
        Utils.showWindow(ProfileSecurityController.class, fxWeaver, controller, new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT,
                600, 500), "Настройки безопастности" );

    }

    protected void openStatisticWindow(HBox currentMenuTile){


    }

    public void showSearchResults() {
        if(displayManager.getDisplayNavigator().getCurrentPage() != ECurrentPage.E_PAGE_SEARCH)
            return;

        System.out.println("showSearchResults()");

        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);

        Set<SearchTile> results = displayManager.getSearchResults();
        List<HBox> userSearchBox = new ArrayList<>();
        List<HBox> roomSearchBox = new ArrayList<>();


        if(results.size() == 0)
            System.out.println("no results!");

        for(SearchTile res : results){
            if(res.getType() == SearchType.USER_TILE_TYPE) {
                HBox searchHBox = GUIUtils.addSearchTile(res.getFirstName(), res.getLogin(), "orange_avatar1.png");
                Button addBtn = (Button) searchHBox.getChildren().get(2);
                addBtn.setOnMouseClicked(event -> {onBtnAddClick();});
                searchHBox.setOnMouseClicked(event -> {
                    showChatWindow();
                });
                userSearchBox.add(searchHBox);
            } else if(res.getType() == SearchType.ROOM_TILE_TYPE) {
                HBox searchHBox = GUIUtils.addSearchTile(res.getName(), res.getUniqueID().toString(), "orange_group_big2.png");
                Button addBtn = (Button) searchHBox.getChildren().get(2);
                addBtn.setOnMouseClicked(event -> {onBtnAddClick();});
                searchHBox.setOnMouseClicked(event -> {
                    showChatWindow();
                });
                roomSearchBox.add(searchHBox);
            }
        }

        if(!userSearchBox.isEmpty()){
            vboxLocalRooms.getChildren().add(GUIUtils.addSearchTileSeparator("Пользователи"));
            vboxLocalRooms.getChildren().addAll(userSearchBox);
        }
        if(!roomSearchBox.isEmpty()){
            vboxLocalRooms.getChildren().add(GUIUtils.addSearchTileSeparator("Диалоги"));
            vboxLocalRooms.getChildren().addAll(roomSearchBox);
        }

    }

    public void showRoomTiles() {
        if(displayManager.getDisplayNavigator().getCurrentPage() != ECurrentPage.E_PAGE_CHATS)
            return;

        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);

        if (!displayManager.IsRoomTileEmpty()) {

            Map<Long, RoomTile> roomTiles = displayManager.getRoomTiles();
            //for (RoomTile room : roomTiles) {
            for (Map.Entry<Long, RoomTile> entry : roomTiles.entrySet()) {
                RoomTile room = entry.getValue();
                HBox roomHBox = GUIUtils.addRoomTile(room.getName(), "Last message", "orange_avatar1.png", 0, false);
                Long uniqueId = room.getUniqueID();
                roomHBox.setOnMouseClicked(event -> {
                    ChatTileClickEvent(EEntityType.ROOM_TILE_TYPE, uniqueId);
                });
                vboxLocalRooms.getChildren().add(roomHBox);
            }
        }
    }

    // клик по ТАЙЛУ чата
    public void ChatTileClickEvent(EEntityType type, Long uniqueId) {
        displayManager.getDisplayNavigator().setCurrentEntityTile(new CurrentEntity(type, uniqueId));
        displayManager.requestRoomInfo(uniqueId); // запрашиваем roominfo
        //req chat contents
        System.out.println("ChatTileClickEvent");

        /////
        displayManager.requestMessages(uniqueId/*, LocalDateTime.now()*/);

        clearInputTextField();

        showChatWindowHeader();
        showChatWindow();
    }

    public void showChatWindow() {
        Long roomId = displayManager.getDisplayNavigator().getCurrentEntityTile().getUniqueId();
        System.out.println("showChatWindow(): uniqueId =  " + roomId);

        Queue<MessageInfo> msgs = displayManager.getMessages(roomId);
        if(msgs == null)
            return;

        vBoxRoomBody.getChildren().clear();

        for(MessageInfo msg : msgs) {
            String messageType = (msg.getSenderId() == displayManager.getCurrentPrincipal().getId()) ? "outgoing" : "incoming" ;
            HBox newMsg = GUIUtils.createMessage("orange_avatar1.png", msg.getText(), messageType);
            vBoxRoomBody.getChildren().add(newMsg);
        }

    }

    public void showChatSingleMessage() {
        Long roomId = displayManager.getDisplayNavigator().getCurrentEntityTile().getUniqueId();
        System.out.println("showChatSingleMessage(): uniqueId =  " + roomId);


    }

    public void showChatWindowHeader() {
        System.out.println("showChatWindowHeader");
        CurrentEntity curRoom = displayManager.getDisplayNavigator().getCurrentEntityTile();
        RoomInfo room = displayManager.getRoomInfo(curRoom.getUniqueId());
        if(room == null)
            return;

        hboxRoomHeader.getChildren().set(0,GUIUtils.createRoomHeaderName(room.getName()));

        if(room.getMembers().size() <= 2) {
            hboxRoomHeader.getChildren().set(1, GUIUtils.changeStatus(room.getMembers().iterator().next().getOnlineStatus().getStatus()));
        }

        //// todo: set room position

    }


    protected void clearInputTextField() {
        input_message_field.clear();
    }

    //------------------------------------------------

    protected void TimerFunc() {
    }
}
