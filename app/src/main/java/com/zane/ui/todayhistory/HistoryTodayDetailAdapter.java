package com.zane.ui.todayhistory;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zane.l.R;
import com.zane.ui.MyApplication;
import com.zane.utility.ScreenUtils;

import java.util.List;

/**
 * Created by shizhang on 2017/7/30.
 */

public class HistoryTodayDetailAdapter extends BaseQuickAdapter<HistoryDetailBean.MPic, BaseViewHolder> {

    public HistoryTodayDetailAdapter(@LayoutRes int layoutResId, @Nullable List<HistoryDetailBean.MPic> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryDetailBean.MPic item) {
        helper.setText(R.id.iv_title, item.pic_title);
        ImageView iv = helper.getView(R.id.iv);
        iv.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenWidth(mContext)));
        Glide.with(mContext)
                .load(item.url)
                .apply(MyApplication.options)
                .into(iv);
        helper.addOnClickListener(R.id.iv);
    }
}
