package com.hhh.lib_api.services.interfaces;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by nova on 12/10/2017.
 */

public interface IHttpService {
    @POST
    Observable<ResponseBody> post(@Url String url, @Body Map<String, Object> params);

    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> params);

    @DELETE
    Observable<ResponseBody> delete(@Url String url);

    @HTTP(method = "DELETE", hasBody = true)
    Observable<ResponseBody> delete(@Url String url, @Body Map<String, Object> value);

    @PATCH
    Observable<ResponseBody> patch(@Url String url, @Body RequestBody value);

    @PATCH
    Observable<ResponseBody> patch(@Url String url, @Body Map<String, Object> value);

    @Multipart
    @POST
    Observable<ResponseBody> upload(@Url String url, @Part MultipartBody.Part file, @PartMap Map<String, RequestBody> params);

    @PUT
    Observable<ResponseBody> put(@Url String url, @Body Map<String, Object> params);
}
