package com.hhh.lib_api.services.impl;

import com.hhh.lib_api.APIHelper;
import com.hhh.lib_api.DataFlatMap;
import com.hhh.lib_api.interfaces.IOnNetEventListener;
import com.hhh.lib_api.services.interfaces.IHttpBaseService;
import com.hhh.lib_api.services.interfaces.IHttpClient;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by nova on 21/02/2018.
 */

public class IHttpClientImp implements IHttpClient {

    private static IOnNetEventListener mListener;

    public static void setOnEventListener(IOnNetEventListener listener) {
        mListener = listener;
    }

    public static void cancelEnentListener() {
        mListener = null;
    }

    private static final String TAG = IHttpClientImp.class.getSimpleName();

    private IHttpBaseService mService;

    private IHttpClientImp() {
        mService = APIHelper.getInstance().createHttpAPI();
    }

    public static IHttpClientImp getInstance() {
        return new IHttpClientImp();
    }

    @Override
    public Observable postWithoutRep(String url, Map params) {
        return mService.post(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2BooleanFlatMap())
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public <T> Observable post(String url, Map params, final Class<T> cls) {
        return mService.post(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2ObjectFlatMap(cls))
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public <T> Observable get(String url, Map params, final Class<T> cls) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2ObjectFlatMap(cls))
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public Observable<String> get(String url, Map params) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2StringFlatMap())
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }


    @Override
    public Observable getWithoutRep(String url, Map params) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2BooleanFlatMap())
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public Observable patch(String url, String value) {
        return mService.patch(url, RequestBody.create(MediaType.parse("text/plain"), value == null ? "" : value))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2BooleanFlatMap())
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public Observable delete(String url) {
        return mService.delete(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2BooleanFlatMap())
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public Observable deleteWithOutRep(String url, Map params) {
        return mService.delete(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2BooleanFlatMap())
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public Observable patch(String url, Map<String, Object> value) {
        return mService.patch(url, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2BooleanFlatMap())
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public Observable getList(String url, Map params, final Type type) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2ListFlatMap(type))
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }


    @Override
    public Observable postForList(String url, Map<String, Object> params, final Type type) {
        return mService.post(url, params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2ListFlatMap(type))
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public <T> Observable put(String url, Map params, final Class<T> cls) {
        return mService.put(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2ObjectFlatMap(cls))
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

    @Override
    public Observable putWithoutRep(String url, Map params) {
        return mService.put(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(DataFlatMap.middleDataFlatMap())
                .flatMap(DataFlatMap.data2BooleanFlatMap())
                .doOnError(DataFlatMap.errorDataReturn(mListener));
    }

}
