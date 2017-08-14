package com.zane.custome;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by shizhang on 2017/8/14.
 */

public class DriRecyclerview extends RecyclerView {


    public DriRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        //得到第一个item
//        int firstVisibleItem = this.getLayoutManager().
    }
}
