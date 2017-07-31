package com.zane.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.zane.ads.ADManagerFactory;
import com.zane.ads.BaseADManager;
import com.zane.ads.OnAdsListener;
import com.zane.custome.HackyViewPager;
import com.zane.l.R;
import com.zane.ui.adapter.HomeFragmentAdapter;
import com.zane.ui.base.BaseFragmentActivity;
import com.zane.ui.fragment.ImageFragment;
import com.zane.utility.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by shizhang on 2017/7/15.
 */

public class ImageFragmentActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener, OnAdsListener{
    private ViewPager hackyViewPager;
    private HomeFragmentAdapter adapter;
    private TextView tvTitle;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] imgUrls;
    private String[] titles;
    BaseADManager adManager;
    private int adPlatform = BaseADManager.AD_PLATFORM_YOUMI;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_fragment_image);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        imgUrls = intent.getExtras().getStringArray("imgUrls");
        titles = intent.getExtras().getStringArray("titles");
        hackyViewPager = (HackyViewPager) findViewById(R.id.hacky_view_pager);
        hackyViewPager.addOnPageChangeListener(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText( "(" + 1 + "/" + imgUrls.length + ")" + titles[0]);
        hackyViewPager.setOffscreenPageLimit(3);
        for (int i=0;i<imgUrls.length;i++) {
            Fragment f = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("imgUrl", imgUrls[i]);
            bundle.putString("title", titles[i]);
            f.setArguments(bundle);
            fragments.add(f);
        }
        adapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        hackyViewPager.setAdapter(adapter);
        loadInsertAd();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvTitle.setText( "(" + (position + 1) + "/" + imgUrls.length + ")" + titles[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void loadInsertAd() {
        adManager = ADManagerFactory.getADManager(mContext, adPlatform);
        if (0 != new Random().nextInt(3)) {
            return;
        }
        if (adManager != null) {
            adManager.loadInterstitialAd(mContext, BaseADManager.ID_INTERT, ImageFragmentActivity.this);
        }
    }

    @Override
    public void onAdsLoaded(boolean success, Object AdDataO, Object adO, int platform, View adView) {
//        if (success) {
//            return;
//        }
//        if (platform == BaseADManager.AD_PLATFORM_YOUMI) {
//            return;
//        }
//        adPlatform = BaseADManager.AD_PLATFORM_YOUMI;
//        loadInsertAd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_YOUMI).onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_YOUMI).onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_YOUMI).onDestroy(this);
    }

    @Override
    public void onBackPressed() {
        if (ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_YOUMI).onBackkeyEvent(mContext)) {
        } else {
            super.onBackPressed();
        }
    }
}
