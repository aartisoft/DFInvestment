package com.tyqhwl.jrqh.homepage.view;

public class BookSecTionContentEntry {
    public int id ;
    public String title;
    public String content;
    public String create_time;
    public int bid;
    public int chapter;


    public BookSecTionContentEntry(int id, String title, String content, String create_time, int bid, int chapter) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
        this.bid = bid;
        this.chapter = chapter;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }
}
