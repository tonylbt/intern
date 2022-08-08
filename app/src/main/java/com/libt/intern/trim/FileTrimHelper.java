package com.libt.intern.trim;

import android.text.TextUtils;

import com.libt.intern.util.DataUtils;
import com.libt.intern.util.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTrimHelper {
    private static final String TAG = "TrimHelper";

    public static void trimTextFile(File file) {
        if (file == null || !file.exists())
            return;

        String line = null;
        BufferedReader reader = null;
        FileAppender fileAppender = null;
        try {
            long fileLength = file.length();
            File destFile = new File(genNewFileName(file));
            fileAppender = new FileAppender(destFile);

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)), (int) fileLength);
            String temp = "";
            while ((line = reader.readLine()) != null) {
//                temp = line.trim();
//                if (TextUtils.isEmpty(temp))
//                    continue;

                matchContent(line);
                temp = startTrim(line);
                if (TextUtils.isEmpty(temp))
                    continue;

                fileAppender.writeToFile(temp + "\n");
                Logger.d(TAG, "readFromFile line : " + temp);
            }
        } catch (IOException e) {
            Logger.d(TAG, "readFromFile IOException:", e);
        } catch (Exception e) {
            Logger.d(TAG, "readFromFile ", e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fileAppender != null) {
                    fileAppender.close();
                }
            } catch (Throwable var2) {
                Logger.d(TAG, "readFromFile IOException:", var2);
            }
        }
    }

    public static String genNewFileName(File file) {
        String newFileName;
        String oldName = file.getAbsolutePath();
        int idx = oldName.indexOf('.');
        newFileName = oldName.substring(0, idx);
        String suffix = oldName.substring(idx);
        newFileName += "_" + DataUtils.formatTime("yyMMddHHmm", System.currentTimeMillis());
        newFileName += suffix;
        return newFileName;
    }

    private static String startTrim(String source) {
        String result = source;
        String lastResult = "";
        while (result.indexOf('<') > 0 && result.indexOf('>') > 0 && !TextUtils.equals(lastResult, result)) {
            lastResult = result;
            result = trimContent(source);
        }
        return result;
    }

    private static String trimContent(String source) {
        String dest = source;
        char annotationStart = '<';
        char annotationEnd = '>';

        int startA = source.indexOf(annotationStart);
        int endA = source.indexOf(annotationEnd);
        if (startA < 0 || endA < 0)
            return source;
        String ann = source.substring(startA + 1, endA + 1);

        int startB = source.indexOf(annotationStart, endA + 1);
        int endB = source.indexOf(annotationEnd, endA + 1);
        if (startB < 0 || endB < 0)
            return source;
        String annB = source.substring(startB + 1, endB + 1);

        if (TextUtils.equals("/" + ann, annB)) {
            dest = source.substring(0, startA) + source.substring(endB + 1, source.length() - 1);
        }

        return dest;
    }

    private static String trimContent2(String source) {
        String dest = source;
        char annotationStart = '<';
        char annotationEnd = '>';

        int startA = source.indexOf(annotationStart);
        int endA = source.indexOf(annotationEnd);
        if (startA < 0 || endA < 0)
            return source;
        String ann = source.substring(startA, endA);

        int startB = source.indexOf(annotationStart, endA + 1);
        int endB = source.indexOf(annotationEnd, endA + 1);
        if (startB < 0 || endB < 0)
            return source;
        String annB = source.substring(startB, endB);

        if (TextUtils.equals("/" + ann, annB)) {
            dest = source.substring(0, startA) + source.substring(endB, source.length() - 1);
        }

        dest.matches("");
        return dest;
    }

    public static void matchContent(String source) {
        if (TextUtils.isEmpty(source.trim()))
            return;

        String matches = "<[^>]+>";
        matches = "<[dfn]\\s*[^>]*>(.*?)</[dfn]>";//匹配<a></a> 取出中间的文字
//        matches = "<a style=\"color\\s*[^>]*>(.*?)</a>";//匹配<a></a> 替换为空

        Pattern pattern = Pattern.compile(matches, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);
//        matcher.replaceAll("");
        while (matcher.find()) {
            Logger.d(TAG + ".matchContent", "matcher.group()=%s, matcher.toString()=%s, matcher.toMatchResult().toString()=%s", matcher.group(), matcher.toString(), matcher.toMatchResult().toString());
        }

    }
}
