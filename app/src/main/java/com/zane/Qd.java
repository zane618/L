package com.zane;

import com.google.gson.annotations.Expose;

/**
 * Created by shizhang on 2017/8/15.
 */

public class Qd {

    /**
     * OpeType : Client005
     * UserName : 张仕
     * AuthID : null
     * UserAccount : shizhang
     * VirtualEnable : 0
     * LngLat : WQNgHbbip1Lg8Xc_c0AZMGsGkhkx6a.J9bwj8TaJKTg=
     * TimeStamp : 1502758495811
     * Token : wQOsCm0uAnAfopIHtCH2WKmvKcQB7kG9qU9OBLSIA7t1rjDe0NnTm9jPK4eMfq3Qld28EzHRNC8S.YHN_5OIfF0IbK.hh9socY6G1CcOTU0=
     * OrgName : 消费者应用研发部研发一部
     * IMEI : 862031035542523
     * UserID : S7AcVnPO63XgUy1kEKwgDYDvfe0=
     * UserCode : 2017001393
     * PointType : 6
     * AppVersion : 1.0.1011
     * PhoneType : Android
     * AreaCode : 01
     * AttendanceAreaID : 1
     */
    @Expose
    public String OpeType;
    @Expose
    public String UserName;
    @Expose
    public String AuthID;
    @Expose
    public String UserAccount;
    @Expose
    public String VirtualEnable;
    @Expose
    public String LngLat;
    @Expose
    public String TimeStamp;
    @Expose
    public String Token;
    @Expose
    public String OrgName;
    @Expose
    public String IMEI;
    @Expose
    public String UserID;
    @Expose
    public String UserCode;
    @Expose
    public String PointType;
    @Expose
    public String AppVersion;
    @Expose
    public String PhoneType;
    @Expose
    public String AreaCode;
    @Expose
    public String AttendanceAreaID;

    public Qd() {
        this.AttendanceAreaID = "1";
        this.AreaCode = "01";
        this.PhoneType = "Android";
        this.AppVersion = "1.0.1011";
        this.PointType = "6";
        this.UserCode = "2017001393";
        this.UserID = "S7AcVnPO63XgUy1kEKwgDYDvfe0=";
        this.IMEI = "862031035542523";
        this.OrgName = "消费者应用研发部研发一部";
        this.Token = "wQOsCm0uAnAfopIHtCH2WKmvKcQB7kG9qU9OBLSIA7t1rjDe0NnTm9jPK4eMfq3Qld28EzHRNC8S.YHN_5OIfF0IbK.hh9socY6G1CcOTU0=";
        this.LngLat = "WQNgHbbip1Lg8Xc_c0AZMGsGkhkx6a.J9bwj8TaJKTg=";
        this.VirtualEnable = "0";
        this.UserAccount = "shizhang";
        this.AuthID = "null";
        this.UserName = "张仕";
        this.OpeType = "Client005";
    }
}
