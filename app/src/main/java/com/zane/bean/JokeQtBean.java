package com.zane.bean;

import android.view.View;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shizhang on 2017/7/10.
 */

public class JokeQtBean extends BaseBean {
    @Expose
    public MResutl result;

    public class MResutl implements Serializable {
        @Expose
        public List<MData> data;
    }

    public static class MData implements Serializable {
        @Expose
        public String content = "";
        @Expose
        public String updatetime = "";
        @Expose
        public String unixtime = "";
        @Expose
        public String url = "";
        public int layoutType = 0;
        public View adView;

        public MData(String content, String updatetime, String url, int layoutType, View adView) {
            this.content = content;
            this.updatetime = updatetime;
            this.layoutType = layoutType;
            this.url = url;
            this.adView = adView;
        }
    }
}
