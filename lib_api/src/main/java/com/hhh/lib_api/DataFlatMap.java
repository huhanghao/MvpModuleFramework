package com.hhh.lib_api;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.GsonBuilder;
import com.hhh.lib_api.error.APIError;
import com.hhh.lib_api.interfaces.IOnNetEventListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * 原始数据的处理转化
 * Created by huhanghao on 2018/3/16.
 */

public class DataFlatMap {

    /**
     * 中间数据转换
     * @return
     */
    public static Function<ResponseBody, Observable<JSONObject>> middleDataFlatMap() {
        return new Function<ResponseBody, Observable<JSONObject>>() {
           
            public Observable<JSONObject> apply(ResponseBody body) throws Exception {
                String json = body.string();
                LogUtils.d(json);

                JSONObject jsonObject = new JSONObject(json);

                int code = jsonObject.optInt("code", HttpCode.CODE_FAILED);
                if (code == HttpCode.CODE_SUCCESS) {
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    return Observable.just(jsonData);
                } else {
                    final String msg = jsonObject.optString("msg");
                    APIError apiError = new APIError(msg.length() == 0 ? "" : msg, code);
                    return Observable.error(apiError);
                }
            }
        };
    }

    /**
     * 错误数据信息处理
     * @param mListener
     * @return
     */
    public static Consumer<Throwable> errorDataReturn(final IOnNetEventListener mListener) {
        return new Consumer<Throwable> (){
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof APIError) {
                    APIError apiError = (APIError) throwable;
                    if (apiError.getCode() == HttpCode.CODE_AUTH_ERROR) {
                        if (mListener != null) mListener.onAuthError();
                    }
                }

            }
        };
    }


    /**
     * 将数据转化为对象
     * @param cls
     * @param <T>
     * @return
     */
    public static <T>Function<JSONObject, ObservableSource<T>> data2ObjectFlatMap(final Class<T> cls) {
        return new Function<JSONObject, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(JSONObject jsonObject) throws Exception {
                try {
                    String json = jsonObject.toString();
                    T data = new GsonBuilder().create().fromJson(json, cls);
                    return Observable.just(data);
                }catch (Exception e) {
                    LogUtils.e("data2ObjectFlatMap", "Exception");
                    return Observable.just(cls.newInstance());
                }
            }
        };
    }


    /**
     * 将数据转化为List
     * @param type
     * @return
     */
    public static Function<JSONObject, ObservableSource<? extends List>> data2ListFlatMap(final Type type) {
        return new Function<JSONObject, ObservableSource<? extends List>>() {
            @Override
            public ObservableSource<? extends List> apply(@NonNull JSONObject jsonObject) throws Exception {
                try {
                    String json = jsonObject.toString();
                    List list = new GsonBuilder().create().fromJson(json, type);
                    return Observable.just(list);
                }catch (Exception e) {
                    LogUtils.e("data2ListFlatMap", "Exception");
                    return Observable.just(null);
                }
            }
        };
    }

    /**
     * 只返会boolean
     * @return
     */
    public static Function<JSONObject, ObservableSource<Boolean>> data2BooleanFlatMap() {
        return new Function<JSONObject, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(@NonNull JSONObject body) throws Exception {
                return Observable.just(true);
            }
        };
    }

    /**
     * 直接返回String
     * @return
     */
    public static Function<JSONObject, ObservableSource<String>> data2StringFlatMap() {
        return new Function<JSONObject, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull JSONObject body) throws Exception {
                return Observable.just(body.toString());
            }
        };
    }


}
