package com.tyqhwl.jrqh.homepage.view;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAttentionEntry implements Serializable {

    public String userId;
    public String thumb;
    public String read;
    public String time;
    public String tital;
    public String summary;
    public int post_id;
    public String userImage;
    public String userName;
    public String image;
    public ArrayList<AttentionListEntry> attentionListEntries;


    public MyAttentionEntry(String userId, String thumb, String read, String time, String tital,
                            String summary, int post_id, String userImage, String userName, ArrayList<AttentionListEntry> attentionListEntries) {
        this.userId = userId;
        this.thumb = thumb;
        this.read = read;
        this.time = time;
        this.tital = tital;
        this.summary = summary;
        this.post_id = post_id;
        this.userImage = userImage;
        this.userName = userName;
        this.attentionListEntries = attentionListEntries;
    }


    public MyAttentionEntry(String userId, String thumb, String read, String time, String tital, String summary,
                            int post_id, String userImage, String userName, String image, ArrayList<AttentionListEntry> attentionListEntries) {
        this.userId = userId;
        this.thumb = thumb;
        this.read = read;
        this.time = time;
        this.tital = tital;
        this.summary = summary;
        this.post_id = post_id;
        this.userImage = userImage;
        this.userName = userName;
        this.image = image;
        this.attentionListEntries = attentionListEntries;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTital() {
        return tital;
    }

    public void setTital(String tital) {
        this.tital = tital;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
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

    public ArrayList<AttentionListEntry> getAttentionListEntries() {
        return attentionListEntries;
    }

    public void setAttentionListEntries(ArrayList<AttentionListEntry> attentionListEntries) {
        this.attentionListEntries = attentionListEntries;
    }
}
