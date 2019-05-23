package com.tyqhwl.jrqh.login.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.login.presenter.LoginHttps;
import com.tyqhwl.jrqh.login.view.LoginView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 * wmy
 * 2019-04-11
 */
public class LoginActivity extends BaseActivity implements LoginView {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.login_act_username_edittext)
    EditText loginActUsernameEdittext;
    @BindView(R.id.login_act_password_edittext)
    EditText loginActPasswordEdittext;
    @BindView(R.id.login_act_watch_checkbox)
    CheckBox loginActWatchCheckbox;
    @BindView(R.id.login_act_button)
    TextView loginActButton;
    @BindView(R.id.login_act_register_button)
    TextView loginActRegisterButton;
    @BindView(R.id.login_act_forget_psd)
    TextView loginActForgetPsd;
    @BindView(R.id.login_act_background_image)
    ImageView loginActBackgroundImage;

    @Override
    public int getXMLLayout() {
        return R.layout.login_actiity;
    }

    @Override
    public void initView() {

    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }

    private AwaitDialog awaitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        Glide.with(this)
                .load(R.drawable.user_background)
                .into(loginActBackgroundImage);
    }

    @OnClick({R.id.back, R.id.login_act_watch_checkbox, R.id.login_act_button, R.id.login_act_register_button, R.id.login_act_forget_psd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.login_act_watch_checkbox:
                if (loginActWatchCheckbox.isChecked()) {
                    loginActPasswordEdittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //如果被选中则显示密码
                    loginActPasswordEdittext.setSelection(loginActPasswordEdittext.getText().length());   //TextView默认光标在最左端，这里控制光标在最右端
                } else {
                    loginActPasswordEdittext.setTransformationMethod(PasswordTransformationMethod.getInstance()); //如果没选中CheckBox则隐藏密码
                    loginActPasswordEdittext.setSelection(loginActPasswordEdittext.getText().length());
                }
                break;
            case R.id.login_act_button:
                //登录
                if (loginActUsernameEdittext.getText().toString().length() > 0 && loginActPasswordEdittext.getText().toString().length() > 0) {
                    LoginHttps loginHttps = new LoginHttps(this, this);
                    loginHttps.getLoginData(loginActUsernameEdittext.getText().toString(), loginActPasswordEdittext.getText().toString());
                } else {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_act_register_button:
                //跳转到注册页面
                IntentSkip.startIntent(this, new RegisterActivity(), null);
                break;
            case R.id.login_act_forget_psd:
                //忘记密码
                IntentSkip.startIntent(this, new ForgetActivity(), null);
                break;
        }
    }

    @Override
    public void loginSuccess(AVUser avUser) {
        ApplicationStatic.setUserLoginState(true);
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        EventBus.getDefault().post(EventBusTag.LOGIN_SUCCESS);
        finish();
    }

    @Override
    public void loginFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
        awaitDialog = new AwaitDialog(this, R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Object eventBusTag) {
        if (eventBusTag.equals(EventBusTag.REGISTER_SUCCESS)) {
            EventBus.getDefault().post(EventBusTag.LOGIN_SUCCESS);
            ApplicationStatic.setUserLoginState(true);
            finish();
        }
    }
}
