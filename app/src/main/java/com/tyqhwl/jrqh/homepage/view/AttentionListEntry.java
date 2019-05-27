package com.tyqhwl.jrqh.homepage.view;

import java.io.Serializable;

public class AttentionListEntry implements Serializable {
    public String userImages;
    public String userNames;
    public String comments;

    public AttentionListEntry(String userImage, String userName, String userComment) {
        this.userImages = userImage;
        this.userNames = userName;
        this.comments = userComment;
    }

    @Override
    public String toString() {
        return "AttentionListEntry{" +
                "userImage='" + userImages + '\'' +
                ", userName='" + userNames + '\'' +
                ", userComment='" + comments + '\'' +
                '}';
    }
}
