package com.zane.constellation;

import com.google.gson.annotations.Expose;
import com.zane.bean.BaseBean;

import java.io.Serializable;

/**
 * Created by shizhang on 2017/8/3.
 */

public class ConstellationBean implements Serializable {
    @Expose
    public String name;
    @Expose
    public String datetime;
    @Expose
    public String all;
    @Expose
    public String color;
    @Expose
    public String health;
    @Expose
    public String love;
    @Expose
    public String money;
    @Expose
    public String number;
    @Expose
    public String QFriend;
    @Expose
    public String summary;
    @Expose
    public String work;
    @Expose
    public int error_code;
}
