package com.zane.ui.meiwen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zane.ads.ADManagerFactory;
import com.zane.ads.BaseADManager;
import com.zane.ads.OnAdsListener;
import com.zane.apis.Urls;
import com.zane.customview.DireScrollView;
import com.zane.l.R;
import com.zane.ui.base.BaseFragment;
import com.zane.utility.DensityUtils;
import com.zane.utility.L;
import com.zane.utility.ToastUtils;

import static com.umeng.analytics.pro.x.O;

/**
 * Created by shizhang on 2017/8/20.
 */

public class MeiwenFragment extends BaseFragment implements View.OnClickListener
, OnAdsListener, DireScrollView.OnSrcollDireChanged{
    public static final int COLLECT = 1;//收藏
    public static final int COLLECT_LIST = 2;//收藏列表
    public static final int RANDOM = 3;//随机一文
    private TextView tvTime;
    private TextView tvContent;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvWc;
    private MwResultBean bean;
    private View vRandom;
    private int vRandomHeight;
    private DireScrollView scroll_view;
    private BaseADManager adManager;
    private FrameLayout flAdLayout;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        scroll_view = (DireScrollView) view.findViewById(R.id.scroll_view);
        scroll_view.setOnSrcollDireChanged(this);
//        scroll_view.setOnTouchListener(this);
        vRandom = view.findViewById(R.id.iv_back_top);
        vRandom.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                vRandomHeight = DensityUtils.dp2px(mContext, 15) + vRandom.getHeight();
//                hideFloatBtn(vRandom, vRandomHeight);
                vRandom.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        vRandom.setOnClickListener(this);
        flAdLayout = (FrameLayout) view.findViewById(R.id.fl_ad_layout);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvAuthor = (TextView) view.findViewById(R.id.tv_author);
        tvWc = (TextView) view.findViewById(R.id.tv_wc);
        adManager = ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_IFLY);
        getToday(Urls.MW_TODAY, null);
        loadAd();
    }
    private void getToday(String url, String urlParam) {
        if (urlParam != null) {
            url += urlParam;
        }
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        bean = mGson.fromJson(response.body().toString(), MwResultBean.class);
                        setValues();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showToast(mContext, "网络异常");
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }
    private void loadAd() {
        if (adManager != null) {
            adManager.loadNativeAd(mContext, BaseADManager.ID_DZ_NATIVE, this);
        }
    }
    private void setValues() {
        tvTitle.setText(bean.data.title);
        tvAuthor.setText("作者:" + bean.data.author);
        String time = bean.data.date.curr.substring(0, 4) + "\n" + bean.data.date.curr.substring(4, 6) + "/"
                + bean.data.date.curr.substring(6);
        tvTime.setText(time);
        tvWc.setText("全文共:" + bean.data.wc + "字");
        String content = bean.data.content.replaceAll("</p><p>", "\n\n\t\t\t\t").replaceFirst("<p>", "\n\t\t\t\t").replaceFirst("</p>", "\n\t\t\t\t");
        tvContent.setText(content + "(完)");
        if (scroll_view != null) {
            scroll_view.post(new Runnable() {
                @Override
                public void run() {
                    scroll_view.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meiwen;
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(mContext, ChooseOperActivity.class), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case COLLECT:
                ToastUtils.showToast(mContext, "成功收藏到本地");
                break;
            case COLLECT_LIST:
                ToastUtils.showToast(mContext, "收藏列表");
                break;
            case RANDOM:
                ToastUtils.showToast(mContext, "随机一文");
                getToday(Urls.MW_RANDOM, null);
                loadAd();
                break;
        }
    }

    @Override
    public void onSrcollUp() {
        hideFloatBtn(vRandom, vRandomHeight);
    }

    @Override
    public void onSrollDown() {
        showFloatBtn(vRandom, vRandomHeight);
    }


    @Override
    public void onAdsLoaded(boolean success, Object AdDataO, Object adO, int platform, View adView) {
        if (!success) {
            return;
        }
        if (flAdLayout.getChildCount() != 0) {
            flAdLayout.removeAllViews();
        }
        flAdLayout.addView(adView);
    }
}
