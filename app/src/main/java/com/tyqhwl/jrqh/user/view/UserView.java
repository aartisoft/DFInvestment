package com.tyqhwl.jrqh.user.view;


import com.tyqhwl.jrqh.base.BaseView;

public interface UserView extends BaseView {
    void saveUserMessageSuccess();
    void saveUserMessageFail(String msg);
}
