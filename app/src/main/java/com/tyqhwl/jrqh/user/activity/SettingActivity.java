package com.tyqhwl.jrqh.user.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.login.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 * wmy
 * 2019-04-22
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.setting_act_modification_psd_text)
    TextView settingActModificationPsdText;
    @BindView(R.id.setting_act_modification_psd_layout)
    LinearLayout settingActModificationPsdLayout;
    @BindView(R.id.setting_act_visoncode_text)
    TextView settingActVisoncodeText;
    @BindView(R.id.setting_act_visoncode_upadte)
    TextView settingActVisoncodeUpadte;
    @BindView(R.id.setting_act_visoncode_layout)
    LinearLayout settingActVisoncodeLayout;
    @BindView(R.id.setting_act_catch_text)
    TextView settingActCatchText;
    @BindView(R.id.setting_act_catch_lyaout)
    LinearLayout settingActCatchLyaout;
    private Handler handler = new Handler();
    private AwaitDialog awaitDialog;
    @Override
    public int getXMLLayout() {
        return R.layout.setting_activity;
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

    @OnClick({R.id.homepage_frag_sign_in, R.id.back, R.id.setting_act_modification_psd_text,
            R.id.setting_act_modification_psd_layout, R.id.setting_act_visoncode_text,
            R.id.setting_act_visoncode_upadte, R.id.setting_act_visoncode_layout,
            R.id.setting_act_catch_text, R.id.setting_act_catch_lyaout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.setting_act_modification_psd_text:
                //修改密码
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(this , new ModificationPswActivity() , null);
                }else {
                    IntentSkip.startIntent(this , new LoginActivity() , null);
                }
                break;
            case R.id.setting_act_modification_psd_layout:
                //修改密码
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(this , new ModificationPswActivity() , null);
                }else {
                    IntentSkip.startIntent(this , new LoginActivity() , null);
                }
                break;
            case R.id.setting_act_visoncode_text:
                //修改密码
                if (ApplicationStatic.getUserLoginState()){
                    IntentSkip.startIntent(this , new ModificationPswActivity() , null);
                }else {
                    IntentSkip.startIntent(this , new LoginActivity() , null);
                }
                break;

            case R.id.setting_act_catch_text:
                //清除缓存
                getHandler();
                break;
            case R.id.setting_act_catch_lyaout:
                //清楚缓存
                getHandler();
                break;
        }
    }




    public void getHandler(){
        awaitDialog = new AwaitDialog(this, R.style.DialogTrangparent);
        awaitDialog.setCancelable(false);
        awaitDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                awaitDialog.dismiss();
                Toast.makeText(SettingActivity.this , "缓存已清理" , Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }




}
