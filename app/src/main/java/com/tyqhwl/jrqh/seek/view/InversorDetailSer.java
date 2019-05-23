package com.tyqhwl.jrqh.seek.view;

import java.io.Serializable;

public class InversorDetailSer implements Serializable {
    private int postId;
    public InversorDetailSer(int post_id) {
        this.postId = post_id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
