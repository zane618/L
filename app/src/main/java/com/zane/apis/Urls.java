package com.zane.apis;

/**
 * Created by shizhang on 2017/7/5.
 */

public class Urls {
    public static final int PAGESIZE = 10;
    //使用样例、url?key=...&page=...&pagesize=...
    //最新笑话
    public static final String URL_JOKE_TEXT = "http://japi.juhe.cn/joke/content/text.from";
    //使用样例、url?key=...&page=...&pagesize=...&sort-...&time=....
    // 按时间查询笑话
    public static final String URL_JOKE_LIST = "http://japi.juhe.cn/joke/content/list.from";

    //最新趣图
    //样例url?key=您申请的KEY&page=1&pagesize=10
    public static final String URL_JOKE_IMG_TEXT = "http://japi.juhe.cn/joke/img/text.from";


    //随机获取趣图/笑话
    //使用样例 url?key=...&type=...(默认笑话、type=pic趣图
    public static final String URL_JOKE_RAND = "http://v.juhe.cn/joke/randJoke.php";
}
