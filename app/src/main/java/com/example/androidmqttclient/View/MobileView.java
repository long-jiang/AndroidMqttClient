package com.example.androidmqttclient.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

//可以移动的View
public class MobileView extends LinearLayout {
    private boolean isOpenMove = false;//打开移动View的功能

    private View moveView;

    public MobileView(Context context) {
        super(context);
    }

    public MobileView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MobileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMobileState(boolean mobileState) {
        this.isOpenMove = mobileState;
    }

    public boolean isMoveView() {
        return this.isOpenMove;
    }

    public void setMoveView(View view) {
        this.moveView = view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (moveView == null) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                moveView.setTranslationX(event.getX());
                moveView.setTranslationY(event.getY());
                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }


}
