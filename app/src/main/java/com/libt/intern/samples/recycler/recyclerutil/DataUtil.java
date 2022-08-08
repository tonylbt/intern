package com.libt.intern.samples.recycler.recyclerutil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lbingt on 2017/2/24.
 */

public class DataUtil {
    public static final String TIME_FORMATTER_YMDHMS = "yyyy年MM月dd日HH时";
	public static String formatTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMATTER_YMDHMS);// HH:mm:ss
		Date date = new Date(System.currentTimeMillis());
		return simpleDateFormat.format(date);
	}
}
