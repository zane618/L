package com.zane.ads.youmiads;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zane.ads.BaseADManager;
import com.zane.ads.OnAdsListener;
import com.zane.utility.L;

import ner.gfd.pjp.nm.bn.BannerManager;
import ner.gfd.pjp.nm.bn.BannerViewListener;
import ner.gfd.pjp.nm.sp.SpotListener;
import ner.gfd.pjp.nm.sp.SpotManager;
import ner.gfd.pjp.nm.sp.SpotRequestListener;


/**
 * Created by shizhang on 2017/7/27.
 */

public class YoumiAdManager extends BaseADManager{
    @Override
    public void loadNativeAd(Context context, int adPositin, OnAdsListener onAdsListener) {

    }

    @Override
    public void loadInterstitialAd(Context context, int adPosition, OnAdsListener l) {
        SpotManager.getInstance(context)
                .setImageType(SpotManager.IMAGE_TYPE_VERTICAL);
        SpotManager.getInstance(context)
                .setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);
        SpotManager.getInstance(context)
                .showSpot(context, new SpotListener() {
                    @Override
                    public void onShowSuccess() {

                    }

                    @Override
                    public void onShowFailed(int i) {

                    }

                    @Override
                    public void onSpotClosed() {

                    }

                    @Override
                    public void onSpotClicked(boolean b) {

                    }
                });
    }

    @Override
    public boolean loadSplashAd(Context context, OnAdsListener onAdsListener, View adLayout) {
        return false;
    }

    @Override
    public void loadBannerAd(Context context, int adPosition, final OnAdsListener onAdsListener, ViewGroup viewGroup) {
        View banner = BannerManager.getInstance(context)
                .getBannerView(context, new BannerViewListener() {
                    @Override
                    public void onRequestSuccess() {
                        onAdsListener.onAdsLoaded(true, null, null, BaseADManager.AD_PLATFORM_YOUMI, null);
                    }

                    @Override
                    public void onSwitchBanner() {

                    }

                    @Override
                    public void onRequestFailed() {
                        onAdsListener.onAdsLoaded(false, null, null, BaseADManager.AD_PLATFORM_YOUMI, null);
                    }
                });
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(banner, lp);
    }

    @Override
    public void loadExitAd(Context context, OnAdsListener onAdsListener) {

    }

    @Override
    public void onAdExposured(View view, Object o, int adPostion) {

    }

    @Override
    public void onAdTouchListener(MotionEvent event, int adPosition) {

    }

    @Override
    public void onAdClickListener(View view, Object o) {

    }

    @Override
    public void requestSpot(Context context) {
        SpotManager.getInstance(context).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {

            }

            @Override
            public void onRequestFailed(int i) {

            }
        });
    }

    @Override
    public void onPause(Context context) {
        SpotManager.getInstance(context).onPause();
    }

    @Override
    public void onStop(Context context) {
        SpotManager.getInstance(context).onStop();
    }

    @Override
    public void onDestroy(Context context) {
        SpotManager.getInstance(context).onDestroy();
    }

    @Override
    public void onAppExit(Context context) {
        SpotManager.getInstance(context).onDestroy();

    }

    @Override
    public boolean onBackkeyEvent(Context context) {
        if (SpotManager.getInstance(context).isSpotShowing()) {
            SpotManager.getInstance(context).hideSpot();
            return true;
        }
        return false;
    }

    private static YoumiAdManager instance;

    public static YoumiAdManager getInstance(Context context) {
        if (instance == null) {
            instance = new YoumiAdManager();
        }
        return instance;
    }
}
