package com.tianhy.study.util;

import java.util.ResourceBundle;

/**
 * @Desc:
 * @Author: thy
 * @Date: 2020/1/31 17:53
 */
public class ResourceUtil {
    private static final ResourceBundle RESOURCE_BUNDLE;


    static {
        RESOURCE_BUNDLE = ResourceBundle.getBundle("config");
    }

    public static String getKey(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }

}
