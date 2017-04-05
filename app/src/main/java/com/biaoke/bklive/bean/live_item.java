package com.biaoke.bklive.bean;

/**
 * Created by hasee on 2017/4/4.
 */

public class live_item {
    //                                                                "Protocol":"Explore",
    //                                                                "UserId":"0",		// 用户ＩＤ
//                                                                    "NickName":"test1",		//用户昵称
//                                                                    "IconUrl":"http://server-test.bk5977.com:8800/BK/icon.png",		//用户头像
//                                                                    "Exp":"0",		//热度 心形后面的数字
//                                                                    "Title":"0",		//视频标题
//                                                                    "SnapshotUrl":"0",		//封面URL
//                                                                    "VideoUrl":"0",		//视频URL
//                                                                    "Format":"mp4",		//视频格式，mp4 m3u8
//                                                                    "HV":"H"	//H 竖屏 V 横屏
//                                                                    "Type":"1"	//1 直播 2视频
    private String Protocol;
    private String UserId;
    private String NickName;
    private String IconUrl;
    private String Exp;
    private String Title;
    private String SnapshotUrl;
    private String VideoUrl;
    private String Format;
    private String HV;
    private String Type;

    public live_item(String protocol, String userId, String nickName, String iconUrl, String exp, String title, String snapshotUrl, String videoUrl, String format, String HV, String type) {
        Protocol = protocol;
        UserId = userId;
        NickName = nickName;
        IconUrl = iconUrl;
        Exp = exp;
        Title = title;
        SnapshotUrl = snapshotUrl;
        VideoUrl = videoUrl;
        Format = format;
        this.HV = HV;
        Type = type;
    }

    @Override
    public String toString() {
        return "live_item{" +
                "Protocol='" + Protocol + '\'' +
                ", UserId='" + UserId + '\'' +
                ", NickName='" + NickName + '\'' +
                ", IconUrl='" + IconUrl + '\'' +
                ", Exp='" + Exp + '\'' +
                ", Title='" + Title + '\'' +
                ", SnapshotUrl='" + SnapshotUrl + '\'' +
                ", VideoUrl='" + VideoUrl + '\'' +
                ", Format='" + Format + '\'' +
                ", HV='" + HV + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }

    public String getProtocol() {
        return Protocol;
    }

    public void setProtocol(String protocol) {
        Protocol = protocol;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getIconUrl() {
        return IconUrl;
    }

    public void setIconUrl(String iconUrl) {
        IconUrl = iconUrl;
    }

    public String getExp() {
        return Exp;
    }

    public void setExp(String exp) {
        Exp = exp;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSnapshotUrl() {
        return SnapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        SnapshotUrl = snapshotUrl;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getHV() {
        return HV;
    }

    public void setHV(String HV) {
        this.HV = HV;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
