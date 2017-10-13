package com.taoerxue.util;

import java.text.DecimalFormat;

/**
 * Created by lizhihui on 2017-03-23 12:23.
 */
public class LocationUtils {

    private static double EARTH_RADIUS = 6378.138;

    private LocationUtils() {

    }

    public static double calculateDistance(double lng1, double lat1, double lng2, double lat2) {
        return Math.round(
                EARTH_RADIUS * 2 * Math.asin(
                        Math.sqrt(
                                Math.pow(Math.sin((lat1 * Math.PI / 180 - lat2 * Math.PI / 180) / 2), 2)
                                        +
                                        Math.cos(lat1 * Math.PI / 180)
                                                *
                                                Math.cos(lat2 * Math.PI / 180)
                                                *
                                                Math.pow(Math.sin((lng1 * Math.PI / 180 - lng2 * Math.PI / 180) / 2), 2)
                        )
                ) * 1000
        );

    }
    public static String calculateDistance(double distance) {
        StringBuilder stringBuilder = new StringBuilder();
        if (distance<1000)
            return stringBuilder.append(distance).append("米").toString();
        else
            return stringBuilder.append(new DecimalFormat("#.0").format(distance/1000)).append("千米").toString();

    }
}
