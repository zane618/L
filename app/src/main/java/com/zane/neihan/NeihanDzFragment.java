package com.zane.neihan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zane.ads.ADManagerFactory;
import com.zane.ads.BaseADManager;
import com.zane.ads.OnAdsListener;
import com.zane.apis.Urls;
import com.zane.bean.JokeDzBean;
import com.zane.bean.JokeDzRandBean;
import com.zane.l.R;
import com.zane.ui.base.BaseFragment;
import com.zane.ui.jokefragment.AdapterJokeDz;
import com.zane.util.CatchLinearLayoutManager;
import com.zane.utility.L;
import com.zane.utility.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by shizhang on 2017/7/5.
 */

public class NeihanDzFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, OnAdsListener{
    private SwipeRefreshLayout swLayout;
    private RecyclerView recyclerView;
    private NeihanDzAdapter adapter;
    private List<NeihandzBean.MDataItem> datas = new ArrayList<>();
    private BaseADManager adManager;
    private String min_time = "1502008860"; //上次更新的时间

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initVs(view);
    }

    private void initVs(View view) {
        swLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_layout);
        swLayout.setOnRefreshListener(this);
        swLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new CatchLinearLayoutManager(mContext));
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//去掉只要设置为false，就可以不显示动画了，也就解决了闪烁问题。 （所有的notifyItem*动画都取消了）
        adapter = new NeihanDzAdapter(datas);
        adapter.setOnLoadMoreListener(this, recyclerView);
//        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 10, mContext.getResources().getColor(R.color.bg)));
        recyclerView.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage();//这个去除第一次回调加载更多方法,超过一屏无须
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        swLayout.post(new Runnable() {
            @Override
            public void run() {
                swLayout.setRefreshing(true);
            }
        });
        adManager = ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_IFLY);
//        if (adManager != null) {
//            adManager.loadNativeAd(mContext, BaseADManager.ID_DZ_NATIVE, this);
//        }
        getDataRand(true);
    }
    /**
     * 随机看
     */
    private void getDataRand(final boolean isRefresh) {
        OkGo.<String>get(Urls.NEIHAN_DZ_DZ + min_time)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        NeihandzBean bean = mGson.fromJson(response.body().toString(), NeihandzBean.class);
                        if (bean.message.equals("success")) {
                            min_time = bean.data.min_time;
                            List<NeihandzBean.MDataItem> list = bean.data.data;
                            if (list != null && list.size() > 0) {
                                if (isRefresh) {
                                    datas.clear();
                                }
                                adapter.addData(list);
                                adapter.loadMoreComplete();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showToast(mContext, mContext.getString(R.string.net_error));
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (isRefresh) {
                            swLayout.setRefreshing(false);
                            adapter.setEnableLoadMore(true);
                        } else {
                            swLayout.setEnabled(true);
                        }
                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke_dz;
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        getDataRand(true);
//        if (adManager != null) {
//            adManager.loadNativeAd(mContext, BaseADManager.ID_DZ_NATIVE, this);
//        }
    }

    @Override
    public void onLoadMoreRequested() {
        swLayout.setEnabled(false);//加载更多，就不能下拉刷新
        getDataRand(false);
    }
    private View adView;
    @Override
    public void onAdsLoaded(boolean success, Object AdDataO, Object adO, int platform, View adView) {
        if (success) {
            this.adView = adView;
        } else {
            this.adView = null;
        }
    }
}
