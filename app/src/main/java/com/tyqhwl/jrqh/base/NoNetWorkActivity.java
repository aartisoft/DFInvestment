package com.tyqhwl.jrqh.base;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.tyqhwl.jrqh.LucencyStatusBar;
import com.tyqhwl.jrqh.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 网络链接失败页面
 * wmy
 * 2019-03-15
 * */
public class NoNetWorkActivity extends RxAppCompatActivity {

    @BindView(R.id.login_back)
    LinearLayout loginBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_network_activity);
        //状态栏透明
        LucencyStatusBar.getLucencyStatusBar(false, this);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.login_back)
    public void onViewClicked() {
        finish();
//        EventBus.getDefault().post(EventBusTag.ABNORMAL_EXIT);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnentThreadMain(Object object) {
        if (object.equals(EventBusTag.NETWORK_UNOBSTRUCTED)) {
            NoNetWorkActivity.this.finish();
        }
    }

}
