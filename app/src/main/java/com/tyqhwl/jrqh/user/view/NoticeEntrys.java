package com.tyqhwl.jrqh.user.view;

import java.io.Serializable;

public class NoticeEntrys implements Serializable {
    public String title;
    public String dataTime;
    public String context;

    public NoticeEntrys(String title, String dataTime, String context) {
        this.title = title;
        this.dataTime = dataTime;
        this.context = context;
    }
}
