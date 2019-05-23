package com.tyqhwl.jrqh.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.user.view.NoticeEntrys;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公告详情页面
 * wmy
 * 2019-04-09
 */
public class NoticeDetailActivity extends BaseActivity {
    @BindView(R.id.authentication_back_image)
    ImageView authenticationBackImage;
    @BindView(R.id.new_details_act_tital)
    TextView newDetailsActTital;
    @BindView(R.id.notice_detail_act_title)
    TextView noticeDetailActTitle;
    @BindView(R.id.notice_detail_act_datetime)
    TextView noticeDetailActDatetime;
    @BindView(R.id.notice_detail_act_conetxt)
    TextView noticeDetailActConetxt;

    @Override
    public int getXMLLayout() {
        return R.layout.notice_detail_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initGetIntent();
    }

    private void initGetIntent() {
        NoticeEntrys noticeEntrys = (NoticeEntrys) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
        noticeDetailActTitle.setText(noticeEntrys.title + "");
        noticeDetailActDatetime.setText(noticeEntrys.dataTime + "");
        noticeDetailActConetxt.setText(noticeEntrys.context + "");
    }

    @OnClick({R.id.authentication_back_image ,R.id.new_details_act_tital})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.authentication_back_image:
                finish();
                break;
            case R.id.new_details_act_tital:
                break;
        }
    }
}
