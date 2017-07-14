package com.zane.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shizhang on 2017/7/10.
 */

public class JokeQtBean extends BaseBean {
    public MResutl result;

    public class MResutl implements Serializable{
        public List<MData> data;
    }
    public static class MData  implements Serializable{
        public String content;
        public String updatetime;
        public String unixtime;
        public String url;
        public int layoutType = 0;

        public MData(String content, String updatetime, int layoutType) {
            this.content = content;
            this.updatetime = updatetime;
            this.layoutType = layoutType;
        }
    }
}
