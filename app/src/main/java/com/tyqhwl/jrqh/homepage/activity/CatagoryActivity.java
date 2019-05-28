package com.tyqhwl.jrqh.homepage.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.BaseFragmentAdapter;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.fragment.CatagoryFragment;
import com.tyqhwl.jrqh.homepage.view.BookEntry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分类首页
 * wmy
 * 2019-05-22
 */
public class CatagoryActivity extends BaseActivity {
    @BindView(R.id.book_detail_act_back)
    ImageView bookDetailActBack;
    @BindView(R.id.optional_view)
    View optionalView;
    @BindView(R.id.optional_textview_layout)
    LinearLayout optionalTextviewLayout;
    @BindView(R.id.international_view)
    View internationalView;
    @BindView(R.id.international_layout)
    LinearLayout internationalLayout;
    @BindView(R.id.study_fragment_tital)
    LinearLayout studyFragmentTital;
    @BindView(R.id.catagory_act_viewpager)
    ViewPager catagoryActViewpager;

    private CatagoryFragment catagoryFragmentFirst;
    private CatagoryFragment catagoryFragmentSecond;
    private ArrayList<Fragment> data = new ArrayList<>();
    @Override
    public int getXMLLayout() {
        return R.layout.catagory_activity;
    }

    @Override
    public void initView() {

    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initIntent();
        getIntentExers();
    }

    private void getIntentExers() {
        if (getIntent() != null && getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD) != null){
            int i = (int) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
            catagoryActViewpager.setCurrentItem(i);
            showIndet(i);
        }
    }

    private void initIntent() {
        ArrayList<BookEntry> firstData = new ArrayList<>();
        ArrayList<BookEntry> secondData = new ArrayList<>();
        ArrayList<BookEntry> arrayList = ApplicationStatic.getBookAllData();
        for (int i = 0 ; i < arrayList.size() ; i++){
            if (i % 2 == 0){
                firstData.add(arrayList.get(i));
            } if (i % 2 == 1){
                secondData.add(arrayList.get(i));
            }
        }
        data.add(catagoryFragmentFirst = new CatagoryFragment(firstData));
        data.add(catagoryFragmentFirst = new CatagoryFragment(secondData));
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager() , data);
        catagoryActViewpager.setAdapter(baseFragmentAdapter);
        catagoryActViewpager.setCurrentItem(0);
        showIndet(0);
        catagoryActViewpager.setOffscreenPageLimit(data.size());
        catagoryActViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                showIndet(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    public void showIndet(int i){
        optionalView.setVisibility(View.INVISIBLE);
        internationalView.setVisibility(View.INVISIBLE);
        switch (i){
            case 0:
                optionalView.setVisibility(View.VISIBLE);
                break;
            case 1:
                internationalView.setVisibility(View.VISIBLE);
                break;
        }
    }


    @OnClick({R.id.book_detail_act_back, R.id.optional_textview_layout, R.id.international_layout, R.id.study_fragment_tital})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.book_detail_act_back:
                finish();
                break;
            case R.id.optional_textview_layout:
                catagoryActViewpager.setCurrentItem(0);
                showIndet(0);
                break;
            case R.id.international_layout:
                catagoryActViewpager.setCurrentItem(1);
                showIndet(1);
                break;
            case R.id.study_fragment_tital:
                break;
        }
    }
}
