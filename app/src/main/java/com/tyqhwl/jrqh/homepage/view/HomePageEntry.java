package com.tyqhwl.jrqh.homepage.view;

import java.io.Serializable;
import java.util.ArrayList;

public class HomePageEntry implements Serializable {
    public String title;
    public String context;
    public String image;
    public int read;
    public ArrayList<String> studObjectId;

    public HomePageEntry(String title, String context, String image, int read, ArrayList<String> studObjectId) {
        this.title = title;
        this.context = context;
        this.image = image;
        this.read = read;
        this.studObjectId = studObjectId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public ArrayList<String> getStudObjectId() {
        return studObjectId;
    }

    public void setStudObjectId(ArrayList<String> studObjectId) {
        this.studObjectId = studObjectId;
    }
}
