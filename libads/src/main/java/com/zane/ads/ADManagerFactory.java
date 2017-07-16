package com.zane.ads;

import android.content.Context;

import com.zane.ads.BaseADManager;
import com.zane.ads.voiceads.IflyAdManager;

/**
 * Created by shizhang on 2017/7/10.
 */

public class ADManagerFactory {
    public static BaseADManager getADManager(Context context, int adPlatform) {
        return IflyAdManager.getInstance(context.getApplicationContext());
    }
}
