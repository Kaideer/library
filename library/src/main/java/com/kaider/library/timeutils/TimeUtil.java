package com.kaider.library.timeutils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author： KaiDer
 * @Date： 2019/8/3 9:57
 * @Description: 事件工具类
 * @Email： 2024468244@qq.com
 */
public class TimeUtil {
    public static String formatTime(String timeMillis) {
        long timeMillisl = Long.parseLong(timeMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillisl);
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间转换为上午下午格式
     *
     * @param timeMillis
     * @return
     */
    public static String formatTimeNoon(String timeMillis) {
        long timeMillisl = Long.parseLong(timeMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd  a HH:mm:ss");
        Date date = new Date(timeMillisl);
        return simpleDateFormat.format(date);
    }

}
