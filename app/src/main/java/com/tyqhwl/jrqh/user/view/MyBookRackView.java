package com.tyqhwl.jrqh.user.view;

import com.tyqhwl.jrqh.base.BaseView;
import com.tyqhwl.jrqh.homepage.view.BookEntry;

import java.util.ArrayList;

public interface MyBookRackView extends BaseView {
    void geMyBookRackSuccess(ArrayList<BookEntry> arrayList );
    void geMyBookRackFail(String msg);
}
