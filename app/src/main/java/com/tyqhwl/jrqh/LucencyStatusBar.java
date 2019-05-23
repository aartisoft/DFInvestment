package com.tyqhwl.jrqh;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * 进行状态栏，标题栏的显示与隐藏
 * wmy
 * 2019.02.23
 * */
public class LucencyStatusBar {
    public static final String SHOW_STATUS_BAR = "showStatusBar";
    public static final String LUCENCY_STATUS_BAR = "lucencyStatusBar";
    public static final String SHOW_ACTION_BAR = "showActionBar";
    public static final String LUCENCY_ACTION_BAR = "lucencyActionBar";

    public static void getLucencyStatusBar( boolean statusBarType , Activity activity){
        if (statusBarType == false){//隐藏导航栏以及标题栏
            if (Build.VERSION.SDK_INT >= 21){
                View decorView = activity.getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

            }
        }
    }
}
