package com.zane.customview.meiwen;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zane.l.R;
import com.zane.ui.meiwen.db.MeiwEntity;

import java.util.List;

/**
 * Created by shizhang on 2017/8/27.
 */

public class CollectListAdapter extends BaseQuickAdapter<MeiwEntity, BaseViewHolder> {

    public CollectListAdapter(@LayoutRes int layoutResId, @Nullable List<MeiwEntity> data) {
        super(R.layout.adapter_collect_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiwEntity item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_author, item.getAuthor());
    }
}
