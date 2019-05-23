package com.tyqhwl.jrqh.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.AwaitDialog;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.homepage.presenter.CommitPresenter;
import com.tyqhwl.jrqh.homepage.view.CommitEntry;
import com.tyqhwl.jrqh.homepage.view.CommitView;
import com.tyqhwl.jrqh.homepage.view.WriteCommentaryEntry;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 填写评论
 */
public class WriteCommentaryActivity extends BaseActivity implements CommitView {
    @BindView(R.id.book_detail_act_back)
    ImageView bookDetailActBack;
    @BindView(R.id.write_comment_act_edittext)
    EditText writeCommentActEdittext;
    @BindView(R.id.write_comment_act_dismess)
    TextView writeCommentActDismess;
    private CommitPresenter commitPresenter = new CommitPresenter(this);
    private AwaitDialog awaitDialog;
    private WriteCommentaryEntry writeCommentaryEntry;
    @Override
    public int getXMLLayout() {
        return R.layout.write_commentary_activity;
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

    @OnClick({R.id.book_detail_act_back, R.id.write_comment_act_dismess})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.book_detail_act_back:
                finish();
                break;
            case R.id.write_comment_act_dismess:
                if (writeCommentActEdittext.getText().length() > 0){
                    commitPresenter.pushUserCommit(writeCommentActEdittext.getText().toString());
                }else {
                    Toast.makeText(this , "评论内容不能为空" , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void getCommitDataSuccess(ArrayList<CommitEntry> data) {

    }

    @Override
    public void getCommitDataFail(String msg) {

    }

    @Override
    public void getPushUseCommitSuccess() {
        Toast.makeText(this , "提交评论成功" , Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new WriteCommentaryEntry(writeCommentActEdittext.getText().toString()));
        finish();
    }

    @Override
    public void getPushUseCommitFail(String msg) {
        Toast.makeText(this , msg , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAddRackSuccess() {

    }

    @Override
    public void getAddRackFail(String msg) {

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
