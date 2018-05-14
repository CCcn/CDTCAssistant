package com.cdtc.student.cdtcassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.cdtc.student.cdtcassistant.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class WebActivity extends BaseTopActivity {

    private static final String TAG = "WebActivity";
    /**
     * webView
     */
    private WebView webView;

    /**
     * web页面链接
     */
    private String url = "";

    /**
     * action名字
     */
    public static final String WEB_URL = "web_view_url";

    /**
     * 浏览页面的标题
     * 暂时无法实现实时更新页面标题，只有跳转的时候传一个标题名称
     */
    public static final String TITLE = "title";

    /**
     * 根布局
     */
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        //启动时获取url
        url = getIntent().getStringExtra(WEB_URL);


        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //避免输入法界面弹出后遮挡输入光标的问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
    }


    private void initView() {
        webView = getView(R.id.web_view);
        rootLayout = getView(R.id.root_web_view);

        initTopBar("浏览");

        webView.loadUrl(url);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    /**
     * 打开一个web
     *
     * @param context 上下文
     * @param url     要打开的链接
     */
    public static void startWeb(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WEB_URL, url);
        intent.putExtra(TITLE, title);

        Log.i(TAG, "startWeb: " + " url:" + url + " title:" + title);

        context.startActivity(intent);
    }

    /**
     * 从父容器中移除webview,然后再销毁webview:
     * 防止关闭Activity后页面播放不能停止
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        rootLayout.removeView(webView);
        webView.destroy();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
