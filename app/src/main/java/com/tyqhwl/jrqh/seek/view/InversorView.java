package com.tyqhwl.jrqh.seek.view;

import com.tyqhwl.jrqh.base.BaseView;

import java.util.ArrayList;


public interface InversorView extends BaseView {
    void getInversorSuccess(ArrayList<InversorEntry> data);
    void getInversorFail(String msg);
}
