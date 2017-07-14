package com.zane.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zane on 2017/1/9.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {
    protected int currIndex;
    protected Context mContext;
//    protected Gson mGson;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        mContext = this;
//        mGson = new Gson();
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
    protected void onDestroy() {
        super.onDestroy();
//        OkGo.getInstance().cancelAll();
    }
}
