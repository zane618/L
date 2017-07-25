package com.zane.ui;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.zane.l.R;
import com.zane.ui.base.BaseFragmentActivity;

/**
 * Created by shizhang on 2017/7/16.
 */

public class WebviewActivity extends BaseFragmentActivity implements View.OnClickListener{
    private ViewGroup layoutMain;
    private AgentWeb agentWeb;
    private View tvBack;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void initView() {
        layoutMain = (ViewGroup) findViewById(R.id.layout_main);
        tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(this);
        if (mGetIntent == null) {
            finish();
        }
        String url_ad = mGetIntent.getStringExtra("url_ad");
//        String url_ad = "https://www.baidu.com/";
        if (!TextUtils.isEmpty(url_ad)) {
            agentWeb = AgentWeb.with(this)
                    .setAgentWebParent(layoutMain, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                    .useDefaultIndicator()// 使用默认进度条
                    .defaultProgressBarColor() // 使用默认进度条颜色
//                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                    .createAgentWeb()//
                    .ready()
                    .go(url_ad);
        }

    }

    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == tvBack) {
            finish();
        }
    }
}
