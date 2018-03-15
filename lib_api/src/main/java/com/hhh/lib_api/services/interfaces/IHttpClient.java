package com.hhh.lib_api.services.interfaces;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by nova on 12/10/2017.
 */

public interface IHttpClient {
    Observable postWithoutRep(String url, Map params);

    <T> Observable post(String url, Map params, Class<T> cls);

    <T> Observable get(String url, Map params, Class<T> cls);

    Observable<String> get(String url, Map params);                 // 不带类型自动转化的的get请求

    Observable getWithoutRep(String url, Map params);

    Observable patch(String url, String value);

    Observable delete(String url);

    Observable deleteWithOutRep(String url, Map params);

    Observable patch(String url, Map<String, Object> value);

    Observable getList(String url, Map params, Type type);

    Observable getListPro(String url, Map params, Type type);

    Observable getListWithPageInfo(String url, Map params, Class cls);

    Observable getListWithoutPageInfo(String url, Map params, Type type);

    Observable postForList(String concat, Map<String, Object> params, Type type);

    <T> Observable put(String url, Map params, Class<T> cls);

    Observable putWithoutRep(String url, Map params);
}
