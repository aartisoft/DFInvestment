package com.tyqhwl.jrqh.goover.view;

import com.tyqhwl.jrqh.base.BaseView;

import java.util.ArrayList;

public interface UserCollectView extends BaseView {
    void  getUserCollectSuccess(ArrayList<UserCollectEntry> arrayList);
    void  getUserCollectFail(String msg);

    void getCancleCollectSuccess();
    void getCancleCollectFail(String msg);

}
