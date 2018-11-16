package com.tanker.base.util;

import android.annotation.SuppressLint;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Tanker
 * @email :zhoukai@zto.cn
 * @date : 2018/11/16
 * @describe : 时间工具类
 */
public class DateUtils {

    protected static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    protected static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static String dataToStr(Date date){
        return getDateFormat(YYYY_MM_DD_HH_MM_SS).format(date);
    }

    public static Date strToData(String date){
        return getDateFormat(YYYY_MM_DD).parse(date,new ParsePosition(0));
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    protected static Date getTimeAsDate() {
        return new Date();
    }

    protected static long getNowTimeAslong() {
        return System.currentTimeMillis();
    }

    @SuppressLint("SimpleDateFormat")
    protected static SimpleDateFormat getDateFormat(String timePattern){
        return new SimpleDateFormat(timePattern);
    }

}