package com.tyqhwl.jrqh.user.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.homepage.view.BookEntry;
import com.tyqhwl.jrqh.user.adapter.LookCatagoryAdapte;
import com.tyqhwl.jrqh.user.presenter.MyBookRackPresneter;
import com.tyqhwl.jrqh.user.view.MyBookRackView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyBookRackFragmentSecond extends BaseFragment  implements MyBookRackView {
    @BindView(R.id.entry_data_image)
    ImageView entryDataImage;
    @BindView(R.id.look_catagory_entry)
    RelativeLayout lookCatagoryEntry;
    @BindView(R.id.look_catagory_act_recyclerview)
    RecyclerView lookCatagoryActRecyclerview;
    @BindView(R.id.look_catagory_act_swipe)
    SwipeRefreshLayout lookCatagoryActSwipe;
    Unbinder unbinder;
    private boolean isSwipe = false;
    private MyBookRackPresneter lookCatagoryPresneter = new MyBookRackPresneter(this);
    private ArrayList<BookEntry> data = new ArrayList<>();
    private LookCatagoryAdapte lookCatagoryAdapte;


    public static MyBookRackFragmentSecond newInstance() {
        
        Bundle args = new Bundle();
        
        MyBookRackFragmentSecond fragment = new MyBookRackFragmentSecond();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getXMLLayout() {
        return R.layout.my_book_rack_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        lookCatagoryPresneter.getLookCatagoryData();
        return rootView;
    }



    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lookCatagoryAdapte = new LookCatagoryAdapte(data, getActivity(), getActivity());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void geMyBookRackSuccess(ArrayList<BookEntry> arrayList) {
        if (isSwipe) {
            Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
            lookCatagoryActSwipe.setRefreshing(false);
            isSwipe = false;
        }
    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }
}
