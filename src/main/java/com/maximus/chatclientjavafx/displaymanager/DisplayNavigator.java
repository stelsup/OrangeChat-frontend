package com.maximus.chatclientjavafx.displaymanager;

public class DisplayNavigator {

    private ECurrentPage currentPage;
    private ECurrentPageTile currentPageTile;
    private Long currentRoomID;


    public ECurrentPage getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(ECurrentPage currentPage) {
        this.currentPage = currentPage;
    }

    public ECurrentPageTile getCurrentPageTile() {
        return currentPageTile;
    }

    public void setCurrentPageTile(ECurrentPageTile currentPageTile) {
        this.currentPageTile = currentPageTile;
    }

    public Long getCurrentRoomID() {
        return currentRoomID;
    }

    public void setCurrentRoomID(Long currentRoomID) {
        this.currentRoomID = currentRoomID;
    }



    public  boolean isInSearchMode(){
        return  currentPage == ECurrentPage.E_PAGE_SEARCH;
    }

    public boolean isInChatMode(){
        return currentPage == ECurrentPage.E_PAGE_CHATS;
    }

    public boolean isInProfileMode(){
        return  currentPage == ECurrentPage.E_PAGE_PROFILE;
    }

    public boolean isInSettingsMode(){
        return  currentPage == ECurrentPage.E_PAGE_SETTINGS;
    }



}
