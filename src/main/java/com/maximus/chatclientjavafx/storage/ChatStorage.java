package com.maximus.chatclientjavafx.storage;

import com.maximus.chatclientjavafx.model.UserPrincipal;
import com.maximus.chatdto.MessageInfo;
import com.maximus.chatdto.ProfileInfo;
import com.maximus.chatdto.RoomInfo;
import com.maximus.chatdto.RoomTile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChatStorage {

    private UserPrincipal principal;
    private ProfileInfo profile;
    private List<RoomTile> roomTiles = new ArrayList<>();
    private Set<RoomInfo> rooms = new HashSet<>();
    private Map<Long, List<MessageInfo>> messages = new HashMap<>();





    public UserPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(UserPrincipal principal) {
        this.principal = principal;
    }

    public ProfileInfo getProfile() {
        return profile;
    }

    public void setProfile(ProfileInfo profile) {
        this.profile = profile;
    }
}
