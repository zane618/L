package com.zane.ui.data;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by shizhang on 2017/7/2.
 */

public class Test extends BmobObject {
    public String name;
    public int score;

    public Test() {
        this.setTableName("T_test");
    }
}
