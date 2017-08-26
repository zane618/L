package com.zane.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.zane.utility.L;

/**
 * Created by shizhang on 2017/8/20.
 */

public class DireScrollView extends ScrollView {

    private OnSrcollDireChanged onSrcollDireChanged;
    public boolean isShow = true;
    private boolean isScrolledToBottom = false;

    public interface OnSrcollDireChanged {
        public void onSrcollUp();
        public void onSrollDown();
    }

    public DireScrollView(Context context) {
        super(context);
    }

    public DireScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DireScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (!isShow && oldt > t && oldt - t > 30 && onSrcollDireChanged != null) { //向下
            isShow = true;
            onSrcollDireChanged.onSrollDown();
            L.e("down");
        } else if (isShow && oldt < t && t - oldt > 30 && onSrcollDireChanged != null) {//向上
            if (isScrolledToBottom) {
                isScrolledToBottom = false;
                return;
            }
            isShow = false;
            onSrcollDireChanged.onSrcollUp();
            L.e("up");
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY > 0 && clampedY && !isScrolledToBottom && !isShow && onSrcollDireChanged != null) {
            isShow = true;
            isScrolledToBottom = true;
            onSrcollDireChanged.onSrollDown();
            L.e("sc", "aaaaaaaaa");
        }
//        if (scrollY == 0) { //滑到顶部了
//        } else if(!isScrolledToBottom){
//            isScrolledToBottom = clampedY;
//            L.e("sc", "scrollY != 0" + String.valueOf(clampedY));
//        }
    }

    public void setOnSrcollDireChanged(OnSrcollDireChanged onSrcollDireChanged) {
        this.onSrcollDireChanged = onSrcollDireChanged;
    }
}
