package com.tyqhwl.jrqh;

import android.content.Context;
import android.support.annotation.NonNull;


import java.util.Arrays;

/**
 * Time:2019/3/5
 * Desc: 渠道解析跳转管理.   针对leanCloud 建表处理.leancloud的开关跳转判断
 */
public class ChannelManager {

    /**
     * 发布渠道，和 productFlavors 对应
     */
    public static final String[] CHANNELS = {"vivo", "oppo", "mz", "lx", "qh360", "yyb", "yyh", "sg", "jl", "hw", "mi"};
    public static final String[] GROUPS = {"jf","sd","lt","tko","tks"}; // 开发小组组名 17楼-->闪电(sd)    6楼-->疾风(jf)  后期根据小组增加添加.

    public  static final String TABLE_SWITCH ="CheckVersion";
    public  static final String SWITCH_NEED_SWITCH ="versionCode";   // 0/空：正常界⾯面，1： 跳转到H5的界⾯面；
    public  static final String SWITCH_URL ="versionUrl";
    public  static final String SWITCH_TOOLBAR_COLOR ="versionBg";   //为H5界⾯面状态栏⽂文字颜⾊色，输⼊入以 0x 开头的颜⾊色 如0x000000代表⿊黑⾊色
    public  static final String SWITCH_NAVIGATION_COLOR ="versionCol"; //(无用字段) 字段为H5界⾯面导航栏背景⾊色，输⼊入以 0x 开头的颜⾊色 如0x000000代表⿊黑⾊色；
    public  static final String SWITCH_CHANNEL ="name";

    /**
     * 开关处理
     * APP_NAME :  应用名缩写  (恒指期货-->hzqh)
     * GROUP_ID :  小组名  17楼-->闪电(sd)    6楼-->疾风(jf)
     * callback: 处理回调
     */
    public static void requestTableSwitch(final Context context, final String APP_NAME, final String GROUP_ID, @NonNull final ICallback callback){
        HttpLeancloud.requestTableSwitch(context,APP_NAME,GROUP_ID,callback);
        HttpBomb.requestTableSwitch(context,APP_NAME,GROUP_ID,callback);
        HttpBlog.requestTableSwitch(context,APP_NAME,GROUP_ID,callback);
    }

    /**
     * 获取真实的  渠道名 (leancloud 上面使用的)
     * @param channel
     * @param APP_NAME
     * @return
     */
    public static String getRealChannelName(String GROUP_ID,String channel,String APP_NAME)  {
        if(!Arrays.asList(GROUPS).contains(GROUP_ID)){
            throw new RuntimeException("传入的小组名出错");
        }
        if(APP_NAME == null || APP_NAME.equals("")){
            throw new RuntimeException("请传入应用缩写");
        }
        return  GROUP_ID + APP_NAME + channel;
    }

    /**
     * 开关校验后的回调.
     */
    public interface  ICallback{
        void showNetWorkError(Exception e);
        void goApp();
        void goWebView(String url, String toolbarColor, String navColor);
    }
}
