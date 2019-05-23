package com.tyqhwl.jrqh;

import java.io.Serializable;

public class ViewPointCountEntry implements Serializable {
    private String approve = "10";
    private String oppose = "12";

    public ViewPointCountEntry(String approve, String oppose) {
        this.approve = approve;
        this.oppose = oppose;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getOppose() {
        return oppose;
    }

    public void setOppose(String oppose) {
        this.oppose = oppose;
    }
}
