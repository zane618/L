package com.zane.ads;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shizhang on 2017/7/10.
 */

public interface ADInterface {

    boolean loadSplashAd(Context context, OnAdsListener onAdsListener, View adLayout);

    void loadBannerAd(Context context, int adPosition, OnAdsListener onAdsListener, ViewGroup viewGroup);

    void loadExitAd(Context context, OnAdsListener onAdsListener);

    void onAdTouchListener(MotionEvent event, int adPosition);

    void onAdClickListener(View view, Object o);
}
