package com.tyqhwl.jrqh.user.view;

import com.tyqhwl.jrqh.base.BaseView;
import com.tyqhwl.jrqh.homepage.view.BookEntry;

import java.util.ArrayList;

public interface LookCatagoryView extends BaseView {
    void getLookCatagorySuccess( ArrayList<BookEntry> arrayList);
    void getLookCatagoryFail(String msg);
}
