package com.hhh.lib_api.services.impl;

import com.hhh.lib_api.APIHelper;
import com.hhh.lib_api.path.IndexPath;
import com.hhh.lib_api.services.interfaces.IHttpClient;
import com.hhh.lib_api.services.interfaces.ISampleService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by huhanghao on 2018/2/27.
 */

public class SampleServiceImp implements ISampleService {

    private IHttpClient mHttpClientService;

    private SampleServiceImp() {
        mHttpClientService = IHttpClientImp.getInstance();
    }

    public static SampleServiceImp create() {
        return new SampleServiceImp();
    }

    @Override
    public Observable<Boolean> addNewsComment(String id, String content) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", content);

        return mHttpClientService.postWithoutRep(
                APIHelper.getBaseUrl().concat(String.format(IndexPath.SUBJECT_LIST.path, id)), params);
    }


    @Override
    public Observable<Boolean> deleteNewsComment(String commentID) {

        return mHttpClientService.delete(
                APIHelper.getBaseUrl().concat(String.format(IndexPath.SUBJECT_TREE_STRUCTURE.path, commentID)));
    }


}
