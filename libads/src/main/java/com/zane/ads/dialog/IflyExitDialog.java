package com.zane.ads.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iflytek.voiceads.NativeADDataRef;
import com.zane.ads.BaseADManager;
import com.zane.ads.R;
import com.zane.ads.voiceads.IflyAdManager;
import com.zane.utility.L;

import static com.lzy.okgo.db.CookieManager.init;

/**
 * Created by shizhang on 2017/7/16.
 */

public class IflyExitDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private Object adItem;
    private View adLayout;
    private ImageView ivAd;
    private TextView tvTitle;
    private View leftBtn, rightBtn;
    private boolean firstFlag = true;

    public IflyExitDialog(Context context, Object adItem) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);
        init(context, adItem);
    }

    private void init(final Context context, final Object adItem) {
        this.context = context;
        this.adItem = adItem;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_exit_ifly, null);
        adLayout = view.findViewById(R.id.ad_layout);
        ivAd = (ImageView) view.findViewById(R.id.iv_ad);
        tvTitle = (TextView) view.findViewById(R.id.ad_title);
        leftBtn = view.findViewById(R.id.left_btn);
        rightBtn = view.findViewById(R.id.right_btn);
        adLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (adItem instanceof NativeADDataRef) {
                    IflyAdManager.getInstance(context).onAdTouchListener(event, BaseADManager.ID_EXIT);
                }
                return false;
            }
        });
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        adLayout.setOnClickListener(this);
        if (adItem instanceof NativeADDataRef) {
            tvTitle.setText(((NativeADDataRef) adItem).getTitle());
            Glide.with(context)
                    .load(((NativeADDataRef) adItem).getImage())
                    .into(ivAd);
        }

        setContentView(view);
    }

    @Override
    public void show() {
        if (!this.isShowing()) {
            super.show();
            if (firstFlag) {
                L.e("退出 onExposured:" + ((NativeADDataRef) adItem).onExposured(adLayout));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == leftBtn) {
            dismiss();
        } else if (v == rightBtn) {
            dismiss();
            ((Activity) context).finish();
        } else if (v == adLayout) {
            if (adItem instanceof NativeADDataRef) {
                ((NativeADDataRef) adItem).onClicked(adLayout);
                dismiss();
            }
        }
    }
}
