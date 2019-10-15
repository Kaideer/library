package com.kaider.library.api;

/**
 * @Author： KaiDer
 * @Date： 2019/7/6 20:31
 * @Description: Base接口存放类
 * @Email： 2024468244@qq.com
 */
public class MyApi {

    /**
     * BaseUrl,网络环境
     */
    public static String Base_Url = "";

    /**
     * 初始化登录成功后需要存入在Sp的用户标识（UserID）的Key
     */
    public static String UserId = "";

    /**
     * 初始化在需要header入参时传入的用户标识（UserID）的入参字段，如果不是header入参则不需要
     */
    public static String UserIdTarget = "";

//    public static String Token = "";

}
