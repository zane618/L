package com.zane.constellation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zane.apis.Urls;
import com.zane.l.R;
import com.zane.ui.base.BaseFragment;
import com.zane.utility.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

/**
 * Created by shizhang on 2017/6/26.
 */

public class ConstellationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnSwipeListener{
    private SwipeRefreshLayout swLayout;
    private RecyclerView recyclerView;
    private ConstellationAdapter adapter;
    private List<ConstellationBean> datas = new ArrayList<>();
    private String[] constellations = new String[]{"白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};
    private int success;
    private int error;
    private boolean firstFlag = true;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        swLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_layout);
        swLayout.setOnRefreshListener(this);
        swLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ConstellationAdapter(R.layout.adapter_constellation_day, datas);
        recyclerView.setAdapter(adapter);
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(adapter, datas, ConstellationFragment.this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showToast(mContext, datas.get(position).name);
            }
        });
    }
    /**
     * 是否第一次启动
     */
    public void fuck() {
        if (firstFlag) {
            swLayout.post(new Runnable() {
                @Override
                public void run() {
                    swLayout.setRefreshing(true);
                }
            });
            firstFlag = false;
        }
        getData(false);
    }
    /**
     * 获取数据
     */
    private void getData(boolean isRefresh) {
        if (isRefresh) {
            success = 0;
            error = 0;
            datas.clear();
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < 12; i++) {
            OkGo.<String>get(Urls.URL_CONSTELLATION_DAY + constellations[i])
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            ConstellationBean bean = mGson.fromJson(response.body().toString(), ConstellationBean.class);
                            if (bean.error_code == 0) {
                                success++;
                                if (bean != null) {
                                    datas.add(bean);
                                }
                            } else {
                                error++;
                            }
                            updateAdapter();
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            error++;
                            updateAdapter();
                        }

                    });
        }

    }

    /**
     *  12次请求完毕后刷新adapter
     */
    private void updateAdapter() {
        if (success + error == 12) {
            adapter.notifyDataSetChanged();
            if (swLayout.isRefreshing()) {
                swLayout.setRefreshing(false);
            }
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_constellation;
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    @Override
    public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
        viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
        if (direction == CardConfig.SWIPING_LEFT) {
            ((BaseViewHolder) viewHolder).getView(R.id.iv_dislike).setAlpha(Math.abs(ratio));
        } else if (direction == CardConfig.SWIPING_RIGHT) {
            ((BaseViewHolder) viewHolder).getView(R.id.iv_like).setAlpha(Math.abs(ratio));
        } else {
            ((BaseViewHolder) viewHolder).getView(R.id.iv_like).setAlpha(0);
            ((BaseViewHolder) viewHolder).getView(R.id.iv_dislike).setAlpha(0);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
        viewHolder.itemView.setAlpha(1f);
        ((BaseViewHolder) viewHolder).getView(R.id.iv_dislike).setAlpha(Math.abs(0f));
        ((BaseViewHolder) viewHolder).getView(R.id.iv_like).setAlpha(Math.abs(0f));
        if (direction == CardConfig.SWIPING_LEFT) {
            Intent intent = new Intent(mContext, ConsDetailActivity.class);
            intent.putExtra(ConsDetailActivity.BEAN_KEY, (ConstellationBean) o);
            startActivity(intent);
        }
    }

    @Override
    public void onSwipedClear() {

    }
}
