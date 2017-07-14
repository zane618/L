package com.zane.ads.voiceads;

import android.content.Context;
import android.view.View;

import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.IFLYNativeAd;
import com.iflytek.voiceads.IFLYNativeListener;
import com.iflytek.voiceads.NativeADDataRef;
import com.zane.ads.ADData;
import com.zane.ads.ADInterface;
import com.zane.ads.OnAdsListener;

import java.util.List;

import static okhttp3.internal.Internal.instance;

/**
 * Created by shizhang on 2017/7/10.
 */

public class IflyAdManager extends BaseADManager {

    @Override
    public boolean loadSplashAd(Context context, OnAdsListener onAdsListener, View adLayout) {
        if (null == context) {
            return false;
        }
        IFLYNativeAd mSplashAd = new IFLYNativeAd(context, "ECE1BE0220D5CFF485ACBBA24681E8C4", new MyIFLYNativeListener(onAdsListener, adLayout));
        mSplashAd.loadAd(1);
        return true;
    }

    class MyIFLYNativeListener implements IFLYNativeListener {
        OnAdsListener onAdsListener;
        View adLayout;
        public MyIFLYNativeListener(OnAdsListener onAdsListener, View adLayout) {
            this.onAdsListener = onAdsListener;
            this.adLayout = adLayout;
        }

        @Override
        public void onADLoaded(List<NativeADDataRef> list) {
            if (null != list && list.size() > 0) {
                NativeADDataRef adDataRef = list.get(0);
                if (null != onAdsListener) {
                    onAdsListener.onAdsLoaded(true, getADData(adDataRef), AD_PLATFORM_IFLY);
                    adDataRef.onExposured(adLayout);
                }
            }
        }

        @Override
        public void onAdFailed(AdError adError) {
            onAdsListener.onAdsLoaded(false, null, AD_PLATFORM_IFLY);
        }

        @Override
        public void onConfirm() {

        }

        @Override
        public void onCancel() {

        }
    }

    private ADData getADData(NativeADDataRef adDataRef) {
        if (null != adDataRef) {
            ADData adData = new ADData();
            adData.mAdClickType = adDataRef.getAdtype();
            adData.mTitle = adDataRef.getTitle();
            adData.mSubTitle = adDataRef.getSubTitle();
            adData.mImageUrl = adDataRef.getImage();
            adData.mIcon = adDataRef.getIcon();
            return adData;
        }
        return null;
    }


    private static IflyAdManager mInstance;
    public static IflyAdManager getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new IflyAdManager(context);
        }
        return mInstance;
    }
    private IflyAdManager(Context context) {
    }
}
