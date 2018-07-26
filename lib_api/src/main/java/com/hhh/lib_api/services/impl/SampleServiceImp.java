package com.hhh.lib_api.services.impl;

import com.hhh.lib_api.APIHelper;
import com.hhh.lib_api.path.SamplePath;
import com.hhh.lib_api.services.interfaces.IHttpClient;
import com.hhh.lib_api.services.interfaces.ISampleService;
import com.hhh.lib_core.beans.SampleUserBean;

import java.util.HashMap;

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
    public Observable<SampleUserBean> getUserInfo(String userName, String userPsw) {

        HashMap<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", userPsw);

        String baseUrl = APIHelper.getBaseUrl().concat(SamplePath.GET_USER_INFO.path);

        return mHttpClientService.post(baseUrl, params, SampleUserBean.class);
    }


}
