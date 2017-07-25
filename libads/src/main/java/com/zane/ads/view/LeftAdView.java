package com.zane.ads.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iflytek.voiceads.NativeADDataRef;
import com.zane.ads.ADData;
import com.zane.ads.BaseADManager;
import com.zane.ads.R;
import com.zane.ads.voiceads.IflyAdManager;
import com.zane.utility.L;

/**
 * Created by shizhang on 2017/7/16.
 */

public class LeftAdView implements View.OnClickListener{
    private Context context;
    private Object o;
    private ADData adItem;
    private View rootView;
    private ImageView ivAd;
    private TextView tvTitle;
    private TextView tvContent;

    public LeftAdView(Context context, ADData adItem, Object o) {
        this.context = context;
        this.adItem = adItem;
        this.o = o;
        initView();
    }

    private void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.ad_left_view, null);
        ivAd = (ImageView) rootView.findViewById(R.id.iv_ad);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        rootView.setOnClickListener(this);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (o instanceof NativeADDataRef) {
                    IflyAdManager.getInstance(context).onAdTouchListener(event, BaseADManager.ID_DZ_NATIVE);
                }
                return false;
            }
        });
        if (o instanceof NativeADDataRef) {
            Glide.with(context)
                    .load(adItem.mImageUrl)
                    .into(ivAd);
            tvTitle.setText(adItem.mTitle);
            tvContent.setText(adItem.mSubTitle);
        }
        rootView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                ((NativeADDataRef) o).onExposured(rootView);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
    }
    public View getView() {
        return rootView;
    }
    @Override
    public void onClick(View v) {
        IflyAdManager.getInstance(context).onAdClickListener(rootView, o);
    }
}
