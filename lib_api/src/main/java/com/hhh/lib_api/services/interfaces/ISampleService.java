package com.hhh.lib_api.services.interfaces;

import com.hhh.lib_core.beans.SampleUserBean;

import io.reactivex.Observable;

/**
 * Created by huhanghao on 2018/3/8.
 */

public interface ISampleService {

    Observable<SampleUserBean> getUserInfo(String userName, String userPsw);

}
