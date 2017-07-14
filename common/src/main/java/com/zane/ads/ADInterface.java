package com.zane.ads;

import android.content.Context;
import android.view.View;

/**
 * Created by shizhang on 2017/7/10.
 */

public interface ADInterface {
    /*
     * 支持的广告平台，只能新增，和配置的广告开关一一对应，同时新增
     */
    public static final int AD_PLATFORM_IFLY = 1;
    boolean loadSplashAd(Context context, OnAdsListener onAdsListener, View adLayout);
}
