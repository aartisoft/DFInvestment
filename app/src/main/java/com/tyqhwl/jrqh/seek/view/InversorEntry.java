package com.tyqhwl.jrqh.seek.view;

import java.io.Serializable;

public class InversorEntry implements Serializable {

    public String title;
    public String summary;
    public String thumb;
    public int post_id;
    public String time;
    public String read;
    public boolean isCheck;

    public InversorEntry(String title, String summary, String thumb, int post_id, String time) {
        this.title = title;
        this.summary = summary;
        this.thumb = thumb;
        this.post_id = post_id;
        this.time = time;
    }


    public InversorEntry(String title, String summary, String thumb, int post_id, String time, String read) {
        this.title = title;
        this.summary = summary;
        this.thumb = thumb;
        this.post_id = post_id;
        this.time = time;
        this.read = read;
    }


    public InversorEntry(String title, String summary, String thumb, int post_id, String time, String read, boolean isCheck) {
        this.title = title;
        this.summary = summary;
        this.thumb = thumb;
        this.post_id = post_id;
        this.time = time;
        this.read = read;
        this.isCheck = isCheck;
    }


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }


    @Override
    public String toString() {
        return "InversorEntry{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", thumb='" + thumb + '\'' +
                ", post_id=" + post_id +
                ", time='" + time + '\'' +
                ", read='" + read + '\'' +
                '}';
    }
}
