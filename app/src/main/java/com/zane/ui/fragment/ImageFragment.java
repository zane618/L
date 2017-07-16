package com.zane.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.zane.l.R;
import com.zane.ui.MyApplication;
import com.zane.ui.base.BaseFragment;

import static com.zane.l.R.id.tv_title;

/**
 * Created by shizhang on 2017/7/15.
 */

public class ImageFragment extends BaseFragment {
    private PhotoView photoView;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String imgUlr = bundle.getString("imgUrl");
        photoView = (PhotoView) view.findViewById(R.id.photo_view);
        if (TextUtils.isEmpty(imgUlr)) {
            return;
        }
        Glide.with(this)
                .load(imgUlr)
                .apply(MyApplication.options)
                .into(photoView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }
}
