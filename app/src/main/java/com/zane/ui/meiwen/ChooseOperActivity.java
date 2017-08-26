package com.zane.ui.meiwen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.zane.l.R;

import java.util.List;

/**
 * Created by shizhang on 2017/7/8.
 */

public class ChooseOperActivity extends Activity implements View.OnClickListener{
    private ViewGroup viewGroup;
    private int resultCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_oper);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SpringChain springChain = SpringChain.create(40, 6, 70, 10);
        viewGroup = (ViewGroup) findViewById(R.id.view_group);
        for (int i = 0; i < viewGroup.getChildCount(); i ++) {
            final View view = viewGroup.getChildAt(i);
            view.setOnClickListener(this);
            springChain.addSpring(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    view.setTranslationY((float) spring.getCurrentValue());
                }
            });
        }
        List<Spring> springs = springChain.getAllSprings();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            springs.get(i).setCurrentValue(800);
        }
        springChain.setControlSpringIndex(0).getControlSpring().setEndValue(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect:
                resultCode = MeiwenFragment.COLLECT;
                break;
            case R.id.collect_list:
                resultCode = MeiwenFragment.COLLECT_LIST;
                break;
            case R.id.random:
                resultCode = MeiwenFragment.RANDOM;
                break;
        }
        setResult(resultCode);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
