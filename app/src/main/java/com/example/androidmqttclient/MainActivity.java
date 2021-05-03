package com.example.androidmqttclient;

import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.example.androidmqttclient.Activity.HomepageActivity;
import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.Protocol.MQTTProtocol;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    private ActivityMainBinding activityMainBinding;

    private static final String TAG = "MainActivity";

    @Override
    public void initData(@Nullable Bundle bundle) {
        StatusBar.setStatusBarColor(this, R.color.colorAccent);
        //TODO 判断是否存在 账号与密码 不存在的情况下 跳转登录界面 存在情况下跳转到控制界面
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
