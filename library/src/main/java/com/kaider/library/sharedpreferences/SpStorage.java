package com.kaider.library.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.kaider.library.api.MyApi;

/**
 * @Author： KaiDer
 * @Date： 2019/7/7 19:29
 * @Description: Sp 储存工具类
 * @Email： 2024468244@qq.com
 */
public class SpStorage {

    /**
     * 存String值
     **/
    public static void setStringValue(Context context, String tableName, String key, String value) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(tableName, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(key, value);
            edit.apply();
        }
    }

    /**
     * 取String值
     */
    public static String getStringValue(Context context, String tableName, String key) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(tableName, Context.MODE_PRIVATE);
            String string = sharedPreferences.getString(key, "");
            return string;
        }
        return "";
    }

    /**
     * 取boolean值
     */
    public static boolean getBooleanValue(Context context, String tableName, String key) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(tableName, Context.MODE_PRIVATE);
            boolean aBoolean = sharedPreferences.getBoolean(key, false);
            return aBoolean;
        }
        return false;
    }

    /**
     * 存boolean值
     */
    public static void setBooleanValue(Context context, String tableName, String key, boolean value) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(tableName, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean(key, value);
            edit.apply();
        }
    }

    /**
     * 删除值的操作
     * 此方法以标记过时
     */
    @Deprecated
    public static void removeValue(Context context, String tableName, String key) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(tableName, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove(key);
            edit.apply();
        }
    }

    /**
     * 清空所有内容的方法
     * 此方法以标记过时
     */
    @Deprecated
    public static void clearValues(Context context, String tableName) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(tableName, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear();
            edit.apply();
        }
    }

    /**
     * 将用户信息的值赋值为空
     */
    public static void setUserInfoValueToEmpty(Context context, String tableName) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(tableName, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("userId", "");
            edit.putString("headPic", "");
            edit.putString("userPwd", "");
            edit.apply();
        }
    }

    /**
     * 取出Sp的用户信息判断是否为空，可作用于判断当前是否为登录状态
     */
    public static boolean getUserInfo(Context context) {
        if (context != null) {
            String userIdValue = SpStorage.getStringValue(context, "userInfo", MyApi.UserId);
            if (userIdValue != null) {
                if (!userIdValue.isEmpty()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

}
