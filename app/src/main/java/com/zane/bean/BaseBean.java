package com.zane.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by shizhang on 2017/7/5.
 */

public class BaseBean implements Serializable {
    @Expose
    public int error_code;
    @Expose
    public String reason;
}
