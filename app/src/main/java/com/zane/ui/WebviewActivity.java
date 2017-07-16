package com.zane.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.zane.l.R;
import com.zane.ui.base.BaseActivity;

/**
 * Created by shizhang on 2017/7/16.
 */

public class WebviewActivity extends BaseActivity {
    private ViewGroup layoutMain;
    private WebView webView;

    @Override
    public void netRefresh() {

    }

    @Override
    public void setContentV() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initView() {
        layoutMain = (ViewGroup) findViewById(R.id.layout_main);
        webView = new WebView(this);
        WebSettings webSettings = webView .getSettings();

//支持获取手势焦点，输入用户名、密码或其他
        webView.requestFocusFromTouch();

        webSettings.setJavaScriptEnabled(true);  //支持js
//        webSettings.setPluginsEnabled(true);  //支持插件,没找到方法？

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(false);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//        webView.setWebChromeClient(new WebChromeClient());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutMain.addView(webView, lp);
        String url_ad = mGetIntent.getStringExtra("url_ad");
        if (!TextUtils.isEmpty(url_ad)) {
            webView.loadUrl(url_ad);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        layoutMain.removeView(webView);
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
