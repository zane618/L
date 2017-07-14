package com.zane.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by shizhang on 2017/6/26.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    public HomeFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return null == fragments || fragments.isEmpty()
                || position < 0 || position >= fragments.size() ?
                null : fragments.get(position);
    }

    @Override
    public int getCount() {
        return null == fragments || fragments.isEmpty() ? 0 : fragments.size();
    }
}
