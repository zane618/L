package com.zane.ui.todayhistory;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zane.l.R;

import java.util.List;

/**
 * Created by shizhang on 2017/7/30.
 */

public class HistoryTodayAdapter extends BaseQuickAdapter<HistoryTodayListBean.MData, BaseViewHolder> {

    public HistoryTodayAdapter(@LayoutRes int layoutResId, @Nullable List<HistoryTodayListBean.MData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryTodayListBean.MData item) {
        helper.setText(R.id.tv_title, item.title)
                .setText(R.id.tv_time, item.date)
                .addOnClickListener(R.id.card_layout)
                .addOnLongClickListener(R.id.card_layout);
    }
}
