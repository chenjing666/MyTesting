package com.biaoke.bklive.message;

/**
 * Created by Holiday on 2017/2/21.
 * 网络连接地址
 */
public class Api {

    private static final String ADDRESS = "http:server-test.bk5977.com:8800";

    public final static String ENCRYPT64=ADDRESS+"/BK/api/en64.php";//加密接口

    public final static String UNENCRYPT64=ADDRESS+"/BK/api/un64.php";//解密接口

    public final static String LOGIN = ADDRESS + "/BK/Logging.php";//登陆接口

    public final static String REGISTER = ADDRESS + "/BK/Register.php";//注册接口

    public final static String LIVEPUT = ADDRESS + "/BK/Live.php";//直播接口

    public final static String USERINFO_USER = ADDRESS + "/BK/UserInfo.php";//直播接口



}
