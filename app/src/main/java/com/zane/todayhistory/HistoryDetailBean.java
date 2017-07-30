package com.zane.todayhistory;

import com.google.gson.annotations.Expose;
import com.zane.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shizhang on 2017/7/30.
 */

public class HistoryDetailBean extends BaseBean {
    @Expose
    public List<MResult> result;

    public static class MResult implements Serializable {
        @Expose
        public String title;
        @Expose
        public String content;
        @Expose
        public List<MPic> picUrl;
    }

    public static class MPic implements Serializable{
        @Expose
        public String pic_title;
        @Expose
        public String url;
    }
}
