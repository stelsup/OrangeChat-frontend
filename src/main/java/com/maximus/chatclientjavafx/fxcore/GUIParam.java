package com.maximus.chatclientjavafx.fxcore;

import javafx.stage.Modality;
import javafx.stage.Window;

public class GUIParam {

    public Modality modality;
    public Window ownerParent;
    public ShowType showType;
    public int minHeight;
    public int minWidth;

    public GUIParam(Modality m, Window w, ShowType st, int minHeight, int minWidth) {
        this.modality = m;
        this.ownerParent = w;
        this.showType = st;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
    }

    public enum ShowType {
        SHOWTYPE_SHOWNORMAL,
        SHOWTYPE_SHOWWAIT
    }


}
