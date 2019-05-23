package com.tyqhwl.jrqh;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.interceptor.HttpLogInterceptor;

import cn.bmob.v3.Bmob;
import tech.com.commoncore.app.FastManager;
import tech.com.commoncore.base.BaseApplication;
import tech.com.commoncore.constant.ApiConstant;
import tech.com.commoncore.utils.Utils;

//import com.umeng.analytics.MobclickAgent;
//import com.umeng.commonsdk.UMConfigure;
//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.PushAgent;
//import com.umeng.socialize.PlatformConfig;

//import com.umeng.socialize.PlatformConfig;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.UMShareConfig;


/**
 * @Author: AriesHoo on 2018/7/31 10:43
 * @E-Mail: AriesHoo@126.com
 * Function:基础配置Application
 * Description:
 */
public class App extends BaseApplication {
    private String TAG = "BaseApp";

    @Override
    public void init() {

        AVOSCloud.initialize(this, "xkU0WDd2s5papbcmzgvRUQGw-gzGzoHsz", "hhQX4udpaoS902UzEAB55Tmq");
        AVOSCloud.setDebugLogEnabled(true);
        Bmob.initialize(this , "82a76cb25b97d5c55b4c9288eabd0188");
        initLog();
        initNet();

        //极光推送
//        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);            // 初始化 JPush

        Utils.init(this);
        //最简单UI配置模式-必须进行初始化
        FastManager.init(this);
        //以下为更丰富自定义方法
        //全局UI配置参数-按需求设置
        AppImpl impl = new AppImpl(getInstance());
        FastManager.getInstance()
                //设置Adapter加载更多视图--默认设置了FastLoadMoreView
                .setLoadMoreFoot(impl)
                //设置RecyclerView加载过程多布局属性
                .setMultiStatusView(impl)
                //设置全局网络请求等待Loading提示框如登录等待loading--观察者必须为FastLoadingObserver及其子类
                .setLoadingDialog(impl)
                //设置SmartRefreshLayout刷新头-自定加载使用BaseRecyclerViewAdapterHelper
                .setDefaultRefreshHeader(impl)
//                设置全局TitleBarView相关配置
                .setTitleBarViewControl(impl)
//                设置Activity滑动返回控制-默认开启滑动返回功能不需要设置透明主题
                .setSwipeBackControl(impl)
//                设置Activity/Fragment相关配置(横竖屏+背景+虚拟导航栏+状态栏+生命周期)
                .setActivityFragmentControl(impl)
//                设置BasisActivity 子类按键监听
                .setActivityKeyEventControl(impl)
                //设置http请求结果全局控制
                .setHttpRequestControl(impl)
//                //设置主页返回键控制-默认效果为2000 毫秒时延退出程序
                .setQuitAppControl(impl);
        initUmengSDK();
    }


    private void initLog() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true);//是否排版显示
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }

    private void initNet() {
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(ApiConstant.BASE_URL)
                .setCookie(true)
                .retryCount(3)
                //配置日志拦截器
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.HEADERS));

    }

    /**
     * 友盟初始化; 分享、統計、推送
     */
    private void initUmengSDK() {
        //https://www.jb51.net/article/137240.html
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.setLogEnabled(true);

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");

        //配置分享
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");


        //注册推送服务，每次调用register方法都会回调该接口
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
        MobclickAgent.setCatchUncaughtExceptions(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
