package com.tyqhwl.jrqh.user.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.bookrack.activity.SigninsActivity;
import com.tyqhwl.jrqh.homepage.presenter.SignInPresenter;
import com.tyqhwl.jrqh.homepage.view.SignInEntry;
import com.tyqhwl.jrqh.homepage.view.SignInView;
import com.tyqhwl.jrqh.login.activity.LoginActivity;
import com.tyqhwl.jrqh.user.activity.AboutUsActivity;
import com.tyqhwl.jrqh.user.activity.LookCatagoryActivity;
import com.tyqhwl.jrqh.user.activity.MyMessageActivity;
import com.tyqhwl.jrqh.user.activity.SettingActivity;
import com.tyqhwl.jrqh.user.activity.UserActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tech.com.commoncore.manager.GlideManager;

/**
 * 我的页面
 */
public class UsersFragment extends BaseFragment implements SignInView {
    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.user_frag_model)
    CheckBox userFragModel;
    @BindView(R.id.user_frage_message)
    ImageView userFrageMessage;
    @BindView(R.id.user_frage_userimage)
    ImageView userFrageUserimage;
    @BindView(R.id.user_frage_username)
    TextView userFrageUsername;
    @BindView(R.id.user_frage_integral)
    TextView userFrageIntegral;
    @BindView(R.id.user_frag_signin)
    LinearLayout userFragSignin;
    @BindView(R.id.user_frag_my_look_catagory)
    LinearLayout userFragMyLookCatagory;
    @BindView(R.id.user_frag_my_bookrack)
    LinearLayout userFragMyBookrack;
    @BindView(R.id.user_frag_my_message)
    LinearLayout userFragMyMessage;
    @BindView(R.id.user_frag_my_about_us)
    LinearLayout userFragMyAboutUs;
    @BindView(R.id.user_frag_my_setting)
    LinearLayout userFragMySetting;
    Unbinder unbinder;


    private SignInPresenter signInPresenter = new SignInPresenter(this);
    private int signinCount = 0;
    public static UsersFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public int getXMLLayout() {
        return R.layout.users_fragment;
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
            signInPresenter.getSignInData();
        }
        if (o.equals(EventBusTag.REGISTER_SUCCESS)) {
            init();
            signInPresenter.getSignInData();
        }
        if (o.equals(EventBusTag.LOGIN_QUIT)) {
            init();
            signInPresenter.getSignInData();
        }

        if (o.equals(EventBusTag.UPDATE_USER_LOGO)) {
            init();
            signInPresenter.getSignInData();
        }

        if (o.equals(EventBusTag.UPDATE_USER_MESSAGE)) {
            init();
            signInPresenter.getSignInData();
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Glide.with(getActivity())
                .load(R.drawable.user_background)
                .into(background);
        init();
        signInPresenter.getSignInData();

        userFragModel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity() , "阅读模式为黑夜模式" , Toast.LENGTH_SHORT).show();
                    ApplicationStatic.saveReadingModel(isChecked);
                }else {
                    Toast.makeText(getActivity() , "阅读模式为白天模式" , Toast.LENGTH_SHORT).show();
                    ApplicationStatic.saveReadingModel(isChecked);
                }
                EventBus.getDefault().post(EventBusTag.READING_MODEL);
            }
        });
        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        if (ApplicationStatic.getUserLoginState()) {
            AVUser avUser = AVUser.getCurrentUser();
            if (avUser.get("icon") != null) {
                GlideManager.loadCircleImg(avUser.get("icon"), userFrageUserimage);
            } else {
                GlideManager.loadCircleImg(R.drawable.user_images, userFrageUserimage);
            }
            userFrageUsername.setText(ApplicationStatic.getUserMessage().getUsername() + "");
        } else {
            GlideManager.loadCircleImg(R.drawable.user_images, userFrageUserimage);
            userFrageUsername.setText("登录");
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.background, R.id.user_frage_message,
            R.id.user_frage_userimage, R.id.user_frage_username, R.id.user_frage_integral,
             R.id.user_frag_signin, R.id.user_frag_my_look_catagory,
            R.id.user_frag_my_bookrack, R.id.user_frag_my_message, R.id.user_frag_my_about_us, R.id.user_frag_my_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_frage_message:
                //消息
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(getActivity() , new MyMessageActivity() , 0);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frage_userimage:
                //用户
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(getActivity() , new UserActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frage_username:
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(getActivity() , new UserActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frage_integral:
                //积分
                if (ApplicationStatic.getUserLoginState()){

                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_signin:
                //签到明细
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(getActivity() , new SigninsActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_my_look_catagory:
                //看单
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(getActivity() , new LookCatagoryActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_my_bookrack:
                //我的书架
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(getActivity() , new MyMessageActivity() , 1);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_my_message:
                //消息通知
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(getActivity() , new MyMessageActivity() , 0);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_my_about_us:
                //关于我们
                IntentSkip.startIntent(getActivity() , new AboutUsActivity() , null);
                break;
            case R.id.user_frag_my_setting:
                //设置
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(getActivity() , new SettingActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getSignInViewSuccess(ArrayList<SignInEntry> arrayList) {
        if (arrayList.size() == 0) {
            //可以进行签到
            signinCount = 0;
            userFrageIntegral.setText("0.00");
        } else {
            ApplicationStatic.setSignInNumberOfDays(arrayList.get(0).signinList.size());
            signinCount = arrayList.get(0).signinList.size();//总签到天数
            userFrageIntegral.setText(signinCount * 100 + ".00");
        }
    }

    @Override
    public void getSignInViewFail(String msg) {

    }

    @Override
    public void signInSuccess() {

    }

    @Override
    public void signInFail(String msg) {

    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }
}
