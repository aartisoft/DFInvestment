package com.tyqhwl.jrqh.information.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.information.presenter.InversorCommentPresenter;
import com.tyqhwl.jrqh.information.view.AddInversorMyEntry;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 圈子评论
 */
public class InversorCommentActivity extends BaseActivity {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.iversror_comment_edittext)
    EditText iversrorCommentEdittext;
    @BindView(R.id.iversror_comment_commit)
    TextView iversrorCommentCommit;
    private AddInversorMyEntry addInversorMyEntry;

    private InversorCommentPresenter inversorCommentPresenter = new InversorCommentPresenter();

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
                inversorCommentPresenter.getInversor(addInversorMyEntry , "数据显示测试");
                break;
        }
    }
}
