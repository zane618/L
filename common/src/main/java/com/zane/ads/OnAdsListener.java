package com.zane.ads;

/**
 * Created by shizhang on 2017/7/10.
 */

public interface OnAdsListener {
    /**
     *
     * @param success
     * @param AdDataO  转化后
     * @param adO   转化前
     * @param platform
     */
    void onAdsLoaded(boolean success, Object AdDataO, Object adO, int platform);
}
