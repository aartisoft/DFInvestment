package com.tyqhwl.jrqh.user.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.user.adapter.LookCatagoryAdapte;
import com.tyqhwl.jrqh.user.presenter.LookCatagoryPresneter;
import com.tyqhwl.jrqh.user.view.LookCatagoryView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的看单
 * wmy
 * 2019-05-22
 */
public class LookCatagoryActivity extends BaseActivity implements LookCatagoryView {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.look_catagory_act_recyclerview)
    RecyclerView lookCatagoryActRecyclerview;
    @BindView(R.id.look_catagory_act_swipe)
    SwipeRefreshLayout lookCatagoryActSwipe;
    @BindView(R.id.entry_data_image)
    ImageView entryDataImage;
    @BindView(R.id.look_catagory_entry)
    RelativeLayout lookCatagoryEntry;

    private boolean isSwipe = false;
    private LookCatagoryPresneter lookCatagoryPresneter = new LookCatagoryPresneter(this);
    private ArrayList<BookEntry> data = new ArrayList<>();
    private LookCatagoryAdapte lookCatagoryAdapte;

    @Override
    public int getXMLLayout() {
        return R.layout.look_catagory_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initRecyclerView();
        lookCatagoryPresneter.getLookCatagoryData();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lookCatagoryAdapte = new LookCatagoryAdapte(data, this, this);
        lookCatagoryActRecyclerview.setLayoutManager(linearLayoutManager);
        lookCatagoryActRecyclerview.setAdapter(lookCatagoryAdapte);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        lookCatagoryActSwipe.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        lookCatagoryActSwipe.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        lookCatagoryActSwipe.setSize(SwipeRefreshLayout.DEFAULT);

        lookCatagoryActSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isSwipe) {
                    isSwipe = true;
                    lookCatagoryPresneter.getLookCatagoryData();
                }
            }
        });

    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void getLookCatagorySuccess(ArrayList<BookEntry> arrayList) {
        if (isSwipe) {
            Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
            lookCatagoryActSwipe.setRefreshing(false);
            isSwipe = false;
        }
        data.clear();
        data.addAll(arrayList);
        lookCatagoryAdapte.notifyDataSetChanged();
        if (arrayList.size() > 0){
            lookCatagoryEntry.setVisibility(View.INVISIBLE);
        }else {
            lookCatagoryEntry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getLookCatagoryFail(String msg) {
        if (isSwipe) {
//            Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
            lookCatagoryActSwipe.setRefreshing(false);
            isSwipe = false;
        }
        lookCatagoryEntry.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }
}
