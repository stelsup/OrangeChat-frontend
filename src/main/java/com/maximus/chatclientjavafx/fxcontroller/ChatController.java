package com.maximus.chatclientjavafx.fxcontroller;


import com.maximus.chatclientjavafx.displaymanager.DisplayManager;
import com.maximus.chatclientjavafx.displaymanager.DisplayTimer;
import com.maximus.chatclientjavafx.displaymanager.ECurrentPage;
import com.maximus.chatclientjavafx.displaymanager.ECurrentPageTile;
import com.maximus.chatclientjavafx.utils.GUIUtils;
import com.maximus.chatclientjavafx.utils.Utils;
import com.maximus.chatclientjavafx.fxcore.GUIController;
import com.maximus.chatclientjavafx.fxcore.GUIParam;
import com.maximus.chatclientjavafx.service.ConnectionService;
import com.maximus.chatdto.RoomTile;
import com.maximus.chatdto.SearchTile;
import com.maximus.chatdto.SearchType;
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

import java.util.Iterator;
import java.util.List;
import java.util.Set;


@Component
@FxmlView("modernchat.fxml")
public class ChatController extends GUIController {

    @FXML
    private Button sidebarSearchBtn;

    @FXML
    private Button sidebarMessageBtn;

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
        sidebarProfileBtn.setGraphic(GUIUtils.loadImage("orange_user1.png", 40, 40));
        sidebarSettingsBtn.setGraphic(GUIUtils.loadImage("orange_settings.png", 40, 40));
        sidebarExitBtn.setGraphic(GUIUtils.loadImage("orange_exit1.png", 40, 40));
        interface_btn_menu.setGraphic(GUIUtils.loadImage("orange_menu.png", 30, 30));
        interface_btn_send.setGraphic(GUIUtils.loadImage("orange_send.png", 40, 40));
        interface_btn_add_picture.setGraphic(GUIUtils.loadImage("orange_picture.png", 40, 40));
        interface_btn_smile.setGraphic(GUIUtils.loadImage("orange_smile.png", 40, 40));
        search_btn_add.setGraphic(GUIUtils.loadImage("add.png", 30, 30));
        search_btn_delete.setGraphic(GUIUtils.loadImage("minus.png", 30, 30));

    }


    @FXML
    protected void onBtnAddClick() {

        if(displayManager.getDisplayNavigator().isInChatMode()){

            FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
            GUIController controller = applicationContext.getBean(NewRoomController.class);
            Utils.showWindow(NewRoomController.class, fxWeaver, controller, new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT,
                    350, 550), "Создание чата" );


        }


//        UserInfo test_info = chatService.getMyUserInfo();
//
//        test_info.setFirstName("Васян");
//        chatService.editMyUserInfo(test_info);

//        HBox newRoom = GUIUtils.addRoomTile("New User", "Last message",
//                "orange_avatar1.png", 10, true );
//        newRoom.setOnMouseClicked(event -> {createChatRoom(newRoom);});
//        vboxLocalRooms.getChildren().add(newRoom);

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

        currentSideBtn = sidebarSearchBtn;  //// navigation
        hboxSearchBar.setDisable(false);
        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.CENTER);
        //vboxLocalRooms.getChildren().add(GUIUtils.getStub("Поиск временно недоступен"));
        //        DisplayTimer timer = new DisplayTimer();

//        displayManager.requestSearch()
    }



    @FXML
    protected void onBtnMessageClick(){
        displayManager.AbortCurrentTimer();

        displayManager.requestRoomTiles();

        displayManager.getDisplayNavigator().setCurrentPage(ECurrentPage.E_PAGE_CHATS);
        hboxSearchBar.setDisable(false);

        showRoomTiles();
    }


    @FXML
    protected  void onBtnProfileClick(){
        HBox myProfile = GUIUtils.addMenuTile("Мой профиль", "profile.png");
        myProfile.setOnMouseClicked(event -> {openProfileWindow(myProfile);});
        HBox notification = GUIUtils.addMenuTile("Уведомления", "notification.png");
        notification.setOnMouseClicked(event -> {currentTile = notification;});
        HBox security = GUIUtils.addMenuTile("Безопасность", "security.png");
        security.setOnMouseClicked(event -> {openSecurityWindow(security);});
        HBox statistic = GUIUtils.addMenuTile("Статистика", "statistics.png");
        statistic.setOnMouseClicked(event -> {currentTile = statistic;});

        displayManager.getDisplayNavigator().setCurrentPage(ECurrentPage.E_PAGE_PROFILE);

        currentSideBtn = sidebarProfileBtn;
        hboxSearchBar.setDisable(true);
        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);
        vboxLocalRooms.getChildren().addAll(myProfile, notification, security, statistic);

    }

    @FXML
    protected  void onBtnSettingsClick(){
        HBox programSettings = GUIUtils.addMenuTile("Настройки программы", "program_settings.png");
        programSettings.setOnMouseClicked(event -> {currentTile = programSettings;});
        HBox securitySettings = GUIUtils.addMenuTile("Настройки безопасности", "security_settings.png");
        securitySettings.setOnMouseClicked(event -> {currentTile = securitySettings;});

        displayManager.getDisplayNavigator().setCurrentPage(ECurrentPage.E_PAGE_SETTINGS);

        currentSideBtn = sidebarSettingsBtn;
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

    protected void createChatWindow(HBox currentRoom){

        currentTile = currentRoom;
        String roomName = GUIUtils.searchRoomName(currentRoom);
        hboxRoomHeader.getChildren().set(0,GUIUtils.createRoomHeaderName(roomName));
        hboxRoomHeader.getChildren().set(1,GUIUtils.changeStatus("Online"));


    }


    protected void openProfileWindow(HBox currentMenuTile){

        currentTile = currentMenuTile;

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
        Set<SearchTile> results = displayManager.getSearchResults();
        if(results.size() == 0)
            System.out.println("no results!");

        for(SearchTile res : results){
            if(res.getType() == SearchType.USER_TILE_TYPE) {
                HBox roomHBox = GUIUtils.addSearchTile(res.getFirstName(), res.getLogin(), "orange_avatar1.png");
                roomHBox.setOnMouseClicked(event -> {
                    createChatWindow(roomHBox);
                });
                vboxLocalRooms.getChildren().add(roomHBox);
            } else if(res.getType() == SearchType.ROOM_TILE_TYPE) {
                HBox roomHBox = GUIUtils.addSearchTile(res.getName(), res.getUniqueID().toString(), "orange_avatar1.png");
                roomHBox.setOnMouseClicked(event -> {
                    createChatWindow(roomHBox);
                });
                vboxLocalRooms.getChildren().add(roomHBox);
            }
        }

    }

    public void showRoomTiles() {
        if(displayManager.getDisplayNavigator().getCurrentPage() != ECurrentPage.E_PAGE_CHATS)
            return;

        vboxLocalRooms.getChildren().clear();
        vboxLocalRooms.setAlignment(Pos.TOP_LEFT);

//        HBox vovan = GUIUtils.addRoomTile("Вован", "Last message", "orange_avatar1.png",0, false );
//        vovan.setOnMouseClicked(event -> {
//            createChatWindow(vovan);});
//        HBox potash = GUIUtils.addRoomTile("Поташ", "Last message", "orange_avatar1.png", 28, true );
//        potash.setOnMouseClicked(event -> {
//            createChatWindow(potash);});
        //vboxLocalRooms.getChildren().addAll(vovan, potash);

        if (!displayManager.IsRoomTileEmpty()) {

            List<RoomTile> roomTiles = displayManager.getRoomTiles();
            for (RoomTile room : roomTiles) {
                HBox roomHBox = GUIUtils.addRoomTile(room.getName(), "Last message", "orange_avatar1.png", 0, false);
                roomHBox.setOnMouseClicked(event -> {
                    createChatWindow(roomHBox);
                });
                vboxLocalRooms.getChildren().add(roomHBox);
            }
        }
    }

    //------------------------------------------------

    protected void TimerFunc() {
    }
}
