package com.zane.ui.jokefragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.zane.bean.JokeQtBean;
import com.zane.bean.JokeQtRandBean;
import com.zane.l.R;
import com.zane.ui.ImageFragmentActivity;
import com.zane.ui.base.BaseFragment;
import com.zane.util.CatchLinearLayoutManager;
import com.zane.utility.L;
import com.zane.utility.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shizhang on 2017/7/10.
 */

public class JokeQtFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, OnAdsListener {
    private static final String TAG = "JokeDzFragment";
    private static final int BROWSE_RAND_TYPE = 0;//随机
    private static final int BROWSE_TEXT_TYPE = 1;//最新
    private static final int BROWSE_TIME_ASC_TYPE = 2;//时间后
    private static final int BROWSE_TIME_DESC_TYPE = 3;//时间前
    private SwipeRefreshLayout swLayout;
    private RecyclerView recyclerView;
    private AdapterJokeQt adapter;
    private List<JokeQtBean.MData> datas = new ArrayList<>();
    private int page = 1;
    private String time;//时间戳
    private String sort;
    private int browseType = 0;//浏览类型(包含时间前、后
     private BaseADManager adManager;
    private boolean firstFlag = true;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initVs(view);
    }

    private void initVs(View view) {
        swLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_layout);
        swLayout.setOnRefreshListener(this);
        swLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//瀑布流
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));//瀑布流
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//去掉只要设置为false，就可以不显示动画了，也就解决了闪烁问题。 （所有的notifyItem*动画都取消了）
        adapter = new AdapterJokeQt(R.layout.adpter_joke_qt, datas);
        adapter.setOnLoadMoreListener(this, recyclerView);
//        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 10, mContext.getResources().getColor(R.color.bg)));
        recyclerView.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage();//这个去除第一次回调加载更多方法,超过一屏无须
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String[] imgUrls = null;
                String[] titles = null;
                if (position < datas.size() - 2) {
                    imgUrls = new String[]{datas.get(position).url, datas.get(position + 1).url, datas.get(position + 2).url};
                    titles = new String[]{datas.get(position).content, datas.get(position + 1).content, datas.get(position + 2).content};
                } else if (position < datas.size() - 1) {
                    imgUrls = new String[]{datas.get(position).url, datas.get(position + 1).url};
                    titles = new String[]{datas.get(position).content, datas.get(position + 1).content};
                } else {
                    imgUrls = new String[]{datas.get(position).url};
                    titles = new String[]{datas.get(position).content};
                }
                Bundle bundle = new Bundle();
                bundle.putStringArray("titles", titles);
                bundle.putStringArray("imgUrls", imgUrls);
                startActivity(new Intent(mContext, ImageFragmentActivity.class).putExtras(bundle));
            }
        });

        adManager = ADManagerFactory.getADManager(mContext, BaseADManager.AD_PLATFORM_IFLY);
//        if (adManager != null) {
//            adManager.loadNativeAd(mContext, BaseADManager.ID_QT_NATIVE, this);
//        }
//        doBrowseType(true);
    }
    public void fuck() {
        if (firstFlag) {
            swLayout.post(new Runnable() {
                @Override
                public void run() {
                    swLayout.setRefreshing(true);
                }
            });
            firstFlag = false;
//            if (adManager != null) {
//                adManager.loadNativeAd(mContext, BaseADManager.ID_QT_NATIVE, this);
//            }
            doBrowseType(true);
        }
    }
    /**
     * 看最新
     */
    private void getDataText(final boolean isRefresh) {
        OkGo.<String>get(Urls.URL_JOKE_IMG_TEXT + "?key=" +
                mContext.getString(R.string.joke_app_key) + "&page=" + page +
                "&pagesize=" + Urls.PAGESIZE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JokeQtBean jokeQtBean = mGson.fromJson(response.body().toString(), JokeQtBean.class);
                        if (jokeQtBean.error_code == 0) {
                            List<JokeQtBean.MData> list = jokeQtBean.result.data;
                            if (list != null && list.size() > 0) {
                                if (isRefresh) {
//                                    int size = datas.size();
                                    datas.clear();
//                                    adapter.notifyItemRangeRemoved(0, size);
                                    adapter.notifyDataSetChanged();
//                                    datas.add(new JokeQtBean.MData("a", "a", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499671312058&di=ed0cd4038eda33c4163ca5c5cc52d561&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201411%2F07%2F20141107192221_kCHCG.jpeg", 1));
                                }
//                                for (JokeDzBean.MData data : list) {
//                                    data.layoutType = 0;
//                                }
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
                        L.e("看最新失败");
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
    /**
     * 随机看
     */
    private void getDataRand(final boolean isRefresh) {
        OkGo.<String>get(Urls.URL_JOKE_RAND + "?key=" +
                mContext.getString(R.string.joke_app_key) + "&type=pic")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JokeQtRandBean jokeQtRandBean = mGson.fromJson(response.body().toString(), JokeQtRandBean.class);
                        if (jokeQtRandBean.error_code == 0) {
                            List<JokeQtBean.MData> list = jokeQtRandBean.result;
                            if (list != null && list.size() > 0) {
                                if (isRefresh) {
//                                    int size = datas.size();
                                    datas.clear();
//                                    adapter.notifyItemRangeRemoved(0, size);
                                    adapter.notifyDataSetChanged();
                                    if (adView != null) {
                                        datas.add(new JokeQtBean.MData(null, null, null, 1, adView));
                                    }
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
                            adapter.setEnableLoadMore(true);
                        } else {
                            swLayout.setEnabled(true);
                        }
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
                getDataText(isRefresh);
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
        return R.layout.fragment_joke_qt;
    }

    @Override
    public void onRefresh() {
        L.e(TAG+ ":onRefresh");
        adapter.setEnableLoadMore(false);
        page = 1;
//        if (adManager != null) {
//            adManager.loadNativeAd(mContext, BaseADManager.ID_QT_NATIVE, this);
//        }
        doBrowseType(true);
    }

    @Override
    public void onLoadMoreRequested() {
        L.e(TAG+ ":onLoadMoreRequested");
        swLayout.setEnabled(false);//加载更多，就不能下拉刷新
        doBrowseType(false);
    }
    private View adView;
    @Override
    public void onAdsLoaded(boolean success, Object AdDataO, Object adO, int platform, View adView) {
        if (success) {
            this.adView = adView;
            doBrowseType(true);
        } else {
            this.adView = null;
            doBrowseType(true);
        }
    }
}
