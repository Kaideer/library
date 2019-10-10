package com.kaider.library.mvp;


import android.content.Context;

import com.kaider.library.api.MyApi;
import com.kaider.library.sharedpreferences.SpStorage;
import com.kaider.library.utils.RetrofitUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author： KaiDer
 * @Date： 2019/7/6 20:50
 * @Description: MvpModel层
 * @Email： 2024468244@qq.com
 */
public class Model implements Contract.IModel {
    private static final String TAG = "Model";

    /**
     * Get 请求，不做任何入参
     */
    @Override
    public void getRequestByGet(String url, Object bean, final CallRequestData callRequestData) {
        RetrofitUtil.getInstance().requestByGet(url, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Get请求Header入参
     *
     * @param context：上下文
     * @param url：请求路径
     * @param bean：请求Bean类
     * @param callRequestData：返回的请求数据
     */
    @Override
    public void getRequestByGetHeader(Context context, String url, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByGetHeader(url, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求Header入参
     *
     * @param context：上下文
     * @param url：请求路径
     * @param bean：请求Bean类
     * @param callRequestData：请求返回的数据
     */
    @Override
    public void getRequestByPostHeader(Context context, String url, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByPostHeader(url, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求File和Header入参，上传头像格式
     *
     * @param context:上下文
     * @param url:请求路径
     * @param file:文件类型
     * @param bean:请求Bean类
     * @param callRequestData:请求返回的数据
     */
    @Override
    public void getRequestByPostFileAndHeader(Context context, String url, File file, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByPostUpHeadImg(url, file, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求Body入参，json请求格式，Header入参
     *
     * @param context:上下文
     * @param url:请求路径
     * @param json:json数据
     * @param bean:请求Bean类
     * @param callRequestData:请求返回数据
     */
    @Override
    public void getRequestByPostBodyAndHeader(Context context, String url, String json, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByPostBodyAndHeader(url, headerMap, json, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求，不做任何入参
     *
     * @param url:请求路径
     * @param bean:请求结果Bean类
     * @param callRequestData:返回结果接口
     */
    @Override
    public void getRequestByPost(String url, Object bean, CallRequestData callRequestData) {
        RetrofitUtil.getInstance().requestByPost(url, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Delete请求，不做任何入参
     *
     * @param url:请求路径
     * @param bean:请求结果Bean类
     * @param callRequestData:返回结果接口
     */
    @Override
    public void getRequestByDelete(String url, Object bean, CallRequestData callRequestData) {
        RetrofitUtil.getInstance().requestByDelete(url, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Delete请求，param入参
     *
     * @param url:请求路径
     * @param paramMap:param入参
     * @param bean:请求结果Bean类
     * @param callRequestData:返回结果接口
     */
    @Override
    public void getRequestByDeleteParam(String url, Map paramMap, Object bean, CallRequestData callRequestData) {
        RetrofitUtil.getInstance().requestByDeleteParam(url, paramMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Put请求，Field入参和Header入参
     *
     * @param url:请求路径
     * @param fieldMap:field入参
     * @param bean:请求结果Bean类
     * @param callRequestData:返回结果接口
     */
    @Override
    public void getRequestByPutFieldAndHeader(Context context,String url, Map fieldMap, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByPutFieldAndHeader(url, fieldMap, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求，Param入参和Field入参
     *
     * @param url:请求路径
     * @param paramMap:param入参
     * @param fieldMap:field入参
     * @param bean:请求结果bean类
     * @param callRequestData:返回结果接口
     */
    @Override
    public void getRequestByPostParamAndField(String url, Map paramMap, Map fieldMap, Object bean, CallRequestData callRequestData) {
        RetrofitUtil.getInstance().requestByPostParamAndField(url, paramMap, fieldMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求，上传图片，Param入参，Header入参
     *
     * @param url:路径
     * @param file:图片
     * @param paramMap:param入参
     * @param bean:请求Bean类
     * @param callRequestData:请求返回数据的接口
     */
    @Override
    public void getRequestByPostFileParamAndHeader(Context context, String url, File file, Map paramMap, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByPostUpFileParamAndHeader(url, file, paramMap, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Delete请求
     *
     * @param context:上下文
     * @param url:请求路径
     * @param paramMap:请求所需Param入参
     * @param bean:请求Bean类
     * @param callRequestData:请求返回数据的回调接口
     */
    @Override
    public void getRequestByDeleteParamAndHeader(Context context, String url, Map paramMap, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByDeleteParamAndHeader(url, paramMap, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求，可上传多张图片
     *
     * @param context:上下文
     * @param url:请求路径
     * @param list:图片集合
     * @param paramMap:请求所需参数
     * @param bean:请求Bean类
     * @param callRequestData:请求返回数据的接口
     */
    @Override
    public void getRequestByPostFilesAndParamAndHeader(Context context, String url, List list, Map paramMap, Object bean, CallRequestData callRequestData) {
        try {
            List<File> fileList = (List<File>) list;
            Map<String, String> headerMap = new HashMap<>(3);
            String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
            headerMap.put(MyApi.UserIdTarget, userId);
            RetrofitUtil.getInstance().requestByPostFileAndParamAndHeader(url, fileList, paramMap, headerMap, bean, new RetrofitUtil.CallResultData() {
                @Override
                public void onResponseSuccess(Object bean) {
                    callRequestData.onRequestSuccess(bean);
                }

                @Override
                public void onResponseFail(Throwable throwable) {
                    callRequestData.onRequestFail(throwable);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Put请求Param入参和Header入参
     *
     * @param context:上下文
     * @param url:请求路径
     * @param queryMap:请求数据Map
     * @param bean:请求Bean类
     * @param callRequestData:请求返回数据
     */
    @Override
    public void getRequestByPutParamAndHeader(Context context, String url, Map queryMap, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByPutParamAndHeader(url, queryMap, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求Field入参和Header入参
     *
     * @param context:上下文
     * @param url:请求路径
     * @param fieldMap:发送请求所需参数
     * @param bean:请求Bean类
     * @param callRequestData:请求返回数据
     */
    @Override
    public void getRequestByPostFieldAndHeader(Context context, String url, Map fieldMap, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByPostFieldAndHeader(url, fieldMap, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求Param入参，和Header入参
     *
     * @param context:上下文
     * @param url:请求路径
     * @param queryMap:请求数据Map
     * @param bean:请求Bean类
     * @param callRequestData:请求返回数据
     */
    @Override
    public void getRequestByPostParamAndHeader(Context context, String url, Map queryMap, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByPostParamAndHeader(url, queryMap, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Post请求，Param入参
     *
     * @param url:请求路径
     * @param queryMap:请求所需参数
     * @param bean:请求Bean类
     * @param callRequestData:请求返回数据
     */
    @Override
    public void getRequestByPostParam(String url, Map queryMap, Object bean, CallRequestData callRequestData) {
        RetrofitUtil.getInstance().requestByPostParam(url, queryMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Get请求，Param入参和Header入参
     *
     * @param context:上下文
     * @param url:请求路径
     * @param queryMap:请求所需参数
     * @param bean:请求Bean类
     * @param callRequestData:请求返回数据
     */
    @Override
    public void getRequestByGetParamAndHeader(Context context, String url, Map queryMap, Object bean, CallRequestData callRequestData) {
        Map<String, String> headerMap = new HashMap<>(3);
        String userId = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
        headerMap.put(MyApi.UserIdTarget, userId);
        RetrofitUtil.getInstance().requestByGetParamAndHeader(url, queryMap, headerMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

    /**
     * Get请求，Param入参
     *
     * @param url:请求路径
     * @param queryMap:请求所需参数
     * @param bean:请求Bean类
     * @param callRequestData:请求返回参数
     */
    @Override
    public void getRequestByGetParam(String url, Map queryMap, Object bean, CallRequestData callRequestData) {
        RetrofitUtil.getInstance().requestByGetParam(url, queryMap, bean, new RetrofitUtil.CallResultData() {
            @Override
            public void onResponseSuccess(Object bean) {
                callRequestData.onRequestSuccess(bean);
            }

            @Override
            public void onResponseFail(Throwable throwable) {
                callRequestData.onRequestFail(throwable);
            }
        });
    }

}
