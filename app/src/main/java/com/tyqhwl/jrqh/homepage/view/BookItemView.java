package com.tyqhwl.jrqh.homepage.view;

import com.tyqhwl.jrqh.base.BaseView;

import java.util.ArrayList;

public interface BookItemView extends BaseView {
    void getBookDataSuccess(ArrayList<BookEntry> arrayList);
    void getBookDataFail(String msg);

    void getBookDataSuccessFu(ArrayList<BookEntry> arrayList);
    void getBookDataFailFu(String msg);
}
