package com.tyqhwl.jrqh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tyqhwl.jrqh.base.BaseFragmentAdapter;
import com.tyqhwl.jrqh.base.IntentSkip;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.com.commoncore.base.BaseActivity;

/**
 * 启动页面
 * wmy
 * 2019-03-11
 */
public class GuideActivity extends BaseActivity {


    @BindView(R.id.guide_act_viewpager)
    ViewPager guideActViewpager;
    @BindView(R.id.guide_act_button)
    TextView guideActButton;
    private ArrayList<Fragment> pageview;
    @Override
    public int getContentLayout() {
        return R.layout.guide_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
        //状态栏透明
        LucencyStatusBar.getLucencyStatusBar(false, this);
    }


    public void init(){

        //将view装入数组
        pageview =new ArrayList<>();
        pageview.add(new GuideFirstFragment());
        pageview.add(new GuideSecondFragment());
        pageview.add(new GuideThreadFragment());
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager() , pageview);
        //绑定适配器
        guideActViewpager.setAdapter(baseFragmentAdapter);
        guideActViewpager.setCurrentItem(0);
        guideActViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 2){
                    guideActButton.setVisibility(View.VISIBLE);
                }else {
                    guideActButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @OnClick({R.id.guide_act_viewpager, R.id.guide_act_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guide_act_button:
                IntentSkip.startIntent(this , new MainActivity() , null);
                GuideActivity.this.finish();
                break;
        }
    }
}
