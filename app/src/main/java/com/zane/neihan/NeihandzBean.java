package com.zane.neihan;

import android.view.View;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.security.acl.Group;
import java.util.List;

/**
 * Created by shizhang on 2017/8/6.
 */

public class NeihandzBean implements Serializable {
    @Expose
    public String message;
    @Expose
    public MData data;

    public static class MData implements Serializable { //和message同级
        @Expose
        public String min_time;
        @Expose
        public List<MDataItem> data;
    }

    public static class MDataItem implements Serializable {
        @Expose
        public int type; // == 1 段子
        @Expose
        public Group group;
        public int layoutType = 0;
        public View adView;
    }

    public static class Group implements Serializable {
        @Expose
        public String content;
        @Expose
        public User user;

    }

    public static class User implements Serializable {
        @Expose
        public String name;
        @Expose
        public String avatar_url;
    }
}
