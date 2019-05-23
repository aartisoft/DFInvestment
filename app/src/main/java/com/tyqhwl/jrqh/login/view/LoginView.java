package com.tyqhwl.jrqh.login.view;

import com.avos.avoscloud.AVUser;
import com.tyqhwl.jrqh.base.BaseView;

public interface LoginView extends BaseView {
    void loginSuccess(AVUser avUser);
    void loginFail(String msg);
    void registerSuccess();
    void registerFail(String msg);
    void findPasswordSuccess();
    void findPasswordFail(String msg);

}
