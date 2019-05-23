package com.tyqhwl.jrqh.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 进行网络监听
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        int netWorkStates = NetworkUtil.getNetWorkStates(context);
        switch (netWorkStates) {
            case NetworkUtil.TYPE_NONE:
//                Log.e("show"  , "断网了");
                //断网了
                if (count <= 0){
                    count++;
                    EventBus.getDefault().post(EventBusTag.NO_NETWORK);
//                    Log.e("show"  , "执行");
                }
                break;
            case NetworkUtil.TYPE_MOBILE:
//                Log.e("show"  , "移动");
                //打开了移动网络
                EventBus.getDefault().post(EventBusTag.NETWORK_UNOBSTRUCTED);
                count = 0;
                break;
            case NetworkUtil.TYPE_WIFI:
//                Log.e("show"  , "无线");
                //打开了WIFI
                EventBus.getDefault().post(EventBusTag.NETWORK_UNOBSTRUCTED);
                count = 0;
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvnetThreadMain(Object o) {

    }

}
