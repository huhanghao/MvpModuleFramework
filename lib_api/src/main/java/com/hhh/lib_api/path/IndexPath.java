package com.hhh.lib_api.path;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引模块的path
 * Created by nova on 23/10/2017.
 */

public class IndexPath {
    public static List<WePath> INDEX_PATHS;

    public static WePath SUBJECT_LIST = new WePath(UrlConstants.Common.home,true);   // 专题列表
    public static WePath SUBJECT_TREE_STRUCTURE = new WePath("/mobile/subjects/%s/folders/%s",true);   // 专题列表

    static {
        INDEX_PATHS = new ArrayList<>();
        INDEX_PATHS.add(SUBJECT_LIST);
        INDEX_PATHS.add(SUBJECT_TREE_STRUCTURE);
    }
}
