package com.tyqhwl.jrqh.homepage.view;

import com.tyqhwl.jrqh.base.BaseView;

import java.util.ArrayList;

public interface SignInView extends BaseView {
    void getSignInViewSuccess(ArrayList<SignInEntry> arrayList);
    void getSignInViewFail(String msg);

    void signInSuccess();
    void signInFail(String msg);
}
