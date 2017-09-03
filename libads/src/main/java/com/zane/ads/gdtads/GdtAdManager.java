package com.zane.ads.gdtads;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.qq.e.ads.cfg.BrowserType;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.comm.util.AdError;
import com.zane.ads.ADData;
import com.zane.ads.BaseADManager;
import com.zane.ads.OnAdsListener;
import com.zane.ads.R;
import com.zane.ads.view.CenterAdView;
import com.zane.ads.view.LeftAdView;
import com.zane.utility.L;

import java.util.List;

/**
 * Created by shizhang on 2017/9/3.
 */

public class GdtAdManager extends BaseADManager {
    private static final String TAG = "GdtAdManager";
    private static int LOAD_AD_COUNT = 1;

    @Override
    public void loadNativeAd(Context context, int adPositin, OnAdsListener onAdsListener) {
        NativeAD nativeAD = null;
        switch (adPositin) {
            case ID_DZ_NATIVE:
                nativeAD = new NativeAD(context, context.getString(R.string.gdt_app_id), context.getString(R.string.gdt_bottom_ad_id),
                        new MyNativeAdListener(context, adPositin, onAdsListener));
                nativeAD.setBrowserType(BrowserType.Inner);
                nativeAD.loadAD(LOAD_AD_COUNT);
                break;
            case ID_QT_NATIVE:
                nativeAD = new NativeAD(context, context.getString(R.string.gdt_app_id), context.getString(R.string.gdt_bottom_ad_id),
                        new MyNativeAdListener(context, adPositin, onAdsListener));
                nativeAD.setBrowserType(BrowserType.Inner);
                nativeAD.loadAD(LOAD_AD_COUNT);
                break;
        }
    }

    private ADData getADData(NativeADDataRef nativeADDataRef) {
        if (nativeADDataRef != null) {
            ADData adData = new ADData();
            adData.mIcon = nativeADDataRef.getIconUrl();
            adData.mImageUrl = nativeADDataRef.getImgUrl();
            adData.mTitle = nativeADDataRef.getTitle();
            adData.mSubTitle = nativeADDataRef.getDesc();
            return adData;
        }
        return null;
    }

    private class MyNativeAdListener implements NativeAD.NativeAdListener {
        private Context c;
        private int adPosition;
        private OnAdsListener onAdsListener;

        public MyNativeAdListener(Context context, int adPositin, OnAdsListener onAdsListener) {
            this.c = context;
            this.adPosition = adPositin;
            this.onAdsListener = onAdsListener;
        }

        @Override
        public void onADLoaded(List<NativeADDataRef> list) {
            if (list.size() > 0 && onAdsListener != null) {
                NativeADDataRef adDataRef = list.get(0);
                switch (adPosition) {
                    case BaseADManager.ID_DZ_NATIVE:
                        LeftAdView leftAdView = new LeftAdView(c, getADData(adDataRef), adDataRef);
                        onAdsListener.onAdsLoaded(true, null, null, BaseADManager.AD_PLATFORM_IFLY, leftAdView.getView());
                        break;
                    case BaseADManager.ID_QT_NATIVE:
                        CenterAdView centerAdView = new CenterAdView(c, getADData(adDataRef), adDataRef);
                        onAdsListener.onAdsLoaded(true, null, null, BaseADManager.AD_PLATFORM_IFLY, centerAdView.getView());
                        break;
                }
            }
        }

        @Override
        public void onNoAD(AdError adError) {
            L.e(TAG + "onNoAD");
        }

        @Override
        public void onADStatusChanged(NativeADDataRef nativeADDataRef) {

        }

        @Override
        public void onADError(NativeADDataRef nativeADDataRef, AdError adError) {
            L.e(TAG + "onADError");
        }
    }

    @Override
    public void loadInterstitialAd(Context context, int adPosition, OnAdsListener l) {
        final InterstitialAD iAd = new InterstitialAD((Activity) context, context.getString(R.string.gdt_app_id),
                context.getString(R.string.gdt_insert_ad_id));
        iAd.setADListener(new AbstractInterstitialADListener() {
            @Override
            public void onADReceive() {
                L.e(TAG + "-loadInterstitialAd-onADReceive");
                iAd.showAsPopupWindow();
            }

            @Override
            public void onNoAD(AdError adError) {
                L.e(TAG + "-loadInterstitialAd-onNoAD");
            }
        });
        iAd.loadAD();
    }

    @Override
    public boolean loadSplashAd(Context context, OnAdsListener onAdsListener, View adLayout) {
        return false;
    }

    @Override
    public void loadBannerAd(Context context, int adPosition, OnAdsListener onAdsListener, ViewGroup viewGroup) {

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
        ((NativeADDataRef) o).onClicked(view);
    }

    @Override
    public void requestSpot(Context context) {

    }

    @Override
    public void onPause(Context context) {

    }

    @Override
    public void onStop(Context context) {

    }

    @Override
    public void onDestroy(Context context) {

    }

    @Override
    public void onAppExit(Context context) {

    }

    @Override
    public boolean onBackkeyEvent(Context context) {
        return false;
    }
    private static GdtAdManager mInstance;
    public static GdtAdManager getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new GdtAdManager(context);
        }
        return mInstance;
    }
    private GdtAdManager(Context context) {
    }
}
