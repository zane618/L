package com.zane.bean;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by shizhang on 2017/7/10.
 */

public class JokeQtRandBean extends BaseBean {
    @Expose
    public List<JokeQtBean.MData> result;
}
