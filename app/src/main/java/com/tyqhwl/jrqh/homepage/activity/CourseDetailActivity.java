package com.tyqhwl.jrqh.homepage.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.BaseTime;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.homepage.view.HomePageEntry;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 课程详情
 * wmy
 * 2019-04-12
 */
public class CourseDetailActivity extends BaseActivity {
    @BindView(R.id.course_detail_act_title)
    TextView courseDetailActTitle;
    @BindView(R.id.course_detail_act_context)
    TextView courseDetailActContext;

    private long startTime = 0;
    private long entTime = 0;

    @Override
    public int getXMLLayout() {
        return R.layout.course_detail_activty;
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
        //获取当前时间毫秒值
        startTime = System.currentTimeMillis();

    }


    @Override
    public void finish() {
        entTime = System.currentTimeMillis();
        SharedPreferences sharedPreferences = getSharedPreferences("LearningTime" , Context.MODE_PRIVATE);
        int s = sharedPreferences.getInt("timeMins" , 0);
        int b = (int) (entTime - startTime);
        Log.e("show" , (entTime - startTime) + "&&&&&" + s);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("timeMins" , new Integer(BaseTime.msToM(b)) + s);
        editor.putString("time" , BaseTime.msToM(b));
        editor.putString("hour" , BaseTime.formatDuring(b , 2));
//        editor.putInt("timeMins" , 0);
//        editor.putString("time" , "0");
//        editor.putString("hour" , "0");
        editor.commit();
        EventBus.getDefault().post(EventBusTag.UPDATE_STATU_TIME);
        super.finish();
    }

    private void initIntent() {
        HomePageEntry homePageEntry = (HomePageEntry) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
        courseDetailActTitle.setText(homePageEntry.title);
        courseDetailActContext.setText(homePageEntry.context);
    }
}
