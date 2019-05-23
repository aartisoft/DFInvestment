package com.tyqhwl.jrqh.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseFragment;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.exercise.actviity.ExerciseActivity;
import com.tyqhwl.jrqh.goover.activity.UserCollectActivity;
import com.tyqhwl.jrqh.homepage.activity.SignInActivity;
import com.tyqhwl.jrqh.login.activity.LoginActivity;
import com.tyqhwl.jrqh.user.activity.AboutUsActivity;
import com.tyqhwl.jrqh.user.activity.MyBooksActivity;
import com.tyqhwl.jrqh.user.activity.SettingActivity;
import com.tyqhwl.jrqh.user.activity.UserActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tech.com.commoncore.manager.GlideManager;


/**
 * 用户中心页面
 * wmy
 * 2019-04-11
 */
public class UserFragmetn extends BaseFragment {

    //    @BindView(R.id.login)
//    Button login;
    Unbinder unbinder;
    @BindView(R.id.user_fragment_user_image)
    ImageView userFragmentUserImage;
    @BindView(R.id.user_fragment_user_name)
    TextView userFragmentUserName;
    @BindView(R.id.user_fragment_user_more)
    ImageView userFragmentUserMore;
    @BindView(R.id.user_fragment_user_layout)
    LinearLayout userFragmentUserLayout;
    @BindView(R.id.user_frag_books_image)
    ImageView userFragBooksImage;
    @BindView(R.id.user_frag_books_text)
    TextView userFragBooksText;
    @BindView(R.id.user_frag_books_more)
    ImageView userFragBooksMore;
    @BindView(R.id.user_frag_books_layout)
    LinearLayout userFragBooksLayout;
    @BindView(R.id.user_frag_goover_image)
    ImageView userFragGooverImage;
    @BindView(R.id.user_frag_goover_text)
    TextView userFragGooverText;
    @BindView(R.id.user_frag_goover_more)
    ImageView userFragGooverMore;
    @BindView(R.id.user_frag_goover_layout)
    LinearLayout userFragGooverLayout;
    @BindView(R.id.user_frag_sigin_image)
    ImageView userFragSiginImage;
    @BindView(R.id.user_frag_sigin_text)
    TextView userFragSiginText;
    @BindView(R.id.user_frag_sigin_more)
    ImageView userFragSiginMore;
    @BindView(R.id.user_frag_sigin_layout)
    LinearLayout userFragSiginLayout;
    @BindView(R.id.user_frag_collect_image)
    ImageView userFragCollectImage;
    @BindView(R.id.user_frag_collect_text)
    TextView userFragCollectText;
    @BindView(R.id.user_frag_collect_more)
    ImageView userFragCollectMore;
    @BindView(R.id.user_frag_collect_layout)
    LinearLayout userFragCollectLayout;
    @BindView(R.id.user_frag_about_us_image)
    ImageView userFragAboutUsImage;
    @BindView(R.id.user_frag_about_us_text)
    TextView userFragAboutUsText;
    @BindView(R.id.user_frag_about_us_more)
    ImageView userFragAboutUsMore;
    @BindView(R.id.user_frag_about_us_layout)
    LinearLayout userFragAboutUsLayout;
    @BindView(R.id.user_frag_setting_image)
    ImageView userFragSettingImage;
    @BindView(R.id.user_frag_setting_text)
    TextView userFragSettingText;
    @BindView(R.id.user_frag_setting_more)
    ImageView userFragSettingMore;
    @BindView(R.id.user_frag_setting_layout)
    LinearLayout userFragSettingLayout;

    public static UserFragmetn newInstance() {

        Bundle args = new Bundle();

        UserFragmetn fragment = new UserFragmetn();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getXMLLayout() {
        return R.layout.user_fragment;
    }

    @Override
    public void initView() {

    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o){
        if (o.equals(EventBusTag.LOGIN_SUCCESS)){
            init();
        }
        if (o.equals(EventBusTag.REGISTER_SUCCESS)){
            init();
        }
        if (o.equals(EventBusTag.LOGIN_QUIT)){
            init();
        }

        if (o.equals(EventBusTag.UPDATE_USER_LOGO)){
            init();
        }

        if (o.equals(EventBusTag.UPDATE_USER_MESSAGE)){
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

    private void init() {
        if (ApplicationStatic.getUserLoginState()){
            AVUser avUser = AVUser.getCurrentUser();
            if (avUser.get("icon") != null) {
                GlideManager.loadCircleImg(avUser.get("icon"), userFragmentUserImage);
            } else {
                GlideManager.loadCircleImg(R.drawable.user_images, userFragmentUserImage);
            }
            userFragmentUserName.setText(ApplicationStatic.getUserMessage().getUsername());
        }else {
            GlideManager.loadCircleImg(R.drawable.user_images, userFragmentUserImage);
            userFragmentUserName.setText("请登录");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_fragment_user_image, R.id.user_fragment_user_name,
            R.id.user_fragment_user_more, R.id.user_fragment_user_layout,
            R.id.user_frag_books_image, R.id.user_frag_books_text, R.id.user_frag_books_more,
            R.id.user_frag_books_layout, R.id.user_frag_goover_image, R.id.user_frag_goover_text,
            R.id.user_frag_goover_more, R.id.user_frag_goover_layout, R.id.user_frag_sigin_image,
            R.id.user_frag_sigin_text, R.id.user_frag_sigin_more, R.id.user_frag_sigin_layout,
            R.id.user_frag_collect_image, R.id.user_frag_collect_text, R.id.user_frag_collect_more,
            R.id.user_frag_collect_layout, R.id.user_frag_about_us_image, R.id.user_frag_about_us_text,
            R.id.user_frag_about_us_more, R.id.user_frag_about_us_layout, R.id.user_frag_setting_image,
            R.id.user_frag_setting_text, R.id.user_frag_setting_more, R.id.user_frag_setting_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_fragment_user_image:
                if (ApplicationStatic.getUserLoginState()){
                    //跳转到用户界面
                    IntentSkip.startIntent(getActivity() , new UserActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_fragment_user_name:
                if (ApplicationStatic.getUserLoginState()){
                    //跳转到用户界面
                    IntentSkip.startIntent(getActivity() , new UserActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_fragment_user_more:
                if (ApplicationStatic.getUserLoginState()){
                    //跳转到用户界面
                    IntentSkip.startIntent(getActivity() , new UserActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_fragment_user_layout:
                if (ApplicationStatic.getUserLoginState()){
                    //跳转到用户界面
                    IntentSkip.startIntent(getActivity() , new UserActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_books_image:
                if (ApplicationStatic.getUserLoginState()){
                    //我的课程
                    IntentSkip.startIntent(getActivity() , new MyBooksActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_books_text:
                if (ApplicationStatic.getUserLoginState()){
                    //我的课程
                    IntentSkip.startIntent(getActivity() , new MyBooksActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_books_more:
                if (ApplicationStatic.getUserLoginState()){
                    //我的课程
                    IntentSkip.startIntent(getActivity() , new MyBooksActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_books_layout:
                if (ApplicationStatic.getUserLoginState()){
                    //我的课程
                    IntentSkip.startIntent(getActivity() , new MyBooksActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_goover_image:
                if (ApplicationStatic.getUserLoginState()){
                    //做题练习
                    IntentSkip.startIntent(getActivity() , new ExerciseActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_goover_text:
                if (ApplicationStatic.getUserLoginState()){
                    //做题练习
                    IntentSkip.startIntent(getActivity() , new ExerciseActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_goover_more:
                if (ApplicationStatic.getUserLoginState()){
                    //做题练习
                    IntentSkip.startIntent(getActivity() , new ExerciseActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_goover_layout:
                if (ApplicationStatic.getUserLoginState()){
                    //做题练习
                    IntentSkip.startIntent(getActivity() , new ExerciseActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_sigin_image:
                if (ApplicationStatic.getUserLoginState()){
                    //打卡记录
                    IntentSkip.startIntent(getActivity() , new SignInActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_sigin_text:
                if (ApplicationStatic.getUserLoginState()){
                    //打卡记录
                    IntentSkip.startIntent(getActivity() , new SignInActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_sigin_more:
                if (ApplicationStatic.getUserLoginState()){
                    //打卡记录
                    IntentSkip.startIntent(getActivity() , new SignInActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_sigin_layout:
                if (ApplicationStatic.getUserLoginState()){
                    //打卡记录
                    IntentSkip.startIntent(getActivity() , new SignInActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_collect_image:
                if (ApplicationStatic.getUserLoginState()){
                    //我的收藏
                    IntentSkip.startIntent(getActivity() , new UserCollectActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_collect_text:
                if (ApplicationStatic.getUserLoginState()){
                    //我的收藏
                    IntentSkip.startIntent(getActivity() , new UserCollectActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_collect_more:
                if (ApplicationStatic.getUserLoginState()){
                    //我的收藏
                    IntentSkip.startIntent(getActivity() , new UserCollectActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_collect_layout:
                if (ApplicationStatic.getUserLoginState()){
                    //我的收藏
                    IntentSkip.startIntent(getActivity() , new UserCollectActivity() , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_about_us_image:
                if (ApplicationStatic.getUserLoginState()){
                    //关于我们
                    IntentSkip.startIntent(getActivity() , new AboutUsActivity()  , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_about_us_text:
                if (ApplicationStatic.getUserLoginState()){
                    //关于我们
                    IntentSkip.startIntent(getActivity() , new AboutUsActivity()  , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_about_us_more:
                if (ApplicationStatic.getUserLoginState()){
                    //关于我们
                    IntentSkip.startIntent(getActivity() , new AboutUsActivity()  , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_about_us_layout:
                if (ApplicationStatic.getUserLoginState()){
                    //关于我们
                    IntentSkip.startIntent(getActivity() , new AboutUsActivity()  , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_setting_image:
                if (ApplicationStatic.getUserLoginState()){
                    //设置
                    IntentSkip.startIntent(getActivity() , new SettingActivity()  , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_setting_text:
                if (ApplicationStatic.getUserLoginState()){
                    //设置
                    IntentSkip.startIntent(getActivity() , new SettingActivity()  , null);
                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_setting_more:
                if (ApplicationStatic.getUserLoginState()){

                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
            case R.id.user_frag_setting_layout:
                if (ApplicationStatic.getUserLoginState()){

                }else {
                    IntentSkip.startIntent(getActivity() , new LoginActivity() , null);
                }
                break;
        }
    }
}
