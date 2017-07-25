package com.zane.bean;

import android.view.View;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shizhang on 2017/7/5.
 */

public class JokeDzBean extends BaseBean {
    @Expose
    public MResutl result;
    public class MResutl implements Serializable {
        public List<MData> data;
    }

    public static class MData implements Serializable {
        @Expose
        public String content;
        @Expose
        public String updatetime;
        @Expose
        public String unixtime;
        public int layoutType = 0;
        public View adView;

        public MData(String content, String updatetime, int layoutType, View adView) {
            this.adView = adView;
            this.content = content;
            this.updatetime = updatetime;
            this.layoutType = layoutType;
        }
    }
}
