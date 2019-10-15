package com.kaider.login.wxlogin;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kaider.library.api.MyApi;
import com.kaider.library.base.BaseActivity;
import com.kaider.library.sharedpreferences.SpStorage;
import com.kaider.library.toastutils.ToastUtil;
import com.kaider.login.api.MyApiLogin;
import com.kaider.login.bean.RequestAccessTokenBean;
import com.kaider.login.bean.RequestRefreshTokenBean;
import com.kaider.login.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author： KaiDer
 * @date： 2019/10/15 16:10
 * @description:
 * @email： 2024468244@qq.com
 */
public abstract class BaseWxLoginActivity extends BaseActivity implements IWXAPIEventHandler {

    private static IWXAPI iwxapi;

    @Override
    protected int initLayout() {
        return setLayout();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        SendAuth.Resp resp = (SendAuth.Resp) baseResp;
        if (resp != null) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //获取用户信息
                    callAccessToken(MyApiLogin.WXAPP_ID, MyApiLogin.WXSecret, resp.code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    Toast.makeText(this, "用户拒绝授权", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    Toast.makeText(this, "用户取消登录", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }

        }
    }

    /**
     * @return
     */
    protected abstract int setLayout();

    @Override
    protected void initView() {
        setView();
    }

    public void createWxApi(Context context) {
        iwxapi = WXAPIFactory.createWXAPI(context, MyApiLogin.WXAPP_ID, true);
        iwxapi.handleIntent(getIntent(), this);
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

    }

    protected void callAccessToken(String wxAppId, String wxSecret, String code) {
        //储存BaseURL
        boolean b = SpStorage.setStringValue(this, "baseUrl", "isHaveBaseUrl", MyApi.Base_Url + "");

        //值存起来之后替换BaseUrl
        MyApi.Base_Url = MyApiLogin.WxLoginGetAccessTokenBaseUrl;
        //判空处理
        if (wxAppId == null || wxAppId.isEmpty()) {
            ToastUtil.showShort(this, "操作失败，没有AppId");
            return;
        }
        if (wxSecret == null || wxSecret.isEmpty()) {
            ToastUtil.showShort(this, "操作失败，没有AppSecret");
            return;
        }
        if (code == null || code.isEmpty()) {
            ToastUtil.showShort(this, "操作失败，没有code码");
        }
        //调用接口
        requestWxAccessToken(wxAppId, wxSecret, code, "authorization_code");

    }

    /**
     * 调用接口
     *
     * @param wxAppId
     * @param wxSecret
     * @param code
     * @param authorization_code
     */
    private void requestWxAccessToken(String wxAppId, String wxSecret, String code, String authorization_code) {
        Map<String, String> paramMap = new HashMap<>(5);
        paramMap.put("appid", wxAppId);
        paramMap.put("secret", wxSecret);
        paramMap.put("code", code);
        paramMap.put("grant_type", authorization_code);
        String s = requestByPostParam(MyApiLogin.Access_Token, paramMap, new RequestAccessTokenBean());
        //当接口调用成功后,把BaseUrl还原
        String baseUrl = SpStorage.getStringValue(this, "baseUrl", "isHaveBaseUrl");
        MyApi.Base_Url = baseUrl;
        if (isNoNet(s)) {
            ToastUtil.showShort(this, "当前无网络连接 !");
        }
    }


    /**
     * 返回数据方法
     *
     * @param resp
     */
    protected abstract void loginWxResp(RequestRefreshTokenBean resp);

    protected void onReceiveWxResult(Context context, Intent intent) {

    }

    @Override
    protected void responseSuccess(Object bean) {
        super.responseSuccess(bean);
        if (bean instanceof RequestAccessTokenBean) {
            String refresh_token = ((RequestAccessTokenBean) bean).getRefresh_token();
            if (refresh_token != null && !refresh_token.isEmpty()) {
                ToastUtil.showShort(context, "操作失败，没有refresh_token");
                return;
            }
            //调用接口，获取refreshToken
            requestWxRefresh_token(MyApiLogin.WXAPP_ID, "refresh_token", refresh_token);
        } else if (bean instanceof RequestRefreshTokenBean) {
            ToastUtil.showShort(context, ((RequestRefreshTokenBean) bean).getNickname());
            loginWxResp((RequestRefreshTokenBean) bean);
        }
    }

    @Override
    protected void responseFail(Throwable throwable) {
        super.responseFail(throwable);
        ToastUtil.showShort(context, "登录失败 ！");
    }

    /**
     * 请求refresh_token
     *
     * @param wxAppId
     * @param refresh_token
     * @param grant_type
     */
    private void requestWxRefresh_token(String wxAppId, String grant_type, String refresh_token) {
        //把BaseURL储存起来
        boolean b = SpStorage.setStringValue(this, "baseUrl", "isHaveBaseUrl", MyApi.Base_Url);

        //值存起来之后替换BaseUrl
        MyApi.Base_Url = MyApiLogin.WxLoginGetAccessTokenBaseUrl;
        //判空处理
        if (wxAppId == null || wxAppId.isEmpty()) {
            ToastUtil.showShort(this, "操作失败，没有AppId");
            return;
        }
        if (grant_type == null || grant_type.isEmpty()) {
            ToastUtil.showShort(this, "操作失败，没有grant_type");
        }
        if (refresh_token == null || refresh_token.isEmpty()) {
            ToastUtil.showShort(this, "操作失败，没有Refresh_token");
            return;
        }

        //调用接口
        requestWxRefreshToken(wxAppId, grant_type, refresh_token);
    }

    private void requestWxRefreshToken(String wxAppId, String grant_type, String refresh_token) {
        Map<String, String> paramMap = new HashMap<>(5);
        paramMap.put("appid", wxAppId);
        paramMap.put("grant_type", grant_type);
        paramMap.put("refresh_token", refresh_token);
        String s = requestByPostParam(MyApiLogin.Refresh_Token, paramMap, new RequestRefreshTokenBean());
        //操作成功后把BaseUrl还原
        //当接口调用后,把BaseUrl还原
        String baseUrl = SpStorage.getStringValue(this, "baseUrl", "isHaveBaseUrl");
        MyApi.Base_Url = baseUrl;
        if (isNoNet(s)) {
            ToastUtil.showShort(context, "没有网络连接 ！");
        }
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

