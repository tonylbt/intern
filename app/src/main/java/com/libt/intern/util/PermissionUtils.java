package com.libt.intern.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class PermissionUtils {

    public static boolean hasStoragePermission(Context context) {
        if (context != null) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null && applicationInfo.targetSdkVersion >= 30 && Build.VERSION.SDK_INT >= 30) {
                return Environment.isExternalStorageManager();
            }
        }

        return hasPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static boolean hasPermission(@Nullable Context context, @NonNull String permission) {
        try {
            return isBeforeM() || context != null && ActivityCompat.checkSelfPermission(context, permission) == 0;
        } catch (Throwable var3) {
            return false;
        }
    }

    private static boolean isBeforeM() {
        return Build.VERSION.SDK_INT < 23;
    }

    public static void requestPermission(FragmentActivity activity, int requestCode) {
        if (hasStoragePermission(activity)) {
            return;
        }

        String[] permsToRequest = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"};

        try {
            ActivityCompat.requestPermissions(activity, permsToRequest, requestCode);
        } catch (ActivityNotFoundException var7) {
            Logger.e("PermissionsUtils", "request permissions", var7);
        }
    }

}
