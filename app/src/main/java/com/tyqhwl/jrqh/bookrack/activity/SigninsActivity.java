package com.tyqhwl.jrqh.bookrack.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.BaseTime;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.bookrack.view.SignInsDialog;
import com.tyqhwl.jrqh.homepage.presenter.SignInPresenter;
import com.tyqhwl.jrqh.homepage.view.SignInEntry;
import com.tyqhwl.jrqh.homepage.view.SignInView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 簽到首页
 * wmy
 * 2019-05-21
 */
public class SigninsActivity extends BaseActivity implements SignInView {
    @BindView(R.id.sigins_activity_back)
    ImageView siginsActivityBack;
    @BindView(R.id.sigins_activity_detial)
    ImageView siginsActivityDetial;
    @BindView(R.id.signin_image)
    ImageView signinImage;
    @BindView(R.id.signins_act_thread)
    TextView signinsActThread;
    @BindView(R.id.signins_act_thread_image)
    ImageView signinsActThreadImage;
    @BindView(R.id.signins_act_record)
    TextView signinsActRecord;
    @BindView(R.id.signin)
    TextView signin;
    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.signins_act_month)
    TextView month;
    @BindView(R.id.signins_act_integral)
    TextView signActintegral;
    private int showStart = 0;//  0:已签到  1：未签到
    private Date calendarViewDate;
    private SignInPresenter signInPresenter = new SignInPresenter(this);
    private ArrayList<SignInEntry> datas = new ArrayList<>();
    private ArrayList<String> data = new ArrayList<>();
    private int signinCount = 0;

    @Override
    public int getXMLLayout() {
        return R.layout.signins_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        Glide.with(this)
                .load(R.drawable.sign_in_background)
                .into(background);
        signInPresenter.getSignInData();

        //获取今日日期
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int months = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);// 获取当日期
        month.setText(year + "年" + months + "月");
        signinsActThread.setText(mDay + "");
    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o) {
        if (o.equals(EventBusTag.INTENT_BOOK_MALL)) {
            finish();
        }
    }


    @OnClick({R.id.sigins_activity_back, R.id.sigins_activity_detial,
            R.id.signin_image, R.id.signins_act_thread,
            R.id.signins_act_thread_image, R.id.signins_act_record, R.id.signin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sigins_activity_back:
                finish();
                break;
            case R.id.sigins_activity_detial:
                //跳转到积分规则页面
                IntentSkip.startIntent(this , new SiginDetailActivity() , null);
                break;
            case R.id.signin_image:
                break;
            case R.id.signins_act_thread:
                break;
            case R.id.signins_act_thread_image:
                break;
            case R.id.signins_act_record:
                //签到记录
                SignInsDialog signInsDialog = new SignInsDialog(this, R.style.DialogTrangparent, signinCount);
                signInsDialog.setCancelable(true);
                signInsDialog.show();
                break;
            case R.id.signin:
                switch (showStart) {
                    case 0:
                        data.add(getDate());
                        signInPresenter.saveSignin(data);
                        break;
                    case 1:
                        //已签到
                        Toast.makeText(this, "今日已签到", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }


    public String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    @Override
    public void getSignInViewSuccess(ArrayList<SignInEntry> arrayList) {
        datas.clear();
        data.clear();
        if (arrayList.size() == 0) {
            //可以进行签到
            showStart = 0;
        } else {
            datas.addAll(arrayList);
            boolean isSignin  = false;
            ApplicationStatic.setSignInNumberOfDays(arrayList.get(0).signinList.size());
            showStart = 1;
            signinCount = arrayList.get(0).signinList.size();//总签到天数
            for (int i = 0; i < arrayList.get(0).signinList.size(); i++) {
                data.add(arrayList.get(0).signinList.get(i));
//                Log.e("show",arrayList.get(0).signinList.get(i))
                Date date = parseServerTime(arrayList.get(0).signinList.get(i));
                if (BaseTime.getIsToday(date.getTime())) {
                    signin.setText("已签到");

                    isSignin = true;
                }
            }
            if (isSignin){
                signinsActThreadImage.setVisibility(View.VISIBLE);
                showStart = 1;

            }else {
                signinsActThreadImage.setVisibility(View.INVISIBLE);
                showStart = 0;
//                signActintegral.setText("0.00");
            }
            signActintegral.setText(signinCount *100 + ".00");
        }
    }


    public Date parseServerTime(String serverTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {

        }
        return date;
    }

    @Override
    public void getSignInViewFail(String msg) {

    }

    @Override
    public void signInSuccess() {
        signActintegral.setText((signinCount+1) *100 + ".00");
        Toast.makeText(this, "签到成功", Toast.LENGTH_SHORT).show();
        signin.setText("已签到");
        signinsActThreadImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void signInFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        signinsActThreadImage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }
}
