package com.zane.constellation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zane.l.R;
import com.zane.utility.L;

import java.util.List;

import cn.bmob.v3.Bmob;

import static java.lang.Integer.parseInt;

/**
 * Created by shizhang on 2017/8/3.
 */

public class ConstellationAdapter extends BaseQuickAdapter<ConstellationBean, BaseViewHolder> {
    private int rb_hight;
    public ConstellationAdapter(@LayoutRes int layoutResId, @Nullable List<ConstellationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ConstellationBean item) {
        if (rb_hight == 0) {
            try {
                Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_start_sel);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.getView(R.id.rb_zhzs).getLayoutParams();
                lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.height = bmp.getHeight();
                holder.getView(R.id.rb_love).setLayoutParams(lp);
                holder.getView(R.id.rb_wealth).setLayoutParams(lp);
                holder.getView(R.id.rb_work).setLayoutParams(lp);
            } catch (Exception e){
            }
        }
        ((RatingBar) holder.getView(R.id.rb_zhzs)).setRating(
                Float.parseFloat(item.all.substring(0, item.all.length() - 1)) / 20
        );
        ((RatingBar) holder.getView(R.id.rb_love)).setRating(
                Float.parseFloat(item.love.substring(0, item.love.length() - 1)) / 20
        );
        ((RatingBar) holder.getView(R.id.rb_wealth)).setRating(
                Float.parseFloat(item.money.substring(0, item.money.length() - 1)) / 20
        );
        ((RatingBar) holder.getView(R.id.rb_work)).setRating(
                Float.parseFloat(item.work.substring(0, item.work.length() - 1)) / 20
        );
        holder.setText(R.id.tv_name, item.name)
                .setText(R.id.tv_time, item.datetime)
                .setText(R.id.tv_color, item.color)
                .setText(R.id.tv_number, item.number)
                .setText(R.id.tv_friend, item.QFriend)
                .setText(R.id.tv_health, item.health)
                .setText(R.id.tv_summary, item.summary);
        switch (item.name) {
            case "白羊座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历3月21日-4月19日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历3月21日-4月19日)");
                break;
            case "金牛座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历4月20日-5月20日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历4月20日-5月20日)");
                break;
            case "双子座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历5月21日-6月21日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历5月21日-6月21日)");
                break;
            case "巨蟹座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历6月22日-7月22日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历6月22日-7月22日)");
                break;
            case "狮子座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历7月23日-8月22日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历7月23日-8月22日)");
                break;
            case "处女座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历8月23日-9月22日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历8月23日-9月22日)");
                break;
            case "天秤座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历9月23日-10月23日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历9月23日-10月23日)");
                break;
            case "天蝎座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历10月24日-11月22)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历10月24日-11月22)");
                break;
            case "射手座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历11月23日-12月21)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历11月23日-12月21)");
                break;
            case "摩羯座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历12月22日-1月19日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历12月22日-1月19日)");
                break;
            case "水瓶座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历1月20日-2月18日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历1月20日-2月18日)");
                break;
            case "双鱼座":
                if (TextUtils.isEmpty(item.birth)) {
                    item.birth = "(出生于阳历)2月19日-3月20日)";
                }
                holder.setText(R.id.tv_birth, "(出生于阳历)2月19日-3月20日");
                break;

        }
    }
}
