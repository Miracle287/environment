package en.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    // 获取当前时间
    public static long getTimel() {
        return (new Date()).getTime();
    }

    // 当前日期
    public static Date getCurrentDate() {
        return new Date();
    }

    // 时间转换
    public static String getChangeTime(long timel) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return timeFormat.format(timel);
    }

    // 时间转换2
    public static String getChangeTime2(long timel) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        return timeFormat.format(timel);
    }

    // 时间转换3
    public static String getChangeTime3(long timel) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd");
        return timeFormat.format(timel);
    }
}
