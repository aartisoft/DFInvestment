package com.tyqhwl.jrqh.homepage.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.homepage.adapter.MyAttentionAdapter;
import com.tyqhwl.jrqh.homepage.presenter.MyAttentionPresenter;
import com.tyqhwl.jrqh.homepage.view.MyAttentionEntry;
import com.tyqhwl.jrqh.homepage.view.MyAttentionView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 我的关注页面
 * wmy
 * 2019-05-24
 */
public class MyAttentionFragment extends BaseFragment implements MyAttentionView {
    @BindView(R.id.my_attention_frag_recyclerview)
    RecyclerView myAttentionFragRecyclerview;
    @BindView(R.id.my_attention_frag_swipe)
    SwipeRefreshLayout myAttentionFragSwipe;
    Unbinder unbinder;

    public static MyAttentionFragment newInstance() {

        Bundle args = new Bundle();

        MyAttentionFragment fragment = new MyAttentionFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public boolean isSwipe = false;
    private MyAttentionPresenter myAttentionPresenter = new MyAttentionPresenter(this);
    private MyAttentionAdapter myAttentionAdapter;
    private ArrayList<MyAttentionEntry> data = new ArrayList<>();
    @Override
    public int getXMLLayout() {
        return R.layout.my_attention_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        myAttentionPresenter.getMyAttentionData();
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myAttentionAdapter = new MyAttentionAdapter(getActivity() ,data);
        myAttentionFragRecyclerview.setLayoutManager(linearLayoutManager);
        myAttentionFragRecyclerview.setAdapter(myAttentionAdapter);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        myAttentionFragSwipe.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        myAttentionFragSwipe.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        myAttentionFragSwipe.setSize(SwipeRefreshLayout.DEFAULT);

        myAttentionFragSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isSwipe) {
                    isSwipe = true;
                    myAttentionPresenter.getMyAttentionData();
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
    public void getMyAttentionSuccess(ArrayList<MyAttentionEntry> attentionEntries) {
        if (isSwipe) {
            Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
            myAttentionFragSwipe.setRefreshing(false);
            isSwipe = false;
        }
        data.clear();
        data.addAll(attentionEntries);
        myAttentionAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMyAttentionFail(String msg) {
        if (isSwipe) {
            myAttentionFragSwipe.setRefreshing(false);
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
