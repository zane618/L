package com.zane.constellation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zane.l.R;
import com.zane.ui.adapter.HomeFragmentAdapter;
import com.zane.ui.base.BaseFragmentActivity;
import com.zane.ui.fragment.JokeFragment;
import com.zane.utility.ToastUtils;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
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
 * 星座详情界面包含日、周、月、年
 * Created by shizhang on 2017/8/5.
 */

public class ConsDetailActivity extends BaseFragmentActivity implements View.OnClickListener{
    private View tvBack;
    public static String BEAN_KEY = "constellationbean";
    public static String NAME_KEY = "constellationbean_name";
    private static String[] TITLES = new String[]{"今日", "本周", "本月", "今年"};
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private HomeFragmentAdapter adapter;
    private ConstellationBean bean;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_cons_detail);
    }

    @Override
    public void initView() {
        tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (mGetIntent != null) {
            bean = (ConstellationBean) mGetIntent.getSerializableExtra(BEAN_KEY);
        }
        if (bean == null) {
            ToastUtils.showToast(mContext, "bean == null");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(BEAN_KEY, bean);
        Fragment consDayFragment = new ConsDayFragment();
        consDayFragment.setArguments(bundle);

        Bundle bundle1 = new Bundle();
        bundle1.putString(NAME_KEY, bean.name);
        Fragment consWeekFragment = new ConsWeekFragment();
        consWeekFragment.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString(NAME_KEY, bean.name);
        Fragment consMonthFragment = new ConsMonthFragment();
        consMonthFragment.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putSerializable(NAME_KEY, bean.name);
        Fragment consYearFragment = new ConsYearFragment();
        consYearFragment.setArguments(bundle3);

        fragments.add(consDayFragment);
        fragments.add(consWeekFragment);
        fragments.add(consMonthFragment);
        fragments.add(consYearFragment);
        adapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator4);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return TITLES.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.parseColor("#88000000"));
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setText(TITLES[index]);
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
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 10));
                linePagerIndicator.setColors(Color.parseColor("#6E000000"));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(ConsDetailActivity.this, 15);
            }
        });

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
                switch (position) {
                    case 1:
                        ((ConsWeekFragment) fragments.get(position)).fuck();
                        break;
                    case 2:
                        ((ConsMonthFragment) fragments.get(position)).fuck();
                        break;
                    case 3:
                        ((ConsYearFragment) fragments.get(position)).fuck();
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
