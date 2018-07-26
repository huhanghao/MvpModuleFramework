package com.hhh.lib_api.path;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引模块的path
 * Created by nova on 23/10/2017.
 */

public class SamplePath {
    public static List<WePath> INDEX_PATHS;

    public static WePath GET_USER_INFO = new WePath("/api/user/login.html",false);   // 获取个人信息

    static {
        INDEX_PATHS = new ArrayList<>();
        INDEX_PATHS.add(GET_USER_INFO);
    }
}
