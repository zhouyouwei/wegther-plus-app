package com.codetreatise.util;

public class CheckToolsUtils {

    /**
     * 校验手机号
     *
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        return str != null && str.length() == 11;
    }

    /**
     * 校验密码
     *
     * @param str
     * @return
     */
    public static boolean isPassWord(String str) {
        return str != null;
    }

}
