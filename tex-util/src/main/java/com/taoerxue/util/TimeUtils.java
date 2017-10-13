package com.taoerxue.util;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

/**
 * Created by lizhihui on 2017-03-14 20:12.
 */
public class TimeUtils {


    public static final DateTime DATE_TIME = new DateTime();

    private TimeUtils() {
    }

    /**
     * @return 当天剩余的秒数
     */
    public static long theRestSecondsOfToday() {
        return theRestTimeOfToday(TimeUnit.SECONDS);
    }

    /**
     * @return 当天剩余的毫秒数
     */
    public static long theRestMillisOfToday() {
        return theRestTimeOfToday(TimeUnit.MILLISECONDS);
    }

    /**
     * @return 当天剩余的分钟数
     */
    public static long theRestMinutesOfToday() {
        return theRestTimeOfToday(TimeUnit.MINUTES);
    }

    /**
     * @return 当天剩余的小时数(一般没啥用)
     */
    public static long theRestHoursOfToday() {
        return theRestTimeOfToday(TimeUnit.HOURS);
    }


    /**
     * 当天剩余的的时间 ,只能传入 HOURS,MINUTES,SECONDS,MILLISECONDS这四个参数
     *
     * @param timeUnit only HOURS,MINUTES,SECONDS,MILLISECONDS can be params
     * @return long
     */

    private static long theRestTimeOfToday(TimeUnit timeUnit) {
        long restTime = 0;
        switch (timeUnit) {
            case HOURS:
                restTime = DATE_TIME.hourOfDay().getMaximumValue() - DATE_TIME.getHourOfDay();
                break;
            case MINUTES:
                restTime = DATE_TIME.minuteOfDay().getMaximumValue() - DATE_TIME.getMinuteOfDay();
                break;
            case SECONDS:
                restTime = DATE_TIME.secondOfDay().getMaximumValue() - DATE_TIME.getSecondOfDay();
                break;
            case MILLISECONDS:
                restTime = DATE_TIME.millisOfDay().getMaximumValue() - DATE_TIME.getMillisOfDay();
                break;
        }
        return restTime;
    }


}
