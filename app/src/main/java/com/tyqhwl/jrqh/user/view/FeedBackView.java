package com.tyqhwl.jrqh.user.view;


import com.tyqhwl.jrqh.base.BaseView;

public interface FeedBackView extends BaseView {
    void getFeedBackSuccess();
    void getFeedBackFail(String msg);
}
