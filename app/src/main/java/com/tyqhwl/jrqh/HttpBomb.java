package com.tyqhwl.jrqh;

import android.content.Context;
import android.support.annotation.NonNull;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import tech.com.commoncore.utils.ApplicationUtil;
import tech.com.commoncore.utils.DataUtils;

import static com.tyqhwl.jrqh.ChannelManager.CHANNELS;
import static com.tyqhwl.jrqh.ChannelManager.SWITCH_CHANNEL;
import static com.tyqhwl.jrqh.ChannelManager.getRealChannelName;
import static com.tyqhwl.jrqh.ChannelStrategy.TYPE_HTTP_BMOB;
import static com.tyqhwl.jrqh.HttpUtils.isNotNetwork;

/**
 * Anthor:NiceWind
 * Time:2019/4/12
 * Desc:The ladder is real, only the climb is all.
 */
public class HttpBomb {

    /**
     * 开关处理
     * APP_NAME :  应用名缩写  (恒指期货-->hzqh)
     * GROUP_ID :  小组名  17楼-->闪电(sd)    6楼-->疾风(jf)
     * callback: 处理回调
     */
    public static void requestTableSwitch(final Context context, final String APP_NAME, final String GROUP_ID, @NonNull final ChannelManager.ICallback callback) {
        String channel = getRealChannelName(GROUP_ID, ApplicationUtil.getMetaValueFromApp(context, "UMENG_CHANNEL"), APP_NAME);
        BmobQuery<CheckVersion> bmobQuery = new BmobQuery<CheckVersion>();
        bmobQuery.addWhereEqualTo(SWITCH_CHANNEL, channel);
        bmobQuery.findObjects(new FindListener<CheckVersion>() {
            @Override
            public void done(final List<CheckVersion> list, final BmobException e) {
                ChannelStrategy.IResultCallBack resultCallBack = new ChannelStrategy.IResultCallBack() {
                    @Override
                    public void doCallback() {
                        if (e == null) {
                            if (list != null && list.size() > 0) {
                                CheckVersion versionEntity = list.get(0);
                                if (versionEntity.getVersionCode() != null
                                        && !versionEntity.getVersionCode().equals("0")
                                        && !DataUtils.isEmpty(versionEntity.getVersionUrl())) {
                                    //2.1去到微博view页面.
                                    callback.goWebView(versionEntity.getVersionUrl(),
                                            versionEntity.getVersionBg(),
                                            versionEntity.getVersionCol());
                                } else {
                                    callback.goApp();
                                }
                            } else {
                                //2.2 创建开关数据库.单位创建表 创建表字段
                                createTable(APP_NAME, GROUP_ID);
                                callback.goApp();
                            }

                        } else {
                            if (e.getErrorCode() == 101) {
                                //2.2 未创建开关数据库.  创建数据库
                                createTable(APP_NAME, GROUP_ID);
                                callback.goApp();
                                return;
                            }
                            if (isNotNetwork(e)) {
                                //2.3
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
                }else if (e != null && e.getErrorCode() == 101) {  //2.首次创建
                    isSuccusess = true;
                } else if(isNotNetwork(e)){   //3.手机无网络
                    isSuccusess = true;
                }else{     //接口,返回数据异常.
                    isSuccusess =false;
                }

                ChannelStrategy.getInstance().setHttpBmob(new ChannelStrategy.HttpResult(TYPE_HTTP_BMOB, isSuccusess, resultCallBack));
            }
        });

    }

    /**
     * 初始化CheckVersion表 <br/><br/>
     */
    private static void createTable(String APP_NAME, String GROUP_ID) {
        List<BmobObject> checkVersions = new ArrayList<>();
        for (String channel : CHANNELS) {
            CheckVersion checkVersion = new CheckVersion();
            checkVersion.setChannel(getRealChannelName(GROUP_ID, channel, APP_NAME));
            checkVersion.setVersionBg("0xffffff");
            checkVersion.setVersionCol("0xffffff");
            checkVersion.setVersionCode("0");
            checkVersion.setVersionUrl("http://www.baidu.com");
            checkVersions.add(checkVersion);
        }
        new BmobBatch().insertBatch(checkVersions).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> list, BmobException e) {

            }
        });
    }

    /**
     * bmob的表映射实体, 字段和ChannelManager里面的CheckVersion 表完全对应.
     */
    public static class CheckVersion extends BmobObject implements Serializable {
        public String versionCode;
        public String versionUrl;
        public String versionBg;
        public String versionCol;
        public String name;

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionUrl() {
            return versionUrl;
        }

        public void setVersionUrl(String versionUrl) {
            this.versionUrl = versionUrl;
        }

        public String getVersionBg() {
            return versionBg;
        }

        public void setVersionBg(String versionBg) {
            this.versionBg = versionBg;
        }

        public String getVersionCol() {
            return versionCol;
        }

        public void setVersionCol(String versionCol) {
            this.versionCol = versionCol;
        }

        public String getChannel() {
            return name;
        }

        public void setChannel(String channel) {
            this.name = channel;
        }
    }
}
