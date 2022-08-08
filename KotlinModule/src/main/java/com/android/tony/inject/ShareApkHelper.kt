package com.android.tony.inject

import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils
import android.util.Log
import android.util.Pair
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.util.HashMap

class ShareApkHelper {
    private val TAG = "libt.ShareApkHelper"
    val KEY_INVITE_TYPE = "invite_type"
    val KEY_BELYA_ID = "belya_id"
    val INVITE_REFERER_SOCIAL_SHARE = "share_link"

    private val SHARE_APK_NAME = "Play Play，It's nice to meet you.apk"
    private val SHARE_APK_TEMP_NAME = "PlayPlay-social-share-link.tmp"

    private val INJECT_APK_DIR = ""
    private val INJECT_APK_PATH = ""

    @Synchronized
    public fun syncInjectShareApkInfo(context: Context) {
        syncInjectShareApkInfo(context, INVITE_REFERER_SOCIAL_SHARE)
    }

    @Synchronized
    fun syncInjectShareApkInfo(context: Context, refer: String) {
        var refer = refer
        try {
            val start = System.currentTimeMillis()
            val app = context.applicationInfo
            val dstFile = getInjectShareApkFile()
            if (!dstFile.exists())
                return
            Log.d(TAG, "syncInjectShareApkInfo() dstFile=" + dstFile.name)
            val shareApkVersionCode = 0
            val currentVersionCode = getVersionCode(context)
            if (TextUtils.isEmpty(refer))
                refer = INVITE_REFERER_SOCIAL_SHARE
            if (dstFile.exists()) {
                if (shareApkVersionCode == currentVersionCode) {
                    Log.d(TAG, "syncInjectShareApkInfo() shareApkVersionCode == currentVersionCode")
                    val refers = ChannelReader.getMap(dstFile)
                    if (refers != null && !refers.isEmpty()) {
                        val olderRefer = refers[KEY_INVITE_TYPE]
                        if (refer == olderRefer) {
                            Log.d(TAG, "syncInjectShareApkInfo() refer.equals(olderRefer)")
                            return
                        } else
                            dstFile.delete()
                    }
                } else
                    dstFile.delete()
            }

            val tempFile = getShareApkTempFile()
            Log.d(TAG, "syncInjectShareApkInfo() tempFile=" + tempFile.name)
            if (tempFile.exists())
                tempFile.delete()
            val srcFile = File(app.sourceDir)
            copy(srcFile, tempFile)
            if (ApkUtil.hasV2SignBlock(tempFile)) {
                Log.d(TAG, "syncInjectShareApkInfo() ApkUtil.hasV2SignBlock=true")
                ChannelWriter.put(tempFile, null, generateReferSocialShare(context))
                tempFile.renameTo(dstFile)
                //                ShareSettings.setShareApkVersionCode(currentVersionCode);
                Log.d(TAG, "inject referer success : " + (System.currentTimeMillis() - start) + "      " + dstFile.absolutePath + "   " + dstFile.length() + "    " + dstFile.exists())
            } else {
                Log.d(TAG, "syncInjectShareApkInfo() ApkUtil.hasV2SignBlock=true, moveApkToSDCard")
                moveApkToSDCard(context)
            }
        } catch (e: Exception) {
            Log.w(TAG, "inject referer failed", e)
        }
    }

    private fun injectFileIsValid(context: Context, File: File?): Boolean {
        if (File == null || !File.exists() || File.length() == 0L)
            return false
        val shareApkVersionCode = 0
        val currentVersionCode = getVersionCode(context)
        return shareApkVersionCode == currentVersionCode
    }

    @Throws(Exception::class)
    fun getShareApkFile(context: Context): Pair<File, Boolean> {
        val shareApkFile = getInjectShareApkFile()
        if (injectFileIsValid(context, shareApkFile))
            return Pair.create(shareApkFile, true)

        moveApkToSDCard(context)
        return Pair.create(getNoInjectShareApkFile(), false)
    }

    @Throws(Exception::class)
    fun moveApkToSDCard(ctx: Context) {
        val app = ctx.applicationInfo
        val srcFile = File(app.sourceDir)
        val dstFile = getNoInjectShareApkFile() ?: return

        if (dstFile.exists() && srcFile.length() == dstFile.length())
            return

        if (dstFile.exists())
            dstFile.delete()

        val tempFile = getNoInjectShareApkTempFile()
        if (tempFile.exists())
            tempFile.delete()

        copy(srcFile, tempFile)
        if (tempFile.length() == srcFile.length())
            tempFile.renameTo(dstFile)
    }

    private fun addRefererSocialShare(context: Context, jo: JSONObject): String {
        try {
            jo.put(KEY_INVITE_TYPE, INVITE_REFERER_SOCIAL_SHARE)
            jo.put(KEY_BELYA_ID, getBeylaId(context))
        } catch (e: JSONException) {
        }

        return jo.toString()
    }

    fun generateReferSocialShare(context: Context): Map<String, String> {
        val refers = HashMap<String, String>()
        refers[KEY_INVITE_TYPE] = INVITE_REFERER_SOCIAL_SHARE
        refers[KEY_BELYA_ID] = getBeylaId(context)!!
        Log.d(TAG, "generateReferSocialShare() =$refers")
        return refers
    }

    private fun getInjectShareApkFile(): File {
        return File(INJECT_APK_DIR, SHARE_APK_NAME)
    }

    private fun getShareApkTempFile(): File {
        return File(INJECT_APK_DIR, SHARE_APK_TEMP_NAME)
    }

    private fun getNoInjectShareApkFile(): File {
        return File(getCacheShareApkDir(), SHARE_APK_NAME)
    }

    private fun getNoInjectShareApkTempFile(): File {
        return File(INJECT_APK_DIR, SHARE_APK_TEMP_NAME)
    }

    private fun getCacheShareApkDir(): File {
        val shareDir = File(INJECT_APK_DIR, "share/")
        if (!shareDir.exists())
            shareDir.mkdir()
        return shareDir
    }


    private fun getBeylaId(context: Context): String? {
        val id = android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID)
        return if (TextUtils.isEmpty(id) || TextUtils.isEmpty(id.trim { it <= ' ' })) null else id
    }

    fun getVersionCode(context: Context): Int {
        val pn = context.packageName
        val pm = context.packageManager
        try {
            return pm.getPackageInfo(pn, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            return 0
        }

    }

    @Throws(IOException::class)
    fun copy(srcFile: File?, dstFile: File) {
        if (srcFile == null)
            throw RuntimeException("source file is null.")
        if (!srcFile.exists())
            throw RuntimeException("source file[" + srcFile.absolutePath + "] is not exists.")

        var outFile: RandomAccessFile? = null
        var inFile: RandomAccessFile? = null
        try {
            outFile = RandomAccessFile(srcFile, "r")
            inFile = RandomAccessFile(dstFile, "rw")

            val buffer = ByteArray(1024 * 16)
            var bytesRead = outFile.read(buffer)
            while (bytesRead != -1) {
                inFile.write(buffer, 0, bytesRead)
                bytesRead = outFile.read(buffer)
            }
        } finally {
            outFile?.close()
            inFile?.close()
        }
    }
}