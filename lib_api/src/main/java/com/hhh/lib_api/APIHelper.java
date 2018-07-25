package com.hhh.lib_api;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hhh.lib_api.error.APIError;
import com.hhh.lib_api.path.PathManager;
import com.hhh.lib_api.services.interfaces.IHttpBaseService;
import com.hhh.lib_api.token.TokenManager;
import com.hhh.lib_core.model.SampleCommonRep;
import com.hhh.lib_core.model.WeConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nova on 26/09/2017.
 */

public class APIHelper {

    private final static String TAG = APIHelper.class.getSimpleName();
    private static APIHelper mInstance;

    private APIHelper() {
    }

    public static APIHelper getInstance() {
        if (mInstance == null) {
            mInstance = new APIHelper();
        }
        return mInstance;
    }

    public static final String BASE_URL = WeConstants.API_ADDRESS;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    private OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // 请求网络之前的预处理
                        Request original = chain.request();
                        String path = original.url().url().getPath();

                        Request.Builder reqBuilder = original.newBuilder();

                        if (PathManager.getInstance().isNeedToken(path)) {
                            String token = TokenManager.getInstance().getToken();
                            if (token != null)
                                reqBuilder.header("Authorization", "Bearer ".concat(token));
                        }

                        Request realRequest = reqBuilder.build();

                        return chain.proceed(realRequest);
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // 获取数据之后的预处理
                        Response response = chain.proceed(chain.request());
                        String codeStr = String.valueOf(response.code());

                        // 解析错误码code
                        if (!codeStr.equals(HttpCode.CODE_SUCCESS)) {

                            SampleCommonRep rep;
                            String errorString = response.body().string();
                            try {
                                rep = new Gson().fromJson(errorString, SampleCommonRep.class);
                            } catch (JsonSyntaxException e) {
                                rep = null;
                            }

                            if (rep != null) {
                                //
                                if (TextUtils.isEmpty(rep.getMessage())) {
                                    throw new APIError("网络请求出错，请稍后重试", response.code());
                                } else {
                                    if (rep.getMessage().equals("Bad credentials")) {    // 特别处理
                                        throw new APIError("账户密码错误", response.code());
                                    } else {
                                        throw new APIError(rep.getMessage(), response.code());
                                    }
                                }
                            }

                            throw new APIError("网络请求出错，请稍后重试", response.code());
                        } else {

                        }

                        // 返回正确的数据对象
                        return response;
                    }
                })
                .addInterceptor(loggingInterceptor)//设置日志打印
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
    }

    public IHttpBaseService createHttpAPI() {
        return createRetrofit().create(IHttpBaseService.class);
    }


}
