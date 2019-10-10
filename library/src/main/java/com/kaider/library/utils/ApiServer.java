package com.kaider.library.utils;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @Author： KaiDer
 * @Date： 2019/7/6 20:25
 * @Description: 网络请求方法接口类
 * @Email： 2024468244@qq.com
 */
public interface ApiServer {

    /**
     * Get请求，不做入参
     *
     * @param url:接口路径
     * @return 返回响应体
     */
    @GET
    Observable<ResponseBody> requestByGet(@Url String url);

    /**
     * Get请求Param入参
     *
     * @param url:接口路径
     * @param map：param入参
     * @return 返回响应体
     */
    @GET
    Observable<ResponseBody> requestByGetParam(@Url String url, @QueryMap Map<String, String> map);

    /**
     * Get请求Header入参
     *
     * @param url:接口路径
     * @param headerMap：hearder入参
     * @return 返回响应体
     */
    @GET
    Observable<ResponseBody> requestByGetHeader(@Url String url, @HeaderMap Map<String, String> headerMap);

    /**
     * Get请求Param和Header入参
     *
     * @param url:接口路径
     * @param queryMap：param入参
     * @param headerMap：header入参
     * @return 返回响应体
     */
    @GET
    Observable<ResponseBody> requestByGetParamAndHeader(@Url String url, @QueryMap Map<String, String> queryMap, @HeaderMap Map<String, String> headerMap);

    /**
     * Post请求，不做入参
     *
     * @param url:接口路径
     * @return 返回响应体
     */
    @POST
    Observable<ResponseBody> requestByPost(@Url String url);

    /**
     * POST请求Param入参
     *
     * @param url:接口路径
     * @param queryMap：param入参
     * @return 返回响应体
     */
    @POST
    Observable<ResponseBody> requestByPostParam(@Url String url, @QueryMap Map<String, String> queryMap);

    /**
     * POST请求Header入参
     *
     * @param url:接口路径
     * @param headerMap：header入参
     * @return 返回响应体
     */
    @POST
    Observable<ResponseBody> requestByPostHeader(@Url String url, @HeaderMap Map<String, String> headerMap);

    /**
     * POST请求Param和Header入参
     *
     * @param url:接口路径
     * @param queryMap：param入参
     * @param headerMap：header入参
     * @return 返回响应体
     */
    @POST
    Observable<ResponseBody> requestByPostParamAndHeader(@Url String url, @QueryMap Map<String, String> queryMap, @HeaderMap Map<String, String> headerMap);

    /**
     * POST请求，仅作用于上传头像
     *
     * @param url:路径
     * @param parts:图片
     * @param headerMap:header入参
     * @return 返回响应体
     */
    @POST
    @Multipart
    Observable<ResponseBody> requestByPostUpHeadImg(@Url String url, @Part MultipartBody.Part parts, @HeaderMap Map<String, String> headerMap);

    /**
     * Post请求，可上传图片类型Param入参和Header入参
     *
     * @param url:路径
     * @param paramMap:param入参
     * @param headerMap:header入参
     * @return 返回响应体
     */
    @POST
    @Multipart
    Observable<ResponseBody> requestByPostUpFileParamAndHeader(@Url String url, @Part MultipartBody.Part part, @QueryMap Map<String, String> paramMap, @HeaderMap Map<String, String> headerMap);

    /**
     * Post请求，可上传多张头像
     *
     * @param url:请求路径
     * @param parts:多张图片资源
     * @param paramMap:请求所需param参数
     * @param headerMap:请求所需用户的header参数
     * @return 返回响应体
     */
    @POST
    @Multipart
    Observable<ResponseBody> requestByPostReleaseCircle(@Url String url, @Part MultipartBody.Part[] parts, @QueryMap Map<String, String> paramMap, @HeaderMap Map<String, String> headerMap);


    /**
     * POST请求Filed和Header入参
     *
     * @param url:接口路径
     * @param fieldMap：body入参
     * @param headerMap：header入参
     * @return 返回响应体
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> requestByPostFieldAndHeader(@Url String url, @FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> headerMap);

    /**
     * POST请求Param和Filed入参
     *
     * @param url:接口路径
     * @param paramMap:请求所需param参数
     * @param fieldMap：body入参
     * @return 返回响应体
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> requestByPostParamAndField(@Url String url, @QueryMap Map<String, String> paramMap, @FieldMap Map<String, String> fieldMap);

    /**
     * Body入参，json格式
     *
     * @param url:接口路径
     * @param headerMap：header入参
     * @param requestBody：Body入参
     * @return 返回响应体
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Observable<ResponseBody> requestByPostBodyAndHeader(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * Put请求Param和Header入参
     *
     * @param url:接口路径
     * @param queryMap：param入参
     * @param headerMap:header入参
     * @return 返回响应体
     */
    @PUT
    Observable<ResponseBody> requestByPutParamAndHeader(@Url String url, @QueryMap Map<String, String> queryMap, @HeaderMap Map<String, String> headerMap);

    /**
     * Put请求Field和Header入参
     *
     * @param url:接口路径
     * @param fieldMap:Field入参
     * @param headerMap:header入参
     * @return 返回响应体
     */
    @PUT
    Observable<ResponseBody> requestByPutFieldAndHeader(@Url String url, @FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> headerMap);

    /**
     * Delete请求，入参Param和Header
     *
     * @param url:接口路径
     * @param paramMap:param入参
     * @param hearMap:header入参
     * @return 返回请求响应体
     */
    @DELETE
    Observable<ResponseBody> requestByDeleteParamAndHeader(@Url String url, @QueryMap Map<String, String> paramMap, @HeaderMap Map<String, String> hearMap);

    /**
     * Delete请求,不入参
     *
     * @param url:接口路径
     * @return 返回请求响应体
     */
    @DELETE
    Observable<ResponseBody> requestByDelete(@Url String url);

    /**
     * Delete请求,不入参
     *
     * @param url:接口路径
     * @param paramMap:param入参
     * @return 返回请求响应体
     */
    @DELETE
    Observable<ResponseBody> requestByDeleteParam(@Url String url, @QueryMap Map<String, String> paramMap);


}
