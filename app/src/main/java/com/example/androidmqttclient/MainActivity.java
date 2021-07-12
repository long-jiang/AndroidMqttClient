package com.example.androidmqttclient;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.example.androidmqttclient.Activity.HomepageActivity;
import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.databinding.ActivityMainBinding;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

//加载界面
public class MainActivity extends BaseActivity {
    // TODO:
    static {
        System.loadLibrary("native-lib");
    }

    private ActivityMainBinding activityMainBinding;

    private static final String TAG = "MainActivity";

    @Override
    public void initData(@Nullable Bundle bundle) {
        StatusBar.setStatusBarColor(this, R.color.colorAccent);
        //初始化日志管理系统
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("初始化日志系统");
    }

    @Override
    public View bindLayout() {
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return activityMainBinding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        new CountDownTimer(2000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getBaseContext(), HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

    }

    @Override
    public void doBusiness() {

    }

    public native String stringFromJNI();
}
