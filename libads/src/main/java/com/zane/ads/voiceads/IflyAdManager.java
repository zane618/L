package com.zane.ads.voiceads;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYBannerAd;
import com.iflytek.voiceads.IFLYNativeAd;
import com.iflytek.voiceads.IFLYNativeListener;
import com.iflytek.voiceads.NativeADDataRef;
import com.zane.ads.ADData;
import com.zane.ads.BaseADManager;
import com.zane.ads.OnAdsListener;
import com.zane.ads.R;
import com.zane.ads.dialog.IflyExitDialog;

import java.util.List;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by shizhang on 2017/7/10.
 */

public class IflyAdManager extends BaseADManager {

    private static final int count = 1;//讯飞原生广告当前仅支持一条
    private IFLYNativeAd mSplashAd;
    private IFLYNativeAd mExitAd;
    @Override
    public boolean loadSplashAd(Context context, final OnAdsListener onAdsListener, final View adLayout) {
        if (null == context) {
            return false;
        }
        mSplashAd = new IFLYNativeAd(context, context.getResources().getString(R.string.splash_ifly_ad_id), new IFLYNativeListener() {
            @Override
            public void onADLoaded(List<NativeADDataRef> list) {
                if (null != list && list.size() > 0) {
                    NativeADDataRef adDataRef = list.get(0);
                    if (null != onAdsListener) {
                        onAdsListener.onAdsLoaded(true, getADData(adDataRef), adDataRef, AD_PLATFORM_IFLY);
                        adDataRef.onExposured(adLayout);//if() 就曝光成功
                    }
                }
            }

            @Override
            public void onAdFailed(AdError adError) {
                onAdsListener.onAdsLoaded(false, null, null, AD_PLATFORM_IFLY);
            }
            @Override
            public void onConfirm() {
            }
            @Override
            public void onCancel() {
            }
        });
        mSplashAd.setParameter(AdKeys.CUSTOM_BROSWER, "com.zane.ui.WebviewActivity");
        mSplashAd.loadAd(count);
        return true;
    }

    @Override
    public void loadBannerAd(Context context, int adPosition, final OnAdsListener onAdsListener, ViewGroup viewGroup) {
        final IFLYBannerAd bannerAd = IFLYBannerAd.createBannerAd(context, context.getString(R.string.exit_bottom_ad_id));
        bannerAd.setAdSize(IFLYAdSize.BANNER);
        bannerAd.setParameter(AdKeys.DOWNLOAD_ALERT, "true");
        viewGroup.removeAllViews();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(bannerAd, lp);
        bannerAd.loadAd(new IFLYAdListener() {
            @Override
            public void onAdReceive() {
                onAdsListener.onAdsLoaded(true, null, null, BaseADManager.AD_PLATFORM_IFLY);
                bannerAd.showAd();
            }

            @Override
            public void onAdFailed(AdError adError) {

            }
            @Override
            public void onAdClick() {
            }
            @Override
            public void onAdClose() {
            }
            @Override
            public void onAdExposure() {
            }
            @Override
            public void onConfirm() {
            }
            @Override
            public void onCancel() {
            }
        });
    }

    private static IflyExitDialog exitDialog;

    @Override
    public void loadExitAd(final Context context, final OnAdsListener onAdsListener) {
        mExitAd = new IFLYNativeAd(context, context.getResources().getString(R.string.splash_ifly_ad_id), new IFLYNativeListener() {
            @Override
            public void onADLoaded(List<NativeADDataRef> list) {
                if (null != list && list.size() > 0) {
                    NativeADDataRef adDataRef = list.get(0);
                    if (null != onAdsListener) {
                        onAdsListener.onAdsLoaded(true, getADData(adDataRef), adDataRef, AD_PLATFORM_IFLY);
//                        adDataRef.onExposured(adLayout);//if() 就曝光成功
                        new IflyExitDialog(context, adDataRef).show();
                    }
                }
            }

            @Override
            public void onAdFailed(AdError adError) {
                onAdsListener.onAdsLoaded(false, null, null, AD_PLATFORM_IFLY);
            }

            @Override
            public void onConfirm() {
            }

            @Override
            public void onCancel() {
            }
        });
        mExitAd.setParameter(AdKeys.CUSTOM_BROSWER, "com.zane.ui.WebviewActivity");
        mExitAd.loadAd(count);
    }

    @Override
    public void onAdTouchListener(MotionEvent event, int adPosition) {
        if(event == null) return;
        switch (adPosition) {
            case BaseADManager.ID_SPLASH:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mSplashAd.setParameter(AdKeys.CLICK_POS_DX, event.getX() + "");
                    mSplashAd.setParameter(AdKeys.CLICK_POS_DY, event.getY() + "");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mSplashAd.setParameter(AdKeys.CLICK_POS_UX, event.getX() + "");
                    mSplashAd.setParameter(AdKeys.CLICK_POS_UY, event.getY() + "");
                }
                break;
            case BaseADManager.ID_EXIT:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mExitAd.setParameter(AdKeys.CLICK_POS_DX, event.getX() + "");
                    mExitAd.setParameter(AdKeys.CLICK_POS_DY, event.getY() + "");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mExitAd.setParameter(AdKeys.CLICK_POS_UX, event.getX() + "");
                    mExitAd.setParameter(AdKeys.CLICK_POS_UY, event.getY() + "");
                }
                break;
        }
    }

    @Override
    public void onAdClickListener(View view, Object o) {
        ((NativeADDataRef) o).onClicked(view);
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
