package com.hhh.lib_api.path;

public class UrlConstants {

    /**
     * BaseUrl
     **/
    public static String BASE_URL = "http://demo.wx.ynccxx.cn/";
    public static String API_TYPE = "app/index.php?i=12&c=entry&m=ewei_shopv2&do=mobile&isApi=App&r=";


    public final static class Common {

        public final static String home = BASE_URL + API_TYPE + "app.index.home";//首页
        public final static String adv = BASE_URL + API_TYPE + "app.index.getbanner";//首页
        public final static String _about = BASE_URL + API_TYPE + "app.article.index.about";
    }

    public final static class Shop{
        public final static String _list = BASE_URL + API_TYPE + "app.shop.notice.get_list";
        public final static String _category_list = BASE_URL + API_TYPE + "app.article.list.get_category";
        public final static String _article_category_list = BASE_URL + API_TYPE + "app.article.list.get_list";
        public final static String _article_detail = BASE_URL + API_TYPE + "article";
        public final static String get_problem_show = BASE_URL + API_TYPE + "commission.extend.ajaxlist";
        public final static String _notify_detail= BASE_URL + API_TYPE + "app.shop.notice.detail";
        public final static String _goods_order = BASE_URL + API_TYPE + "app.order.create.order";
        public final static String _goods_pay = BASE_URL + API_TYPE + "app.index.pay_parameter";

    }

}
