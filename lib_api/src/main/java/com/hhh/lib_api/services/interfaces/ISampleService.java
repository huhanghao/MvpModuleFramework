package com.hhh.lib_api.services.interfaces;

import io.reactivex.Observable;

/**
 * Created by huhanghao on 2018/3/8.
 */

public interface ISampleService {
    Observable<Boolean> addNewsComment(String id, String content);
    Observable<Boolean> deleteNewsComment(String commentID);
}
