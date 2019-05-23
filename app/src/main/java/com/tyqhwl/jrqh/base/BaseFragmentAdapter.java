package com.tyqhwl.jrqh.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 用于显示所有fragment页面，静态数据较多，fragment页面数量不多，不多频繁进行数据交互
 * wmy
 * 2019-03-06
 * */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }



    

}
