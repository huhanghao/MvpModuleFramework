package com.hhh.lib_api.services.impl;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.hhh.lib_api.APIHelper;
import com.hhh.lib_api.error.APIError;
import com.hhh.lib_api.interfaces.IOnNetEventListener;
import com.hhh.lib_api.services.interfaces.IHttpClient;
import com.hhh.lib_api.services.interfaces.IHttpService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by nova on 21/02/2018.
 */

public class IHttpClientImp implements IHttpClient {

    public static class HTTP_CODE {
        public static int CODE_AUTH_ERROR = 401;
    }

    public static IOnNetEventListener getOnEventListener() {
        return mListener;
    }

    private static IOnNetEventListener mListener;

    public static void setOnEventListener(IOnNetEventListener listener) {
        mListener = listener;
    }

    public static void cancelEnentListener() {
        mListener = null;
    }

    private static final String TAG = IHttpClientImp.class.getSimpleName();

    private IHttpService mService;

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
                .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull ResponseBody body) throws Exception {
                        return Observable.just(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public <T> Observable post(String url, Map params, final Class<T> cls) {
        return mService.post(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull ResponseBody body) throws Exception {
                        try {
                            String json = body.string();
                            T data = new GsonBuilder().create().fromJson(json, cls);
                            return Observable.just(data);
                        } catch (IOException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(cls.newInstance());
                        } catch (JsonSyntaxException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(cls.newInstance());
                        } catch (Exception e) {
                            LogUtils.e(TAG, "Exception");
                            return Observable.just(cls.newInstance());
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public <T> Observable get(String url, Map params, final Class<T> cls) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull ResponseBody body) throws Exception {
                        try {
                            String json = body.string();
                            T data = new GsonBuilder().create().fromJson(json, cls);
                            return Observable.just(data);
                        } catch (IOException e) {
                            LogUtils.e(TAG, "IOException");
//                        return Observable.just(cls.newInstance());
                            return Observable.error(e);
                        } catch (JsonSyntaxException e) {
                            LogUtils.e(TAG, "IOException");
//                        return Observable.just(cls.newInstance());
                            return Observable.error(e);
                        } catch (Exception e) {
                            LogUtils.e(TAG, "Exception");
//                        return Observable.just(cls.newInstance());
                            return Observable.error(e);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public Observable<String> get(String url, Map params) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull ResponseBody body) throws Exception {
                        try {
                            String json = body.string();
                            return Observable.just(json);
                        } catch (IOException e) {
                            LogUtils.e(TAG, "IOException");
//                        return Observable.just(cls.newInstance());
                            return Observable.error(e);
                        } catch (JsonSyntaxException e) {
                            LogUtils.e(TAG, "IOException");
//                        return Observable.just(cls.newInstance());
                            return Observable.error(e);
                        } catch (Exception e) {
                            LogUtils.e(TAG, "Exception");
//                        return Observable.just(cls.newInstance());
                            return Observable.error(e);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }


    @Override
    public Observable getWithoutRep(String url, Map params) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull ResponseBody body) throws Exception {
                        return Observable.just(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public Observable patch(String url, String value) {
        return mService.patch(url, RequestBody.create(MediaType.parse("text/plain"), value == null ? "" : value))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull ResponseBody body) throws Exception {
                        return Observable.just(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public Observable delete(String url) {
        return mService.delete(url)
                .subscribeOn(Schedulers.io())
//        .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull ResponseBody body) throws Exception {
                        return Observable.just(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public Observable deleteWithOutRep(String url, Map params) {
        return mService.delete(url, params)
                .subscribeOn(Schedulers.io())
//        .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull ResponseBody body) throws Exception {
                        return Observable.just(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public Observable patch(String url, Map<String, Object> value) {
        return mService.patch(url, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull ResponseBody body) throws Exception {
                        return Observable.just(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public  Observable getList(String url, Map params, final Type type) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<? extends List>>() {
                    @Override
                    public ObservableSource<? extends List> apply(@NonNull ResponseBody body) throws Exception {
                        try {
                            String json = body.string();
                            JSONArray array = new JSONArray(json);
                            List list = new ArrayList<>();
                            for(int i = 0 ; i < array.length();i++){
                                Object object = new GsonBuilder().create().fromJson(array.get(i).toString(), type);
                                list.add(object);
                            }
                            return Observable.just(list);
                        } catch (IOException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(null);
                        } catch (JsonSyntaxException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(null);
                        } catch (Exception e) {
                            LogUtils.e(TAG, "Exception");
                            return Observable.just(null);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public  Observable getListPro(String url, Map params, final Type type) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<? extends List>>() {
                    @Override
                    public ObservableSource<? extends List> apply(@NonNull ResponseBody body) throws Exception {
                        try {
                            String json = body.string();
                            List data = new GsonBuilder().create().fromJson(json, type);
                            return Observable.just(data);
                        } catch (IOException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(null);
                        } catch (JsonSyntaxException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(null);
                        } catch (Exception e) {
                            LogUtils.e(TAG, "Exception");
                            return Observable.just(null);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public Observable getListWithPageInfo(String url, Map params, final Class cls) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, Observable<Object>>() {
                    @Override
                    public Observable<Object> apply(@NonNull ResponseBody body) throws Exception {
                        try {
                            String json = body.string();
                            Object data = new GsonBuilder().create().fromJson(json, cls);

                            return Observable.just(data);
                        } catch (IOException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(null);
                        } catch (JsonSyntaxException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(null);
                        } catch (Exception e) {
                            LogUtils.e(TAG, "Exception");
                            return Observable.just(null);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public Observable getListWithoutPageInfo(String url, Map params, final Type type) {
        return mService.get(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<? extends List>>() {
                    @Override
                    public ObservableSource<? extends List> apply(@NonNull ResponseBody body) throws Exception {
                        try {
                            String json = body.string();
                            JSONArray obj = new JSONArray(json);
                            List data = new GsonBuilder().create().fromJson(obj.toString(), type);
                            return Observable.just(data);
                        } catch (IOException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(null);
                        } catch (JsonSyntaxException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(null);
                        } catch (Exception e) {
                            LogUtils.e(TAG, "Exception");
                            return Observable.just(null);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public Observable postForList(String url, Map<String, Object> params, final Type type) {
        return mService.post(url, params)
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<? extends List>>() {
                    @Override
                    public ObservableSource<? extends List> apply(@NonNull ResponseBody body) throws Exception {
                        String json = body.string();
                        JSONObject obj = new JSONObject(json);
                        List data = new GsonBuilder().create().fromJson(obj.optJSONArray("list").toString(), type);
                        return Observable.just(data);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public <T> Observable put(String url, Map params, final Class<T> cls) {
        return mService.put(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull ResponseBody body) throws Exception {
                        try {
                            String json = body.string();
                            T data = new GsonBuilder().create().fromJson(json, cls);
                            return Observable.just(data);
                        } catch (IOException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(cls.newInstance());
                        } catch (JsonSyntaxException e) {
                            LogUtils.e(TAG, "IOException");
                            return Observable.just(cls.newInstance());
                        } catch (Exception e) {
                            LogUtils.e(TAG, "Exception");
                            return Observable.just(cls.newInstance());
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }

    @Override
    public Observable putWithoutRep(String url, Map params) {
        return mService.put(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(@NonNull ResponseBody body) throws Exception {
                        return Observable.just(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof APIError) {
                            APIError apiError = (APIError) throwable;
                            if (apiError.getCode() == HTTP_CODE.CODE_AUTH_ERROR) {
                                if (mListener != null) mListener.onAuthError();
                            }
                        }
                    }
                });
    }
}
