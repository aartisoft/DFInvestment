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
import android.widget.TextView;
import android.widget.Toast;

import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.activity.CourseDetailActivity;
import com.tyqhwl.jrqh.homepage.adapter.HomePageAdapter;
import com.tyqhwl.jrqh.homepage.adapter.HomePageSecondAdapter;
import com.tyqhwl.jrqh.homepage.presenter.HomePgaePresenter;
import com.tyqhwl.jrqh.homepage.view.HomePageEntry;
import com.tyqhwl.jrqh.homepage.view.HomePageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的课程页面
 * wmy
 * 2019-04-22
 */
public class MyBooksActivity extends BaseActivity implements HomePageView {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.my_books_activity_recyclerview)
    RecyclerView myBooksActivityRecyclerview;
    @BindView(R.id.my_books_activity_swipe_refresh)
    SwipeRefreshLayout myBooksActivitySwipeRefresh;
    @BindView(R.id.recycler_enter_image)
    ImageView recyclerEnterImage;
    @BindView(R.id.recycler_enter_title)
    TextView recyclerEnterTitle;
    @BindView(R.id.recycler_enter_content)
    TextView recyclerEnterContent;
    @BindView(R.id.go_over_frag_recycler_view_layout)
    RelativeLayout goOverFragRecyclerViewLayout;

    private HomePgaePresenter homePgaePresenter = new HomePgaePresenter(this);
    private HomePageSecondAdapter homePageAdapterSecond;
    private ArrayList<HomePageEntry> dataSecond = new ArrayList<>();
    AwaitDialog awaitDialog;
    private boolean isSwipe = false;

    @Override
    public int getXMLLayout() {
        return R.layout.my_books_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initRecyclerHeadView();
        homePgaePresenter.getHomeRecyclerData();

    }


    private void initRecyclerHeadView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        homePageAdapterSecond = new HomePageSecondAdapter(dataSecond, this, new HomePageAdapter.HomePageLinsener() {
            @Override
            public void onClick(int index) {
                //item点击事件
                IntentSkip.startIntent(MyBooksActivity.this , new CourseDetailActivity() , dataSecond.get(index));
            }
        });
        myBooksActivityRecyclerview.setLayoutManager(linearLayoutManager);
        myBooksActivityRecyclerview.setAdapter(homePageAdapterSecond);


        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        myBooksActivitySwipeRefresh.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        myBooksActivitySwipeRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        myBooksActivitySwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);

        myBooksActivitySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isSwipe) {
//                    ((BaseLinearLayout) linearLayoutManager).setScrollEnable(false);
//                    datas.clear();
                    isSwipe = true;
                    homePgaePresenter.getHomeRecyclerDataAwait();
                }
            }
        });


    }


    @OnClick({R.id.homepage_frag_sign_in, R.id.back, R.id.my_books_activity_recyclerview,
            R.id.my_books_activity_swipe_refresh, R.id.recycler_enter_image, R.id.recycler_enter_title,
            R.id.recycler_enter_content, R.id.go_over_frag_recycler_view_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.recycler_enter_image:
                break;
            case R.id.recycler_enter_title:
                break;
            case R.id.recycler_enter_content:
                break;
            case R.id.go_over_frag_recycler_view_layout:
                break;
        }
    }

    @Override
    public void getHomePageDataSuccess(ArrayList<HomePageEntry> arrayList) {

        if (isSwipe) {
            Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
            myBooksActivitySwipeRefresh.setRefreshing(false);
            isSwipe = false;
        }

        ArrayList<HomePageEntry> headRecyclerList = new ArrayList<>();
        for (HomePageEntry homePageEntry : arrayList) {
            for (int i = 0; i < homePageEntry.getStudObjectId().size(); i++) {
                if (ApplicationStatic.getUserLoginState()
                        &&
                        ApplicationStatic.getUserMessage().getObjectId().equals(homePageEntry.getStudObjectId().get(i))) {
                    headRecyclerList.add(homePageEntry);
                }
            }
        }

        dataSecond.clear();
        if (headRecyclerList.size() >= 3){
            for (int i = 0 ; i < headRecyclerList.size() ; i++){
                dataSecond.add(headRecyclerList.get(i));
            }
        }else {
            for (int i = 0 ; i < headRecyclerList.size() ; i++){
                dataSecond.add(headRecyclerList.get(i));
            }
        }
        if (dataSecond.size() > 0) {
            goOverFragRecyclerViewLayout.setVisibility(View.GONE);
            homePageAdapterSecond.notifyDataSetChanged();
        } else {
            goOverFragRecyclerViewLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getHomePageDataFail(String msg) {
        if (isSwipe) {
            Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
            myBooksActivitySwipeRefresh.setRefreshing(false);
            isSwipe = false;
        }
    }

    @Override
    public void getUserBooksDataSuccess(ArrayList<String> datas) {

    }

    @Override
    public void getUserBooksDataFail(String msg) {

    }

    @Override
    public void getSignInTodaySuccess(int count) {

    }

    @Override
    public void getSignInTodayFail(String msg) {

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
