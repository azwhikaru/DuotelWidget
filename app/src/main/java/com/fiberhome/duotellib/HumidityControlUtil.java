package com.fiberhome.duotellib;

public class HumidityControlUtil {
    static {
        System.loadLibrary("humidity");
    }

    public native int getHumidity();

    public native int getTemperature();
}
