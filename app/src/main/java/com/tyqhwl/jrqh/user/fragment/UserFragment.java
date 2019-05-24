package com.tyqhwl.jrqh.user.fragment;

import android.os.Bundle;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;

/**
 * 用户中心
 * wmy
 * 2019-05-24
 * */
public class UserFragment extends BaseFragment {

    public static UserFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getXMLLayout() {
        return R.layout.use_fragment;
    }

    @Override
    public void initView() {

    }
}
