package com.kaider.im.app;

import android.app.Application;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.im.android.api.JMessageClient;

/**
 * @author： KaiDer
 * @date： 2019/10/15 15:34
 * @description: Im的Application
 * @email： 2024468244@qq.com
 */
public abstract class BaseImApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //在极光IM之前集成一些东西
        initJgImBefore();
        //开启debug
        JMessageClient.setDebugMode(true);
        //初始化，并开启消息缓存
        JMessageClient.init(this, true);

        //开启debug
        JAnalyticsInterface.setDebugMode(true);
        //开启初始化
        JAnalyticsInterface.init(this);
        //开启缓存
        JAnalyticsInterface.initCrashHandler(this);

        //在极光IM之后集成一些东西
        initJgImAfter();

    }

    protected void initJgImAfter() {

    }

    protected void initJgImBefore() {

    }
}
