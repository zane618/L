package com.zane.constellation;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zane.l.R;

import java.util.List;

/**
 * Created by shizhang on 2017/8/3.
 */

public class ConstellationAdapter extends BaseQuickAdapter<ConstellationBean, BaseViewHolder> {

    public ConstellationAdapter(@LayoutRes int layoutResId, @Nullable List<ConstellationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ConstellationBean constellationBean) {
        holder.setText(R.id.tv_name_constellation, constellationBean.name);
        holder.addOnClickListener(R.id.tv_name_constellation);
    }
}
