package com.kaider.mymvplibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kaider.library.base.BaseActivity;
import com.kaider.library.toastutils.ToastUtil;

public class MainActivity extends BaseActivity {


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected void initView() {
        ToastUtil.showLong(context, "测试library");
    }

    @Override
    protected void preparingData() {

    }

    @Override
    protected void destroy() {

    }
}
