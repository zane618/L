package com.zane.ads;

import android.content.Context;

import com.zane.ads.BaseADManager;
import com.zane.ads.voiceads.IflyAdManager;
import com.zane.ads.youmiads.YoumiAdManager;

import ner.gfd.pjp.AdManager;
import ner.gfd.pjp.nm.sp.SpotManager;


/**
 * Created by shizhang on 2017/7/10.
 */

public class ADManagerFactory {
    public static BaseADManager getADManager(Context context, int adPlatform) {
        if (adPlatform == BaseADManager.AD_PLATFORM_IFLY) {
            return IflyAdManager.getInstance(context.getApplicationContext());
        } else if(adPlatform == BaseADManager.AD_PLATFORM_YOUMI){
            return YoumiAdManager.getInstance(context.getApplicationContext());
        }
        return null;
    }
    public static void initAdPlatform(Context context) {
        AdManager.getInstance(context)
                .init(context.getString(R.string.youmi_appid), context.getString(R.string.youmi_appkey), false);
        YoumiAdManager.getInstance(context).requestSpot(context);
    }
    public static void onAppExit(Context context) {
        SpotManager.getInstance(context).onAppExit();
    }
}
