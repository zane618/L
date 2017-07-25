package com.zane.ads;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shizhang on 2017/7/10.
 */

public interface ADInterface {

    void loadNativeAd(Context context, int adPositin, OnAdsListener onAdsListener);

    void loadInterstitialAd(Context context, int adPosition);
    boolean loadSplashAd(Context context, OnAdsListener onAdsListener, View adLayout);

    void loadBannerAd(Context context, int adPosition, OnAdsListener onAdsListener, ViewGroup viewGroup);

    void loadExitAd(Context context, OnAdsListener onAdsListener);

    void onAdExposured(View view, Object o, int adPostion);

    void onAdTouchListener(MotionEvent event, int adPosition);

    void onAdClickListener(View view, Object o);
}
