package com.libt.intern.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lbingt on 2017/2/24.
 */

public class DataUtils {
    public static final String TIME_FORMATTER_YMDHMS = "yyyy/MM/dd HH:mm:ss";


    public static String formatTime(String formatter, long time) {
        SimpleDateFormat format = new SimpleDateFormat(formatter, Locale.getDefault());
        Date date = new Date(time);
        return format.format(date);
    }
}
