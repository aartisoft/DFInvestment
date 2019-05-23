package com.tyqhwl.jrqh.login.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.login.presenter.LoginHttps;
import com.tyqhwl.jrqh.login.view.LoginView;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码
 * wmy
 * 2019-04-11
 */
public class ForgetActivity extends BaseActivity implements LoginView {
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.login_act_username_edittext)
    EditText loginActUsernameEdittext;
    @BindView(R.id.register_act_verification_code_edittext)
    EditText registerActVerificationCodeEdittext;
    @BindView(R.id.register_act_verification_code)
    TextView registerActVerificationCode;
    @BindView(R.id.login_act_password_edittext)
    EditText loginActPasswordEdittext;
    @BindView(R.id.login_act_watch_checkbox)
    CheckBox loginActWatchCheckbox;
    @BindView(R.id.login_act_button)
    TextView loginActButton;
    private boolean verificationCodeType = true;
    int index = 60;
    private AwaitDialog awaitDialog;
    private LoginHttps loginHttps = new LoginHttps(this , this);
    @Override
    public int getXMLLayout() {
        return R.layout.forget_activty;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.register_act_verification_code, R.id.login_act_watch_checkbox, R.id.login_act_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.register_act_verification_code:
                //获取验证码
                countDown();
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
                //修改密码
                if (isMobileNO(loginActUsernameEdittext.getText().toString()) && loginActPasswordEdittext.getText().toString().length() >= 6
                        && registerActVerificationCodeEdittext.getText().toString().length() > 0) {
                    loginHttps.findPassword(registerActVerificationCodeEdittext.getText().toString() ,loginActPasswordEdittext.getText().toString());
                }
                break;
        }
    }


    //验证码倒计时
    public void countDown(){
        if (verificationCodeType){
            if (isMobileNO(loginActUsernameEdittext.getText().toString())){
                loginHttps.sendVerificationCode(loginActUsernameEdittext.getText().toString());
                verificationCodeType = false;
                registerActVerificationCode.setBackgroundResource(R.drawable.register_act_verification_code_shape_second);
                final Timer timer = new Timer();
                final TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        index--;
                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                if (registerActVerificationCode != null){
                                    registerActVerificationCode.setText(index + "s");
                                }
                                if (index <= 0){
                                    timer.cancel();
                                    verificationCodeType = true;
                                    index = 60;
                                    if (registerActVerificationCode != null){
                                        registerActVerificationCode.setBackgroundResource(R.drawable.register_act_verification_code_shape);
                                        registerActVerificationCode.setText("获取验证码");
                                    }

                                }
                            }
                        });

                    }
                };

                timer.schedule(timerTask , 0 , 1000);
            }else {
                Toast.makeText(this , "请输入正确手机号码"  , Toast.LENGTH_LONG).show();
            }

        }

    }


    //正则表达式验证是否为手机
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }


    @Override
    public void loginSuccess(AVUser avUser) {

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
        Toast.makeText(this , "重置密码成功" , Toast.LENGTH_LONG).show();
        EventBus.getDefault().post(EventBusTag.PASSWORD_MODIFICATION);
        finish();
    }

    @Override
    public void findPasswordFail(String msg) {
        Toast.makeText(this , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAwait() {
        awaitDialog = new AwaitDialog(this , R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
    }

    @Override
    public void finishAwait() {
        awaitDialog.dismiss();
    }
}
