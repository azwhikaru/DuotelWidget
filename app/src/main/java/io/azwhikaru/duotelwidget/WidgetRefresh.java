package io.azwhikaru.duotelwidget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;

import com.fiberhome.duotellib.HumidityControlUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class WidgetRefresh extends AppWidgetProvider {

    HumidityControlUtil mHumidityControlUtil = new HumidityControlUtil();

    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SensorTimer(context, appWidgetManager), 1, 10000);
    }


    private class SensorTimer extends TimerTask {

        RemoteViews remoteViews;
        AppWidgetManager appWidgetManager;
        ComponentName thisWidget;

        public SensorTimer(Context context, AppWidgetManager appWidgetManager) {
            this.appWidgetManager = appWidgetManager;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget_provider);
            thisWidget = new ComponentName(context, WidgetRefresh.class);
        }

        @Override
        public void run() {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            Date curDate = new Date(System.currentTimeMillis());
            String createDate = formatter.format(curDate);

            remoteViews.setTextViewText(R.id.textview_show_refresh_date,
                    createDate);

            remoteViews.setTextViewText(R.id.textview_show_humidity,
                    String.valueOf(mHumidityControlUtil.getHumidity()));

            remoteViews.setTextViewText(R.id.textview_show_temperature,
                    String.valueOf(mHumidityControlUtil.getTemperature()));

            appWidgetManager.updateAppWidget(thisWidget, remoteViews);
        }
    }
}
