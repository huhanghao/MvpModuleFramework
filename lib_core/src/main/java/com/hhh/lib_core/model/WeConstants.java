package com.hhh.lib_core.model;

import com.hhh.lib_core.BuildConfig;

/**
 * 存储一些常用的重要的常量数据
 */
public class WeConstants {

    private static final boolean isDebug = true;

    public static final String WEBSITE = BuildConfig.DEBUG ? "" : "";
//    public static final String API_ADDRESS = BuildConfig.DEBUG ? "http://192.168.7.74:3000" : "http://119.23.19.142:3000";
    public static final String API_ADDRESS = BuildConfig.DEBUG ? "http://119.23.19.142:3000" : "http://119.23.19.142:3000";

    public static final String INTENT_PARAM_NAME_1 = "param_1";
    public static final String INTENT_PARAM_NAME_2 = "param_2";
    public static final String INTENT_PARAM_NAME_3 = "param_3";
    public static final String INTENT_PARAM_NAME_4 = "param_4";

    public static final String DIVIDER = ",,,,,,,,,";

    public static final String HTML_IMG = "<div style=\"width: %s;text-align: center;\">\n<img src='%s'/>\n</div>";
}
