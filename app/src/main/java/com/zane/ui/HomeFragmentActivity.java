package com.zane.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zane.ads.ADManagerFactory;
import com.zane.ads.BaseADManager;
import com.zane.ads.OnAdsListener;
import com.zane.ui.constellation.ConstellationFragment;
import com.zane.l.R;
import com.zane.ui.adapter.HomeFragmentAdapter;
import com.zane.ui.base.BaseFragmentActivity;
import com.zane.ui.fragment.JokeFragment;
import com.zane.ui.meiwen.MeiwenFragment;
import com.zane.utility.ToastUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by shizhang on 2017/6/26.
 */

public class HomeFragmentActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener, OnAdsListener{
    private static final String[] CHANNELS = new String[]{"笑哈", "每文", "星座"};
    private static final int[] IMGS_SEL = new int[]{R.drawable.bottom_1_sel, R.drawable.bottom_3_sel, R.drawable.bottom_2_sel};
    private static final int[] IMGS_NOL = new int[]{R.drawable.bottom_1_nol, R.drawable.bottom_3_nol, R.drawable.bottom_2_nol};
    private static final String TAG = "HomeFragmentActivity";
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private HomeFragmentAdapter homeFragmentAdapter;
    private int currIndex;
    private BaseADManager exitAdManager;
    private ViewGroup adLayout;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_fragment_home);
    }

    @Override
    public void initView() {
        adLayout = (ViewGroup) findViewById(R.id.ad_layout);
        initViewpager();
        appUpdate();
/*
        ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_IFLY)
                .loadBannerAd(mContext, BaseADManager.ID_BANNER,  new OnAdsListener() {
                    @Override
                    public void onAdsLoaded(boolean success, Object AdDataO, Object adO, int platform, View adView) {
                        if (success) {
                            adLayout.setVisibility(View.VISIBLE);
                        } else {
                            adLayout.setVisibility(View.GONE);
                        }
                    }
                }, adLayout);
*/

    }

    private void initViewpager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        Bundle bJoke = new Bundle();
        bJoke.putString("key", "");
        Fragment jokeFragment = new JokeFragment();
        jokeFragment.setArguments(bJoke);
        fragments.add(jokeFragment);

        Fragment meiwenFragment = new MeiwenFragment();
        fragments.add(meiwenFragment);

        Bundle bConstellation = new Bundle();
        bConstellation.putString("key", "");
        Fragment constellationFragment = new ConstellationFragment();
        constellationFragment.setArguments(bConstellation);
        fragments.add(constellationFragment);
//
//        Bundle bTinhistory = new Bundle();
//        bTinhistory.putString("key", "");
//        Fragment tinhistoryFragment = new TinhistoryFragment();
//        tinhistoryFragment.setArguments(bTinhistory);
//        fragments.add(tinhistoryFragment);

        homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(homeFragmentAdapter);
        initMagicIndicator();
        viewPager.addOnPageChangeListener(this);
        exitAdManager = ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_IFLY);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void appUpdate() {
        BmobUpdateAgent.update(this);
    }

    @Override
    public void onBackPressed() {
//        if (exitAdManager != null) {
//            exitAdManager.loadExitAd(mContext, this);
//        } else {
            exitByTime();
//        }

    }

    private long mClickBackTime = -1;
    private void exitByTime() {
        long curTime = System.currentTimeMillis();
        if (curTime - mClickBackTime > 1500) {
            mClickBackTime = curTime;
            ToastUtils.showToast(mContext, "双击退出程序");
        } else {
            finish();
        }
    }

    @Override
    public void onAdsLoaded(boolean success, Object AdDataO, Object adO, int platform, View adView) {
        if (!success) {
            exitAdManager = null;
            exitByTime();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ADManagerFactory.onAppExit(this);
    }
    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator1);
        magicIndicator.setBackgroundColor(Color.BLACK);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return IMGS_SEL.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

                // load custom layout
                View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
                final ImageView titleImg = (ImageView) customLayout.findViewById(R.id.title_img);
                final TextView titleText = (TextView) customLayout.findViewById(R.id.title_text);
                titleImg.setImageResource(IMGS_SEL[index]);
                titleText.setText(CHANNELS[index]);
                commonPagerTitleView.setContentView(customLayout);

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(Color.parseColor("#03A9F5"));
                        titleImg.setImageResource(IMGS_SEL[index]);
                         if (index == 2) {
                            ((ConstellationFragment) fragments.get(2)).fuck();
                        }
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(Color.parseColor("#8a8a8a"));
                        titleImg.setImageResource(IMGS_NOL[index]);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
//                        titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
//                        titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
//                        titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
//                        titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }
}
