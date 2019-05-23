package com.tyqhwl.jrqh;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aries.ui.util.StatusBarUtil;
import com.aries.ui.view.title.TitleBarView;
import com.bumptech.glide.Glide;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.base.BrowerActivity;
import tech.com.commoncore.utils.FastUtil;
import tech.com.commoncore.utils.SPUtil;
import tech.com.commoncore.widget.NetErrorDialog;

public class SplashActivity extends BaseTitleActivity {
    public static final int TIME_DELAY =  2000;  //延迟跳转时间
    private ImageView imageView;
    @Override
    public int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void beforeSetContentView() {
        if (!isTaskRoot()) {//防止应用后台后点击桌面图标造成重启的假象---MIUI及Flyme上发现过(原生未发现)
            finish();
            return;
        }
        super.beforeSetContentView();
    }
    @Override
    public void initView(Bundle savedInstanceState) {
        imageView = findViewById(R.id.acct_splash_loading);
        Glide.with(this)
                .load(R.drawable.load_image)
                .into(imageView);
        if (!StatusBarUtil.isSupportStatusBarFontChange()) {
            //隐藏状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
//        CacheData.requestFutureAll(); //缓存必要数据(期货分类所有数据)
        jumpSwitch();

    }

    /**
     * 1. 跳转开关,处理跳转webView还是跳转自身
     */
    private void jumpSwitch(){
        ChannelManager.requestTableSwitch(mContext,"dfqhh",ChannelManager.GROUPS[2],new ChannelManager.ICallback(){

            @Override
            public void showNetWorkError(Exception e) {

            }

            @Override
            public void goApp() {
                goAPP();
            }
            @Override
            public void goWebView(String url, String toolbarColor, String navColor) {
                //去到webView
                nextJump(3,url,toolbarColor,navColor);
            }
        });
    }

    /**
     * 2.处理APP 自身的逻辑
     */
    private void goAPP(){
        if (SPUtil.contains(this, "HAS-GUIDED")) {
            //如果不是第一次就直接自动登录
            autoLogin();
        } else {
            //第一次就进GUIDE 页面
            SPUtil.put(this, "HAS-GUIDED", "has-guided");
            nextJump(0,null,null,null);
        }
    }

    /**
     * 3.自动登录
     */
    private  void  autoLogin() {
//        String account = CacheData.getLoginAccount(mContext);
//        String password = CacheData.getLoginPassword(mContext);
//        if (!DataUtils.isEmpty(account) && !DataUtils.isEmpty(password)) {
//            AVUser.loginByMobilePhoneNumberInBackground(account, password, new LogInCallback<AVUser>() {
//                @Override
//                public void done(AVUser avUser, AVException e) {
//                    nextJump(1,null,null,null);
//                }
//            });
//        } else {
//            nextJump(1,null,null,null);
//        }

        nextJump(1,null,null,null);
    }

    /**
     * 4.当网络错误时,处理错误页面展示
     */
    Dialog netErrorDialog;
    private void showNetError () {
        if (netErrorDialog == null) {
            netErrorDialog = new NetErrorDialog(mContext).setOnCloseListener(new NetErrorDialog.OnCloseListener() {
                @Override
                public void onClose() {
                    SplashActivity.this.finish();
                }
            }).getDialog();
            netErrorDialog.setCancelable(false);
            netErrorDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    SplashActivity.this.finish();
                }
            });
        }
        netErrorDialog.show();
    }



    /**
     * 跳转统一延迟处理.
     * @param type 0:guide页面; 1:主页; 其他:webview
     * @param url
     */
    private void nextJump(int type , final String url,final String toolBarcolor, final String navcolor){
        if(type == 0){
            mContentView.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    imageView.setVisibility(View.GONE);
                    //TODO:需要设计给出图片
                    //第一次就进GUIDE 页面
                    FastUtil.startActivity(mContext,MainActivity.class);
//                    FastUtil.startActivity(mContext,GuideActivity.class);
                    SplashActivity.this.finish();
                }
            }, TIME_DELAY);
        }else if(type ==1){
            mContentView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //进入主页
                    FastUtil.startActivity(mContext,MainActivity.class);
                    SplashActivity.this.finish();
                }
            }, TIME_DELAY);

        }else{
            mContentView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //去到微博view页面.
                    startActivity(BrowerActivity.actionToActivity(mContext,url,toolBarcolor,navcolor));
                    SplashActivity.this.finish();
                }
            }, TIME_DELAY);
        }
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setLeftTextDrawable(null)
                .setBackgroundColor(Color.TRANSPARENT);
    }


}
