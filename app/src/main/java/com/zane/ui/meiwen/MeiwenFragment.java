package com.zane.ui.meiwen;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zane.apis.Urls;
import com.zane.customview.DireScrollView;
import com.zane.customview.meiwen.CollectListAdapter;
import com.zane.customview.meiwen.CollectListDialog;
import com.zane.l.R;
import com.zane.ui.MyApplication;
import com.zane.ui.base.BaseFragment;
import com.zane.ui.meiwen.db.DaoMaster;
import com.zane.ui.meiwen.db.DaoSession;
import com.zane.ui.meiwen.db.MeiwEntity;
import com.zane.ui.meiwen.db.MeiwEntityDao;
import com.zane.utility.DensityUtils;
import com.zane.utility.L;
import com.zane.utility.ToastUtils;

import org.greenrobot.greendao.database.Database;

import java.util.List;
import java.util.Random;


/**
 * Created by shizhang on 2017/8/20.
 */

public class MeiwenFragment extends BaseFragment implements View.OnClickListener
, DireScrollView.OnSrcollDireChanged, CollectListDialog.OnItemChoose{
    public static final int COLLECT = 1;//收藏
    public static final int COLLECT_LIST = 2;//收藏列表
    public static final int RANDOM = 3;//随机一文
    public static final String IS_EXIST = "is_exist";
    private TextView tvTime;
    private TextView tvContent;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvWc;
    private MwResultBean bean;
    private View vRandom;
    private int vRandomHeight;
    private DireScrollView scroll_view;
    private FrameLayout flAdLayout;
    public static MeiwEntityDao meiwenDao;
    private CollectListDialog collectListDialog;
    private int page = 1;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        scroll_view = (DireScrollView) view.findViewById(R.id.scroll_view);
        scroll_view.setOnSrcollDireChanged(this);
//        scroll_view.setOnTouchListener(this);
        vRandom = view.findViewById(R.id.iv_back_top);
        vRandom.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                vRandomHeight = DensityUtils.dp2px(mContext, 15) + vRandom.getHeight();
//                hideFloatBtn(vRandom, vRandomHeight);
                vRandom.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        vRandom.setOnClickListener(this);
        flAdLayout = (FrameLayout) view.findViewById(R.id.fl_ad_layout);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvAuthor = (TextView) view.findViewById(R.id.tv_author);
        tvWc = (TextView) view.findViewById(R.id.tv_wc);
        getToday(Urls.MW_TODAY, null);
        initMeiwenDb();
    }
    private void initMeiwenDb() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "meiwen");
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        meiwenDao = daoSession.getMeiwEntityDao();
    }
    private void getToday(String url, String urlParam) {
        if (urlParam != null) {
            url += urlParam;
        }
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        bean = mGson.fromJson(response.body().toString(), MwResultBean.class);
                        setValues();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showToast(mContext, "网络异常");
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }

    private void setValues() {
        tvTitle.setText(bean.data.title);
        tvAuthor.setText("作者:" + bean.data.author);
        String time = bean.data.date.curr.substring(0, 4) + "\n" + bean.data.date.curr.substring(4, 6) + "/"
                + bean.data.date.curr.substring(6);
        tvTime.setText(time);
        tvWc.setText("全文共:" + bean.data.wc + "字");
        String content = bean.data.content.replaceAll("</p><p>", "\n\n\t\t\t\t").replaceFirst("<p>", "\n\t\t\t\t").replaceFirst("</p>", "\n\t\t\t\t");
        tvContent.setText(content + "(完)");
        if (scroll_view != null) {
            scroll_view.post(new Runnable() {
                @Override
                public void run() {
                    scroll_view.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        }
        queryExist();//每次加载完一条就查一个是否存在
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meiwen;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ChooseOperActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_EXIST, isExist);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    private boolean isExist = false;
    private boolean queryExist() {
        List<MeiwEntity> meiwEntities = meiwenDao.queryBuilder()
                .where(MeiwEntityDao.Properties.Title.eq(bean.data.title))
                .build().list();
        if (meiwEntities != null && meiwEntities.size() > 0) {
            isExist = true;
        } else {
            isExist = false;
        }
        return isExist;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case COLLECT:
                if (isExist) {//存在、就删
                    ToastUtils.showToast(mContext, "已取消");
                    isExist = false;
                    MeiwEntity meiwEntity = meiwenDao.queryBuilder()
                            .where(MeiwEntityDao.Properties.Title.eq(bean.data.title))
                            .build().unique();
                    if (meiwEntity != null) {
                        meiwenDao.deleteByKey(meiwEntity.getId());
                    }
                } else {
                    ToastUtils.showToast(mContext, "已收藏");
                    isExist = true;
                    MeiwEntity meiwEntity = new MeiwEntity(null, bean.data.title,
                            bean.data.date.curr, bean.data.author);
                    meiwenDao.insert(meiwEntity);
                }
                break;
            case COLLECT_LIST:
                List<MeiwEntity> meiwEntities = meiwenDao.queryBuilder()
//                        .where(MeiwEntityDao.Properties.Id.notEq(1))
//                        .limit(5)
                        .build().list();
                if (collectListDialog == null) {
                    collectListDialog = new CollectListDialog(mContext, meiwEntities, this);
                    collectListDialog.show();
                } else {
                    collectListDialog.show();
                    collectListDialog.upData(meiwEntities);
                }
                break;
            case RANDOM:
//                ToastUtils.showToast(mContext, "随机一文");
                getToday(Urls.MW_RANDOM, null);
                page++;
                break;
        }
    }

    @Override
    public void onSrcollUp() {
        hideFloatBtn(vRandom, vRandomHeight);
    }

    @Override
    public void onSrollDown() {
        showFloatBtn(vRandom, vRandomHeight);
    }


    @Override
    public void onItem(String date) {
        if (date.equals(bean.data.date.curr)) {
            return;
        }
        getToday(Urls.MW_THEDAY, date);
    }
}
