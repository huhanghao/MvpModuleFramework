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
    public static Function<ResponseBody, Observable<String>> middleDataFlatMap() {
        return new Function<ResponseBody, Observable<String>>() {

            public Observable<String> apply(ResponseBody body) throws Exception {
                String json = body.string();
                LogUtils.d(json);

                JSONObject jsonObject = new JSONObject(json);

                int code = jsonObject.optInt("error", HttpCode.CODE_FAILED);
                if (code == HttpCode.CODE_SUCCESS) {
                    String jsonStr = jsonObject.getString("data");
                    return Observable.just(jsonStr);
                } else {
                    final String msg = jsonObject.optString("message");
                    APIError apiError = new APIError(msg.length() == 0 ? "网络访问错误" : msg, code);
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
                    // 对token过期进行统一处理
                    if (apiError.getCode() == HttpCode.CODE_TOKEN_ERROR) {
                        if (mListener != null) {
                            mListener.onAuthError();
                        }
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
    public static <T>Function<String, ObservableSource<T>> data2ObjectFlatMap(final Class<T> cls) {
        return new Function<String, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(String jsonStr) throws Exception {
                try {
                    String json = jsonStr;
                    T data = new GsonBuilder().create().fromJson(json, cls);
                    return Observable.just(data);
                }catch (Exception e) {
                    LogUtils.e("data2ObjectFlatMap", e.toString());
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
    public static Function<String, ObservableSource<? extends List>> data2ListFlatMap(final Type type) {
        return new Function<String, ObservableSource<? extends List>>() {
            @Override
            public ObservableSource<? extends List> apply(@NonNull String jsonStr) throws Exception {
                try {
                    String json = jsonStr;
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
    public static Function<String, ObservableSource<Boolean>> data2BooleanFlatMap() {
        return new Function<String, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(@NonNull String body) throws Exception {
                return Observable.just(true);
            }
        };
    }

    /**
     * 直接返回String
     * @return
     */
    public static Function<String, ObservableSource<String>> data2StringFlatMap() {
        return new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String body) throws Exception {
                return Observable.just(body.toString());
            }
        };
    }



}
