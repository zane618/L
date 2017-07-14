package com.zane.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zane.bean.JokeDzBean;

/**
 * 解决recyclerview 错误
 * Created by shizhang on 2017/7/9.
 */

public class CatchLinearLayoutManager extends LinearLayoutManager{
    public CatchLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
        }
    }
    /*方法二：
     if (page == 0) {
        int size = datas.size();
        datas.clear();
        adapter.notifyItemRangeRemoved(0, size);
        datas.add(new JokeDzBean.MData("a", "a", 1));
    }*/
}
