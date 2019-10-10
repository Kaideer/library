package com.kaider.library.utils;

import com.google.gson.Gson;
import com.kaider.library.api.MyApi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Author： KaiDer
 * @Date： 2019/7/6 20:27
 * @Description: 网络请求工具类，静态内部类+单例模式 ，使用Retrofit+RxJava，观察者模式线程切换请求数据
 * @Email： 2024468244@qq.com
 */
public class RetrofitUtil<O> {

    private ApiServer apiServer;
    private static final String TAG = "RetrofitUtil";

    public static RetrofitUtil<Object> getInstance() {
        return RetrofitManager.ourInstance;
    }

    /**
     * 静态内部类
     */
    private static class RetrofitManager {
        private static final RetrofitUtil<Object> ourInstance = new RetrofitUtil<>();
    }

    private RetrofitUtil() {

        //初始化Retrofit
        initRetrofit();

    }

    private void initRetrofit() {

        //应为Retrofit的是对OkHttp进行了一层封装，所以要初始化OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //设置连接时长
                .connectTimeout(10, TimeUnit.SECONDS)
                //设置读取时长
                .readTimeout(30, TimeUnit.SECONDS)
                //设置连接超时时长
                .writeTimeout(30, TimeUnit.SECONDS)
                //添加日志拦截器
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        //初始化Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                //初始化BaseUrl
                .baseUrl(MyApi.Base_Url)
                //添加RxJava,对请求做管理
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        //获取请求方法实例
        apiServer = retrofit.create(ApiServer.class);

    }

    /**
     * 根据Get请求，不做任何入参方法
     */
    public void requestByGet(String url, O bean, CallResultData callResultData) {

        apiServer.requestByGet(url)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * 根据Get请求，Param入参
     */
    public void requestByGetParam(String url, Map<String, String> queryMap, O bean, CallResultData callResultData) {
        if (queryMap == null) {
            queryMap = new HashMap<>(16);
        }
        apiServer.requestByGetParam(url, queryMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * 根据Get请求，Header入参
     */
    public void requestByGetHeader(String url, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        apiServer.requestByGetHeader(url, headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * Post请求，用于发布圈子
     */
    public void requestByPostFileAndParamAndHeader(String url, List<File> files, Map<String, String> paramMap, Map<String, String> headerMap, O bean, CallResultData callResultData) {

        //判空处理
        if (files == null) {
            files = new ArrayList<>();
        }
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        if (paramMap == null) {
            paramMap = new HashMap<>(16);
        }


        MultipartBody.Part[] parts = new MultipartBody.Part[files.size()];
        int index = 0;
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
            parts[index] = filePart;
            index++;
        }

        apiServer.requestByPostReleaseCircle(url, parts, paramMap, headerMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));

    }

    /**
     * 根据Get请求，Param入参，Header入参
     */
    public void requestByGetParamAndHeader(String url, Map<String, String> queryMap, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        if (queryMap == null) {
            queryMap = new HashMap<>(16);
        }
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        apiServer.requestByGetParamAndHeader(url, queryMap, headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * Post请求，不入参
     */
    public void requestByPost(String url, O bean, CallResultData callResultData) {
        apiServer.requestByPost(url)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * 根据POST请求，param入参
     */
    public void requestByPostParam(String url, Map<String, String> queryMap, O bean, CallResultData callResultData) {
        if (queryMap == null) {
            queryMap = new HashMap<>(16);
        }
        apiServer.requestByPostParam(url, queryMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * 根据POST请求，Header入参
     */
    public void requestByPostHeader(String url, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        apiServer.requestByPostHeader(url, headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * 根据Post请求，Param入参，Header入参
     */
    public void requestByPostParamAndHeader(String url, Map<String, String> queryMap, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        if (queryMap == null) {
            queryMap = new HashMap<>(16);
        }
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        apiServer.requestByPostParamAndHeader(url, queryMap, headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * Post请求，用于上传头像
     */
    public void requestByPostUpHeadImg(String url, File file, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        apiServer.requestByPostUpHeadImg(url, filePart, headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));

    }

    /**
     * Post请求，可上传图片Param入参和Header入参
     */
    public void requestByPostUpFileParamAndHeader(String url, File file, Map<String, String> paramMap, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        if (paramMap == null) {
            paramMap = new HashMap<>(16);
        }
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("content", file.getName(), requestBody);

        apiServer.requestByPostUpFileParamAndHeader(url, filePart, paramMap, headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));

    }

    /**
     * Post请求，Field入参，Header入参
     */
    public void requestByPostFieldAndHeader(String url, Map<String, String> fieldMap, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        if (fieldMap == null) {
            fieldMap = new HashMap<>(16);
        }
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        apiServer.requestByPostFieldAndHeader(url, fieldMap, headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * Post请求，Param入参，Field入参
     */
    public void requestByPostParamAndField(String url, Map<String, String> paramMap, Map<String, String> fieldMap, O bean, CallResultData callResultData) {
        //判空处理
        if (paramMap == null) {
            paramMap = new HashMap<>(16);
        }
        if (fieldMap == null) {
            fieldMap = new HashMap<>(16);
        }
        apiServer.requestByPostParamAndField(url, paramMap, fieldMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * Put请求，Param入参，Header入参
     */
    public void requestByPutParamAndHeader(String url, Map<String, String> queryMap, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        if (queryMap == null) {
            queryMap = new HashMap<>(16);
        }
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        apiServer.requestByPutParamAndHeader(url, queryMap, headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    public void requestByPutFieldAndHeader(String url, Map<String, String> fieldMap, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        //判空处理
        if (fieldMap == null) {
            fieldMap = new HashMap<>();
        }
        if (headerMap == null) {
            headerMap = new HashMap<>();
        }
        apiServer.requestByPutFieldAndHeader(url,fieldMap,headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * Post请求，Body体入参和Header入参
     */
    public void requestByPostBodyAndHeader(String url, Map<String, String> headerMap, String json, O bean, CallResultData callResultData) {
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        apiServer.requestByPostBodyAndHeader(url, headerMap, requestBody)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * Delete请求，param入参，header入参
     */
    public void requestByDeleteParamAndHeader(String url, Map<String, String> paramMap, Map<String, String> headerMap, O bean, CallResultData callResultData) {
        //判空处理
        if (paramMap == null) {
            paramMap = new HashMap<>(16);
        }
        if (headerMap == null) {
            headerMap = new HashMap<>(3);
        }
        apiServer.requestByDeleteParamAndHeader(url, paramMap, headerMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }

    /**
     * Delete请求，param入参
     */
    public void requestByDeleteParam(String url, Map<String, String> paramMap, O bean, CallResultData callResultData) {
        //判空处理
        if (paramMap == null) {
            paramMap = new HashMap<>(16);
        }

        apiServer.requestByDeleteParam(url, paramMap)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));

    }

    public void requestByDelete(String url, O bean, CallResultData callResultData) {
        apiServer.requestByDelete(url)
                //指定请求线程
                .subscribeOn(Schedulers.io())
                //指定结果返回线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(bean, callResultData));
    }


    /**
     * 请求数据方法
     *
     * @param bean:请求数据传来的Bean类，以便解析数据
     * @param callResultData：返回请求的结果
     * @return 返回请求响应体
     */
    private Observer<ResponseBody> getObserve(O bean, CallResultData callResultData) {
        Observer<ResponseBody> observer = new Observer<ResponseBody>() {
            /**
             * 当所有请求执行成功执行，与onError相反
             * */
            @Override
            public void onCompleted() {

            }

            /**
             * 当任何一个请求执行失败执行
             * */
            @Override
            public void onError(Throwable e) {
                //因为这里的接口有可能会被传入空，所以要进行判空处理
                if (callResultData != null) {
                    //将整段错误信息传回
                    callResultData.onResponseFail(e);
                }
            }

            /**
             * 当任何一个请求执行成功执行
             * */
            @Override
            public void onNext(ResponseBody responseBody) {
                //因为这里的接口有可能会被传入空，所以要进行判空处理
                if (callResultData != null) {

                    //进行tryCatch处理
                    try {
                        //获取请求及结果
                        String string = responseBody.string();
                        //将结果通过传入的Bean类进行解析
                        Gson gson = new Gson();
                        Object json = gson.fromJson(string, bean.getClass());
                        //将解析后的Bean类传回
                        callResultData.onResponseSuccess(json);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        return observer;
    }


    /**
     * 定义接口，用于回调请求结果,直接回调整个Bean类
     *
     * @param <O>
     */
    public interface CallResultData<O> {
        /**
         * 返回请求成功的数据
         *
         * @param bean : 请求的bean类数据
         */
        void onResponseSuccess(O bean);

        /**
         * 返回失败信息
         *
         * @param throwable : 请求的失败信息
         */
        void onResponseFail(Throwable throwable);
    }


}
