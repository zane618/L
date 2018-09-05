package com.zane.ui.todayhistory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zane.apis.Urls;
import com.zane.l.R;
import com.zane.ui.base.BaseFragment;
import com.zane.utility.ClipboardHelper;
import com.zane.utility.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 历史上的今天，列表界面
 * Created by shizhang on 2017/7/30.
 */

public class FragmentHistoryToday extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swLayout;
    private RecyclerView recyclerView;
    private HistoryTodayAdapter adapter;
    private List<HistoryTodayListBean.MData> datas = new ArrayList<>();
    private int month = 1;
    private int day;
    private boolean firstFlag = true;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        swLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_layout);
        swLayout.setOnRefreshListener(this);
        swLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        adapter = new HistoryTodayAdapter(R.layout.adapter_hisory_today, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, HistoryDetailActivity.class);
                intent.putExtra("eId", datas.get(position).e_id);
                startActivity(intent);
            }
        });

        adapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                ClipboardHelper.copyText(mContext, datas.get(position).date + "，" + datas.get(position).title);
                ToastUtils.showToast(mContext, "已复制到剪切板");
                return true;
            }
        });
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
//        getData();
    }
    public void fuck() {
        if (firstFlag && swLayout != null) {
            swLayout.post(new Runnable() {
                @Override
                public void run() {
                    swLayout.setRefreshing(true);
                }
            });
            firstFlag = false;
            getData();
        }
    }
    /**
     * 获取列表数据
     */
    private void getData() {
        OkGo.<String>get(Urls.URL_HISTORY_TODAY_LIST + month + "/" + day)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        HistoryTodayListBean bean = mGson.fromJson(response.body().toString(), HistoryTodayListBean.class);
                        if (bean.error_code == 0) {
                            List<HistoryTodayListBean.MData> list = bean.result;
                            if (datas != null && list != null && list.size() > 0) {
                                datas.clear();
                                adapter.addData(list);
                                adapter.loadMoreEnd();
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
                        if (swLayout.isRefreshing()) {
                            swLayout.setRefreshing(false);
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history_today;
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
