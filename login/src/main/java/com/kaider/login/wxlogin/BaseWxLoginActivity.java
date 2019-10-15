package com.kaider.login.wxlogin;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kaider.library.base.BaseActivity;
import com.kaider.login.api.MyApiLogin;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author： KaiDer
 * @date： 2019/10/15 16:10
 * @description:
 * @email： 2024468244@qq.com
 */
public abstract class BaseWxLoginActivity extends BaseActivity {

    private static IWXAPI iwxapi;

    @Override
    protected int initLayout() {
        return setLayout();
    }


    public void registerToWx() {

    }

    /**
     * @return
     */
    protected abstract int setLayout();

    @Override
    protected void initView() {
        setView();
    }

    public void createWxApi(Context context, String appId) {
        iwxapi = WXAPIFactory.createWXAPI(context, appId, true);
    }

    protected abstract void setView();

    @Override
    protected void preparingData() {
        //动态监听微信，注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                iwxapi.registerApp(MyApiLogin.WXAPP_ID);

                onReceiveWxResult(context, intent);

            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

        //接收返回数据
        SendAuth.Resp resp = new SendAuth.Resp();
        if (resp != null) {
            loginWxResp(resp);
        }


    }

    /**
     * 返回数据方法
     *
     * @param resp
     */
    protected abstract void loginWxResp(SendAuth.Resp resp);

    protected void onReceiveWxResult(Context context, Intent intent) {

    }

    /**
     * 发送登录请求到微信
     */
    public void sendReqAuth(@Nullable String scope, @Nullable String state) {

        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        //判断是否为空
        if (scope == null || scope.isEmpty()) {
            req.scope = "snsapi_userinfo";
        } else {
            req.scope = scope + "";
        }
        if (state == null || state.isEmpty()) {
            req.state = "";
        } else {
            req.state = state + "";
        }
        iwxapi.sendReq(req);

    }

    protected void registerWxAppId(String appId) {
        iwxapi.registerApp(appId);
    }

    @Override
    protected void destroy() {

    }
}

