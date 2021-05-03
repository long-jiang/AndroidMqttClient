package com.example.androidmqttclient.Util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

public class Tools {

    /**
     * 从dp单位转换为px
     *
     * @param dp dp值
     * @return 返回转换后的px值
     */
    public static int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
