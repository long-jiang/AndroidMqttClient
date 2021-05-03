package com.example.androidmqttclient.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidmqttclient.R;

public class CustomView extends View {

    private String TAG="IMListView";
    private Paint mPaint;
    private RectF oval;
    private int start_angle;
    private float move_angle;

    public CustomView(Context context) {
        super(context);
        init(context,null);
    }

    public CustomView(Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attributeSet){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        oval=new RectF();
        if (attributeSet!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomView);
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                if (index==R.styleable.CustomView_start_angle)
                {
                    start_angle = typedArray.getInteger(index, 180);
                }
                if (index==R.styleable.CustomView_move_angle)
                {
                    move_angle = typedArray.getFloat(index, 135f);
                }
            }
            typedArray.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        int hmode = MeasureSpec.getMode(heightMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (wmode) {
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "onMeasure: AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "onMeasure: EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "onMeasure: UNSPECIFIED");
                break;
        }
        Log.i(TAG, "onMeasure: width:"+wsize+"--height:"+ size);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout: "+changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        // FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int with = getWidth();
        int height = getHeight();
        Log.e(TAG, "onDraw---->" + with + "*" + height);
        float radius = with / 4;
        canvas.drawCircle(with / 2, with / 2, radius, mPaint);
        mPaint.setColor(Color.BLUE);
        oval.set(with / 2 - radius, with / 2 - radius, with / 2
                + radius, with / 2 + radius);//用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval, start_angle, move_angle, true, mPaint);  //根据进度画圆弧
    }
}
