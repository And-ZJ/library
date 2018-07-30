package com.andzj.library.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by zj on 2016/8/25.
 */
public class MyTimeUtils {
    private static String DateFormatStr = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormatStr, Locale.getDefault());

    public static String getMyTimeStr(Date date) {
        return dateFormat.format(date);
    }

    public static String getMyTimeStr(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month-1, day);
        return getMyTimeStr(calendar);
    }

    public static String getMyTimeStr(GregorianCalendar calendar) {
        Date date = calendar.getTime();
        return getMyTimeStr(date);
    }

    public static GregorianCalendar getCalendar(String formatStr)
    {
        int i = formatStr.indexOf("-");
        String yearStr = formatStr.substring(0,i);

        formatStr = formatStr.substring(i+1,formatStr.length());
        i = formatStr.indexOf("-");
        String monthStr = formatStr.substring(0,i);

        formatStr = formatStr.substring(i+1,formatStr.length());
        i = formatStr.indexOf(" ");
        String dayStr = formatStr.substring(0,i);

        formatStr = formatStr.substring(i+1,formatStr.length());
        i = formatStr.indexOf(":");
        String hourStr = formatStr.substring(0,i);

        formatStr = formatStr.substring(i+1,formatStr.length());
        i = formatStr.indexOf(":");
        String minuteStr = formatStr.substring(0,i);

        formatStr = formatStr.substring(i+1,formatStr.length());
        String secondStr = formatStr;

        return new GregorianCalendar(Integer.parseInt(yearStr),Integer.parseInt(monthStr)-1,Integer.parseInt(dayStr),
                Integer.parseInt(hourStr),Integer.parseInt(minuteStr),Integer.parseInt(secondStr));
    }

    public static Date getDate(String formatStr)
    {
        return getCalendar(formatStr).getTime();
    }
}
