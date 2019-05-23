package com.tyqhwl.jrqh.homepage.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.homepage.adapter.TopRecyclerAdapter;
import com.tyqhwl.jrqh.homepage.view.BookEntry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 榜单
 * wmy
 * 2019-05-22
 */
public class TopActivity extends BaseActivity {
    @BindView(R.id.book_detail_act_back)
    ImageView bookDetailActBack;
    @BindView(R.id.top_act_recyclerview)
    RecyclerView topActRecyclerview;


    private ArrayList<BookEntry> data = new ArrayList<BookEntry>();
    private TopRecyclerAdapter topRecyclerAdapter;
    @Override
    public int getXMLLayout() {
        return R.layout.top_activity;
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
        initData();
    }

    private void initData() {
        data.clear();
        data.addAll(ApplicationStatic.getBookAllData());
        topRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        topRecyclerAdapter = new TopRecyclerAdapter(data , this , this);
        topActRecyclerview.setLayoutManager(linearLayoutManager);
        topActRecyclerview.setAdapter(topRecyclerAdapter);
    }

    @OnClick(R.id.book_detail_act_back)
    public void onViewClicked() {
        finish();
    }
}
