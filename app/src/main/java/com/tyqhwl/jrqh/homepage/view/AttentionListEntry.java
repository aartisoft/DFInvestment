package com.tyqhwl.jrqh.homepage.view;

import java.io.Serializable;

public class AttentionListEntry implements Serializable {
    public String userImage;
    public String userName;
    public String userComment;

    public AttentionListEntry(String userImage, String userName, String userComment) {
        this.userImage = userImage;
        this.userName = userName;
        this.userComment = userComment;
    }

    @Override
    public String toString() {
        return "AttentionListEntry{" +
                "userImage='" + userImage + '\'' +
                ", userName='" + userName + '\'' +
                ", userComment='" + userComment + '\'' +
                '}';
    }
}
