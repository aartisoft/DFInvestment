package com.tyqhwl.jrqh.seek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.seek.view.NewMessageSerilize;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 详细信息实体页面
 * wmy
 * 2019-03-06
 */
public class ShowNewMessage extends BaseActivity {
    @BindView(R.id.login_back)
    LinearLayout loginBack;
    @BindView(R.id.show_new_message_title)
    TextView showNewMessageTitle;
    @BindView(R.id.show_new_message_text)
    TextView showNewMessageText;
    @BindView(R.id.show_new_image_background)
    ImageView showNewImageBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_new_message);
        ButterKnife.bind(this);
        Glide.with(this)
                .load(R.drawable.background_image)
                .into(showNewImageBackground);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        NewMessageSerilize newMessageSerilize = (NewMessageSerilize) intent.getSerializableExtra(IntentSkip.INTENT_BUILD);
        showNewMessageTitle.setText(newMessageSerilize.getTital());
        showNewMessageText.setText(newMessageSerilize.getText());

    }

    @OnClick({R.id.login_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
        }
    }

    @Override
    public int getXMLLayout() {
        return R.layout.show_new_message;
    }

    @Override
    public void initView() {

    }
}
