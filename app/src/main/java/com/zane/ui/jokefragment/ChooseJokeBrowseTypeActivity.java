package com.zane.ui.jokefragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.zane.l.R;
import com.zane.utility.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by shizhang on 2017/7/8.
 */

public class ChooseJokeBrowseTypeActivity extends Activity {
    private ViewGroup viewGroup;
    private int requestCode;
    private int browseType;
    private String sort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mode);
        Intent intent = getIntent();
        if (intent != null) {
            requestCode = intent.getIntExtra("requestCode", 0);
        }
        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initCustomTimePicker();
    }
    private void setResultFinish(String time) {
        Intent intent = new Intent();
        intent.putExtra("browseType", browseType);
        if (sort != null) {
            intent.putExtra("sort", sort);
        }
        if (time != null) {
            intent.putExtra("time", time);
        }
        setResult(requestCode, intent);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        SpringChain springChain = SpringChain.create(15, 5, 50, 7);
        viewGroup = (ViewGroup) findViewById(R.id.view_group);
        for (int i = 0; i < viewGroup.getChildCount(); i ++) {
            final View view = viewGroup.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    browseType = Integer.parseInt(v.getTag().toString());
                    switch (browseType) {
                        case 0:
                        case 1:
                            setResultFinish(null);
                            break;
                        case 2:
//                            sort = "asc";
//                            if (pvCustomTime != null) {
//                                pvCustomTime.show();
//                            }
                            setResultFinish(null);
                            break;
                        case 3:
//                            sort = "desc";
//                            if (pvCustomTime != null) {
//                                pvCustomTime.show();
//                            }
                            finish();
                            break;
                    }
                }
            });
            springChain.addSpring(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    super.onSpringUpdate(spring);
                    view.setTranslationY((float) spring.getCurrentValue());
                }
            });
        }
        List<Spring> springs = springChain.getAllSprings();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            springs.get(i).setCurrentValue(800);
        }
        springChain.setControlSpringIndex(2).getControlSpring().setEndValue(0);
    }

    private TimePickerView pvCustomTime;
    private void initCustomTimePicker() {
        /**
         * @description
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                try {
                    setResultFinish(DateUtil.dateToStamp(getTime(date)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /*.gravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.dialog_date_choose_layout, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
