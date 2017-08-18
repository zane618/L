package com.zane.ui.constellation;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by shizhang on 2017/8/3.
 */

public class ConstellationBean implements Serializable {
    @Expose
    public String name;//星座名
    @Expose
    public String datetime;//日，时间
    @Expose
    public String date;//月，时间
    @Expose
    public String job;//周，求职
    @Expose
    public String weekth;//周
    @Expose
    public String all;//概况
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

    public String birth;
}
