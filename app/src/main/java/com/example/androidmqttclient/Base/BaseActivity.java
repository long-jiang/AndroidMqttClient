package com.example.androidmqttclient.Base;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidmqttclient.R;
import com.example.androidmqttclient.Util.StatusBar;
import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import java.util.Objects;


public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        StatusBar.setStatusBarColor(this, R.color.white);
        initData(getIntent().getExtras());
        setContentView();
        initView(savedInstanceState);
        doBusiness();
        //RxBus订阅
        RxBus.get().register(this);
    }

    @Override
    public void setContentView() {
        setContentView(bindLayout());
    }

    @Override
    protected void onDestroy() {
        Logger.d("界面销毁");
        super.onDestroy();
        //RxBus取消订阅
        RxBus.get().unregister(this);
    }

}
