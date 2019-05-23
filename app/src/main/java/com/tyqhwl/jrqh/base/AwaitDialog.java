package com.tyqhwl.jrqh.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tyqhwl.jrqh.R;


//网络连接等待页面
public class AwaitDialog extends Dialog {
    private Context context;
    public AwaitDialog(@NonNull Context context) {
        super(context);
    }

    public AwaitDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AwaitDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.await_dialog);
    }
}
