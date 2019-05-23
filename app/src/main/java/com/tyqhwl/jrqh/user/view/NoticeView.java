package com.tyqhwl.jrqh.user.view;

import com.tyqhwl.jrqh.base.BaseView;

import java.util.ArrayList;

public interface NoticeView extends BaseView {
    void getNoticeSuccess(ArrayList<NoticeEntrys> arrayList);
    void getNoticeFail(String msg);
}
