package com.tyqhwl.jrqh.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 适用于显示多个fragment页面，系统会回收不在当前显示的fragment   可以节省更多内存
 * wmy
 * 2019-03-06
 * */
public class BaseStateFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list;

    public BaseStateFragmentAdapter(FragmentManager fm, List<Fragment> list) {
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
