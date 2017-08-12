package com.zane.ui.jokefragment;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.zane.l.R;
import com.zane.bean.JokeDzBean;

import java.util.List;

/**
 * Created by shizhang on 2017/7/5.
 */

public class AdapterJokeDz extends BaseQuickAdapter<JokeDzBean.MData, BaseViewHolder> {

    public AdapterJokeDz(@LayoutRes int layoutResId, @Nullable List<JokeDzBean.MData> data) {
        super(layoutResId, data);
        setMultiTypeDelegate(new MultiTypeDelegate<JokeDzBean.MData>() {
            @Override
            protected int getItemType(JokeDzBean.MData mData) {
                return mData.layoutType;
            }
        });
        getMultiTypeDelegate()
                .registerItemType(0, R.layout.adpter_joke_dz)
                .registerItemType(1, R.layout.adapter_joke_dz_ad);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeDzBean.MData item) {

        switch (helper.getItemViewType()) {
            case 0:
                helper.setText(R.id.tv_time, item.content.substring(0, 7));
                helper.setText(R.id.tv_content, item.content);
                break;
            case 1:
                ViewGroup viewGroup = helper.getView(R.id.view_group);
                viewGroup.removeAllViews();
                viewGroup.addView(item.adView);
                break;
        }
    }
}
