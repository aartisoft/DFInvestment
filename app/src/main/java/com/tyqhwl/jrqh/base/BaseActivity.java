package com.tyqhwl.jrqh.base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tyqhwl.jrqh.LucencyStatusBar;
import com.tyqhwl.jrqh.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends RxAppCompatActivity implements ActivityFragmentView {

    private Context context;
    private LayoutInflater inflater;
    private boolean isEvent;
    private EventBus eventBus;
    private View myView;
    //默认关闭状态  需要打开，必须要加入通过@Subscribe修饰的方法

    private NetworkConnectChangedReceiver networkChangedReceiver;


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isEventOrBindInit()) {
            EventBus.getDefault().register(this);
        }

        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }

        //状态栏透明
        LucencyStatusBar.getLucencyStatusBar(false, this);
        myView = LayoutInflater.from(getApplicationContext()).inflate(getXMLLayout(), null);


        setContentView(myView);
        initView();

        networkChangedReceiver = new NetworkConnectChangedReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangedReceiver, intentFilter);
//        setStatusBarUpperAPI19();

    }



    private void setStatusBarUpperAPI19() {
        Window window = getWindow();
        //设置悬浮透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        int statusBarHeight = getStatusBarHeight();
        int statusColor = getResources().getColor(R.color.color_ACACAC);

        View mTopView = mContentView.getChildAt(0);
        if (mTopView != null && mTopView.getLayoutParams() != null &&
                mTopView.getLayoutParams().height == statusBarHeight) {
            mTopView.setBackgroundColor(statusColor);
            return;
        }

        //制造一个和状态栏等尺寸的 View
        mTopView = new View(this);
        Log.e("show" , statusBarHeight + "");
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        mTopView.setBackgroundColor(statusColor);
        //将view添加到第一个位置
        mContentView.addView(mTopView, 0, lp);
    }



    private int getStatusBarHeight() {
        int result = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isEventOrBindInit()) {
            EventBus.getDefault().unregister(this);
        }
        unregisterReceiver(networkChangedReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object object) {
//        Toast.makeText(this, " 显示", Toast.LENGTH_SHORT).show();
        if (object.equals(EventBusTag.NO_NETWORK)) {
            Log.e("show", "我是启动");
            IntentSkip.startIntent(this, new NoNetWorkActivity(), null);
        }
    }

}
