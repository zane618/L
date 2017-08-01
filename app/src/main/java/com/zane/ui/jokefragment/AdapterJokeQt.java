package com.zane.ui.jokefragment;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.zane.bean.JokeQtBean;
import com.zane.l.R;
import com.zane.ui.MyApplication;

import java.util.List;

import static java.lang.System.load;

/**
 * Created by shizhang on 2017/7/10.
 */

public class AdapterJokeQt extends BaseQuickAdapter<JokeQtBean.MData, BaseViewHolder> {

    public AdapterJokeQt(@LayoutRes int layoutResId, @Nullable List<JokeQtBean.MData> data) {
        super(layoutResId, data);
        setMultiTypeDelegate(new MultiTypeDelegate<JokeQtBean.MData>() {
            @Override
            protected int getItemType(JokeQtBean.MData mData) {
                return mData.layoutType;
            }
        });
        getMultiTypeDelegate()
                .registerItemType(0, R.layout.adpter_joke_qt)
                .registerItemType(1, R.layout.adapter_joke_dz_ad);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeQtBean.MData item) {

        switch (helper.getItemViewType()) {
            case 0:
                /*if (item.updatetime == null) {
                    helper.setText(R.id.tv_time, item.unixtime);
                } else {
                    helper.setText(R.id.tv_time, item.updatetime);
                }*/
                helper.setText(R.id.tv_title, item.content);
                Glide.with(mContext)
                        .load(item.url)
                        .apply(MyApplication.options)
                        .into((ImageView) helper.getView(R.id.iv_img));
                break;
            case 1:
                ViewGroup viewGroup = helper.getView(R.id.view_group);
                viewGroup.removeAllViews();
                viewGroup.addView(item.adView);
                break;
        }
    }
}
