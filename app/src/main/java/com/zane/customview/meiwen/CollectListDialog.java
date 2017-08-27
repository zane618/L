package com.zane.customview.meiwen;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zane.custome.RecycleViewDivider;
import com.zane.l.R;
import com.zane.ui.meiwen.db.MeiwEntity;

import java.util.List;

/**
 * Created by shizhang on 2017/8/27.
 */

public class CollectListDialog extends Dialog {
    private RecyclerView recyclerView;
    private List<MeiwEntity> datas;
    private CollectListAdapter adapter;
    private Context mContext;
    private OnItemChoose onItemChoose;
    private View close;
    public interface OnItemChoose {
        void onItem(String date);
    }
    public CollectListDialog(@NonNull Context context, List<MeiwEntity> datas, OnItemChoose onItemChoose) {
        super(context, R.style.MyDialog);
        this.datas = datas;
        mContext = context;
        this.onItemChoose = onItemChoose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_meiwen_collect_list);
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        close = findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        adapter = new CollectListAdapter(0, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dismiss();
                if (onItemChoose != null) {
                    onItemChoose.onItem(datas.get(position).getDate());
                }
            }
        });
    }

    public void upData(List<MeiwEntity> datas) {
        if (this.datas != null && this.datas.size() > 0) {
            this.datas.clear();
        }
        this.datas.addAll(datas);
        this.adapter.notifyDataSetChanged();
    }
}
