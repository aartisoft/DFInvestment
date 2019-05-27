package com.tyqhwl.jrqh.user.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.user.adapter.LookCatagoryAdapte;
import com.tyqhwl.jrqh.user.presenter.MyBookRackPresneter;
import com.tyqhwl.jrqh.user.view.MyBookRackView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的书架
 * wmy
 * 2019-05-27
 */
public class MyBookRackActivity extends BaseActivity implements MyBookRackView {
    @BindView(R.id.entry_data_image)
    ImageView entryDataImage;
    @BindView(R.id.look_catagory_entry)
    RelativeLayout lookCatagoryEntry;
    @BindView(R.id.look_catagory_act_recyclerview)
    RecyclerView lookCatagoryActRecyclerview;
    @BindView(R.id.look_catagory_act_swipe)
    SwipeRefreshLayout lookCatagoryActSwipe;
    @BindView(R.id.sigins_activity_back)
    ImageView siginsActivityBack;
    @BindView(R.id.signins_act_tital)
    RelativeLayout signinsActTital;
    private boolean isSwipe = false;
    private MyBookRackPresneter lookCatagoryPresneter = new MyBookRackPresneter(this);
    private ArrayList<BookEntry> data = new ArrayList<>();
    private LookCatagoryAdapte lookCatagoryAdapte;

    @Override
    public int getXMLLayout() {
        return R.layout.my_book_rack_activity;
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

    @OnClick({R.id.entry_data_image, R.id.look_catagory_entry,
            R.id.look_catagory_act_recyclerview, R.id.look_catagory_act_swipe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.entry_data_image:
                break;
            case R.id.look_catagory_entry:
                break;
            case R.id.look_catagory_act_recyclerview:
                break;
            case R.id.look_catagory_act_swipe:
                break;
        }
    }

    @Override
    public void geMyBookRackSuccess(ArrayList<BookEntry> arrayList) {
        if (isSwipe) {
            Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
            lookCatagoryActSwipe.setRefreshing(false);
            isSwipe = false;
        }
        data.clear();
        data.addAll(arrayList);
        lookCatagoryAdapte.notifyDataSetChanged();
        if (arrayList.size() > 0) {
            lookCatagoryEntry.setVisibility(View.INVISIBLE);
        } else {
            lookCatagoryEntry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void geMyBookRackFail(String msg) {
        if (isSwipe) {
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

    @OnClick(R.id.sigins_activity_back)
    public void onViewClicked() {
        finish();
    }
}
