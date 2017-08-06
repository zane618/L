package com.zane.neihan;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zane.l.R;

import java.util.List;

/**
 * Created by shizhang on 2017/8/6.
 */

public class NeihanDzAdapter extends BaseQuickAdapter<NeihandzBean.MDataItem, BaseViewHolder> {

    public NeihanDzAdapter(@Nullable List<NeihandzBean.MDataItem> data) {
        super(R.layout.adpter_neihan_dz, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NeihandzBean.MDataItem item) {
        if (item.type == 1) {
            helper.setText(R.id.tv_time, "作者:" + item.group.user.name)
                    .setText(R.id.tv_content, item.group.content);
        }
    }
}
