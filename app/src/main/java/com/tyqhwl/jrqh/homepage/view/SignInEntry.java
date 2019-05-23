package com.tyqhwl.jrqh.homepage.view;

import java.io.Serializable;
import java.util.ArrayList;

public class SignInEntry implements Serializable {
    public String userId;
    public ArrayList<String> signinList;

    public SignInEntry(String userId, ArrayList<String> signinList) {
        this.userId = userId;
        this.signinList = signinList;
    }
}
