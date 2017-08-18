package com.zane.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.zane.l.R;
import com.zane.ui.neihan.NeihanDzFragment;
import com.zane.ui.todayhistory.FragmentHistoryToday;
import com.zane.ui.adapter.HomeFragmentAdapter;
import com.zane.ui.base.BaseFragment;
import com.zane.ui.jokefragment.JokeDzFragment;
import com.zane.ui.jokefragment.ChooseJokeBrowseTypeActivity;
import com.zane.ui.jokefragment.JokeQtFragment;
import com.zane.utility.L;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shizhang on 2017/6/26.
 */

public class JokeFragment extends BaseFragment {
    private static final String TAG = "JokeFragment";
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private HomeFragmentAdapter adapter;
    private static final String[] titleDatas = new String[]{"最新段子", "经典段子","史上今日","经典趣图"};
    private Button test;
    public static int JokeFragmentCurrIndex;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initViewpager(view);
    }

    private void initViewpager(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        fragments.add(new NeihanDzFragment());
        fragments.add(new JokeDzFragment());
        fragments.add(new FragmentHistoryToday());
        fragments.add(new JokeQtFragment());
        adapter = new HomeFragmentAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        initMagicIndicator(view);
        test = (Button) view.findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChooseJokeBrowseTypeActivity.class);
                intent.putExtra("requestCode", JokeFragmentCurrIndex);
                startActivityForResult(intent, JokeFragmentCurrIndex);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == JokeFragmentCurrIndex && resultCode == JokeFragmentCurrIndex) {
            if (data == null) {
                return;
            }
            L.e("66onActivityResult" + ":" + JokeFragmentCurrIndex);
            switch (JokeFragmentCurrIndex) {
                case 0:
                    ((JokeDzFragment) fragments.get(JokeFragmentCurrIndex)).doSetBrowseParameterValue(data);
                    break;
                case 1:
                    ((JokeQtFragment) fragments.get(JokeFragmentCurrIndex)).doSetBrowseParameterValue(data);
                    break;
                case 2:
                    break;
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke;
    }
    private void initMagicIndicator(View view) {
        final MagicIndicator magicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.parseColor("#FFFFFF"));
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragments == null ? 0 : fragments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titleDatas[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#88000000"));
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.parseColor("#6E000000"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
//        ViewPagerHelper.bind(magicIndicator, viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                JokeFragmentCurrIndex = position;
                magicIndicator.onPageSelected(position);
                if(position == 2){
                    ((FragmentHistoryToday) fragments.get(2)).fuck();
                } else if (position == 3) {
                    ((JokeQtFragment) fragments.get(3)).fuck();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
    }
}
