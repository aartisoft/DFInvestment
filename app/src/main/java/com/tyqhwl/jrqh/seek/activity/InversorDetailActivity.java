package com.tyqhwl.jrqh.seek.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.IntentSkip;
import com.tyqhwl.jrqh.seek.presenter.InversorDetailPresenter;
import com.tyqhwl.jrqh.seek.view.InversorDetailEntry;
import com.tyqhwl.jrqh.seek.view.InversorDetailSer;
import com.tyqhwl.jrqh.seek.view.InversorDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.wcy.htmltext.HtmlImageLoader;
import me.wcy.htmltext.HtmlText;

/**
 * 投资入门详情
 * wmy
 * 2019-03-19
 */
public class InversorDetailActivity extends BaseActivity implements InversorDetailView {

    @BindView(R.id.inversor_detail_act_count)
    TextView inversorDetailActCount;
    @BindView(R.id.authentication_back_image)
    ImageView authenticationBackImage;
//    @BindView(R.id.authentication_back_text)
//    TextView authenticationBackText;
    @BindView(R.id.new_details_act_tital)
    TextView newDetailsActTital;
    @BindView(R.id.inversor_detail_act_image)
    ImageView inversorDetailActImage;
    private int postId;

    private InversorDetailPresenter inversorDetailPresenter = new InversorDetailPresenter(this);

    @Override
    public int getXMLLayout() {
        return R.layout.inversor_detail_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Glide.with(this)
                .load(R.drawable.background_image)
                .into(inversorDetailActImage);
        init();
    }

    private void init() {
        InversorDetailSer inversorDetailSer = (InversorDetailSer) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
        postId = inversorDetailSer.getPostId();
        Log.e("show" , postId + "");
        inversorDetailPresenter.getInversor(postId);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void getInversorDetailSuccess(InversorDetailEntry inversorDetailEntry) {
        newDetailsActTital.setText(inversorDetailEntry.getTitle() + "");
        HtmlText.from(inversorDetailEntry.getContent())
                .setImageLoader(new HtmlImageLoader() {
                    @Override
                    public void loadImage(String s, final Callback callback) {
                        // Glide sample, you can also use other image loader
                        Glide.with(getApplicationContext())
                                .load(s)
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        BitmapDrawable bit = (BitmapDrawable) resource;
                                        callback.onLoadComplete(bit.getBitmap());
                                    }
                                });
                    }

                    @Override
                    public Drawable getDefaultDrawable() {
                        return ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg);
                    }

                    @Override
                    public Drawable getErrorDrawable() {
                        return ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg);
                    }

                    @Override
                    public int getMaxWidth() {
                        return inversorDetailActCount.getMaxWidth();
                    }

                    @Override
                    public boolean fitWidth() {
                        return false;
                    }
                })
                .into(inversorDetailActCount);
    }

//    @Override
//    public void getInversorDetailSuccess(InversorDetailEntry inversorDetailEntry) {
//
//    }

    @Override
    public void getInversorDetailFail(String msg) {

    }

    @Override
    public void showAwait() {

    }

    @Override
    public void finishAwait() {

    }

    @OnClick({R.id.authentication_back_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.authentication_back_image:
                finish();
                break;
        }
    }
}
