package com.tyqhwl.jrqh.seek.view;


public interface SaveNewMessageView extends com.tyqhwl.jrqh.base.BaseView {
    void getSaveNewMessageSuccess();
    void getSaveNewMessageFail(String msg);

    void cancelSaveSuccess();
    void cancelSaveFail(String msg);
}
