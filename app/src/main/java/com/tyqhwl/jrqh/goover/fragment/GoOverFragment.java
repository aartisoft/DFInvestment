package com.tyqhwl.jrqh.goover.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.exercise.actviity.ExerciseActivity;
import com.tyqhwl.jrqh.goover.activity.AllLessonsActivity;
import com.tyqhwl.jrqh.goover.activity.UserCollectActivity;
import com.tyqhwl.jrqh.homepage.activity.CourseDetailActivity;
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
 * 复习首页
 * wmy
 * 2019-04-11
 */
public class GoOverFragment extends BaseFragment implements HomePageView {


    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.sign_in)
    LinearLayout signIn;
    @BindView(R.id.go_over_frag_exercise_image)
    ImageView goOverFragExerciseImage;
    @BindView(R.id.go_over_frag_exercise_text)
    TextView goOverFragExerciseText;
    @BindView(R.id.go_over_frag_exercise_layout)
    LinearLayout goOverFragExerciseLayout;
    @BindView(R.id.go_over_frag_collect_image)
    ImageView goOverFragCollectImage;
    @BindView(R.id.go_over_frag_collect_text)
    TextView goOverFragCollectText;
    @BindView(R.id.go_over_frag_collect_layout)
    LinearLayout goOverFragCollectLayout;
    @BindView(R.id.go_over_frag_course_image)
    ImageView goOverFragCourseImage;
    @BindView(R.id.go_over_frag_course_text)
    TextView goOverFragCourseText;
    @BindView(R.id.go_over_frag_course_layout)
    LinearLayout goOverFragCourseLayout;
    @BindView(R.id.go_over_frag_recycler_view)
    RecyclerView goOverFragRecyclerView;
    Unbinder unbinder;
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
    @Override
    public boolean isEventOrBindInit() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o){
        if (o.equals(EventBusTag.LOGIN_SUCCESS)){
            homePgaePresenter.getHomeRecyclerData();
        }
        if (o.equals(EventBusTag.LOGIN_QUIT)){
            homePgaePresenter.getHomeRecyclerData();
        }
    }

    public static GoOverFragment newInstance() {

        Bundle args = new Bundle();

        GoOverFragment fragment = new GoOverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getXMLLayout() {
        return R.layout.go_over_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
//        if (!ApplicationStatic.getUserLoginState()) {
//            goOverFragRecyclerViewLayout.setVisibility(View.VISIBLE);
//        }

        initRecyclerHeadView();
        homePgaePresenter.getHomeRecyclerData();

        return rootView;
    }


    private void initRecyclerHeadView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        homePageAdapterSecond = new HomePageSecondAdapter(dataSecond, getActivity(), new HomePageAdapter.HomePageLinsener() {
            @Override
            public void onClick(int index) {
                //item点击事件
                IntentSkip.startIntent(getActivity() , new CourseDetailActivity() , dataSecond.get(index));
            }
        });
        goOverFragRecyclerView.setLayoutManager(linearLayoutManager);
        goOverFragRecyclerView.setAdapter(homePageAdapterSecond);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.homepage_frag_sign_in, R.id.sign_in, R.id.go_over_frag_exercise_image,
            R.id.go_over_frag_exercise_text, R.id.go_over_frag_exercise_layout, R.id.go_over_frag_collect_image,
            R.id.go_over_frag_collect_text, R.id.go_over_frag_collect_layout, R.id.go_over_frag_course_image,
            R.id.go_over_frag_course_text, R.id.go_over_frag_course_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                break;
            case R.id.sign_in:
                break;
            case R.id.go_over_frag_exercise_image:
                if (ApplicationStatic.getUserLoginState()) {
                    //跳转到做题页面
                    IntentSkip.startIntent(getActivity(), new ExerciseActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.go_over_frag_exercise_text:
                if (ApplicationStatic.getUserLoginState()) {
                    //跳转到做题页面
                    IntentSkip.startIntent(getActivity(), new ExerciseActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.go_over_frag_exercise_layout:
                if (ApplicationStatic.getUserLoginState()) {
                    //跳转到做题页面
                    IntentSkip.startIntent(getActivity(), new ExerciseActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.go_over_frag_collect_image:
                if (ApplicationStatic.getUserLoginState()) {
                    //跳转到收藏页面
                    IntentSkip.startIntent(getActivity(), new UserCollectActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.go_over_frag_collect_text:
                if (ApplicationStatic.getUserLoginState()) {
                    //跳转到收藏页面
                    IntentSkip.startIntent(getActivity(), new UserCollectActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.go_over_frag_collect_layout:
                if (ApplicationStatic.getUserLoginState()) {
                    //跳转到收藏页面
                    IntentSkip.startIntent(getActivity(), new UserCollectActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.go_over_frag_course_image:
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new AllLessonsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.go_over_frag_course_text:
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new AllLessonsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.go_over_frag_course_layout:
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new AllLessonsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
        }
    }

    @OnClick({R.id.recycler_enter_image, R.id.recycler_enter_title, R.id.recycler_enter_content, R.id.go_over_frag_recycler_view_layout})
    public void onViewClickeds(View view) {
        switch (view.getId()) {
            case R.id.recycler_enter_image:
                //学习课程
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new AllLessonsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.recycler_enter_title:
                //学习课程
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new AllLessonsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.recycler_enter_content:
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
            goOverFragRecyclerView.setVisibility(View.VISIBLE);
            homePageAdapterSecond.notifyDataSetChanged();
        } else {
            goOverFragRecyclerViewLayout.setVisibility(View.VISIBLE);
            goOverFragRecyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void getHomePageDataFail(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        goOverFragRecyclerViewLayout.setVisibility(View.VISIBLE);
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

    }

    @Override
    public void finishAwait() {

    }
}
