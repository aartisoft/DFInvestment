package com.tyqhwl.jrqh.goover.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.goover.adapter.UserCollectAdapter;
import com.tyqhwl.jrqh.goover.presenter.UserCollectPresenter;
import com.tyqhwl.jrqh.goover.view.UserCollectEntry;
import com.tyqhwl.jrqh.goover.view.UserCollectView;
import com.tyqhwl.jrqh.seek.activity.InversorDetailActivity;
import com.tyqhwl.jrqh.seek.view.InversorDetailSer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 用户收藏页面
 * wmy
 * 2019-04-18
 */

public class UserCollectActivity extends BaseActivity implements UserCollectView {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.user_collect_activity_recyclerview)
    RecyclerView userCollectActivityRecyclerview;
    @BindView(R.id.user_collect_activity_swipe_refresh)
    SwipeRefreshLayout userCollectActivitySwipeRefresh;
    @BindView(R.id.go_over_frag_recycler_view_layout)
    RelativeLayout goOverFragRecyclerViewLayout;

    private AwaitDialog awaitDialog;
    private UserCollectPresenter userCollectPresenter = new UserCollectPresenter(this);
    private boolean isSwipe = false;
    private UserCollectAdapter userCollectAdapter;
    private ArrayList<UserCollectEntry> data = new ArrayList<>();

    @Override
    public int getXMLLayout() {
        return R.layout.user_collect_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        userCollectPresenter.getUserCollectData();
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userCollectAdapter = new UserCollectAdapter(this, data, new UserCollectAdapter.UserCollectListener() {
            @Override
            public void onClick(int index, int postId) {
                //item点击事件
                Log.e("show", postId + "sssss");
                IntentSkip.startIntent(UserCollectActivity.this, new InversorDetailActivity(), new InversorDetailSer(postId));
            }
        }, new UserCollectAdapter.CancelCollectListener() {
            @Override
            public void onCancelCollect(int postId) {
                //取消收藏
                userCollectPresenter.deleteuserNewMessage(postId);
            }
        });

        userCollectActivityRecyclerview.setLayoutManager(linearLayoutManager);
        userCollectActivityRecyclerview.setAdapter(userCollectAdapter);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        userCollectActivitySwipeRefresh.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        userCollectActivitySwipeRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        userCollectActivitySwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);

        userCollectActivitySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isSwipe) {
//                    ((BaseLinearLayout) linearLayoutManager).setScrollEnable(false);
//                    datas.clear();
                    isSwipe = true;
                    userCollectPresenter.getUserCollectData();
                }
            }
        });


    }

    @OnClick({R.id.homepage_frag_sign_in, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void getUserCollectSuccess(ArrayList<UserCollectEntry> arrayList) {
        if (arrayList.size() > 0) {
//            Log.e("show" , "显示");
            goOverFragRecyclerViewLayout.setVisibility(View.GONE);
            if (isSwipe) {
                Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
                userCollectActivitySwipeRefresh.setRefreshing(false);
                isSwipe = false;
            }
            data.clear();
            data.addAll(arrayList);
            userCollectAdapter.notifyDataSetChanged();
        } else {
//            Log.e("show" , "不显示");
            if (isSwipe) {
                Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
                userCollectActivitySwipeRefresh.setRefreshing(false);
                isSwipe = false;
            }
            data.clear();
            data.addAll(arrayList);
            userCollectAdapter.notifyDataSetChanged();
            goOverFragRecyclerViewLayout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void getUserCollectFail(String msg) {
        goOverFragRecyclerViewLayout.setGravity(View.VISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCancleCollectSuccess() {
        userCollectPresenter.getUserCollectData();
    }

    @Override
    public void getCancleCollectFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAwait() {
        awaitDialog = new AwaitDialog(this, R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }
}
