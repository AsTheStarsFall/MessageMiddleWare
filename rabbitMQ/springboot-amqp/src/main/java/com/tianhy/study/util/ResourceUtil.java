package com.tianhy.study.util;

import java.util.ResourceBundle;

/**
 * @Author: thy
 * @Date: 2020/2/1 1:14
 * @Desc:
 */
public class ResourceUtil {

    private static final ResourceBundle RESOURCE_BUNDLE;

    static {
        RESOURCE_BUNDLE = ResourceBundle.getBundle("config");
    }

    public static String getKey(String key) {
        return RESOURCE_BUNDLE.getString("rabbitmq.uri");
    }

}
