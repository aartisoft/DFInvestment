package com.tyqhwl.jrqh.bookrack.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.EventBusTag;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

/**
 * 签到记录页面
 * wmy
 * 2019-05-21
 */

public class SignInsDialog extends Dialog {

    private Context context;
    private TextView go_bookmall;
    private ImageView imageView;
    private int signinCount;
    private TextView signinsDiaCount;
    private TextView signinsDialogMonth;
    public SignInsDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public SignInsDialog(@NonNull Context context, int themeResId , int signinCount) {
        super(context, themeResId);
        this.context = context;
        this.signinCount = signinCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signins_dialog);
        imageView = findViewById(R.id.background_image);
        signinsDiaCount = findViewById(R.id.signins_dia_count);
        signinsDialogMonth = findViewById(R.id.signins_dialog_month);
        signinsDiaCount.setText(signinCount + "");
        RoundedCorners roundedCorners= new RoundedCorners(20);
        Glide.with(context)
                .load(R.drawable.sign_in_background)
                .apply(new RequestOptions().bitmapTransform(roundedCorners).error(R.drawable.tu_wuzhuangtai).placeholder(R.drawable.tu_jiazai))
                .into(imageView);
        go_bookmall = findViewById(R.id.signins_dialog_go_bookmall);
        go_bookmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(EventBusTag.INTENT_BOOK_MALL);
                dismiss();
            }
        });

        //获取今日日期
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int months = calendar.get(Calendar.MONTH) + 1;
        signinsDialogMonth.setText(year + "年" + months + "月");
    }

}
