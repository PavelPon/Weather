package com.example.weather.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String APP_ID = "a8986919fc6f80b4f507ffb8d0339c09";
    public static Location current_location = null;

    public static String convertUnixToData(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH: mm dd EEE MM yyyy");
        String formatted = simpleDateFormat.format(date);
        return formatted;
    }

    public static String convertUnixToHour(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH: mm ");
        String formatted = simpleDateFormat.format(date);
        return formatted;
    }
}
