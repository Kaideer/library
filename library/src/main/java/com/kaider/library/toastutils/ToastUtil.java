package com.kaider.library.toastutils;

import android.content.Context;
import android.widget.Toast;

/**
 * @Author： KaiDer
 * @Date： 2019/7/14 21:10
 * @Description: 吐司工具类
 * @Email： 2024468244@qq.com
 */
public class ToastUtil {
    /**
     * 短吐司方法
     */
    public static void showShort(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长吐司方法
     */
    public static void showLong(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
