package com.zane.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext = null;
//    protected Gson mGson = null;
    protected View noDataView, errorView;
    protected Intent mGetIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置App只能竖屏显示
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentV();
        mContext = this;
        mGetIntent = getIntent();
//        mGson = new Gson();
        getIntentData();
        initView();
        initData();
    }

    /**
     * 网络错误刷新
     */
    public abstract void netRefresh();
    /**
     * 初始化布局
     */
    public abstract void setContentV();
    /**
     * 获取Intent数据
     */
    public abstract void getIntentData();
    /**
     * 初始化控件
     */
    public abstract void initView();
    /**
     * 初始化数据
     */
    public abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        OkGo.getInstance().cancelTag(this);
    }
}
