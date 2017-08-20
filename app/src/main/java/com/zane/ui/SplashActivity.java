package com.zane.ui;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zane.ads.ADData;
import com.zane.ads.BaseADManager;
import com.zane.ads.OnAdsListener;
import com.zane.ads.ADManagerFactory;
import com.zane.l.R;
import com.zane.ui.base.BaseFragmentActivity;
import com.zane.utility.L;

/**
 * Created by shizhang on 2017/7/10.
 */

public class SplashActivity extends BaseFragmentActivity {
    private Button btnJump;
    private LinearLayout adLayout;
    private BaseADManager adManager;
    private static final String TAG = "SplashActivity";
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initView() {
        btnJump = (Button) findViewById(R.id.btn_jump_sp);
        adLayout = (LinearLayout) findViewById(R.id.ad_layout);
        adManager = ADManagerFactory.getADManager(this, BaseADManager.AD_PLATFORM_IFLY);
        if (adManager != null) {
            adManager.loadSplashAd(this, new OnAdsListener() {
                @Override
                public void onAdsLoaded(boolean success, final Object o, final Object o2, int platform, View adView) {
                    if (success) {
                        final ImageView iv = new ImageView(mContext);
                        iv.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                adManager.onAdTouchListener(event, BaseADManager.AD_PLATFORM_IFLY);
                                return false;
                            }
                        });
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                adManager.onAdClickListener(iv, o2);
                            }
                        });
                        iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        adLayout.addView(iv, lp);
                        L.e(((ADData) o).mImageUrl);
                        Glide.with(SplashActivity.this)
                                .load(((ADData)o).mImageUrl)
                                .into(iv);
                        adManager.onAdExposured(iv, o2, BaseADManager.ID_SPLASH);
                    }
                }
            }, adLayout);
        }
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeActivity();
            }
        });
        btnJump.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(3000 + 300, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            L.e(TAG + millisUntilFinished);
            btnJump.setText("跳过(" + (millisUntilFinished / 1000 - 1) + "s)");
        }

        @Override
        public void onFinish() {
//            btnJump.setText("跳过(" + 0 + "s)");
            goToHomeActivity();
        }
    };

    private void goToHomeActivity() {
        timerCancel();
        startActivity(new Intent(this, HomeFragmentActivity.class));
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerCancel();
    }
    private void timerCancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
