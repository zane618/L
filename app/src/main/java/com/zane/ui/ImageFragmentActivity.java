package com.zane.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zane.ads.ADManagerFactory;
import com.zane.ads.BaseADManager;
import com.zane.custome.HackyViewPager;
import com.zane.l.R;
import com.zane.ui.adapter.HomeFragmentAdapter;
import com.zane.ui.base.BaseFragmentActivity;
import com.zane.ui.fragment.ImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shizhang on 2017/7/15.
 */

public class ImageFragmentActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener{
    private ViewPager hackyViewPager;
    private HomeFragmentAdapter adapter;
    private TextView tvTitle;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] imgUrls;
    private String[] titles;
    BaseADManager adManager;
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
        adManager = ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_IFLY);
        if (adManager != null) {
            adManager.loadInterstitialAd(mContext, BaseADManager.ID_INTERT);
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
}
