package com.hhh.lib_api.path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nova on 23/10/2017.
 */

public class IndexPath {
    public static List<WePath> INDEX_PATHS;

    public static WePath SUBJECT_LIST = new WePath("/mobile/subjects",true);   // 专题列表
    public static WePath SUBJECT_TREE_STRUCTURE = new WePath("/mobile/subjects/%s/folders/%s",true);   // 专题列表
    public static WePath SUBJECT_FIRST_FOLDERE = new WePath("/mobile/subjects/%s/first/folders",true);   // 专题列表
    public static WePath NOTICE_LIST = new WePath("/mobile/notices",true);   // 通知列表
    public static WePath NOTICE_DETAIL = new WePath("/mobile/notices/%s",true);   // 专题列表
    public static WePath NOTICE_FIRST = new WePath("/mobile/notices/first",true);   // 首页专题
    public static WePath NEWS_LIST = new WePath("/mobile/articles",true);   // 新闻列表
    public static WePath NEWS_CONTENT_SHOW = new WePath("/mobile/articles/%s",true);   // 新闻详情
    public static WePath NEWS_ADD_COMMENT = new WePath("/mobile/articles/%s/comments",true);   // 添加评论
    public static WePath NEWS_DELETE_COMMENT = new WePath("/mobile/articles/comments/%s",true);   // 删除评论
    public static WePath NEWS_COMMENT = new WePath("/mobile/articles/%s/comments",true);   // 文章评论

    static {
        INDEX_PATHS = new ArrayList<>();
        INDEX_PATHS.add(SUBJECT_LIST);
        INDEX_PATHS.add(SUBJECT_TREE_STRUCTURE);
        INDEX_PATHS.add(SUBJECT_FIRST_FOLDERE);
        INDEX_PATHS.add(NOTICE_LIST);
        INDEX_PATHS.add(NOTICE_DETAIL);
        INDEX_PATHS.add(NOTICE_FIRST);
        INDEX_PATHS.add(NEWS_LIST);
        INDEX_PATHS.add(NEWS_CONTENT_SHOW);
        INDEX_PATHS.add(NEWS_ADD_COMMENT);
        INDEX_PATHS.add(NEWS_DELETE_COMMENT);
        INDEX_PATHS.add(NEWS_COMMENT);
    }
}
