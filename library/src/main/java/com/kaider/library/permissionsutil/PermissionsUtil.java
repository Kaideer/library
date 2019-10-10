package com.kaider.library.permissionsutil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author： KaiDer
 * @Date： 2019/7/31 20:24
 * @Description: 权限工具类
 * //权限数组，比如：两个读写权限和一个网络权限
 * //    String[] permissions = new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_EXTERNAL_STORAGE};
 * <p>
 * //    //创建监听权限的接口对象
 * //    PermissionsUtil.IPermissionsResult permissionsResult = new PermissionsUtil.IPermissionsResult() {
 * //        @Override
 * //        public void passPermissons() {
 * //            Toast.makeText(MainActivity.this, "权限通过，可以做其他事情!", Toast.LENGTH_SHORT).show();
 * //        }
 * //
 * //        @Override
 * //        public void forbitPermissons() {
 * //            Toast.makeText(MainActivity.this, "权限不通过!", Toast.LENGTH_SHORT).show();
 * //            finish();
 * //        }
 * //    };
 * @Email： 2024468244@qq.com
 */
public class PermissionsUtil {

    //权限请求码
    private final int mRequestCode = 100;
    public static boolean showSystemSetting = true;
    public static final int androidSdk = 23;

    private PermissionsUtil() {
    }

    private static PermissionsUtil PermissionsUtil;
    private IPermissionsResult mPermissionsResult;

    public static PermissionsUtil getInstance() {
        if (PermissionsUtil == null) {
            PermissionsUtil = new PermissionsUtil();
        }
        return PermissionsUtil;
    }

    public void checkPermissions(Activity context, String[] permissions, @NonNull IPermissionsResult permissionsResult) {
        mPermissionsResult = permissionsResult;

        //6.0才用动态权限
        if (Build.VERSION.SDK_INT < androidSdk) {
            permissionsResult.passPermissons();
            return;
        }

        //创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPermissionsList中
        List<String> mPermissionList = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                //添加还未授予的权限
                mPermissionList.add(permissions[i]);
            }
        }

        //申请权限
        //有权限没有通过，需要申请
        if (mPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(context, permissions, mRequestCode);
        } else {
            //说明权限都已经通过，可以做你想做的事情去
            permissionsResult.passPermissons();
            return;
        }


    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限

    public void onRequestPermissionsResult(Activity context, int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //有权限没有通过
        boolean hasPermissionDismiss = false;
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                if (showSystemSetting) {
                    //跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                    showSystemPermissionsSettingDialog(context);
                } else {
                    mPermissionsResult.forbitPermissons();
                }
            } else {
                //全部权限通过，可以进行下一步操作。。。
                mPermissionsResult.passPermissons();
            }
        }

    }


    /**
     * 不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;

    private void showSystemPermissionsSettingDialog(final Activity context) {
        final String mPackName = context.getPackageName();
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(context)
                    .setMessage("已禁用权限，请手动授予")
                    .setCancelable(false)
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                            //mContext.finish();
                            mPermissionsResult.forbitPermissons();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    /**
     * 关闭对话框
     */
    private void cancelPermissionDialog() {
        if (mPermissionDialog != null) {
            mPermissionDialog.cancel();
            mPermissionDialog = null;
        }

    }


    public interface IPermissionsResult {
        void passPermissons();

        void forbitPermissons();
    }

}
