package com.kaider.library.mvp;

import android.content.Context;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Author： KaiDer
 * @Date： 2019/7/6 20:47
 * @Description: Mvp契约类
 * @Email： 2024468244@qq.com
 */
public interface Contract {

    /**
     * View层
     *
     * @param <O>
     */
    public interface IView<O> {
        /**
         * 请求成功返回到View层的数据
         *
         * @param bean 成功数据
         */
        void onSuccess(O bean);

        /**
         * 请求失败返回到View层的错误信息
         *
         * @param throwable 失败信息
         */
        void onFail(Throwable throwable);
    }

    /**
     * Model层
     *
     * @param <B>
     */
    public interface IModel<B> {

        /**
         * get请求，不做入参
         *
         * @param url:请求路径
         * @param bean:请求数据Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByGet(String url, B bean, CallRequestData callRequestData);

        /**
         * get请求，param入参
         *
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求数据Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByGetParam(String url, Map<String, String> queryMap, B bean, CallRequestData callRequestData);

        /**
         * get请求，header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param bean:param入参
         * @param callRequestData:返回结果接口
         */
        void getRequestByGetHeader(Context context, String url, B bean, CallRequestData callRequestData);

        /**
         * get请求，Param入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求数据Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByGetParamAndHeader(Context context, String url, Map<String, String> queryMap, B bean, CallRequestData callRequestData);

        /**
         * post请求，Param入参
         *
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求数据Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPostParam(String url, Map<String, String> queryMap, B bean, CallRequestData callRequestData);

        /**
         * post请求，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param bean:请求数据Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPostHeader(Context context, String url, B bean, CallRequestData callRequestData);

        /**
         * Post请求，Param入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求数据Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPostParamAndHeader(Context context, String url, Map<String, String> queryMap, B bean, CallRequestData callRequestData);

        /**
         * Post请求，Field入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param fieldMap:param入参
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPostFieldAndHeader(Context context, String url, Map<String, String> fieldMap, B bean, CallRequestData callRequestData);

        /**
         * Post请求，File入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param file:File入参
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPostFileAndHeader(Context context, String url, File file, B bean, CallRequestData callRequestData);

        /**
         * Put请求，Param入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPutParamAndHeader(Context context, String url, Map<String, String> queryMap, B bean, CallRequestData callRequestData);

        /**
         * Post请求，Body入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param json:param入参
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPostBodyAndHeader(Context context, String url, String json, B bean, CallRequestData callRequestData);

        /**
         * Post请求，可上传多张图片
         *
         * @param context:上下文
         * @param url:请求路径
         * @param files:上传File，上传多张图片
         * @param paramMap:param入参
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPostFilesAndParamAndHeader(Context context, String url, List<File> files, Map<String, String> paramMap, B bean, CallRequestData callRequestData);

        /**
         * Delete请求，param入参，header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param paramMap:param入参
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByDeleteParamAndHeader(Context context, String url, Map<String, String> paramMap, B bean, CallRequestData callRequestData);

        /**
         * Post请求，上传图片Param入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param file:File入参，可上传图片
         * @param paramMap:param入参
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPostFileParamAndHeader(Context context, String url, File file, Map<String, String> paramMap, B bean, CallRequestData callRequestData);

        /**
         * Post请求，不做任何入参
         *
         * @param url:请求路径
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPost(String url, B bean, CallRequestData callRequestData);

        /**
         * Post请求，Param入参和Field入参
         *
         * @param url:请求路径
         * @param paramMap:param入参
         * @param fieldMap:field入参
         * @param bean:请求结果bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPostParamAndField(String url, Map<String, String> paramMap, Map<String, String> fieldMap, B bean, CallRequestData callRequestData);

        /**
         * Put请求，Field入参和Header入参
         *
         * @param url:请求路径
         * @param fieldMap:field入参
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByPutFieldAndHeader(Context context,String url, Map<String, String> fieldMap, B bean, CallRequestData callRequestData);

        /**
         * Delete请求，不做任何入参
         *
         * @param url:请求路径
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByDelete(String url, B bean, CallRequestData callRequestData);

        /**
         * Delete请求，param入参
         *
         * @param url:请求路径
         * @param paramMap:param入参
         * @param bean:请求结果Bean类
         * @param callRequestData:返回结果接口
         */
        void getRequestByDeleteParam(String url, Map<String, String> paramMap, B bean, CallRequestData callRequestData);

        interface CallRequestData<O> {
            /**
             * 返回请求的数据
             *
             * @param bean：请求的数据
             */
            void onRequestSuccess(O bean);

            /**
             * 返回请求失败的信息
             *
             * @param throwable：失败信息
             */
            void onRequestFail(Throwable throwable);
        }
    }

    /**
     * Presenter层
     *
     * @param <IView>
     * @param <O>
     */
    public interface IPresenter<IView, O> {
        void attach(IView iView);

        void detach();

        /**
         * get请求，不做入参
         *
         * @param url:请求路径
         * @param bean:请求数据Bean类
         */
        void mutualByGet(String url, O bean);

        /**
         * get请求，param入参
         *
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求数据Bean类
         */
        void mutualByGetParam(String url, Map<String, String> queryMap, O bean);

        /**
         * get请求，header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param bean:param入参
         */
        void mutualByGetHeader(Context context, String url, O bean);

        /**
         * get请求，Param入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求数据Bean类
         */
        void mutualByGetParamAndHeader(Context context, String url, Map<String, String> queryMap, O bean);

        /**
         * post请求，Param入参
         *
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求数据Bean类
         */
        void mutualByPostParam(String url, Map<String, String> queryMap, O bean);

        /**
         * post请求，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param bean:请求数据Bean类
         */
        void mutualByPostHeader(Context context, String url, O bean);

        /**
         * Post请求，Param入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求数据Bean类
         */
        void mutualByPostParamAndHeader(Context context, String url, Map<String, String> queryMap, O bean);

        /**
         * Post请求，Field入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param fieldMap:param入参
         * @param bean:请求结果Bean类
         */
        void mutualByPostFieldAndHeader(Context context, String url, Map<String, String> fieldMap, O bean);

        /**
         * Post请求，File入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param file:File入参
         * @param bean:请求结果Bean类
         */
        void mutualByPostFileAndHeader(Context context, String url, File file, O bean);

        /**
         * Put请求，Param入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param queryMap:param入参
         * @param bean:请求结果Bean类
         */
        void mutualByPutParamAndHeader(Context context, String url, Map<String, String> queryMap, O bean);

        /**
         * Post请求，Body入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param json:param入参
         * @param bean:请求结果Bean类
         */
        void mutualByPostBodyAndHeader(Context context, String url, String json, O bean);

        /**
         * Post请求，可上传多张图片
         *
         * @param context:上下文
         * @param url:请求路径
         * @param files:上传File，上传多张图片
         * @param paramMap:param入参
         * @param bean:请求结果Bean类
         */
        void mutualByPostFileAndParamAndHeader(Context context, String url, List<File> files, Map<String, String> paramMap, O bean);

        /**
         * Delete请求，param入参，header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param paramMap:param入参
         * @param bean:请求结果Bean类
         */
        void mutualByDeleteParamAndHeader(Context context, String url, Map<String, String> paramMap, O bean);

        /**
         * Post请求，上传图片Param入参，Header入参
         *
         * @param context:上下文
         * @param url:请求路径
         * @param file:File入参，可上传图片
         * @param paramMap:param入参
         * @param bean:请求结果Bean类
         */
        void mutualByPostFileParamAndHeader(Context context, String url, File file, Map<String, String> paramMap, O bean);

        /**
         * Post请求，不做任何入参
         *
         * @param url:请求路径
         * @param bean:请求结果Bean类
         */
        void mutualByPost(String url, O bean);

        /**
         * Post请求，Param入参和Field入参
         *
         * @param url:请求路径
         * @param paramMap:param入参
         * @param fieldMap:field入参
         * @param bean:请求结果bean类
         */
        void mutualByPostParamAndField(String url, Map<String, String> paramMap, Map<String, String> fieldMap, O bean);

        /**
         * Put请求，Field入参和Header入参
         *
         * @param url:请求路径
         * @param fieldMap:field入参
         * @param bean:请求结果Bean类
         */
        void mutualByPutFieldAndHeader(Context context,String url, Map<String, String> fieldMap,O bean);

        /**
         * Delete请求，不做任何入参
         *
         * @param url:请求路径
         * @param bean:请求结果Bean类
         */
        void mutualByDelete(String url, O bean);

        /**
         * Delete请求，param入参
         *
         * @param url:请求路径
         * @param paramMap:param入参
         * @param bean:请求结果Bean类
         */
        void mutualByDeleteParam(String url, Map<String, String> paramMap, O bean);

    }

}
