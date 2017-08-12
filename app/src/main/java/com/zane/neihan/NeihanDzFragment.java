package com.zane.neihan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.ImageView;

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
import com.zane.utility.ClipboardHelper;
import com.zane.utility.L;
import com.zane.utility.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private int page;
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
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (datas.get(i).type == 1 && datas.get(i).group != null) {
                    ClipboardHelper.copyText(mContext, datas.get(i).group.content);
                    ToastUtils.showToast(mContext, "已复制到剪切板");
                }
                return true;
            }
        });
        swLayout.post(new Runnable() {
            @Override
            public void run() {
                swLayout.setRefreshing(true);
            }
        });
        adManager = ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_IFLY);
        if (adManager != null) {
            adManager.loadNativeAd(mContext, BaseADManager.ID_QT_NATIVE, this);
        }
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
                                page ++;
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
                        } else {
                            swLayout.setEnabled(true);
                        }
                        adapter.setEnableLoadMore(true);
                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke_dz;
    }

    @Override
    public void onRefresh() {
        page = 1;
        adapter.setEnableLoadMore(false);
        getDataRand(true);
    }

    @Override
    public void onLoadMoreRequested() {
        if (/*page % 2 == 0 &&*/ adManager != null) {
            adManager.loadNativeAd(mContext, BaseADManager.ID_QT_NATIVE, this);
        }
        swLayout.setEnabled(false);//加载更多，就不能下拉刷新
        getDataRand(false);
    }
    @Override
    public void onAdsLoaded(boolean success, Object AdDataO, Object adO, int platform, View adView) {
        if (success) {
            int size = datas.size();
            if (size < 10) {
                return;
            }
            NeihandzBean.MDataItem item = new NeihandzBean.MDataItem();
            item.adView = adView;
            int insetPosition = new Random().nextInt(6);
            datas.add(size - insetPosition, item);
            adapter.notifyItemInserted(size - insetPosition);
        }
    }
}
