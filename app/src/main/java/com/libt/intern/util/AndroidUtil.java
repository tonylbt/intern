package com.libt.intern.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;

import java.io.Closeable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AndroidUtil {
    // debug开关
    public static boolean DEBUG_ENABLE = true;

    public static int dp2px(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int px2dp(Context context, int px) {
        return (int) (px / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 获得缺省日期
     *
     * @param pattern 日期格式
     * @return 缺省日期
     */
    public static String formatUnixDate(String pattern, long time) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date(time);
        String now = format.format(date);

        return now;
    }

    /**
     * 可以出调用该方法的堆栈路径：<br/>
     * <br/>
     * 使用方法： <b>AndroidUtil.printStackTrace(Thread.currentThread().getStackTrace()); </b><br/>
     * <br/>
     * Ex : 登录成功后的通知：<br/>
     * StackTrace index [0] dalvik.system.VMStack.getThreadStackTrace(Native Method)<br/>
     * StackTrace index [1] java.lang.Thread.getStackTrace(Thread.java:579)<br/>
     * StackTrace index [2] com.android.tony.activity.LoginActivity.showHint(LoginActivity.java:868)<br/>
     * StackTrace index [3] com.android.tony.activity.LoginActivity.onLoginCallback(LoginActivity.java:714)<br/>
     * StackTrace index [4] com.android.tony.activity.LoginActivity.access$21(LoginActivity.java:705)<br/>
     * StackTrace index [5] com.android.tony.activity.LoginActivity$20.onReceive(LoginActivity.java:1057)<br/>
     *
     * @param elements 此处必须传入参数 Thread.currentThread().getStackTrace()
     * @author libingtao 2014-05-22
     */
    public static void printStackTrace() {
        if (!DEBUG_ENABLE) {
            return;
        }
        try {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (elements == null || elements.length == 0) {
                return;
            }

            StackTraceElement element;
            String fileName;
            StringBuilder builder = new StringBuilder();
            int idx = 0;//
            for (; idx < elements.length; idx++) {
                element = elements[idx];
                fileName = element.getClassName();
                if (TextUtils.isEmpty(fileName) /* || !fileName.startsWith("com.android.tony") */) {
                    continue;
                }
                builder.append("StackTrace index [" + idx + "] ");
                builder.append(element.toString());
                builder.append("\n");
            }
            Log.d("lbingt", builder.toString());
        } catch (Throwable throwable) {
            Log.d("lbingt", "printStackTrace error : " + throwable.toString());
        }
    }

    public static StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    /**
     * 获取Context所在进程的名称
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 根据进程名称获取进程Id
     *
     * @param processName
     * @return
     */
    public static int getProcessPid(Context context, String processName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> procList = null;
        int result = -1;
        procList = activityManager.getRunningAppProcesses();
        for (Iterator<RunningAppProcessInfo> iterator = procList.iterator(); iterator.hasNext(); ) {
            RunningAppProcessInfo procInfo = iterator.next();
            if (procInfo.processName.equals(processName)) {
                result = procInfo.pid;
                break;
            }
        }
        return result;
    }

    /**
     * 检查系统应用程序，并打开应用程序
     */
    public static void openApp(Context context, String pkgName) {
        // 应用过滤条件
        Intent query = new Intent(Intent.ACTION_MAIN, null);
        query.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> mAllApps = packageManager.queryIntentActivities(query, 0);

        String pkg, cls;
        ComponentName component;
        for (ResolveInfo res : mAllApps) {
            // 该应用的包名和主Activity
            pkg = res.activityInfo.packageName;

            // 打开应用
            if (pkg.equals(pkgName)) {
                cls = res.activityInfo.name;
                component = new ComponentName(pkg, cls);
                Intent intent = new Intent();
                intent.setComponent(component);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            }
        }
    }

    /**
     * close object quietly, catch and ignore all exceptions.
     * @param object the closeable object like inputstream, outputstream, reader, writer, randomaccessfile.
     */
    public static void close(Closeable object) {
        if (object != null) {
            try {
                object.close();
            } catch (Exception e) {}
        }
    }

    private void printJavaInfo() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        StackTraceElement element;
        String name;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stes.length; i++) {
            element = stes[i];

            builder.append("ClassName [" + element.getClassName() + "]");
            builder.append("; MethodName [" + element.getMethodName() + "]");
            builder.append("; FileName [" + element.getFileName() + "]");
            name = element.getClassName();
            Log.i("", "element [" + i + "] : " + builder.toString());
            builder.delete(0, builder.length() - 1);
            if (TextUtils.isEmpty(name) || !name.startsWith("com.android.gitcommand")) {
                continue;
            }

            Log.i("libt.AndroidUtil", "printJavaInfo element [" + i + "] : " + element.toString());
        }
        Log.i("libt.AndroidUtil", "generateTag : " + generateTag(stes[4]));
    }

    private String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }
}
