package com.tyqhwl.jrqh.homepage.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.BaseFragmentAdapter;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.activity.PostMessageActivity;
import com.tyqhwl.jrqh.information.fragment.InformationItemFragment;
import com.tyqhwl.jrqh.login.activity.LoginActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 圈子
 * wmy
 * 2019-05-24
 */
public class CircleFragment extends BaseFragment {
    @BindView(R.id.circle_tital)
    TextView circleTital;
    @BindView(R.id.attention_tital)
    TextView attentionTital;
    @BindView(R.id.circle_frag_post_message)
    LinearLayout circleFragPostMessage;
    @BindView(R.id.circle_frag_viewpager)
    ViewPager circleFragViewpager;
    Unbinder unbinder;

    public static CircleFragment newInstance() {
        Bundle args = new Bundle();
        CircleFragment fragment = new CircleFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private InformationItemFragment informationItemFragment;
    private MyAttentionFragment myAttentionFragment;
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    @Override
    public int getXMLLayout() {
        return R.layout.circle_fragment;
    }

    @Override
    public void initView() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initViewPager();
        return rootView;
    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o) {

        if (o.equals(EventBusTag.MY_ATTENTION) || o.equals(EventBusTag.LAVE_A_MESSAGE_SUCCESS)) {
            circleFragViewpager.setCurrentItem(1);
            showIndex(1);
        }
    }



    private void initViewPager() {
        arrayList.clear();
        arrayList.add(informationItemFragment = new InformationItemFragment());
        arrayList.add(myAttentionFragment = new MyAttentionFragment());
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager() , arrayList);
        circleFragViewpager.setAdapter(baseFragmentAdapter);
        circleFragViewpager.setCurrentItem(0);
        showIndex(0);
        circleFragViewpager.setOffscreenPageLimit(arrayList.size());
        circleFragViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                showIndex(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    public void showIndex(int index){
        circleTital.setTextColor(Color.parseColor("#99FFFFFF"));
        attentionTital.setTextColor(Color.parseColor("#99FFFFFF"));
        switch (index){
            case 0:
                circleTital.setTextColor(Color.WHITE);

                break;
            case 1:
                attentionTital.setTextColor(Color.WHITE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.circle_tital, R.id.attention_tital, R.id.circle_frag_post_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_tital:
                circleFragViewpager.setCurrentItem(0);
                showIndex(0);
                break;
            case R.id.attention_tital:
                circleFragViewpager.setCurrentItem(1);
                showIndex(1);
                break;
            case R.id.circle_frag_post_message:
                //发帖
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(getActivity() , new PostMessageActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
        }
    }
}
