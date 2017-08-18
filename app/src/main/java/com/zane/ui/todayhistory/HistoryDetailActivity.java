package com.zane.ui.todayhistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zane.ads.ADManagerFactory;
import com.zane.ads.BaseADManager;
import com.zane.ads.OnAdsListener;
import com.zane.apis.Urls;
import com.zane.custome.RecycleViewDivider;
import com.zane.l.R;
import com.zane.ui.ImageFragmentActivity;
import com.zane.ui.base.BaseFragmentActivity;
import com.zane.utility.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by shizhang on 2017/7/30.
 */

public class HistoryDetailActivity extends BaseFragmentActivity implements View.OnClickListener{
    private View tvBack;
    private TextView tvTitle;
    private View headView;
    private RecyclerView recyclerView;
    private HistoryTodayDetailAdapter adapter;
    private List<HistoryDetailBean.MPic> pics = new ArrayList<>();
    private String eId;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_history_today);
    }

    @Override
    public void initView() {
        tvBack = findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        if (mGetIntent != null) {
            eId = mGetIntent.getStringExtra("eId");
        }
        tvBack.setOnClickListener(this);
        adapter = new HistoryTodayDetailAdapter(R.layout.adpater_history_today_detail, pics);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArray("titles", new String[]{pics.get(position).pic_title});
                bundle.putStringArray("imgUrls", new String[]{pics.get(position).url});
                startActivity(new Intent(mContext, ImageFragmentActivity.class).putExtras(bundle));
            }
        });
        recyclerView.setAdapter(adapter);
        getData();
        loadInsertAd();
    }

    private void loadInsertAd() {
        if (0 != new Random().nextInt(2)) {
            return;
        }
        ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_IFLY)
                .loadInterstitialAd(mContext, BaseADManager.ID_INTERT, new OnAdsListener() {
                    @Override
                    public void onAdsLoaded(boolean success, Object AdDataO, Object adO, int platform, View adView) {

                    }
                });
    }

    /**
     * 获取详情数据
     */
    private void getData() {
        if (eId == null) {
            finish();
            ToastUtils.showToast(mContext, "这条数据懵逼了");
            return;
        }
        OkGo.<String>get(Urls.URL_HISTORY_TODAY_DETAIL + eId)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        HistoryDetailBean bean = mGson.fromJson(response.body().toString(), HistoryDetailBean.class);
                        if (bean.error_code == 0 && bean.result != null && bean.result.size() > 0) {
                            HistoryDetailBean.MResult mResult = bean.result.get(0);
                            String title = mResult.title;
                            String content = mResult.content;
                            tvTitle.setText(title);
                            adapter.addHeaderView(headView(content));
                            adapter.addData(mResult.picUrl);
                            adapter.loadMoreEnd();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private View headView(String content) {
        if (headView == null) {
            headView = LayoutInflater.from(mContext).inflate(R.layout.head_distory_detail, null);
        }
        ((TextView) headView.findViewById(R.id.tv_content)).setText(content);
        return headView;
    }

    @Override
    public void onClick(View v) {
        if (tvBack == v) {
            finish();
        }
    }
}
