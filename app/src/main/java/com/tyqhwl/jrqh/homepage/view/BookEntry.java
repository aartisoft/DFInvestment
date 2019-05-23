package com.tyqhwl.jrqh.homepage.view;

import java.io.Serializable;

public class BookEntry implements Serializable {
    public String id;
    public String title;
    public String description;
    public String author;
    public String images;
    public String bid;
    public String type;
    public String click_number;
    public String create_time;


    public BookEntry(String id, String title, String description, String author,
                     String images, String bid, String type, String click_number, String create_time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.images = images;
        this.bid = bid;
        this.type = type;
        this.click_number = click_number;
        this.create_time = create_time;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClick_number() {
        return click_number;
    }

    public void setClick_number(String click_number) {
        this.click_number = click_number;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
