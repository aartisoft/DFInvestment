package com.tyqhwl.jrqh.homepage.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.BaseTime;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.goover.activity.AllLessonsActivity;
import com.tyqhwl.jrqh.homepage.activity.CourseDetailActivity;
import com.tyqhwl.jrqh.homepage.activity.SignInActivity;
import com.tyqhwl.jrqh.homepage.adapter.HomePageAdapter;
import com.tyqhwl.jrqh.homepage.adapter.HomePageSecondAdapter;
import com.tyqhwl.jrqh.homepage.presenter.HomePgaePresenter;
import com.tyqhwl.jrqh.homepage.view.HomePageEntry;
import com.tyqhwl.jrqh.homepage.view.HomePageView;
import com.tyqhwl.jrqh.login.activity.LoginActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页fragment
 * wmy
 * 2019-04-11
 */
public class HomePageFragment extends BaseFragment implements HomePageView {

    Unbinder unbinder;
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.sign_in)
    LinearLayout signIn;
    @BindView(R.id.homepage_frag_server)
    ImageView homepageFragServer;
    @BindView(R.id.server)
    LinearLayout server;
    @BindView(R.id.homepage_frag_study_time)
    TextView homepageFragStudyTime;
    @BindView(R.id.homepage_frag_max_study_time)
    TextView homepageFragMaxStudyTime;
    @BindView(R.id.homepage_frag_sign_in_day)
    TextView homepageFragSignInDay;
    @BindView(R.id.homepage_frag_more)
    TextView homepageFragMore;
    @BindView(R.id.homepage_frag_study_recycler_view)
    RecyclerView homepageFragStudyRecyclerView;
    @BindView(R.id.homepage_frag_recommend_recycler_view)
    RecyclerView homepageFragRecommendRecyclerView;
    @BindView(R.id.hp_fragment_image)
    ImageView hpFragmentImage;
    @BindView(R.id.hp_fragment_text)
    TextView hpFragmentText;
    @BindView(R.id.hp_fragment_layout)
    RelativeLayout hpFragmentLayout;
    private AwaitDialog awaitDialog;

    @Override
    public boolean isEventOrBindInit() {
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o) {
        if (o.equals(EventBusTag.LOGIN_SUCCESS)) {
            homePgaePresenter.getHomeRecyclerData();
            initShowTime();
            homePgaePresenter.getSignIn();
        }

        if (o.equals(EventBusTag.UPDATE_STATU_TIME)) {
            initShowTime();
        }

        if (o.equals(EventBusTag.REGISTER_SUCCESS)) {
            initShowTime();
            homePgaePresenter.getSignIn();
        }

        if (o.equals(EventBusTag.LOGIN_QUIT)) {
            initShowTime();
            homepageFragSignInDay.setText(0 + "");
            homePgaePresenter.getHomeRecyclerData();
        }

        if (o.equals(EventBusTag.SIGN_IN_SUCCESS)) {
            homePgaePresenter.getSignIn();
        }

    }

    private HomePgaePresenter homePgaePresenter = new HomePgaePresenter(this);
    private ArrayList<HomePageEntry> data = new ArrayList<>();
    private HomePageAdapter homePageAdapter;
    private HomePageSecondAdapter homePageAdapterSecond;
    private ArrayList<HomePageEntry> dataSecond = new ArrayList<>();

    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getXMLLayout() {
        return R.layout.homepage_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        initRecyclerHeadView();
        awaitDialog = new AwaitDialog(getActivity(), R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        homePgaePresenter.getHomeRecyclerData();
        homePgaePresenter.getSignIn();
        if (!ApplicationStatic.getUserLoginState()) {
            hpFragmentLayout.setVisibility(View.VISIBLE);
        }
        initShowTime();

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void initShowTime() {
        if (ApplicationStatic.getUserLoginState()) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LearningTime", Context.MODE_PRIVATE);
//            sharedPreferences.getInt("timeMins", 0);
//            sharedPreferences.getString("time" , BaseTime.getTodayZero() + "");
//            sharedPreferences.getString("hour"  , "0.0");

            @SuppressLint("UseValueOf") Long today = new Long(BaseTime.getTimeStame());
            @SuppressLint("UseValueOf") Long oldDay = new Long(sharedPreferences.getString("time", BaseTime.getTodayZero() + ""));

            if (today >= oldDay) {
                homepageFragStudyTime.setText(sharedPreferences.getInt("timeMins", 0) + "");
            } else {
                homepageFragStudyTime.setText(0 + "");
            }
            homepageFragMaxStudyTime.setText(sharedPreferences.getString("hour", "0.0"));
        } else {
            homepageFragStudyTime.setText(0 + "");
            homepageFragMaxStudyTime.setText(0.0 + "");
        }

    }


    private void initRecyclerHeadView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homePageAdapterSecond = new HomePageSecondAdapter(dataSecond, getActivity(), new HomePageAdapter.HomePageLinsener() {
            @Override
            public void onClick(int index) {
                //item点击事件
                IntentSkip.startIntent(getActivity(), new CourseDetailActivity(), dataSecond.get(index));
            }
        });
        homepageFragStudyRecyclerView.setLayoutManager(linearLayoutManager);
        homepageFragStudyRecyclerView.setAdapter(homePageAdapterSecond);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(homepageFragStudyRecyclerView);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        homePageAdapter = new HomePageAdapter(data, getActivity(), new HomePageAdapter.HomePageLinsener() {
            @Override
            public void onClick(int index) {
                //item点击事件
                IntentSkip.startIntent(getActivity(), new CourseDetailActivity(), data.get(index));
            }
        });
        homepageFragRecommendRecyclerView.setLayoutManager(linearLayoutManager);
        homepageFragRecommendRecyclerView.setAdapter(homePageAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.homepage_frag_sign_in, R.id.sign_in, R.id.homepage_frag_server,
            R.id.server, R.id.homepage_frag_study_time, R.id.homepage_frag_max_study_time,
            R.id.homepage_frag_sign_in_day, R.id.homepage_frag_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                //签到
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new SignInActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.sign_in:
                //签到
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new SignInActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.homepage_frag_server:
                break;
            case R.id.server:
                break;
            case R.id.homepage_frag_study_time:
                break;
            case R.id.homepage_frag_max_study_time:
                break;
            case R.id.homepage_frag_sign_in_day:
                break;
            case R.id.homepage_frag_more:
                //学习课程
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new AllLessonsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
        }
    }

    @Override
    public void getHomePageDataSuccess(ArrayList<HomePageEntry> arrayList) {
        data.clear();
        for (HomePageEntry homePageEntry : arrayList) {
            boolean isExist = false;
            for (int i = 0; i < homePageEntry.getStudObjectId().size(); i++) {
                if (ApplicationStatic.getUserLoginState()
                        &&
                        ApplicationStatic.getUserMessage().getObjectId().equals(homePageEntry.getStudObjectId().get(i))) {
//                    headRecyclerList.add(homePageEntry);
                    isExist = true;
                    Log.e("show", i + "");
                }
            }
            if (!isExist) {
                data.add(homePageEntry);
            }
        }

//        data.addAll(arrayList);
        homePageAdapter.notifyDataSetChanged();


        recyclerHeadUpdate(arrayList);


    }

    //头部数据刷新
    private void recyclerHeadUpdate(ArrayList<HomePageEntry> arrayList) {
        dataSecond.clear();
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

        if (headRecyclerList.size() >= 3) {
            for (int i = 0; i < 3; i++) {
                dataSecond.add(headRecyclerList.get(i));
            }
        } else {
            for (int i = 0; i < headRecyclerList.size(); i++) {
                dataSecond.add(headRecyclerList.get(i));
            }
        }
        if (dataSecond.size() > 0) {
            homepageFragStudyRecyclerView.setVisibility(View.VISIBLE);
            hpFragmentLayout.setVisibility(View.GONE);
            homePageAdapterSecond.notifyDataSetChanged();
        } else {
            hpFragmentLayout.setVisibility(View.VISIBLE);
            homepageFragStudyRecyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void getHomePageDataFail(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        hpFragmentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void getUserBooksDataSuccess(ArrayList<String> datas) {

    }

    @Override
    public void getUserBooksDataFail(String msg) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getSignInTodaySuccess(int count) {
        homepageFragSignInDay.setText(count + "");
    }

    @Override
    public void getSignInTodayFail(String msg) {

    }

    @Override
    public void showAwait() {

        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }

    @OnClick({R.id.hp_fragment_image, R.id.hp_fragment_text, R.id.hp_fragment_layout})
    public void onViewClickeds(View view) {
        switch (view.getId()) {
            case R.id.hp_fragment_image:
                //学习课程
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new AllLessonsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.hp_fragment_text:
                //学习课程
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new AllLessonsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.hp_fragment_layout:
                //学习课程
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new AllLessonsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
        }
    }
}
