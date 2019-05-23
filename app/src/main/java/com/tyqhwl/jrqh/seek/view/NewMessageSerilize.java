package com.tyqhwl.jrqh.seek.view;

import java.io.Serializable;

public class NewMessageSerilize implements Serializable {

    private String tital;
    private String text;

    public NewMessageSerilize(String tital, String text) {
        this.tital = tital;
        this.text = text;
    }


    public String getTital() {
        return tital;
    }

    public void setTital(String tital) {
        this.tital = tital;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
