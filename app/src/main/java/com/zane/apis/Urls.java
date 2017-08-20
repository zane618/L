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


    //历史上的今天列表  date = 1/1
    public static final String URL_HISTORY_TODAY_LIST = "http://v.juhe.cn/todayOnhistory/queryEvent.php?key=3ee4cb8470f2f2a4a97aaebaab1e73bc&date=";
    //历史上的今天详情
    public static final String URL_HISTORY_TODAY_DETAIL = "http://v.juhe.cn/todayOnhistory/queryDetail.php?key=3ee4cb8470f2f2a4a97aaebaab1e73bc&e_id=";


    //星座运势(包含日、周、月、年
    public static final String URL_CONSTELLATION_DAY = "http://web.juhe.cn:8080/constellation/getAll?key=16fca1c824662d7b57a3228c995c1429&type=today&consName=";
    //星座运势(包含周、月、年
    public static final String URL_CONSTELLATION_ALL = "http://web.juhe.cn:8080/constellation/getAll?key=16fca1c824662d7b57a3228c995c1429&type=";

    //内涵段子api
    public static final String NEIHAN_DZ_DZ = "http://is.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-102&message_cursor=-1&am_longitude=110&am_latitude=120&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&am_loc_time=1489226058493&count=10&screen_width=1450&do00le_col_mode=0&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=650&update_version_code=650&min_time=";


    //每日一文
    public static final String MW_TODAY = "https://interface.meiriyiwen.com/article/today?dev=1";
    //随机一文
    public static final String MW_RANDOM = "https://interface.meiriyiwen.com/article/random?dev=1";

}
