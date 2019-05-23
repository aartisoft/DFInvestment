package com.tyqhwl.jrqh.goover.view;

import java.io.Serializable;

public class UserCollectEntry implements Serializable {

    public String read;
    public String content;
    public String time;
    public String title;
    public String imageUrl;
    public int postId;
    public String userId;

    public UserCollectEntry(String read, String content, String time, String title, String imageUrl, int postId, String userId) {
        this.read = read;
        this.content = content;
        this.time = time;
        this.title = title;
        this.imageUrl = imageUrl;
        this.postId = postId;
        this.userId = userId;
    }
}
