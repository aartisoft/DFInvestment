package com.tyqhwl.jrqh.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.aries.ui.view.title.TitleBarView;
import com.tyqhwl.jrqh.R;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import tech.com.commoncore.base.BaseTitleActivity;
import tech.com.commoncore.constant.ApiConstant;
import tech.com.commoncore.utils.brower.SonicJavaScriptInterface;
import tech.com.commoncore.utils.brower.SonicRuntimeImpl;
import tech.com.commoncore.utils.brower.SonicSessionClientImpl;

/**
 * Created by dell on 2019/3/13.
 */

public class KLineActivity extends BaseTitleActivity {

    SonicSession sonicSession;

    public static final String SYMbOL_CODE = "symbol";
    public static final String SYMBOL_NAME = "name";
    public static final String TYPE = "TYPE";
    public static final int TYPE_INLINE = 1;


    private WebView mWebview;
    private ProgressBar progressBar;

    String symbolCode = "";
    String symbolName = "";

    String url;

    int type;

    @Override
    public int getContentLayout() {
        return R.layout.activity_future_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        Intent intent = getIntent();
        symbolCode = intent.getStringExtra(SYMbOL_CODE);
        type = intent.getIntExtra(TYPE, TYPE_INLINE);
        mWebview = (WebView) findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress_web);

        if (type == TYPE_INLINE) {
            url = ApiConstant.GET_FUTURE_DETAIL + "?param=" + SymbolUtil.getSymbol(symbolCode);
        } else {
            url = ApiConstant.GET_FUTURE_DETAIL + "?symbol=" + SymbolUtil.getSymbol(symbolCode);
        }

        /*String url = intent.getStringExtra(PARAM_URL);
        int mode = intent.getIntExtra(PARAM_MODE, -1);
        if (TextUtils.isEmpty(url) || -1 == mode) {
            finish();
            return;
        }*/

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }

        SonicSessionClientImpl sonicSessionClient = null;

        // if it's sonic mode , startup sonic session at first time
        // sonic mode
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);

        // if it's offline pkg mode, we need to intercept the session connection


        // create sonic session and run sonic flow
        sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
        } else {
            // this only happen when a same sonic session is already running,
            // u can comment following codes to feedback as a default mode.
            // throw new UnknownError("create session fail!");
            //            Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
        }

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                return !(url.startsWith("http") || url.startsWith("https"));
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });

        WebSettings webSettings = mWebview.getSettings();

        // add java script interface
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.setJavaScriptEnabled(true);
        mWebview.removeJavascriptInterface("searchBoxJavaBridge_");
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        mWebview.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "sonic");

        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(mWebview);
            sonicSessionClient.clientReady();
        } else { // default mode
            mWebview.loadUrl(url);
        }


        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
//                    progressBar.setVisibility(View.GONE); // 加载完毕进度条消失
                } else {
//                    progressBar.setProgress(newProgress); // 更新进度
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        symbolName = getIntent().getStringExtra(SYMBOL_NAME);
        titleBar.setTitleMainText(symbolName);
        titleBar.setTitleMainTextColor(getResources().getColor(R.color.colorWhite));
        titleBar.setLeftTextDrawable(R.mipmap.back_black);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroy();
    }
}
