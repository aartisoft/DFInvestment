package com.tyqhwl.jrqh.user.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.user.presenter.ModificationUserNamePresenter;
import com.tyqhwl.jrqh.user.view.UserView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改用户昵称
 * wmy
 * 2019-04-22
 */
public class ModificationUserNameActivity extends BaseActivity implements UserView {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.user_act_login_myusername_edittext)
    EditText userActLoginMyusernameEdittext;
    @BindView(R.id.user_act_login_myusername_layout)
    LinearLayout userActLoginMyusernameLayout;
    @BindView(R.id.user_act_button)
    TextView userActButton;

    private AwaitDialog awaitDialog;
    private ModificationUserNamePresenter modificationUserNamePresenter = new ModificationUserNamePresenter(this);

    @Override
    public int getXMLLayout() {
        return R.layout.modification_user_name_activity;
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

    @OnClick({R.id.homepage_frag_sign_in, R.id.back, R.id.user_act_login_myusername_edittext,
            R.id.user_act_login_myusername_layout, R.id.user_act_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.user_act_button:
                if (userActLoginMyusernameEdittext.getText().toString().length() > 0){
                    modificationUserNamePresenter.saveUserMessage(userActLoginMyusernameEdittext.getText().toString() +"");
                }else {
                    Toast.makeText(this , "请输入您的昵称" , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void saveUserMessageSuccess() {
        EventBus.getDefault().post(EventBusTag.UPDATE_USER_MESSAGE);
        Toast.makeText(this , "修改成功" , Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void saveUserMessageFail(String msg) {
        Toast.makeText(this , "修改用户名失败，请重新提交。" , Toast.LENGTH_SHORT).show();
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
}
