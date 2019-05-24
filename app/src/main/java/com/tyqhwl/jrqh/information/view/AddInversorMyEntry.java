package com.tyqhwl.jrqh.information.view;

import com.tyqhwl.jrqh.homepage.view.AttentionListEntry;

import java.io.Serializable;
import java.util.ArrayList;

public class AddInversorMyEntry implements Serializable {

    public String thumb;
    public String read;
    public String time;
    public String tital;
    public String summary;
    public int postId;
    public String commitUserId;
    public String userImage;
    public String contentImage;
    public String userName;
    public ArrayList<AttentionListEntry> attentionList;


    public AddInversorMyEntry(String thumb, String read, String time, String tital, String summary, int postId,
                              String commitUserId, String userImage, String contentImage, String userName, ArrayList<AttentionListEntry> attentionList) {
        this.thumb = thumb;
        this.read = read;
        this.time = time;
        this.tital = tital;
        this.summary = summary;
        this.postId = postId;
        this.commitUserId = commitUserId;
        this.userImage = userImage;
        this.contentImage = contentImage;
        this.userName = userName;
        this.attentionList = attentionList;
    }


    public AddInversorMyEntry(String thumb, String read, String time, String tital,
                              String summary, int postId, String commitUserId, String userImage, String contentImage, String userName) {
        this.thumb = thumb;
        this.read = read;
        this.time = time;
        this.tital = tital;
        this.summary = summary;
        this.postId = postId;
        this.commitUserId = commitUserId;
        this.userImage = userImage;
        this.contentImage = contentImage;
        this.userName = userName;
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

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCommitUserId() {
        return commitUserId;
    }

    public void setCommitUserId(String commitUserId) {
        this.commitUserId = commitUserId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<AttentionListEntry> getAttentionList() {
        return attentionList;
    }

    public void setAttentionList(ArrayList<AttentionListEntry> attentionList) {
        this.attentionList = attentionList;
    }
}
