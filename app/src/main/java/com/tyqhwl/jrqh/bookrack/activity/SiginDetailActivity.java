package com.tyqhwl.jrqh.bookrack.activity;


import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 积分规则详情页面
 * wmy
 * 2019-05-22
 */
public class SiginDetailActivity extends BaseActivity {
    @BindView(R.id.sigins_activity_back)
    ImageView siginsActivityBack;
    @BindView(R.id.signin_detail_image)
    ImageView signinDetailImage;
    @BindView(R.id.background)
    ImageView background;

    @Override
    public int getXMLLayout() {
        return R.layout.signin_detail_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        Glide.with(this)
                .load(R.drawable.sign_in_background)
                .into(background);
    }

    @OnClick(R.id.sigins_activity_back)
    public void onViewClicked() {
        finish();
    }
}
