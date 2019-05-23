package com.tyqhwl.jrqh.user.fragment;

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
import com.tyqhwl.jrqh.base.BaseLinearLayout;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.user.activity.NoticeDetailActivity;
import com.tyqhwl.jrqh.user.adapter.NoticeAdapter;
import com.tyqhwl.jrqh.user.presenter.NoticePresenter;
import com.tyqhwl.jrqh.user.view.NoticeEntrys;
import com.tyqhwl.jrqh.user.view.NoticeView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 消息页面
 * wmy
 * 2019-05-17
 */
public class MessageFragment extends BaseFragment implements NoticeView {
    @BindView(R.id.message_fragment_recyclerview)
    RecyclerView messageFragmentRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.notice_Act_swipe_refresh_layout)
    SwipeRefreshLayout noticeActSwipeRefreshLayout;
    private NoticePresenter noticePresenter = new NoticePresenter(this);
    private NoticeAdapter noticeAdapter;
    private ArrayList<NoticeEntrys> data = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private boolean isRefresh = false;


    public static MessageFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getXMLLayout() {
        return R.layout.message_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        noticePresenter.getNoticeDate();
        initRecycler();
        return rootView;
    }

    private void initRecycler() {
        linearLayoutManager = new BaseLinearLayout(getActivity());
        noticeAdapter = new NoticeAdapter(getActivity(), data, new NoticeAdapter.NoticeListener() {
            @Override
            public void onClick(int index) {
                //item点击事件
                IntentSkip.startIntent(getActivity() , new NoticeDetailActivity() , data.get(index));
            }
        });
        messageFragmentRecyclerview.setLayoutManager(linearLayoutManager);
        messageFragmentRecyclerview.setAdapter(noticeAdapter);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        noticeActSwipeRefreshLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        noticeActSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        noticeActSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        noticeActSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefresh) {
                    ((BaseLinearLayout) linearLayoutManager).setScrollEnable(false);
//                    datas.clear();
                    isRefresh = true;
                    noticePresenter.getNoticeDate();
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
    public void getNoticeSuccess(ArrayList<NoticeEntrys> arrayList) {
        if (isRefresh) {
            ((BaseLinearLayout) linearLayoutManager).setScrollEnable(true);
            Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
            noticeActSwipeRefreshLayout.setRefreshing(false);
            isRefresh = false;
            data.clear();
        }
        data.addAll(arrayList);
        noticeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNoticeFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        if (isRefresh) {
            ((BaseLinearLayout) linearLayoutManager).setScrollEnable(true);
//            Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
            noticeActSwipeRefreshLayout.setRefreshing(false);
            isRefresh = false;
        }
    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }
}
