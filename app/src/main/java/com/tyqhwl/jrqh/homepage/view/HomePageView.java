package com.tyqhwl.jrqh.homepage.view;

import com.tyqhwl.jrqh.base.BaseView;

import java.util.ArrayList;

/**
 *
 * */
public interface HomePageView extends BaseView {
    void getHomePageDataSuccess(ArrayList<HomePageEntry> arrayList);
    void getHomePageDataFail(String msg);

    void getUserBooksDataSuccess(ArrayList<String> datas);
    void getUserBooksDataFail(String msg);

    void getSignInTodaySuccess(int count);
    void getSignInTodayFail(String msg);
}
