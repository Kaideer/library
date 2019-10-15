package com.kaider.login.wxapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.kaider.login.R;
import com.kaider.login.bean.RequestRefreshTokenBean;
import com.kaider.login.wxlogin.BaseWxLoginActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * @author KaiDer
 * @desc 微信登录
 */
public class WXEntryActivity extends BaseWxLoginActivity {

    @Override
    protected int setLayout() {
        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_wxentry;
    }

    @Override
    protected void setView() {
        createWxApi(context);
        sendReqAuth("snsapi_userinfo", "wechat_sdk_demo_test");
    }

    @Override
    protected void loginWxResp(RequestRefreshTokenBean resp) {

    }
}
