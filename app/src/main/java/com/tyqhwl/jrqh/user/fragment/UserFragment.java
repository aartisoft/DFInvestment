package com.tyqhwl.jrqh.user.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.bookrack.activity.SigninsActivity;
import com.tyqhwl.jrqh.login.activity.LoginActivity;
import com.tyqhwl.jrqh.user.activity.AboutUsActivity;
import com.tyqhwl.jrqh.user.activity.LookCatagoryActivity;
import com.tyqhwl.jrqh.user.activity.MyBookRackActivity;
import com.tyqhwl.jrqh.user.activity.SettingActivity;
import com.tyqhwl.jrqh.user.activity.UserActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tech.com.commoncore.manager.GlideManager;

/**
 * 用户中心
 * wmy
 * 2019-05-24
 */
public class UserFragment extends BaseFragment {

    @BindView(R.id.user_frag_signin)
    TextView userFragSignin;
    @BindView(R.id.user_frag_userimage)
    ImageView userFragUserimage;
    @BindView(R.id.user_frag_username)
    TextView userFragUsername;
    @BindView(R.id.user_frag_background)
    ImageView userFragBackground;
    @BindView(R.id.user_frag_signin_integral)
    RelativeLayout userFragSigninIntegral;
    //    @BindView(R.id.user_frag_my_integral)
//    LinearLayout userFragMyIntegral;
    @BindView(R.id.user_frag_mybookrack)
    LinearLayout userFragMybookrack;
    @BindView(R.id.user_frag_myreading_record)
    LinearLayout userFragMyreadingRecord;
    @BindView(R.id.user_frag_mycollect)
    LinearLayout userFragMycollect;
    @BindView(R.id.user_frag_myinterest)
    LinearLayout userFragMyinterest;
    @BindView(R.id.user_frag_setting)
    LinearLayout userFragSetting;
    @BindView(R.id.user_frag_aboutus)
    LinearLayout userFragAboutus;
    Unbinder unbinder;

    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getXMLLayout() {
        return R.layout.use_fragment;
    }

    @Override
    public void initView() {

    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o) {
        if (o.equals(EventBusTag.LOGIN_SUCCESS)) {
            init();

        }
        if (o.equals(EventBusTag.REGISTER_SUCCESS)) {
            init();

        }
        if (o.equals(EventBusTag.LOGIN_QUIT)) {
            init();

        }

        if (o.equals(EventBusTag.UPDATE_USER_LOGO)) {
            init();

        }

        if (o.equals(EventBusTag.UPDATE_USER_MESSAGE)) {
            init();

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        if (ApplicationStatic.getUserLoginState()) {
            AVUser avUser = AVUser.getCurrentUser();
            if (avUser.get("icon") != null) {
                GlideManager.loadCircleImg(avUser.get("icon"), userFragUserimage);
            } else {
                GlideManager.loadCircleImg(R.drawable.user_image, userFragUserimage);
            }
            userFragUsername.setText(ApplicationStatic.getUserMessage().getUsername() + "");
        } else {
            GlideManager.loadCircleImg(R.drawable.user_image, userFragUserimage);
            userFragUsername.setText("登录");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_frag_signin, R.id.user_frag_userimage,
            R.id.user_frag_username, R.id.user_frag_background,
            R.id.user_frag_signin_integral,
            R.id.user_frag_mybookrack, R.id.user_frag_myreading_record,
            R.id.user_frag_mycollect, R.id.user_frag_myinterest,
            R.id.user_frag_setting, R.id.user_frag_aboutus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_frag_signin:
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new SigninsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.user_frag_userimage:
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new UserActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.user_frag_username:
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new UserActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.user_frag_background:
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new SigninsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.user_frag_signin_integral:
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new SigninsActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.user_frag_mybookrack:
                //我的书架
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new MyBookRackActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.user_frag_myreading_record:
                //我的阅读记录
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new LookCatagoryActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.user_frag_mycollect:
                //我的收藏
                break;
            case R.id.user_frag_myinterest:
                //我的关注
                EventBus.getDefault().post(EventBusTag.MY_ATTENTION);
                break;
            case R.id.user_frag_setting:
                //我的设置
                if (ApplicationStatic.getUserLoginState()) {
                    IntentSkip.startIntent(getActivity(), new SettingActivity(), null);
                } else {
                    IntentSkip.startIntent(getActivity(), new LoginActivity(), null);
                }
                break;
            case R.id.user_frag_aboutus:
                //关于我们
                IntentSkip.startIntent(getActivity(), new AboutUsActivity(), null);
                break;
        }
    }
}
