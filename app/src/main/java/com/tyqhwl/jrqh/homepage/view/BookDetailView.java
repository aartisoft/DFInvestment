package com.tyqhwl.jrqh.homepage.view;

import com.tyqhwl.jrqh.base.BaseView;

import java.util.ArrayList;

public interface BookDetailView extends BaseView {
    void getBookDetailSectionSec(ArrayList<BookSection> arrayList);
    void getBookDetailSectionFail(String msg);

    void getBookSectionContentSec(BookSecTionContentEntry bookSecTionContentEntry);
    void getBookSectionContentFail(String msg);
}
