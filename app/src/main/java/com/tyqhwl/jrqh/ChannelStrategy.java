package com.tyqhwl.jrqh;

import android.support.annotation.NonNull;

/**
 * Anthor:NiceWind
 * Time:2019/4/11
 * Desc:The ladder is real, only the climb is all.
 */
public class ChannelStrategy {
    public static final String TYPE_HTTP_LEANCLOUD = "leancloud";
    public static final String TYPE_HTTP_BMOB = "Bmob";
    public static final String TYPE_HTTP_BLOG = "blog";

    /**
     * 创建策略 单列.
     */
    private static class SingletonHolder {
        private static final ChannelStrategy INSTANCE = new ChannelStrategy();
    }

    private ChannelStrategy() {
    }

    public static final ChannelStrategy getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 状态池
     */
    private HttpResult httpLeanCloud;
    private HttpResult httpBomb;
    private HttpResult httpBlog;

    /**
     * 增加结果状态池 的状态数据.   //触发策略选择.
     *
     * @param channelResult
     */
    public void setHttpLeancloud(HttpResult channelResult) {
        this.httpLeanCloud = channelResult;
        doStrategy();
    }

    public void setHttpBmob(HttpResult channelResult) {
        this.httpBomb = channelResult;
        doStrategy();
    }

    public void setHttpBlog(HttpResult channelResult) {
        this.httpBlog = channelResult;
        doStrategy();
    }


    /**
     * 先 leancloud ---> BMOB ---> blog
     */
    private synchronized void doStrategy() {
        //1.先判断leancloud;存在就处理,不存在等待下一次处理.
        if (httpLeanCloud == null) {
            return;
        } else {
            if (httpLeanCloud.hasCanceled) {
                return;
            }
            //1.1  返回成功处理,失败不做任何处理进入下一轮.
            if (httpLeanCloud.status) {
                httpLeanCloud.callback.doCallback();
                cancelAllHttp();
                return;
            }
        }

        //2.Bmob;存在就处理,不存在等待下一次处理.
        if (httpBomb == null) {
            return;
        } else {
            if (httpBomb.hasCanceled) {
                return;
            }
            if (httpBomb.status) {
                httpBomb.callback.doCallback();
                cancelAllHttp();
                return;
            }
        }

        //3.blog;存在就处理,不存在下一次处理.
        if (httpBlog == null) {
            return;
        } else {
            if (httpBlog.hasCanceled) {
                return;
            }
            if (httpBlog.status) {
                httpBlog.callback.doCallback();
                cancelAllHttp();
                return;
            }
        }
        /*if (!isIntoMain) {
            //最终都不能处理, 跳转到App.
            Intent intent = new Intent(App.getInstance(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getInstance().startActivity(intent);
            isIntoMain = true;
        }
*/
    }

    /**
     * 关闭所有 的请求状态.
     */
    private void cancelAllHttp() {
        if (httpLeanCloud != null) {
            httpLeanCloud.hasCanceled = true;
        }
        if (httpBomb != null) {
            httpBomb.hasCanceled = true;
        }
        if (httpBlog != null) {
            httpBlog.hasCanceled = true;
        }
    }

    /**
     * 结果状态类,策略用来处结果.  不能完全按,权重处理.
     */
    public static class HttpResult {
        public HttpResult(String type, boolean status, @NonNull IResultCallBack callback) {
            this.type = type;
            this.status = status;
            this.callback = callback;
        }

        public String type;    //权重类型, leancloud\bmob\blog
        public boolean status;   //请求成功,失败
        public boolean hasCanceled;   //手动关闭停止.
        public IResultCallBack callback;   //回调
    }

    public interface IResultCallBack {
        void doCallback();
    }


}
