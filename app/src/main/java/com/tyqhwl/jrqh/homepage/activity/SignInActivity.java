package com.tyqhwl.jrqh.homepage.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.BaseTime;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.homepage.presenter.SignInPresenter;
import com.tyqhwl.jrqh.homepage.view.SignInEntry;
import com.tyqhwl.jrqh.homepage.view.SignInView;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 签到页面
 */

public class SignInActivity extends BaseActivity implements SignInView {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.sign_in_act_duration)
    TextView signInActDuration;
    @BindView(R.id.sign_in_act_max_duration)
    TextView signInActMaxDuration;
    @BindView(R.id.sign_in_act_day_count)
    TextView signInActDayCount;
    @BindView(R.id.sign_in_button)
    TextView signInButton;

    private int showStart = 0;//  0:已签到  1：未签到
    private Date calendarViewDate;
    private SignInPresenter signInPresenter = new SignInPresenter(this);
    private ArrayList<SignInEntry> datas = new ArrayList<>();
    private ArrayList<String> data = new ArrayList<>();

    @Override
    public int getXMLLayout() {
        return R.layout.sign_in_activity;
    }

    @Override
    public void initView() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        signInPresenter.getSignInData();
        initDate();
        initShowTime();
    }

    private void initShowTime() {
        if (ApplicationStatic.getUserLoginState()) {
            SharedPreferences sharedPreferences = getSharedPreferences("LearningTime", Context.MODE_PRIVATE);
//            sharedPreferences.getInt("timeMins", 0);
//            sharedPreferences.getString("time" , BaseTime.getTodayZero() + "");
//            sharedPreferences.getString("hour"  , "0.0");

            Long today = new Long(BaseTime.getTimeStame());
            Long oldDay =  new Long(sharedPreferences.getString("time" , BaseTime.getTodayZero() + ""));

            if (today >= oldDay){
                signInActDuration.setText(sharedPreferences.getInt("timeMins", 0) + "");
            }else {
                signInActDuration.setText(0 + "");
            }
            signInActMaxDuration.setText(sharedPreferences.getString("hour"  , "0.0"));
        }else {
            signInActDuration.setText(0 + "");
            signInActMaxDuration.setText(0.0 + "");
        }

    }


    private void initDate() {


        calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(calendar.getYear() + "-").append(calendar.getMonth() + "-").append(calendar.getDay());
                calendarViewDate = parseServerTime(stringBuffer.toString());
//                Log.e("show" , date.getTime() + "");

                if (data.size() > 0) {
                    boolean isEntry = false;
//                    Log.e("show" , datas.get(0).signinList.size() + "");
                    for (int i = 0; i < datas.get(0).signinList.size(); i++) {
                        Date date = parseServerTime(datas.get(0).signinList.get(i));
                        if (date.equals(calendarViewDate)) {
                            isEntry = true;
                        } else {
                            if (isEntry) {
                                continue;
                            }
                        }
                    }

                    if (isEntry) {
                        signInButton.setText("已打卡");
                        signInButton.setBackgroundResource(R.drawable.sign_in_act_shape_second);
                        showStart = 1;

                    } else {
                        signInButton.setText("立即打卡");
                        signInButton.setBackgroundResource(R.drawable.sign_in_act_shape);
                        showStart = 0;
                    }

                }

            }
        });

    }

    @OnClick({R.id.homepage_frag_sign_in, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }


    @OnClick(R.id.sign_in_button)
    public void onViewClicked() {
        Log.e("show" ,BaseTime.getTodayZero() + "%%%%" +  BaseTime.getTodayEnd() + "$$$$$$" + calendarViewDate.getTime());
        //点击签到
        switch (showStart) {
            case 0:
                if (calendarViewDate.getTime() < BaseTime.getTodayZero()) {
                    Toast.makeText(this, "已超过签到时间", Toast.LENGTH_SHORT).show();
                    break;
                } else if (calendarViewDate.getTime() > BaseTime.getTodayEnd()) {
                    Toast.makeText(this, "未到签到时间", Toast.LENGTH_SHORT).show();
                    break;
                }
                //可以进行签到
                data.add(getDate());
                signInPresenter.saveSignin(data);
                break;
            case 1:
                //已签到
                Log.e("show", "sss");
                break;
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getSignInViewSuccess(ArrayList<SignInEntry> arrayList) {
        datas.clear();
        data.clear();
        if (arrayList.size() == 0) {
            //可以进行签到
            showStart = 0;
        } else {
            datas.addAll(arrayList);
            ApplicationStatic.setSignInNumberOfDays(arrayList.get(0).signinList.size());
            signInActDayCount.setText(arrayList.get(0).signinList.size() + "");
            for (int i = 0; i < arrayList.get(0).signinList.size(); i++) {
                data.add(arrayList.get(0).signinList.get(i));
                Date date = parseServerTime(arrayList.get(0).signinList.get(i));
                if (date.equals(calendarViewDate)) {
                    signInButton.setText("已打卡");
                    signInButton.setBackgroundResource(R.drawable.sign_in_act_shape_second);
                    showStart = 1;
                }
            }
        }

        Log.e("show", data.size() + "&&&&" + datas.size());
    }


    public Date parseServerTime(String serverTime) {
//        if (format == null || format.isEmpty()) {
//            format = "yyyy-MM-dd";
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {

        }
        return date;
    }





    public String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    @Override
    public void getSignInViewFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        signInButton.setText("已打卡");
        signInButton.setBackgroundResource(R.drawable.sign_in_act_shape_second);
    }

    @Override
    public void signInSuccess() {
        signInPresenter.getSignInData();
        Toast.makeText(this, "打卡成功", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(EventBusTag.SIGN_IN_SUCCESS);
    }

    @Override
    public void signInFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }
}
