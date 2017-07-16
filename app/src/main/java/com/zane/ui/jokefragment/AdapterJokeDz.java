package com.zane.ui.jokefragment;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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
                if (item.updatetime == null) {
                    helper.setText(R.id.tv_time, item.unixtime);
                } else {
                    helper.setText(R.id.tv_time, item.updatetime);
                }
                helper.setText(R.id.tv_content, item.content);
                break;
            case 1:
                Glide.with(mContext)
                        .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499671312058&di=ed0cd4038eda33c4163ca5c5cc52d561&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201411%2F07%2F20141107192221_kCHCG.jpeg")
                        .into((ImageView) helper.getView(R.id.iv_ad));
                break;
        }
    }
}
