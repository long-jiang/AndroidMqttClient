package com.example.androidmqttclient.Util;

import android.app.Activity;
import android.view.Display;

public class ScreenUtils {
    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return display.getHeight();
    }

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return display.getWidth();
    }
}
