package com.tyqhwl.jrqh.homepage.view;

import java.io.Serializable;

public class CommitEntry implements Serializable {
    public String userId;
    public String userName;
    public String userComment;
    public String userImage;
    public CommitEntry(String userId, String userName, String userComment) {
        this.userId = userId;
        this.userName = userName;
        this.userComment = userComment;
    }


    public CommitEntry(String userId, String userName, String userComment, String userImage) {
        this.userId = userId;
        this.userName = userName;
        this.userComment = userComment;
        this.userImage = userImage;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
