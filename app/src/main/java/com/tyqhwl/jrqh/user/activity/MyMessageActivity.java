package com.tyqhwl.jrqh.user.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.BaseFragmentAdapter;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.user.fragment.MessageFragment;
import com.tyqhwl.jrqh.user.fragment.MyBookRackFragmentSecond;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的书架or我的消息
 */
public class MyMessageActivity extends BaseActivity {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.my_msg_first_view)
    View myMsgFirstView;
    @BindView(R.id.my_msg_first)
    LinearLayout myMsgFirst;
    @BindView(R.id.my_msg_second_view)
    View myMsgSecondView;
    @BindView(R.id.my_msg_second)
    LinearLayout myMsgSecond;
    @BindView(R.id.my_msg_viewpager)
    ViewPager myMsgViewpager;

    private MessageFragment messageFragment;
    private MyBookRackFragmentSecond myBookRackActivity;
    private ArrayList<Fragment> arrayList = new ArrayList<>();

    @Override
    public int getXMLLayout() {
        return R.layout.my_message_activtiy;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initViewPager();
        getIntentEcter();
    }

    private void getIntentEcter() {
        if (getIntent() != null){
            int i = getIntent().getIntExtra(IntentSkip.INTENT_BUILD , 0);
            myMsgViewpager.setCurrentItem(i);
            showIndex(i);
        }
    }

    private void initViewPager() {
        arrayList.clear();
        arrayList.add(messageFragment = MessageFragment.newInstance());
        arrayList.add(myBookRackActivity = MyBookRackFragmentSecond.newInstance());
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), arrayList);
        myMsgViewpager.setAdapter(baseFragmentAdapter);
        myMsgViewpager.setOffscreenPageLimit(arrayList.size());
        myMsgViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


    public void showIndex(int index) {
        myMsgFirstView.setVisibility(View.INVISIBLE);
        myMsgSecondView.setVisibility(View.INVISIBLE);
        switch (index) {
            case 0:
                myMsgFirstView.setVisibility(View.VISIBLE);
                break;
            case 1:
                myMsgSecondView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick({R.id.back, R.id.my_msg_first_view, R.id.my_msg_first, R.id.my_msg_second_view, R.id.my_msg_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.my_msg_first_view:
                break;
            case R.id.my_msg_first:
                showIndex(0);
                myMsgViewpager.setCurrentItem(0);
                break;
            case R.id.my_msg_second_view:
                break;
            case R.id.my_msg_second:
                showIndex(1);
                myMsgViewpager.setCurrentItem(1);
                break;
        }
    }
}
