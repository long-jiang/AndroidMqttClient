package com.example.androidmqttclient.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    //时间比较 endTime>返回3
    public static int getTimeCompareSize(Date startDate, Date endDate){
        int i=0;
        // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
        if (endDate.getTime()<startDate.getTime()){
            i= 1;
        }else if (endDate.getTime()==startDate.getTime()){
            i= 2;
        }else if (endDate.getTime()>startDate.getTime()){
            //正常情况下的逻辑操作.
            i= 3;
        }
        return  i;
    }

}
