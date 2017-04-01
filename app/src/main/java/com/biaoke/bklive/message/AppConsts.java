package com.biaoke.bklive.message;

/**
 * Created by hasee on 2017/3/30.
 * App内一些常用的常量
 */

public class AppConsts {
    /**
     * 不可变常量
     */
    public static final int NETWORKTYPE_INVALID = 0;//没有网络
    public static final int NETWORKTYPE_GPRS = 1;//cprs网络
    public static final int NETWORKTYPE_WIFI = 2;//wifi网络

    public static final String POWER_BAR_WHITE = "#FFFFFF";//白色背景
    public static final String POWER_BAR_BLUE = "#5293e4";//蓝色背景
    public static final String POWER_BAR_BACKGROUND2 = "#9d8ce5";//背景
    public static final String POWER_BAR_BACKGROUND = "#6f57d6";//背景
    public static final String POWER_BAR_BACKGROUND3 = "#9900ff";//背景
    /**
     * 可变常量
     */
    public  static Integer mScreenWidth;//屏幕的宽度
    public  static Integer mScreenHeight;//屏幕的高度
    public  static Integer mBuildVersion;//当前SDK版本
    public  static Integer mVersionCode;//当前APP的版本号
    public  static String  mVersionName;//当前APP的版本名字
    public  static String  mImei;//Imei
    public  static String  mMac;//Mac
    public  static boolean  mWhetherLogin=false;//用户是否登陆默认为false



}
