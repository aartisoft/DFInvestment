package com.tyqhwl.jrqh;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.bookrack.fragment.BookRackFragment;
import com.tyqhwl.jrqh.goover.fragment.GoOverFragment;
import com.tyqhwl.jrqh.homepage.fragment.BookMallFragment;
import com.tyqhwl.jrqh.login.presenter.LoginHttps;
import com.tyqhwl.jrqh.login.view.LoginView;
import com.tyqhwl.jrqh.user.fragment.UsersFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements LoginView {
    @BindView(R.id.act_main_frame_layout)
    FrameLayout actMainFrameLayout;
    @BindView(R.id.acct_main_homepage_checkbox)
    CheckBox acctMainHomepageCheckbox;
    @BindView(R.id.acct_main_homepage_layout)
    LinearLayout acctMainHomepageLayout;
    @BindView(R.id.acct_main_market_checkbox)
    CheckBox acctMainMarketCheckbox;
    @BindView(R.id.acct_main_market_layout)
    LinearLayout acctMainMarketLayout;
    @BindView(R.id.acct_main_user_checkbox)
    CheckBox acctMainUserCheckbox;
    @BindView(R.id.acct_main_user_layout)
    LinearLayout acctMainUserLayout;
    @BindView(R.id.acct_main_homepage_text)
    TextView acctMainHomepageText;
    @BindView(R.id.acct_main_market_text)
    TextView acctMainMarketText;
    @BindView(R.id.acct_main_user_text)
    TextView acctMainUserText;

    private final String TAG0 = "tag0";
    private final String TAG1 = "tag1";
    private final String TAG2 = "tag2";
    private final String TAG3 = "tag3";
    @BindView(R.id.acct_main_circle_checkbox)
    CheckBox acctMainCircleCheckbox;
    @BindView(R.id.acct_main_circle_text)
    TextView acctMainCircleText;
    @BindView(R.id.acct_main_circle_layout)
    LinearLayout acctMainCircleLayout;

    private BookMallFragment homePageFragment;
    private BookRackFragment seekFragment;
    private GoOverFragment goOverFragment;
    private UsersFragment userFragmetn;
    private FragmentManager fragmentManager;

    @Override
    public int getXMLLayout() {
        return R.layout.activity_main;
    }

    private LoginHttps loginHttps = new LoginHttps(this, this);

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initFragment();
        getShard();
    }


    private void getShard() {
        if (getSharedPreferences("userLogin", MODE_PRIVATE) != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
            if (!"".equals(sharedPreferences.getString("name", ""))) {
                loginHttps.getLoginData(sharedPreferences.getString("name", ""), sharedPreferences.getString("password", ""));
            }
        }
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.act_main_frame_layout, homePageFragment = BookMallFragment.newInstance(), TAG0);
        ft.add(R.id.act_main_frame_layout, seekFragment = BookRackFragment.newInstance(), TAG1);
//        ft.add(R.id.act_main_frame_layout, goOverFragment = GoOverFragment.newInstance(), TAG2);
        ft.add(R.id.act_main_frame_layout, userFragmetn = UsersFragment.newInstance(), TAG2);
        ft.commit();
        showFragment(TAG0);
        showIndex(0);
    }


    public void showFragment(String tag) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (tag) {
            case TAG0:
                ft.show(homePageFragment);
                ft.hide(seekFragment);
//                ft.hide(goOverFragment);
                ft.hide(userFragmetn);
                break;
            case TAG1:
                ft.show(seekFragment);
                ft.hide(homePageFragment);
//                ft.hide(goOverFragment);
                ft.hide(userFragmetn);
                break;
//            case TAG2:
//                ft.show(goOverFragment);
//                ft.hide(seekFragment);
//                ft.hide(homePageFragment);
//                ft.hide(userFragmetn);
//                break;
            case TAG2:
                ft.show(userFragmetn);
                ft.hide(seekFragment);
//                ft.hide(goOverFragment);
                ft.hide(homePageFragment);
                break;

        }
        ft.commitAllowingStateLoss();

    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o) {
//        Toast.makeText(this , "wei执行" , Toast.LENGTH_SHORT).show();
        if (o.equals(EventBusTag.INTENT_BOOK_MALL)) {
//            Toast.makeText(this , "执行" , Toast.LENGTH_SHORT).show();
            showFragment(TAG0);
            showIndex(0);
        }
    }


    @OnClick({R.id.acct_main_homepage_checkbox, R.id.acct_main_homepage_layout,
            R.id.acct_main_market_checkbox, R.id.acct_main_market_layout,
            R.id.acct_main_user_checkbox, R.id.acct_main_user_layout})
    public void onViewClickeds(View view) {
        switch (view.getId()) {
            case R.id.acct_main_homepage_checkbox:
                showIndex(0);
                showFragment(TAG0);
                break;
            case R.id.acct_main_homepage_layout:
                showIndex(0);
                showFragment(TAG0);
                break;
            case R.id.acct_main_market_checkbox:
                showIndex(1);
                showFragment(TAG1);
                break;
            case R.id.acct_main_market_layout:
                showIndex(1);
                showFragment(TAG1);
                break;

            case R.id.acct_main_user_checkbox:
                showIndex(2);
                showFragment(TAG2);
                break;
            case R.id.acct_main_user_layout:
                showIndex(2);
                showFragment(TAG2);
                break;
        }
    }


    public void showIndex(int index) {
        acctMainHomepageCheckbox.setChecked(false);
        acctMainMarketCheckbox.setChecked(false);
        acctMainUserCheckbox.setChecked(false);
        acctMainHomepageText.setTextColor(Color.parseColor("#4A4A4A"));
        acctMainMarketText.setTextColor(Color.parseColor("#4A4A4A"));
        acctMainUserText.setTextColor(Color.parseColor("#4A4A4A"));

        switch (index) {
            case 0:
                acctMainHomepageCheckbox.setChecked(true);
                acctMainHomepageText.setTextColor(Color.parseColor("#17B2AA"));
                break;
            case 1:
                acctMainMarketCheckbox.setChecked(true);
                acctMainMarketText.setTextColor(Color.parseColor("#17B2AA"));
                break;
            case 2:
                acctMainUserCheckbox.setChecked(true);
                acctMainUserText.setTextColor(Color.parseColor("#17B2AA"));
                break;
        }
    }

    @OnClick({R.id.acct_main_homepage_text, R.id.acct_main_market_text, R.id.acct_main_user_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.acct_main_homepage_text:
                showIndex(0);
                showFragment(TAG0);
                break;
            case R.id.acct_main_market_text:

                showIndex(1);
                showFragment(TAG1);

                break;
//            case R.id.acct_main_information_text:
//
//                    showIndex(2);
//                    showFragment(TAG2);
//
//                break;
            case R.id.acct_main_user_text:
                showIndex(2);
                showFragment(TAG2);
                break;
        }
    }

    @Override
    public void loginSuccess(AVUser avUser) {
        Toast.makeText(this, "已登录", Toast.LENGTH_SHORT).show();
        ApplicationStatic.setUserLoginState(true);
        EventBus.getDefault().post(EventBusTag.LOGIN_SUCCESS);
    }

    @Override
    public void loginFail(String msg) {

    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerFail(String msg) {

    }

    @Override
    public void findPasswordSuccess() {

    }

    @Override
    public void findPasswordFail(String msg) {

    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }
}
