package com.zane.todayhistory;

import com.google.gson.annotations.Expose;
import com.zane.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shizhang on 2017/7/30.
 */

public class HistoryTodayListBean extends BaseBean {
    @Expose
    public List<MData> result;

    public static class MData implements Serializable {
        @Expose
        public String date;
        @Expose
        public String title;
        @Expose
        public String e_id;
    }
}
