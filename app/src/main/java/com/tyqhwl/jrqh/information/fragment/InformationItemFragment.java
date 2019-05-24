package com.tyqhwl.jrqh.information.fragment;

import android.annotation.SuppressLint;
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
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.BaseLinearLayout;
import com.tyqhwl.jrqh.base.EndlessRecyclerOnScrollListener;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.base.RecycleViewLoadWrapper;
import com.tyqhwl.jrqh.information.adapter.InversorAdapter;
import com.tyqhwl.jrqh.seek.activity.InversorDetailActivity;
import com.tyqhwl.jrqh.seek.presenter.InversorPresenter;
import com.tyqhwl.jrqh.seek.view.InversorDetailSer;
import com.tyqhwl.jrqh.seek.view.InversorEntry;
import com.tyqhwl.jrqh.seek.view.InversorView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 资讯页面显示
 * wmy
 * 2019-05-17
 */
public class InformationItemFragment extends BaseFragment implements InversorView {
    @BindView(R.id.information_item_recyclerview_second)
    RecyclerView informationItemRecyclerviewSecond;
    Unbinder unbinder;
    @BindView(R.id.information_item_recyclerview_swipe)
    SwipeRefreshLayout informationItemRecyclerviewSwipe;

    public static InformationItemFragment newInstance() {

        Bundle args = new Bundle();

        InformationItemFragment fragment = new InformationItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private InversorPresenter inversorPresenter = new InversorPresenter(this);
    private LinearLayoutManager linearLayoutManager;
    private InversorAdapter inversorAdapter;
    private RecycleViewLoadWrapper recycleViewLoadWrapper;
    private ArrayList<InversorEntry> arrayList = new ArrayList<>();
    private int page = 1;//分页加载
    private String tags = "sd";//显示标签分组
    private AwaitDialog awaitDialog;

    private boolean isSwipe = false;

    @SuppressLint("ValidFragment")
    public InformationItemFragment(String tags) {
        this.tags = tags;
    }

    public InformationItemFragment() {
    }

    @Override
    public int getXMLLayout() {
        return R.layout.information_item_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        inversorPresenter.getInversorDataSecond(tags, page);
        init();
        return rootView;
    }


    private void init() {
        linearLayoutManager = new BaseLinearLayout(getActivity());
        informationItemRecyclerviewSecond.setLayoutManager(linearLayoutManager);
        inversorAdapter = new InversorAdapter(getActivity(), arrayList, new InversorAdapter.InversorClickListener() {
            @Override
            public void onClickListerer(int index) {
                //item点击事件
                IntentSkip.startIntent(getActivity(), new InversorDetailActivity(), new InversorDetailSer(arrayList.get(index).post_id));
            }
        }, getActivity());
        recycleViewLoadWrapper = new RecycleViewLoadWrapper(inversorAdapter, new RecycleViewLoadWrapper.EntryClickListener() {
            @Override
            public void Click() {
            }
        });
        informationItemRecyclerviewSecond.setAdapter(recycleViewLoadWrapper);

        informationItemRecyclerviewSecond.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                page++;
                recycleViewLoadWrapper.setLoadState(recycleViewLoadWrapper.LOADING);
                inversorPresenter.getInversorData(tags, page);
            }

            @Override
            public void onPullDown(RecyclerView recyclerView, int dx, int dy) {
            }
        });

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        informationItemRecyclerviewSwipe.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        informationItemRecyclerviewSwipe.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        informationItemRecyclerviewSwipe.setSize(SwipeRefreshLayout.DEFAULT);

        informationItemRecyclerviewSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isSwipe) {
                    isSwipe = true;
                    page = 1;
                    inversorPresenter.getInversorDataSecond(tags, page);
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
    public void getInversorSuccess(ArrayList<InversorEntry> data) {

        if (isSwipe) {
            Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
            informationItemRecyclerviewSwipe.setRefreshing(false);
            isSwipe = false;
            arrayList.clear();
        }

        if (data.size() == 0) {
            recycleViewLoadWrapper.setLoadState(recycleViewLoadWrapper.LOADING_END);
            Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
        } else {
            arrayList.addAll(data);
            inversorAdapter.notifyDataSetChanged();
            recycleViewLoadWrapper.notifyDataSetChanged();
            recycleViewLoadWrapper.setLoadState(recycleViewLoadWrapper.LOADING_COMPLETE);
        }
    }

    @Override
    public void getInversorFail(String msg) {
        if (isSwipe) {
            Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
            informationItemRecyclerviewSwipe.setRefreshing(false);
            isSwipe = false;
        }
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAwait() {
        awaitDialog = new AwaitDialog(getActivity(), R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }
}
