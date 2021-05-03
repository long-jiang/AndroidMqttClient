package com.example.androidmqttclient.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.androidmqttclient.R;

public class RoundedRectangleView extends RelativeLayout {
    public RoundedRectangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(View.inflate(context, R.layout.rounded_rectangle_view, null));
    }
}
