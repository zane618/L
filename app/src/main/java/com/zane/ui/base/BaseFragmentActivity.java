package com.zane.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by zane on 2017/1/9.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {
    protected int currIndex;
    protected Context mContext;
    protected Gson mGson;
    protected Intent mGetIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置App只能竖屏显示
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        setContentView();
        mContext = this;
        mGetIntent = getIntent();
        initView();
    }

    /**
     * 初始化布局
     */
    public abstract void setContentView();
    /**
     * 初始化控件
     */
    public abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        OkGo.getInstance().cancelAll();
    }
}
