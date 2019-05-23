package com.tyqhwl.jrqh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import java.util.ArrayList;
import java.util.List;
import tech.com.commoncore.utils.ApplicationUtil;
import tech.com.commoncore.utils.DataUtils;
import static com.tyqhwl.jrqh.ChannelManager.CHANNELS;
import static com.tyqhwl.jrqh.ChannelManager.SWITCH_CHANNEL;
import static com.tyqhwl.jrqh.ChannelManager.SWITCH_NAVIGATION_COLOR;
import static com.tyqhwl.jrqh.ChannelManager.SWITCH_NEED_SWITCH;
import static com.tyqhwl.jrqh.ChannelManager.SWITCH_TOOLBAR_COLOR;
import static com.tyqhwl.jrqh.ChannelManager.SWITCH_URL;
import static com.tyqhwl.jrqh.ChannelManager.TABLE_SWITCH;
import static com.tyqhwl.jrqh.ChannelManager.getRealChannelName;
import static com.tyqhwl.jrqh.ChannelStrategy.TYPE_HTTP_LEANCLOUD;
import static com.tyqhwl.jrqh.HttpUtils.isNotNetwork;

/**
 * Anthor:NiceWind
 * Time:2019/4/12
 * Desc:The ladder is real, only the climb is all.
 */
public class HttpLeancloud {

    /**
     * 开关处理
     * APP_NAME :  应用名缩写  (恒指期货-->hzqh)
     * GROUP_ID :  小组名  17楼-->闪电(sd)    6楼-->疾风(jf)
     * callback: 处理回调
     */
    public static void requestTableSwitch(final Context context, final String APP_NAME, final String GROUP_ID, @NonNull final ChannelManager.ICallback callback) {
        String channel = getRealChannelName(GROUP_ID, ApplicationUtil.getMetaValueFromApp(context, "UMENG_CHANNEL"), APP_NAME);

        AVQuery avQuery = new AVQuery(TABLE_SWITCH);
        avQuery.whereEqualTo(SWITCH_CHANNEL, channel);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(final List<AVObject> list, final AVException e) {

                ChannelStrategy.IResultCallBack resultCallBack = new ChannelStrategy.IResultCallBack() {
                    @Override
                    public void doCallback() {
                        if (e == null) {
                            if (list != null && list.size() > 0) {
                                AVObject channelObject = list.get(0);
                                if (channelObject.getString(SWITCH_NEED_SWITCH) != null
                                        && !channelObject.getString(SWITCH_NEED_SWITCH).equals("0")
                                        && !DataUtils.isEmpty(channelObject.getString(SWITCH_URL))) {
                                    //2.1去到微博view页面.
                                    callback.goWebView(channelObject.getString(SWITCH_URL),
                                            channelObject.getString(SWITCH_TOOLBAR_COLOR),
                                            channelObject.getString(SWITCH_NAVIGATION_COLOR));
                                } else {
                                    callback.goApp();
                                }

                            } else {
                                //2.2 创建开关数据库.单位创建表 创建表字段
                                createTable(APP_NAME, GROUP_ID);
                                callback.goApp();
                            }
                        } else {
                            Log.e("channel", e.toString());
                            if (e.getCode() == 101) {
                                //2.2 未创建开关数据库.  创建数据库
                                createTable(APP_NAME, GROUP_ID);
                                callback.goApp();
                                return;
                            }
                            if (isNotNetwork(e)) {
                                //2.3网络错误
                                callback.showNetWorkError(e);
                            } else {
//                                callback.goApp();
                            }
                        }
                    }
                };
                boolean isSuccusess = true;
                if(e==null){   //1.无异常
                    isSuccusess = true;
                }else if (e != null && e.getCode() == 101) {  //2.首次创建
                    isSuccusess = true;
                } else if(isNotNetwork(e)){   //3.手机无网络
                    isSuccusess = true;
                }else{     //接口,返回数据异常.
                    isSuccusess =false;
                }
                ChannelStrategy.getInstance().setHttpLeancloud(new ChannelStrategy.HttpResult(TYPE_HTTP_LEANCLOUD, isSuccusess, resultCallBack));
            }
        });
    }

    /**
     * 初始化CheckVersion表 <br/><br/>
     */
    private static void createTable(String APP_NAME, String GROUP_ID) {
        List<AVObject> items = new ArrayList<>();
        for (String channel : CHANNELS) {
            AVObject t_check_version = new AVObject(TABLE_SWITCH);
            t_check_version.put(SWITCH_CHANNEL, getRealChannelName(GROUP_ID, channel, APP_NAME));
            t_check_version.put(SWITCH_NEED_SWITCH, "0");
            t_check_version.put(SWITCH_URL, "http://www.baidu.com");
            t_check_version.put(SWITCH_TOOLBAR_COLOR, "0xffffff");
            t_check_version.put(SWITCH_NAVIGATION_COLOR, "0x#ffffff");
            items.add(t_check_version);
        }
        AVObject.saveAllInBackground(items);
    }

}
