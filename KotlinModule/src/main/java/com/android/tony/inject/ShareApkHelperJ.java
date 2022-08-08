package com.android.tony.inject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class ShareApkHelperJ {
    private static final String TAG = "libt.ShareApkHelper";
    public static final String KEY_INVITE_TYPE = "invite_type";
    public static final String KEY_BELYA_ID = "belya_id";
    public static final String INVITE_REFERER_SOCIAL_SHARE = "share_link";

    private static final String SHARE_APK_NAME = "Play Play，It's nice to meet you.apk";
    private static final String SHARE_APK_TEMP_NAME = "PlayPlay-social-share-link.tmp";

    private static final String INJECT_APK_DIR = "";
    private static final String INJECT_APK_PATH = "";

    public static synchronized void syncInjectShareApkInfo(Context context) {
        syncInjectShareApkInfo(context, INVITE_REFERER_SOCIAL_SHARE);
    }

    public static synchronized void syncInjectShareApkInfo(Context context, String refer) {
        try {
            long start = System.currentTimeMillis();
            ApplicationInfo app = context.getApplicationInfo();
            File dstFile = getInjectShareApkFile();
            if (!dstFile.exists())
                return;
            Log.d(TAG, "syncInjectShareApkInfo() dstFile=" + dstFile.getName());
            int shareApkVersionCode = 0;
            int currentVersionCode = getVersionCode(context);
            if (TextUtils.isEmpty(refer))
                refer = INVITE_REFERER_SOCIAL_SHARE;
            if (dstFile.exists()) {
                if (shareApkVersionCode == currentVersionCode) {
                    Log.d(TAG, "syncInjectShareApkInfo() shareApkVersionCode == currentVersionCode");
                    Map<String, String> refers = ChannelReader.getMap(dstFile);
                    if (refers != null && !refers.isEmpty()) {
                        String olderRefer = refers.get(KEY_INVITE_TYPE);
                        if (refer.equals(olderRefer)) {
                            Log.d(TAG, "syncInjectShareApkInfo() refer.equals(olderRefer)");
                            return;
                        } else
                            dstFile.delete();
                    }
                } else
                    dstFile.delete();
            }

            File tempFile = getShareApkTempFile();
            Log.d(TAG, "syncInjectShareApkInfo() tempFile=" + tempFile.getName());
            if (tempFile.exists())
                tempFile.delete();
            File srcFile = new File(app.sourceDir);
            copy(srcFile, tempFile);
            if (ApkUtil.hasV2SignBlock(tempFile)) {
                Log.d(TAG, "syncInjectShareApkInfo() ApkUtil.hasV2SignBlock=true");
                ChannelWriter.put(tempFile, null, generateReferSocialShare(context));
                tempFile.renameTo(dstFile);
//                ShareSettings.setShareApkVersionCode(currentVersionCode);
                Log.d(TAG, "inject referer success : " + (System.currentTimeMillis() - start) + "      " + dstFile.getAbsolutePath() + "   " + dstFile.length() + "    " + dstFile.exists());
            } else {
                Log.d(TAG, "syncInjectShareApkInfo() ApkUtil.hasV2SignBlock=true, moveApkToSDCard");
                moveApkToSDCard(context);
            }
        } catch (Exception e) {
            Log.w(TAG, "inject referer failed", e);
        }
    }

    private static boolean injectFileIsValid(Context context, File File) {
        if (File == null || !File.exists() || File.length() == 0)
            return false;
        int shareApkVersionCode = 0;
        int currentVersionCode = getVersionCode(context);
        return shareApkVersionCode == currentVersionCode;
    }

    public static Pair<File, Boolean> getSHAREitAPKFile(Context context) throws Exception {
        File shareApkFile = getInjectShareApkFile();
        if (injectFileIsValid(context, shareApkFile))
            return Pair.create(shareApkFile, true);

        moveApkToSDCard(context);
        return Pair.create(getNoInjectShareApkFile(), false);
    }

    public static void moveApkToSDCard(final Context ctx) throws Exception {
        ApplicationInfo app = ctx.getApplicationInfo();
        File srcFile = new File(app.sourceDir);
        File dstFile = getNoInjectShareApkFile();
        if (dstFile == null)
            return;

        if (dstFile.exists() && srcFile.length() == dstFile.length())
            return;

        if (dstFile.exists())
            dstFile.delete();

        File tempFile = getNoInjectShareApkTempFile();
        if (tempFile.exists())
            tempFile.delete();

        copy(srcFile, tempFile);
        if (tempFile.length() == srcFile.length())
            tempFile.renameTo(dstFile);
    }

    private static String addRefererSocialShare(Context context, JSONObject jo) {
        try {
            jo.put(KEY_INVITE_TYPE, INVITE_REFERER_SOCIAL_SHARE);
            jo.put(KEY_BELYA_ID, getBeylaId(context));
        } catch (JSONException e) {
        }
        return jo.toString();
    }

    public static Map<String, String> generateReferSocialShare(Context context) {
        Map<String, String> refers = new HashMap<>();
        refers.put(KEY_INVITE_TYPE, INVITE_REFERER_SOCIAL_SHARE);
        refers.put(KEY_BELYA_ID, getBeylaId(context));
        Log.d(TAG, "generateReferSocialShare() =" + refers.toString());
        return refers;
    }

    private static File getInjectShareApkFile() {
        return new File(INJECT_APK_DIR, SHARE_APK_NAME);
    }

    private static File getShareApkTempFile() {
        return new File(INJECT_APK_DIR, SHARE_APK_TEMP_NAME);
    }

    private static File getNoInjectShareApkFile() {
        return new File(getCacheShareApkDir(), SHARE_APK_NAME);
    }

    private static File getNoInjectShareApkTempFile() {
        return new File(INJECT_APK_DIR, SHARE_APK_TEMP_NAME);
    }

    private static File getCacheShareApkDir() {
        File shareDir = new File(INJECT_APK_DIR, "share/");
        if (!shareDir.exists())
            shareDir.mkdir();
        return shareDir;
    }


    private static String getBeylaId(Context context) {
        String id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(id.trim()))
            return null;
        return id;
    }

    public static int getVersionCode(Context context) {
        String pn = context.getPackageName();
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(pn, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static void copy(File srcFile, File dstFile) throws IOException {
        if (srcFile == null)
            throw new RuntimeException("source file is null.");
        if (!srcFile.exists())
            throw new RuntimeException("source file[" + srcFile.getAbsolutePath() + "] is not exists.");

        RandomAccessFile outFile = null;
        RandomAccessFile inFile = null;
        try {
            outFile = new RandomAccessFile(srcFile, "r");
            inFile = new RandomAccessFile(dstFile, "rw");

            byte[] buffer = new byte[1024 * 16];
            int bytesRead;
            while ((bytesRead = outFile.read(buffer)) != -1)
                inFile.write(buffer, 0, bytesRead);
        } finally {
            if (outFile != null)
                outFile.close();
            if (inFile != null)
                inFile.close();
        }
    }
}
