package com.zane.ui.constellation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zane.l.R;
import com.zane.ui.base.BaseFragment;

/**
 * Created by shizhang on 2017/8/6.
 */

public class ConsDayFragment extends BaseFragment {
    private TextView tvName;
    private TextView tvTime;
    private TextView tvColor;
    private TextView tvFriend;
    private TextView tvNumber;
    private TextView tvHealth;
    private TextView tvBirth;
    private TextView tvSummary;
    private RatingBar rbZhzs;
    private RatingBar rbLove;
    private RatingBar rbWork;
    private RatingBar rbWealth;

    private ConstellationBean bean;
    private int rb_hight;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        init(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = (ConstellationBean) bundle.getSerializable(ConsDetailActivity.BEAN_KEY);
        }
        if (bean != null) {
            tvName.setText(bean.name);
            tvBirth.setText(bean.birth);
            tvTime.setText(bean.datetime);
            tvColor.setText(bean.color);
            tvFriend.setText(bean.QFriend);
            tvNumber.setText(bean.number);
            tvHealth.setText(bean.health);
            tvSummary.setText(bean.summary);
            rbZhzs.setRating(Float.parseFloat(bean.all.substring(0, bean.all.length() - 1)) / 20);
            rbLove.setRating(Float.parseFloat(bean.love.substring(0, bean.love.length() - 1)) / 20);
            rbWork.setRating(Float.parseFloat(bean.work.substring(0, bean.work.length() - 1)) / 20);
            rbWealth.setRating(Float.parseFloat(bean.money.substring(0, bean.money.length() - 1)) / 20);
        }
    }

    private void init(View view) {
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvColor = (TextView) view.findViewById(R.id.tv_color);
        tvFriend = (TextView) view.findViewById(R.id.tv_friend);
        tvNumber = (TextView) view.findViewById(R.id.tv_number);
        tvHealth = (TextView) view.findViewById(R.id.tv_health);
        tvBirth = (TextView) view.findViewById(R.id.tv_birth);
        tvSummary = (TextView) view.findViewById(R.id.tv_summary);
        rbZhzs = (RatingBar) view.findViewById(R.id.rb_zhzs);
        rbLove = (RatingBar) view.findViewById(R.id.rb_love);
        rbWork = (RatingBar) view.findViewById(R.id.rb_work);
        rbWealth = (RatingBar) view.findViewById(R.id.rb_wealth);
        if (rb_hight == 0) {
            try {
                Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_start_sel);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rbZhzs.getLayoutParams();
                lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.height = bmp.getHeight();
                rbLove.setLayoutParams(lp);
                rbWork.setLayoutParams(lp);
                rbWealth.setLayoutParams(lp);
            } catch (Exception e){
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cons_day;
    }
}
