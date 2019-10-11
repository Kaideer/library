package com.kaider.library.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
 * @Date： 2019/7/7 20:07
 * @Description: Fragment基类
 * @Email： 2024468244@qq.com
 */
public abstract class BaseFragment extends Fragment implements Contract.IView {

    private Presenter presenter;
    private static final String NORMAL = "0000";
    private static final String NOLOGIN = "2001";
    private static final String NONET = "2002";
    /**
     * 定义一个存放所有Fragment的集合
     */
    public static List<Fragment> fragments;
    //    private AlertDialog dialog;
    public static Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initLayout(), null);

        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //设置适应状态栏字体颜色
//        StatusBarUtil.setStatusBarColor(getActivity(), R.color.black);
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);

        initView(view);

        context = getActivity();

        addFragment(this);

        //判断是否注册EventBus
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
//
//        //初始化加载框
//        //初始化AlertDialog
//        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//        dialog = alert.create();
//        //加载布局
//        View loading = LayoutInflater.from(getContext()).inflate(R.layout.loading, null);
//        //设置点击外部不可取消
//        dialog.setView(loading);
//        dialog.setCanceledOnTouchOutside(false);


        //获取实例
        presenter = new Presenter();
        presenter.attach(this);

        return view;
    }

    /**
     * 用于决定是否开启EventBus
     *
     * @return
     */
    public boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 往集合中添加Activity
     *
     * @param fragment:要添加的Fragment
     */
    public static void addFragment(Fragment fragment) {
        if (fragments == null) {
            //如果集合为空  则初始化
            fragments = new ArrayList<>();
        }
        //将activity加入到集合中
        fragments.add(fragment);
    }

    /**
     * 结束掉所有的Fragment
     */
    public static void finishAllFragment() {
        //循环集合,  将所有的Fragment删除
        for (Fragment fragment : fragments) {
            if (!fragment.isDetached()) {
                fragment.onDestroy();
            }
        }
    }

    /**
     * 获取当前Fragment的实例
     *
     * @param fragment:fragment
     * @return
     */
    public static Fragment getCurrencyFragmentInstance(Fragment fragment) {
        if (fragment != null) {
            for (int i = 0; i < fragments.size(); i++) {
                if (fragments.get(i) == fragment) {
                    return fragments.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 删除某个Fragment
     *
     * @param fragment
     */
    public static void removeFragment(Fragment fragment) {
        //移除已经销毁的Activity
        fragments.remove(fragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        preparingData();

        initListener();

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
    public void onStart() {
        super.onStart();
        try {
            reLoadData();
        } catch (Exception e) {
            ToastUtil.showShort(getContext(), "数据错误 ！");
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        pagePause();
    }

    @Override
    public void onStop() {
        super.onStop();
        pageStop();
    }


    /**
     * Get请求，不做任何入参
     */
    public String requestByGet(String url, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByGetHeader(getActivity(), url, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByGetParamAndHeader(getActivity(), url, queryMap, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            presenter.mutualByPostParam(url, queryMap, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByPostHeader(getActivity(), url, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，Param入参和Field入参
     */
    public String requestByPostParamAndField(String url, Map<String, String> paramMap, Map<String, String> fieldMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            presenter.mutualByPostParamAndField(url, paramMap, fieldMap, bean);
            return NORMAL;
        } else {
            return NONET;
        }
    }

    /**
     * Post请求，Field入参，Header入参
     */
    public String requestByPostFieldAndHeader(String url, Map<String, String> fieldMap, Object bean) {
        //获取网络连接
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByPostFieldAndHeader(getActivity(), url, fieldMap, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByPostParamAndHeader(getActivity(), url, queryMap, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByPostFileAndHeader(getActivity(), url, key, file, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByPutParamAndHeader(getActivity(), url, queryMap, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByPutFieldAndHeader(getActivity(), url, fieldMap, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByPostBodyAndHeader(getActivity(), url, json, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByPostFileAndParamAndHeader(getActivity(), url, key, files, paramMap, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByDeleteParamAndHeader(getActivity(), url, paramMap, bean);
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
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
        boolean networkConnected = NetConnections.isNetworkConnected(getActivity());
        if (networkConnected) {
            boolean isUserInfo = SpStorage.getUserInfo(getActivity());
            if (isUserInfo) {
                presenter.mutualByPostFileParamAndHeader(getActivity(), url, key, file, paramMap, bean);
                return NORMAL;
            } else {
                return NOLOGIN;
            }
        } else {
            return NONET;
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detach();
        EventBus.getDefault().unregister(this);
        destroy();
        removeFragment(this);
    }

    /**
     * 初始化布局方法
     *
     * @return 返回布局ID
     */
    protected abstract int initLayout();

    /**
     * 初始化控件方法
     *
     * @param view
     */
    protected abstract void initView(View view);

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

    ;

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

}
