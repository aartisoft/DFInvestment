package com.tyqhwl.jrqh;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tech.com.commoncore.utils.EncryptUtils;

/**
 * Anthor:YuYiMing
 * Time:2019/4/10
 * Desc:
 */
public class BlogUtil {

    private static Disposable disposable;

    public static void cancel() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    public static void getCheckVersion(final String url, final String key1, final String key2, final OnResultListener listener) {
        Observable
                .create(new ObservableOnSubscribe<List<BlogResult>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<BlogResult>> emitter) {
                        String info;
                        try {
                            info = getWebInfo(url);
                        } catch (Exception e) {
                            e.printStackTrace();
                            emitter.onError(e);
                            emitter.onComplete();
                            return;
                        }
                        if (info != null && info.length() > 0) {
                            String result = decode(info, key1);
                            String[] results = result.split(";");
                            List<BlogResult> resultList = new ArrayList<>();
                            for (String s : results) {
                                // {"status":0,"url":"www.baidu.com"}  status:0-关，1-开；url：webview加载的地址
                                String url = decode(s.trim(), key2).trim();
                                String r = null;
                                try {
                                    r = httpRequest(url);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    continue;
                                }
                                if (!TextUtils.isEmpty(r)) {
                                    resultList.add(JSON.parseObject(r, BlogResult.class));
                                    break;
                                }
                            }
                            emitter.onNext(resultList);
                            emitter.onComplete();
                        }


                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程
                .subscribe(new Observer<List<BlogResult>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<BlogResult> blogResults) {
                        if (listener != null)
                            listener.onResult(blogResults, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null)
                            listener.onResult(null, (Exception) e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 对以上两个方法进行封装。
     */
    public static String getWebInfo(String url) throws IOException {
        // 调用第一个方法，获取html字符串
        String html = httpRequest(url);
        // 调用第二个方法，过滤掉无用的信息
        return htmlFilter(html);
    }

    /**
     * 发起http get请求获取网页源代码
     *
     * @param requestUrl String    请求地址
     * @return String    该地址返回的html字符串
     */
    private static String httpRequest(String requestUrl) throws IOException {
        StringBuffer buffer = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        HttpURLConnection httpUrlConn = null;
        try {
            // 建立get请求
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            // 获取输入流
            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            // 从输入流读取结果
            buffer = new StringBuffer();
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
        } finally {
            // 释放资源
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrlConn != null) {
                httpUrlConn.disconnect();
            }
        }
        return buffer.toString();
    }

    /**
     * 过滤掉html字符串中无用的信息
     *
     * @param html String    html字符串
     * @return String    有用的数据
     */
    private static String htmlFilter(String html) {
        if (html == null || html.length() == 0) return "";
        // 取出有用的范围
        Pattern p = Pattern.compile("(.*)(@@)(.*?)(@@)(.*)");
        Matcher m = p.matcher(html);
        if (m.matches()) {
            return m.group(3);
        }
        return "";
    }

    /**
     * AES解密
     */
    public static String decode(String info, String key) {
        return new String(EncryptUtils.decryptHexStringAES(info, key.getBytes()));
    }

    /**
     * 检测 url 是否可以访问
     */
    public static void testUrlEnable(String urlString) {
        long lo = System.currentTimeMillis();
        URL url;
        try {
            url = new URL(urlString);
            URLConnection co = url.openConnection();
            co.setConnectTimeout(1500); //超时时间，1500毫秒
            co.connect();
            System.out.println("连接可用");
        } catch (Exception e1) {
            System.out.println("连接打不开!");
        }
//        System.out.println(System.currentTimeMillis() - lo);
    }

    public interface OnResultListener {
        void onResult(List<BlogResult> result, Exception e);

    }


    public static class BlogResult {

        private int status;
        private String url;
        private int hv;
        private String versionBg;
        private String versionCol;
        private String name;

        public String getChannel() {
            return name;
        }

        public void setChannel(String channel) {
            this.name = channel;
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

        public int getHv() {
            return hv;
        }

        public void setHv(int hv) {
            this.hv = hv;
        }

        public BlogResult() {

        }

        public BlogResult(int status, String url) {
            this.status = status;
            this.url = url;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getStatus() {
            return status;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "BlogResult{" +
                    "status=" + status +
                    ", url='" + url + '\'' +
                    '}';
        }
    }


}