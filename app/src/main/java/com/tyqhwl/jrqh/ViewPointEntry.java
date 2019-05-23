package com.tyqhwl.jrqh;

//观点实体类

import java.io.Serializable;

public class ViewPointEntry implements Serializable {

    private String userName;
    private String context;
    private String dataTime;
    private int like;
    private int disLike;
    private String imageUrl;
    private String userImage;
    private String userId;

    public ViewPointEntry(String userName, String context, String dataTime, int like, int disLike, String imageUrl, String userImage) {
        this.userName = userName;
        this.context = context;
        this.dataTime = dataTime;
        this.like = like;
        this.disLike = disLike;
        this.imageUrl = imageUrl;
        this.userImage = userImage;
    }

    public ViewPointEntry(String userName, String context, String dataTime, int like, int disLike, String imageUrl, String userImage, String userId) {
        this.userName = userName;
        this.context = context;
        this.dataTime = dataTime;
        this.like = like;
        this.disLike = disLike;
        this.imageUrl = imageUrl;
        this.userImage = userImage;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDisLike() {
        return disLike;
    }

    public void setDisLike(int disLike) {
        this.disLike = disLike;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
