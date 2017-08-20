package com.zane.ui;

import android.app.Application;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.zane.ads.ADManagerFactory;
import com.zane.l.BuildConfig;
import com.zane.l.R;
import com.zane.utility.L;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;
import okhttp3.OkHttpClient;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
/**
 * Created by shizhang on 2017/6/26.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    public static RequestOptions options;
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, this.getResources().getString(R.string.bmob_app_id));
        BmobUpdateAgent.setUpdateCheckConfig(false);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(this)
        .setOkHttpClient(builder.build());
        initGildeOptions();
        ADManagerFactory.initAdPlatform(this);
        L.isDebug = BuildConfig.LOG;
    }
    public MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }
    //glide options初始化
    private void initGildeOptions() {
        options = new RequestOptions();
        options.error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }
}
