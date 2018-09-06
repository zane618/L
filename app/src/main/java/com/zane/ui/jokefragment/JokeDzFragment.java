package com.zane.ui.jokefragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zane.apis.Urls;
import com.zane.bean.JokeDzRandBean;
import com.zane.customview.DireRecyclerview;
import com.zane.l.R;
import com.zane.ui.base.BaseFragment;
import com.zane.bean.JokeDzBean;
import com.zane.util.CatchLinearLayoutManager;
import com.zane.utility.ClipboardHelper;
import com.zane.utility.DensityUtils;
import com.zane.utility.L;
import com.zane.utility.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shizhang on 2017/7/5.
 */

public class JokeDzFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener, DireRecyclerview.OnDireChanged{
    private static final String TAG = "JokeDzFragment";
    private static final int BROWSE_RAND_TYPE = 0;//随机
    private static final int BROWSE_TEXT_TYPE = 1;//最新
    private static final int BROWSE_TIME_ASC_TYPE = 2;//时间后
    private static final int BROWSE_TIME_DESC_TYPE = 3;//时间前
    private SwipeRefreshLayout swLayout;
    private DireRecyclerview recyclerView;
    private AdapterJokeDz adapter;
    private List<JokeDzBean.MData> datas = new ArrayList<>();
    private int page = 1;
    private String time;//时间戳
    private String sort;
    private int browseType = 0;//浏览类型(包含时间前、后
    private ImageView ivBackTop;
    private int sfbHeitht;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initVs(view);
    }

    private void initVs(View view) {
        swLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_layout);
        swLayout.setOnRefreshListener(this);
        swLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView = (DireRecyclerview) view.findViewById(R.id.recyler_view);
        recyclerView.setOnDireChanged(this);
        ivBackTop = (ImageView) view.findViewById(R.id.iv_back_top);
        ivBackTop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sfbHeitht = DensityUtils.dp2px(mContext, 15) + ivBackTop.getHeight();
                hideFloatBtn(ivBackTop, sfbHeitht);
                ivBackTop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        ivBackTop.setOnClickListener(this);
        recyclerView.setLayoutManager(new CatchLinearLayoutManager(mContext));
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//去掉只要设置为false，就可以不显示动画了，也就解决了闪烁问题。 （所有的notifyItem*动画都取消了）
        adapter = new AdapterJokeDz(R.layout.adpter_joke_dz, datas);
        adapter.setOnLoadMoreListener(this, recyclerView);
//        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 10, mContext.getResources().getColor(R.color.bg)));
        recyclerView.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage();//这个去除第一次回调加载更多方法,超过一屏无须
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (datas.get(i).layoutType == 0 && datas.get(i) != null) {
                    ClipboardHelper.copyText(mContext, datas.get(i).content);
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
        doBrowseType(true);
    }
    /**
     * 随机看
     */
    private void getDataRand(final boolean isRefresh) {
        OkGo.<String>get(Urls.URL_JOKE_RAND + "?key=" +
                mContext.getString(R.string.joke_app_key))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JokeDzRandBean jokeDzRandBean = mGson.fromJson(response.body().toString(), JokeDzRandBean.class);
                        if (jokeDzRandBean.error_code == 0) {
                            List<JokeDzBean.MData> list = jokeDzRandBean.result;
                            if (list != null && list.size() > 0) {
                                if (isRefresh) {
//                                    int size = datas.size();
                                    datas.clear();
//                                    adapter.notifyItemRangeRemoved(0, size);
                                    adapter.notifyDataSetChanged();
//                                    if (adView != null) {
//                                        datas.add(new JokeDzBean.MData("a", "a", 1, adView));
//                                    }
                                }
                                adapter.addData(list);
                                if (list.size() == Urls.PAGESIZE) {
                                    adapter.loadMoreComplete();
                                } else {
                                    adapter.loadMoreEnd();
                                }
                                L.e("size" + datas.size());
                                page ++;
                            } else {
                                adapter.loadMoreFail();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showToast(mContext, mContext.getString(R.string.net_error));
                        L.e("失败");
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

    //设置浏览类型参数
    public void doSetBrowseParameterValue(Intent intent) {
        if (null != intent && browseType != intent.getIntExtra("browseType", 0)) {
            browseType = intent.getIntExtra("browseType", 0);
            sort = intent.getStringExtra("sort");
            time = intent.getStringExtra("time");
            page = 0;
        }
        doBrowseType(true);
    }

    /**
     *
     * @param isRefresh 是否下拉刷新，用于判断page是否++
     */
    private void doBrowseType(boolean isRefresh) {
        switch (browseType) {
            case BROWSE_RAND_TYPE:
                getDataRand(isRefresh);
                break;
            case BROWSE_TEXT_TYPE:
//                if (!isRefresh) {
//                    page++;
//                }
                break;
            case BROWSE_TIME_ASC_TYPE:
//                if (!isRefresh) {
//                    page++;
//                }
//                getDataByTime(isRefresh);
                recyclerView.smoothScrollToPosition(0);
                break;
            case BROWSE_TIME_DESC_TYPE:
//                if (!isRefresh) {
//                    page++;
//                }
//                getDataByTime(isRefresh);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke_dz;
    }

    @Override
    public void onRefresh() {
        page = 1;
        adapter.setEnableLoadMore(false);
        doBrowseType(true);
    }

    @Override
    public void onLoadMoreRequested() {
        swLayout.setEnabled(false);//加载更多，就不能下拉刷新
        doBrowseType(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_top:
                recyclerView.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    public void onShow() {
        showFloatBtn(ivBackTop, sfbHeitht);
    }

    @Override
    public void onHide() {
        hideFloatBtn(ivBackTop, sfbHeitht);
    }
}
