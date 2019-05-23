package com.tyqhwl.jrqh.goover.activity;

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

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.activity.CourseDetailActivity;
import com.tyqhwl.jrqh.homepage.adapter.HomePageAdapter;
import com.tyqhwl.jrqh.homepage.presenter.HomePgaePresenter;
import com.tyqhwl.jrqh.homepage.view.HomePageEntry;
import com.tyqhwl.jrqh.homepage.view.HomePageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 所有课程or我的课程
 */
public class AllLessonsActivity extends BaseActivity  implements HomePageView {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.all_lessons_act_title)
    TextView allLessonsActTitle;
    @BindView(R.id.all_lessons_act_swipe_refresh_reyclerview)
    RecyclerView allLessonsActSwipeRefreshReyclerview;
    @BindView(R.id.all_lessons_act_swipe_refresh)
    SwipeRefreshLayout allLessonsActSwipeRefresh;
    @BindView(R.id.user_collect_act_image)
    ImageView userCollectActImage;
    @BindView(R.id.go_over_frag_recycler_view_layout)
    RelativeLayout goOverFragRecyclerViewLayout;

    private boolean isSwipe = false;
    private HomePgaePresenter homePgaePresenter = new HomePgaePresenter(this);
    private ArrayList<HomePageEntry> data = new ArrayList<>();
    private HomePageAdapter homePageAdapter;
    private AwaitDialog awaitDialog;
    @Override
    public int getXMLLayout() {
        return R.layout.all_lessons_activity;
    }

    @Override
    public void initView() {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        homePgaePresenter.getHomeRecyclerData();
        initRecyclerView();
    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        homePageAdapter = new HomePageAdapter(data,this, new HomePageAdapter.HomePageLinsener() {
            @Override
            public void onClick(int index) {
                //item点击事件
                IntentSkip.startIntent(AllLessonsActivity.this , new CourseDetailActivity() , data.get(index));
            }
        });
        allLessonsActSwipeRefreshReyclerview.setLayoutManager(linearLayoutManager);
        allLessonsActSwipeRefreshReyclerview.setAdapter(homePageAdapter);



        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        allLessonsActSwipeRefresh.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        allLessonsActSwipeRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        allLessonsActSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);

        allLessonsActSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

    @OnClick({R.id.homepage_frag_sign_in, R.id.back, R.id.all_lessons_act_title,
            R.id.all_lessons_act_swipe_refresh_reyclerview, R.id.all_lessons_act_swipe_refresh,
            R.id.user_collect_act_image, R.id.go_over_frag_recycler_view_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.all_lessons_act_title:
                break;
            case R.id.all_lessons_act_swipe_refresh_reyclerview:
                break;
            case R.id.all_lessons_act_swipe_refresh:
                break;
            case R.id.user_collect_act_image:
                break;
            case R.id.go_over_frag_recycler_view_layout:
                break;
        }
    }

    @Override
    public void getHomePageDataSuccess(ArrayList<HomePageEntry> arrayList) {
        if (arrayList.size() > 0){
            goOverFragRecyclerViewLayout.setVisibility(View.GONE);
            if (isSwipe) {
                Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
                allLessonsActSwipeRefresh.setRefreshing(false);
                isSwipe = false;
            }
            data.clear();
        }else {
            if (isSwipe) {
                Toast.makeText(this, "刷新完成", Toast.LENGTH_SHORT).show();
                allLessonsActSwipeRefresh.setRefreshing(false);
                isSwipe = false;
            }
            data.clear();
            goOverFragRecyclerViewLayout.setVisibility(View.VISIBLE);
        }

        data.addAll(arrayList);
        homePageAdapter.notifyDataSetChanged();
    }

    @Override
    public void getHomePageDataFail(String msg) {
        if (isSwipe) {
            allLessonsActSwipeRefresh.setRefreshing(false);
            isSwipe = false;
        }
        goOverFragRecyclerViewLayout.setVisibility(View.VISIBLE);
        Toast.makeText(this , msg , Toast.LENGTH_SHORT).show();
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
