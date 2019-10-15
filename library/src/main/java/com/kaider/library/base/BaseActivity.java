package com.kaider.library.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kaider.library.mvp.Contract;
import com.kaider.library.mvp.Presenter;
import com.kaider.library.netutil.NetConnections;
import com.kaider.library.sharedpreferences.SpStorage;
import com.kaider.library.statusutils.StatusBarUtil;
import com.kaider.library.toastutils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author： KaiDer
 * @Date： 2019/7/7 19:34
 * @Description: Activity基类
 * @Email： 2024468244@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity implements Contract.IView {

    private Presenter presenter;
    private static final String NORMAL = "0000";
    private static final String NOLOGIN = "2001";
    private static final String NONET = "2002";
    /**
     * 定义一个存放所有Activity的集合
     */
    public static List<Activity> activities;
    private static Activity activity;
    public static Context context;
    //    private AlertDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(initLayout());
        } catch (Exception e) {
            e.printStackTrace();
        }

        getInstaceState(savedInstanceState);

        /**PackageManager packageManager = this.getApplication().getPackageManager();
         Intent intent = packageManager.getLaunchIntentForPackage(this.getPackageName());
         ComponentName launchComponentName = intent.getComponent();
         ComponentName componentName = this.getComponentName();
         if(componentName.toString().equals(launchComponentName.toString())){
         Log.i("min77",componentName.getClassName()+"是第一个启动的activity");
         }else {
         Log.i("min77",componentName.getClassName()+"不是第一个启动的activity");
         }*/

        activity = this;
        context = this;

        addActivity(this);

        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //设置适应状态栏字体颜色
//        StatusBarUtil.setStatusBarColor(this, R.color.black);
        StatusBarUtil.setStatusBarDarkTheme(this, true);

        //初始化控件,此方法禁止请求数据或者对数据进行操作，请求数据的操作请在 preparingData()方法内操作
        initView();

        //判断是否注册EventBus
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }

//        //初始化加载框
//        //初始化AlertDialog
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        dialog = alert.create();
//        //加载布局
//        View loading = LayoutInflater.from(this).inflate(R.layout.loading, null);
//        //设置点击外部不可取消
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setView(loading);
//

        //获取实例
        presenter = new Presenter();
        presenter.attach(this);

        preparingData();

        initListener();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceState(outState);
    }

    public void saveInstanceState(Bundle outState) {

    }

    public void getInstaceState(Bundle savedInstanceState) {

    }

    public boolean isRegisterEventBus() {
        return false;
    }

    public static List<Activity> baseActivitys(int index) {
        return activities;
    }

    public static Activity baseActivity() {
        return activity;
    }

    /**
     * 往集合中添加Activity
     *
     * @param activity:要添加的Activity
     */
    public static void addActivity(Activity activity) {
        if (activities == null) {
            //如果集合为空  则初始化
            activities = new ArrayList<>();
        }
        //将activity加入到集合中
        activities.add(activity);
    }

    /**
     * 结束掉所有的Activity
     */
    public static void finishAllActivity() {
        //循环集合,  将所有的activity finish
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 从集合中获取当前Activity的实例
     *
     * @param activity:Activity的实例
     * @return
     */
    public static Activity getCurrencyActivityInstence(Activity activity) {
        if (activity != null) {
            for (int i = 0; i < activities.size(); i++) {
                //判断activity 的集合是否存有当前activity的实例
                if (activities.get(i) == activity) {
                    //有则返回
                    return activities.get(i);
                } else {
                    //没有则返回null
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 删除某个Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        //移除Activity
        activities.remove(activity);
    }


    /**
     * 判断是否处于接口正常调用
     *
     * @param resultCode:判断的返回值
     * @return
     */
    public static boolean isNormal(String resultCode) {
        if (!resultCode.isEmpty()) {
            if (resultCode.equals(NORMAL)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 判断是否处于未登录状态
     *
     * @param resultCode:判断的返回值
     * @return
     */
    public static boolean isNoLogin(String resultCode) {
        if (!resultCode.isEmpty()) {
            if (resultCode.equals(NOLOGIN)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 判断是否处于无网络状态
     *
     * @param resultCode:判断的返回值
     * @return
     */
    public static boolean isNoNet(String resultCode) {
        if (!resultCode.isEmpty()) {
            if (resultCode.equals(NONET)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            reLoadData();
        } catch (Exception e) {
            ToastUtil.showShort(this, "数据错误 ！");
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pagePause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pageStop();
    }

    /**
     * 数据返回成功
     */
    @Override
    public void onSuccess(Object bean) {
        //请求成功方法
        responseSuccess(bean);
    }

    /**
     * 数据返回失败
     */
    @Override
    public void onFail(Throwable throwable) {
        //请求失败方法
        responseFail(throwable);
    }

    /**
     * Get请求，不做任何入参
     */
    public String requestByGet(String url, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            presenter.mutualByGet(url, bean);
            return NORMAL;
        } else {
            return NONET;
        }
    }

    /**
     * Get请求，Param入参
     */
    public String requestByGetParam(String url, Map<String, String> queryMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            presenter.mutualByGetParam(url, queryMap, bean);
            return NORMAL;
        } else {
            return NONET;
        }
    }

    /**
     * Get请求，Header入参
     */
    public String requestByGetHeader(String url, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByGetHeader(this, url, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Get请求，Param入参，Header入参
     */
    public String requestByGetParamAndHeader(String url, Map<String, String> queryMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByGetParamAndHeader(this, url, queryMap, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，不做入参
     */
    public String requestByPost(String url, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            presenter.mutualByPost(url, bean);
            return NORMAL;
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，Param入参
     */
    public String requestByPostParam(String url, Map<String, String> queryMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            presenter.mutualByPostParam(url, queryMap, bean);
            return NORMAL;
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，Param入参和Field入参
     */
    public String requestByPostParamAndField(String url, Map<String, String> paramMap, Map<String, String> fieldMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            presenter.mutualByPostParamAndField(url, paramMap, fieldMap, bean);
            return NORMAL;
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，Header入参
     */
    public String requestByPostHeader(String url, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByPostHeader(this, url, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，Field入参，Header入参
     */
    public String requestByPostFieldAndHeader(String url, Map<String, String> fieldMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByPostFieldAndHeader(this, url, fieldMap, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，Param入参，Header入参
     */
    public String requestByPostParamAndHeader(String url, Map<String, String> queryMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByPostParamAndHeader(this, url, queryMap, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，File入参，Header入参
     */
    public String requestByPostFileAndHeader(String url, String key, File file, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByPostFileAndHeader(this, url, key, file, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Put请求，Param入参，Header入参
     */
    public String requestByPutParamAndHeader(String url, Map<String, String> queryMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByPutParamAndHeader(this, url, queryMap, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Put请求，Field入参和Header入参
     */
    public String requestByPutFieldAndHeader(String url, Map<String, String> fieldMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByPutFieldAndHeader(this, url, fieldMap, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，Body入参，Header入参
     */
    public String requestByPostBodyAndHeader(String url, String json, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByPostBodyAndHeader(this, url, json, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，可上传多张图片
     */
    public String requestByPostFilesAndParamAndHeader(String url, String key, List<File> files, Map<String, String> paramMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByPostFileAndParamAndHeader(this, url, key, files, paramMap, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Delete请求
     */
    public String requestByDeleteParamAndHeader(String url, Map<String, String> paramMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByDeleteParamAndHeader(this, url, paramMap, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Delete请求，不做入参
     */
    public String requestByDelete(String url, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            presenter.mutualByDelete(url, bean);
            return NORMAL;
        } else {
            return NONET;
        }
    }

    /**
     * Delete请求，Param入参
     */
    public String requestByDeleteParam(String url, Map<String, String> paramMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            presenter.mutualByDeleteParam(url, paramMap, bean);
            return NORMAL;
        } else {
            return NONET;
        }
    }

    /**
     * Post请求
     */
    public String requestByPostFileParamAndHeader(String url, String key, File file, Map<String, String> paramMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(this);
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(this);
            if (isUserInfo) {
                presenter.mutualByPostFileParamAndHeader(this, url, key, file, paramMap, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }


    /**
     * 初始化布局方法
     *
     * @return 返回布局ID
     */
    protected abstract int initLayout();

    /**
     * 初始化控件方法
     */
    protected abstract void initView();

    /**
     * 加载数据方法
     */
    protected abstract void preparingData();

    /**
     * 刷新数据方法
     */
    protected void reLoadData() {

    }

    /**
     * 页面暂停方法
     */
    protected void pagePause() {

    }

    /**
     * 页面停止方法，不可见时
     */
    protected void pageStop() {

    }

    /**
     * 初始化监听方法
     */
    protected void initListener() {

    }

    /**
     * 数据响应成功方法
     *
     * @param bean 返回的数据
     */
    protected void responseSuccess(Object bean) {

    }

    /**
     * 数据响应失败方法
     *
     * @param throwable 数据失败信息
     */
    protected void responseFail(Throwable throwable) {

    }

    /**
     * 释放一些内存和对象的方法
     */
    protected abstract void destroy();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
        //释放内存
        presenter.detach();
        EventBus.getDefault().unregister(this);
        destroy();
    }


}
