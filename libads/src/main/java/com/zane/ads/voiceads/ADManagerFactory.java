package com.zane.ads.voiceads;

import android.content.Context;

/**
 * Created by shizhang on 2017/7/10.
 */

public class ADManagerFactory {
    public static BaseADManager getADManager(Context context, int adPlatform) {
        return IflyAdManager.getInstance(context.getApplicationContext());
    }
}
