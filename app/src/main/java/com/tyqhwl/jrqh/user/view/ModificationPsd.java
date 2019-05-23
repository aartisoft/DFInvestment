package com.tyqhwl.jrqh.user.view;


import com.tyqhwl.jrqh.base.BaseView;

public interface ModificationPsd extends BaseView {
    void updateUserPsdSuccess();
    void updateUserPsdFail(String msg);
}
