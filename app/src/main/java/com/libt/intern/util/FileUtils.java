package com.libt.intern.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lbingt on 2016/7/7.
 */
public class FileUtils {

    public static void copyDatabaseOut(Context context, String dbName) {
        String outPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ShareDemo/" + dbName;
        String dataDir = context.getApplicationInfo().dataDir;
        String absolutePath;
        try {
            if(dataDir.endsWith("/")) {
                absolutePath = dataDir + "databases/" + dbName;
            } else {
                absolutePath = dataDir + "/" + "databases/" + dbName;
            }

            FileInputStream is = new FileInputStream(absolutePath);
            FileOutputStream fos = new FileOutputStream(outPath, false);
            byte[] buffer = new byte[is.available()];
            fos.write(buffer);
            fos.flush();
            fos.close();
            is.close();
        } catch (FileNotFoundException var10) {
            Log.e("libt", var10.getMessage());
        } catch (IOException var11) {
            Log.e("libt", var11.getMessage());
        }
    }

    public static JSONArray loadJSONArrayFromAssets(Context context, String fileName) {
        String content = readAssetsFile(context, fileName);
        JSONArray jsonArray = new JSONArray();
        if (TextUtils.isEmpty(content)) {
            return jsonArray;
        }

        try {
            jsonArray = new JSONArray(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static String readAssetsFile(Context context, String fileName) {
        StringBuffer buffer = new StringBuffer("");
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                if(!line.startsWith("#//#"))
                    buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
