package com.biaoke.bklive.user.bean;

/**
 * Created by hasee on 2017/3/30.
 * 用户信息实体类
 */

public class User {
    private String uId;
    private String uNickName;
    private String uPhoneNum;
    private String uSex;
    private String uPassWord;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuNickName() {
        return uNickName;
    }

    public void setuNickName(String uNickName) {
        this.uNickName = uNickName;
    }

    public String getuPhoneNum() {
        return uPhoneNum;
    }

    public void setuPhoneNum(String uPhoneNum) {
        this.uPhoneNum = uPhoneNum;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public String getuPassWord() {
        return uPassWord;
    }

    public void setuPassWord(String uPassWord) {
        this.uPassWord = uPassWord;
    }
}
