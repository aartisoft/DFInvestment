package com.tyqhwl.jrqh.information.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.information.presenter.InversorCommentPresenter;
import com.tyqhwl.jrqh.information.view.AddInversorMyEntry;
import com.tyqhwl.jrqh.information.view.InversorCommentView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 圈子评论
 */
public class InversorCommentActivity extends BaseActivity implements InversorCommentView {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.iversror_comment_edittext)
    EditText iversrorCommentEdittext;
    @BindView(R.id.iversror_comment_commit)
    TextView iversrorCommentCommit;
    private AddInversorMyEntry addInversorMyEntry;

    private InversorCommentPresenter inversorCommentPresenter = new InversorCommentPresenter(this);
    private AwaitDialog awaitDialog;
    @Override
    public int getXMLLayout() {
        return R.layout.inversor_comment_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (getIntent() != null){
            addInversorMyEntry = (AddInversorMyEntry) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
        }
    }

    @OnClick({R.id.back, R.id.iversror_comment_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iversror_comment_commit:
                if (iversrorCommentEdittext.getText().toString().length() > 0){
                    inversorCommentPresenter.getInversor(addInversorMyEntry , iversrorCommentEdittext.getText().toString());
                }else {
                    Toast.makeText(this , "请添加您的评论" , Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void getInversorCommentSuccess() {
        Toast.makeText(this , "留言成功" , Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(EventBusTag.LAVE_A_MESSAGE_SUCCESS);
        finish();
    }

    @Override
    public void getInversorCommentFail(String msg) {

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
