package com.zane.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zane.l.R;
import com.zane.ui.adapter.HomeFragmentAdapter;
import com.zane.ui.base.BaseFragmentActivity;
import com.zane.ui.fragment.ConstellationFragment;
import com.zane.ui.fragment.JokeFragment;
import com.zane.ui.fragment.TinhistoryFragment;
import com.zane.utility.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by shizhang on 2017/6/26.
 */

public class HomeFragmentActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener{
    private static final String TAG = "HomeFragmentActivity";
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private HomeFragmentAdapter homeFragmentAdapter;
    private RadioGroup radioGroup;
    private int lastBottomId = R.id.rb_1;
    private Map<Integer, RadioButton> bottoms = new HashMap<>();
    private Map<Integer, Integer> bottomKeys = new HashMap<>();
    private int currIndex;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_fragment_home);
    }

    @Override
    public void initView() {
        initViewpager();
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        bottoms.put(R.id.rb_1, (RadioButton) findViewById(R.id.rb_1));
        bottoms.put(R.id.rb_2, (RadioButton) findViewById(R.id.rb_2));
        bottoms.put(R.id.rb_3, (RadioButton) findViewById(R.id.rb_3));
        bottomKeys.put(0, R.id.rb_1);
        bottomKeys.put(1, R.id.rb_2);
        bottomKeys.put(2, R.id.rb_3);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                radioChangeAnimation(checkedId);
            }
        });
        appUpdate();
    }

    private void initViewpager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        Bundle bJoke = new Bundle();
        bJoke.putString("key", "");
        Fragment jokeFragment = new JokeFragment();
        jokeFragment.setArguments(bJoke);
        fragments.add(jokeFragment);

        Bundle bConstellation = new Bundle();
        bConstellation.putString("key", "");
        Fragment constellationFragment = new ConstellationFragment();
        constellationFragment.setArguments(bConstellation);
        fragments.add(constellationFragment);

        Bundle bTinhistory = new Bundle();
        bTinhistory.putString("key", "");
        Fragment tinhistoryFragment = new TinhistoryFragment();
        tinhistoryFragment.setArguments(bTinhistory);
        fragments.add(tinhistoryFragment);

        homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(homeFragmentAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottoms.get(bottomKeys.get(position)).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void radioChangeAnimation(int checkedId) {
        L.e(TAG + ":radioChangeAnimation");
        if (checkedId != lastBottomId) {
            bottoms.get(lastBottomId).setTextColor(getResources().getColor(R.color.c_content));
            bottoms.get(checkedId).setTextColor(getResources().getColor(R.color.black));
            lastBottomId = checkedId;
            currIndex = Integer.parseInt((bottoms.get(checkedId)).getTag().toString());
            viewPager.setCurrentItem(currIndex);
        }
    }
    private void appUpdate() {
        BmobUpdateAgent.update(this);
    }
}
