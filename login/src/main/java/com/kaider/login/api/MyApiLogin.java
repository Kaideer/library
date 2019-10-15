package com.kaider.login.api;

import com.kaider.login.wxlogin.BaseWxLoginActivity;

/**
 * @author： KaiDer
 * @date： 2019/10/15 16:09
 * @description: 登录API存放类
 * @email： 2024468244@qq.com
 */
public class MyApiLogin {

    /**
     * 应用唯一标识，在微信开放平台提交应用审核通过后获得
     */
    public static String WXAPP_ID = "";

    /**
     * 应用密钥 AppSecret，在微信开放平台提交应用审核通过后获得
     */
    public static String WXSecret = "";

    /**
     * 通过 code 获取 access_token
     */
    public static String Access_Token = "sns/oauth2/access_token";

    /**
     * 获取refresh_token
     */
    public static String Refresh_Token = "sns/oauth2/refresh_token";

    /**
     * 获取用户信息的BaseUrl
     */
    public static String WxLoginGetAccessTokenBaseUrl = "https://api.weixin.qq.com/";

}
