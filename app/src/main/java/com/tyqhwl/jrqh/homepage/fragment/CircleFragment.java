package com.tyqhwl.jrqh.homepage.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.BaseFragmentAdapter;
import com.tyqhwl.jrqh.information.fragment.InformationItemFragment;

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
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initViewPager();
        return rootView;
    }

    private void initViewPager() {
        arrayList.clear();
        arrayList.add(informationItemFragment = new InformationItemFragment());
        arrayList.add(myAttentionFragment = new MyAttentionFragment());
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager() , arrayList);
        circleFragViewpager.setAdapter(baseFragmentAdapter);
        circleFragViewpager.setCurrentItem(0);
        circleFragViewpager.setOffscreenPageLimit(arrayList.size());
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
                break;
            case R.id.attention_tital:
                break;
            case R.id.circle_frag_post_message:
                break;
        }
    }
}
