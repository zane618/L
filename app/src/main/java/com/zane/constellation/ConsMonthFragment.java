package com.zane.constellation;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zane.apis.Urls;
import com.zane.l.R;
import com.zane.ui.base.BaseFragment;
import com.zane.utility.ToastUtils;

/**
 * Created by shizhang on 2017/8/6.
 */

public class ConsMonthFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swLayout;
    private TextView tvName;
    private TextView tvTime;
    private TextView tvSummary;
    private TextView tvHealth;
    private TextView tvLove;
    private TextView tvWork;
    private TextView tvWealth;

    private String name;
    private ConstellationBean bean;
    private boolean firstFlag = true;
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        init(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString(ConsDetailActivity.NAME_KEY);
        }
    }

    public void fuck() {
        if (name != null && firstFlag) {
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

    private void getData() {
        OkGo.<String>get(Urls.URL_CONSTELLATION_ALL + "month&consName=" + name)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ConstellationBean bean = mGson.fromJson(response.body().toString(), ConstellationBean.class);
                        if (bean.error_code == 0) {
                            if (bean != null) {
                                ConsMonthFragment.this.bean = bean;
                                setViews();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showToast(mContext, "网络异常");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (swLayout.isRefreshing()) {
                            swLayout.setRefreshing(false);
                        }
                    }
                });
    }

    private void setViews() {
        tvName.setText(bean.name);
        tvTime.setText(bean.date);
        tvSummary.setText(bean.all);
        tvHealth.setText(bean.health);
        tvLove.setText(bean.love);
        tvWork.setText(bean.work);
        tvWealth.setText(bean.money);
    }
    private void init(View view) {
        swLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw_layout);
        swLayout.setOnRefreshListener(this);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvSummary = (TextView) view.findViewById(R.id.tv_summary);
        tvHealth = (TextView) view.findViewById(R.id.tv_health);
        tvLove = (TextView) view.findViewById(R.id.tv_love);
        tvWork = (TextView) view.findViewById(R.id.tv_work);
        tvWealth = (TextView) view.findViewById(R.id.tv_wealth);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cons_month;
    }

    @Override
    public void onRefresh() {
        if (bean != null) {
            if (swLayout.isRefreshing()) {
                swLayout.setRefreshing(false);
            }
        } else {
            getData();
        }
    }
}
