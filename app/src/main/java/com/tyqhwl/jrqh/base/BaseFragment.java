package com.tyqhwl.jrqh.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseFragment extends RxFragment implements ActivityFragmentView {


    @Override
    public boolean isEventOrBindInit() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isEventOrBindInit()){
            EventBus.getDefault().register(this);
        }
        View view = inflater.inflate(getXMLLayout() , null);
        initView();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isEventOrBindInit()){
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Object event){

    }


}
