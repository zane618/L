package com.zane.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.zane.utility.L;

/**
 * Created by shizhang on 2017/8/18.
 * 监控滑动方向的Recyclerview
 */

public class DireRecyclerview extends RecyclerView {
    private static final String TAG = "DireRecyclerview";
    private boolean isShow = true;
    private OnDireChanged onDireChanged;
    private int disy;//滑动距离
    public interface OnDireChanged{
        void onShow();
        void onHide();
    }
    public DireRecyclerview(Context context) {
        super(context);
    }

    public DireRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DireRecyclerview(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        //得到第一个item
        int firstVisibleItem = ((LinearLayoutManager) this.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItem == 0) {
            if (!isShow && onDireChanged != null) {
                isShow = true;
                L.e(TAG + "firstVisibleItem == 0");
                onDireChanged.onShow();
            }
        } else { //position=0的不在显示
            if (disy > 25 && isShow && onDireChanged != null) {
                isShow = false;
                onDireChanged.onHide();
                disy = 0;
                L.e(TAG + "onDireChanged.onHide();");
            } else if (disy < -25 && !isShow && onDireChanged != null) {
                isShow = true;
                onDireChanged.onShow();
                disy = 0;
                L.e(TAG + "onDireChanged.onShow();");
            }
        }
        if ((isShow && dy > 0) || (!isShow && dy < 0)) {
            disy += dy;
        }
    }

    public void setOnDireChanged(OnDireChanged onDireChanged) {
        this.onDireChanged = onDireChanged;
    }
}
