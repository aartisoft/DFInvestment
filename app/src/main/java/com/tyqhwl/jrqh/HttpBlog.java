package com.tyqhwl.jrqh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import java.util.List;
import static com.tyqhwl.jrqh.HttpUtils.isNotNetwork;


/**
 * Anthor:NiceWind
 * Time:2019/4/12
 * Desc:The ladder is real, only the climb is all.
 */
public class HttpBlog {

    public static void requestTableSwitch(final Context context, final String APP_NAME, final String GROUP_ID, @NonNull final ChannelManager.ICallback callback) {
        BlogUtil.getCheckVersion("https://blog.csdn.net/gsyes168/article/details/89182329", "1111111122222222", "42980fcm2d3409d!", new BlogUtil.OnResultListener() {
            @Override
            public void onResult(final List<BlogUtil.BlogResult> result, final Exception e) {
                /*boolean isSuccess = false;
                if (result != null && result.size() > 0) {
                    isSuccess = true;
                } else if (e != null) {
                    isSuccess = isNotNetwork(e);
                }*/

//                final boolean finalIsSuccess = isSuccess;
                ChannelStrategy.IResultCallBack resultCallBack = new ChannelStrategy.IResultCallBack() {
                    @Override
                    public void doCallback() {
                        if (result != null && result.size() > 0) {
                            for (BlogUtil.BlogResult blogResult : result) {
                                //0 关  1  开
                                if (blogResult.getStatus() == 1) {
                                    callback.goWebView(blogResult.getUrl(), blogResult.getVersionBg() == null ? ContextCompat.getColor(context, R.color.colorAccent) + "" : blogResult.getVersionBg(),
                                            blogResult.getVersionCol() == null ? ContextCompat.getColor(context, R.color.colorAccent) + "" : blogResult.getVersionCol());
                                    return;
                                }
                            }
                            callback.goApp();
                        } else if (e != null) {
                            if (isNotNetwork(e)) {
                                callback.showNetWorkError(e);
                            } else {
                                //
                                callback.goApp();
                            }
                        } else {
                            callback.goApp();
                        }
                    }
                };
                ChannelStrategy.getInstance().setHttpBlog(new ChannelStrategy.HttpResult(ChannelStrategy.TYPE_HTTP_BLOG, true, resultCallBack));
            }
        });
    }
}