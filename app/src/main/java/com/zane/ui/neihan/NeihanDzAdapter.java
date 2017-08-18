package com.zane.ui.neihan;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.zane.l.R;

import java.util.List;

/**
 * Created by shizhang on 2017/8/6.
 */

public class NeihanDzAdapter extends BaseQuickAdapter<NeihandzBean.MDataItem, BaseViewHolder> {

    public NeihanDzAdapter(@Nullable List<NeihandzBean.MDataItem> data) {
        super(R.layout.adpter_neihan_dz, data);
        setMultiTypeDelegate(new MultiTypeDelegate<NeihandzBean.MDataItem>() {
            @Override
            protected int getItemType(NeihandzBean.MDataItem mDataItem) {
                return mDataItem.layoutType;
            }
        });
        getMultiTypeDelegate()
                .registerItemType(0, R.layout.adpter_neihan_dz)
                .registerItemType(1, R.layout.adapter_joke_dz_ad);
    }

    @Override
    protected void convert(BaseViewHolder helper, NeihandzBean.MDataItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                if (item.type == 1) {
                    helper.setText(R.id.tv_time, "段子手:" + item.group.user.name)
                            .setText(R.id.tv_content, item.group.content);
                }
                break;
            case 1:
                ViewGroup viewGroup = helper.getView(R.id.view_group);
                viewGroup.removeAllViews();
                viewGroup.addView(item.adView);
                break;
        }

    }
}
