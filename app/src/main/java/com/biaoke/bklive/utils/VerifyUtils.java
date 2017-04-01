package com.biaoke.bklive.utils;

import java.util.regex.Pattern;

/**
 * Created by hasee 2017/3/30
 * 参数校验类
 */

public class VerifyUtils {

    /**
     * 校验用户名是否合格
     * @param str
     * @return
     */
    public static boolean verifyUserName(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[a-zA-Z\\d]{6,20}", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(str).matches()) {
            return true;
        }
        return false;
    }
    /**
     * 校验密码是否合格
     * @param str
     * @return
     */
    public static  boolean  verifyPassword(String str)
    {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        int len=StringUtils.length(str);
        if(6<=len&&len<20){
            return true;
        }
        return false;
    }

    /**
     * 验证手机号
     *
     * @param mobile
     * @return
     */
    public static final boolean verifyMobileNo(String mobile) {

		/*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return mobile.matches(telRegex);

    }
}
