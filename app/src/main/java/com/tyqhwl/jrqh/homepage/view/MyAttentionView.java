package com.tyqhwl.jrqh.homepage.view;

import com.tyqhwl.jrqh.base.BaseView;

import java.util.ArrayList;

public interface MyAttentionView extends BaseView {
    void getMyAttentionSuccess(ArrayList<MyAttentionEntry> attentionEntries);
    void getMyAttentionFail(String msg);
}
