package com.tyqhwl.jrqh.user.activity;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.tyqhwl.jrqh.user.presenter.MofificationPsdPresente;
import com.tyqhwl.jrqh.user.view.ModificationPsdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改用户密码
 * wmy
 * 2019-04-22
 */
public class ModificationPswActivity extends BaseActivity implements ModificationPsdView {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.modification_old_psd)
    EditText modificationOldPsd;
    @BindView(R.id.modification_new_psd)
    EditText modificationNewPsd;
    @BindView(R.id.modification_new_psd_second)
    EditText modificationNewPsdSecond;
    @BindView(R.id.mofdification_button)
    TextView mofdificationButton;
    AwaitDialog awaitDialog;
    private MofificationPsdPresente mofificationPsdPresente = new MofificationPsdPresente(this);
    @Override
    public int getXMLLayout() {
        return R.layout.modification_psw_activity;
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

    @OnClick({R.id.homepage_frag_sign_in, R.id.back, R.id.modification_old_psd,
            R.id.modification_new_psd, R.id.modification_new_psd_second, R.id.mofdification_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.modification_old_psd:
                break;
            case R.id.modification_new_psd:
                break;
            case R.id.modification_new_psd_second:
                break;
            case R.id.mofdification_button:
                if (modificationOldPsd.getText().toString().length() > 0 &&
                        modificationNewPsd.getText().toString().length() > 0 &&
                        modificationNewPsdSecond.getText().toString().length() > 0){
                    if (modificationNewPsd.getText().toString().equals(modificationNewPsdSecond.getText().toString())){
                        mofificationPsdPresente.updateUserPsd(modificationOldPsd.getText().toString() , modificationNewPsd.getText().toString());
                    }else {
                        Toast.makeText(this , "输入重复密码不一致" , Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void getModificationPsdSuccess() {
        Toast.makeText(this , "密码修改成功" , Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("name" , userName);
        editor.putString("password",  modificationNewPsd.getText().toString());
        editor.commit();
        finish();
    }

    @Override
    public void getModificationPsdFail(String msg) {
        Toast.makeText(this , msg , Toast.LENGTH_SHORT).show();
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
