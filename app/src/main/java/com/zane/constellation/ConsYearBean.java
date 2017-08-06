package com.zane.constellation;

import com.google.gson.annotations.Expose;
import com.zane.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shizhang on 2017/8/6.
 */

public class ConsYearBean extends BaseBean {
    @Expose
    public String name;
    @Expose
    public String date;
    @Expose
    public Mima mima;
    @Expose
    public List<String> career;
    @Expose
    public List<String> love;
    @Expose
    public List<String> health;
    @Expose
    public List<String> finance;
    @Expose
    public String luckeyStone;

    public static class Mima implements Serializable {
        @Expose
        public String info;
        @Expose
        public List<String> text;
    }

}
