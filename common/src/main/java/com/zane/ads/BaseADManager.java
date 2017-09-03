package com.zane.ads;

/**
 * Created by shizhang on 2017/7/10.
 */

public abstract class BaseADManager implements ADInterface{
    /*
      * 支持的广告平台，只能新增，和配置的广告开关一一对应，同时新增
         */
    public static final int AD_PLATFORM_IFLY = 1;
    public static final int AD_PLATFORM_YOUMI = 2;
    public static final int AD_PLATFORM_GDT = 3;


    /**
     * 广告位id
     */
    public static final int ID_SPLASH = 1;
    public static final int ID_EXIT = 2;
    public static final int ID_BANNER = 3;
    public static final int ID_DZ_NATIVE = 4;
    public static final int ID_QT_NATIVE = 5;
    public static final int ID_INTERT = 6;

}
