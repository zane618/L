package com.zane.ui;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.zane.l.R;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;
import okhttp3.OkHttpClient;

/**
 * Created by shizhang on 2017/6/26.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, this.getResources().getString(R.string.bmob_app_id));
        BmobUpdateAgent.setUpdateCheckConfig(false);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(this);
        OkGo.getInstance().setOkHttpClient(builder.build());
    }
}
