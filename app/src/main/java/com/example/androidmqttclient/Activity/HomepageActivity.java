package com.example.androidmqttclient.Activity;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidmqttclient.Protocol.MQTTProtocol;
import com.example.androidmqttclient.R;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.Bean.DeviceBean;
import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.View.ButtonExtendM;
import com.example.androidmqttclient.Callback.DeviceBlockClick;
import com.example.androidmqttclient.Adapter.DeviceContainerAdapter;
import com.example.androidmqttclient.databinding.ActivityHomepageBinding;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;
import java.util.ArrayList;

public class HomepageActivity extends BaseActivity implements View.OnClickListener, DeviceBlockClick, DrawerLayout.DrawerListener {
    private static final String TAG = "HomepageActivity";
    private static final int EXIT_TIME = 2000; // 连续按返回键退出时间

    //连接M Q T T服务器参数
    private static final String userName = "test2";//登录M Q T T服务器的账号密码 目前不支持匿名登录 匿名情况下会直接拒绝登录
    private static final String password = "123456";
    private static final String closeTopic = "android_server";
    private static final String URL = "tcp://121.5.238.148:1883";//M Q T T服务器地址
    private static final String clientId = "android_control_system";//该客服端ID

    public static final int ADD_DEVICE = 1;
    public static final int LOGIN_ACTIVITY = 2;

    private long lastTime; // 上次点击返回键时间
    private ActivityHomepageBinding homepageBinding;


    @Override
    public void initData(@Nullable Bundle bundle) {
        //连接服务器 网络不好的情况下会出现卡顿的情况  故需要线程
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MQTTProtocol.getInstance()
                        .createConnect(URL, userName, password, clientId, closeTopic);
            }
        });
        thread.start();
    }

    @Override
    public View bindLayout() {
        homepageBinding = ActivityHomepageBinding.inflate(getLayoutInflater());
        return homepageBinding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        StatusBar.translucentStatusBar(this);
        homepageBinding.include.buttonOpenDrawer.setOnClickListener(this);
        ButtonExtendM buttonExtendM = homepageBinding.includeDrawMenu.buttonMenuAddDevice;
        buttonExtendM.setOnClickListener(this);
        Button buttonExit = homepageBinding.includeDrawMenu.buttonExit;
        buttonExit.setOnClickListener(this);
        LinearLayout login = homepageBinding.includeDrawMenu.login;
        login.setOnClickListener(this);

        int height = StatusBar.getStatusBarHeightCompat(this);
        homepageBinding.linearLayoutBar.setBackgroundResource(R.color.colorAccent);
        homepageBinding.linearLayoutBar.setMinimumHeight(height);
        homepageBinding.DrawerLayout.setScrimColor(Color.parseColor("#3F000000"));
        homepageBinding.DrawerLayout.addDrawerListener(this);

        List<DeviceBean> list = new ArrayList<>();
        list.add(new DeviceBean(1, "1", "体育馆系统控制 ", "灯光与窗户跟随光照、温度改变，通过RFID卡进入体育馆"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        homepageBinding.recyclerDeviceContainer.setLayoutManager(layoutManager);

        homepageBinding.recyclerDeviceContainer.setAdapter(new DeviceContainerAdapter(list, this));
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == homepageBinding.include.buttonOpenDrawer.getId()) {
            homepageBinding.DrawerLayout.openDrawer(findViewById(R.id.linearLayout_draw_menu));
        } else if (v.getId() == homepageBinding.includeDrawMenu.buttonMenuAddDevice.getId()) {
            Intent intent = new Intent(this, AddDeviceActivity.class);
            startActivityForResult(intent, ADD_DEVICE);
        } else if (v.getId() == homepageBinding.includeDrawMenu.buttonExit.getId()) {
            exit();
        } else if (v.getId() == homepageBinding.includeDrawMenu.login.getId()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_ACTIVITY);
        }
    }

    @Override
    public void deviceBlockClick(View v) {
        //调到控制界面 传设备id过去
        String deviceId = v.findViewById(R.id.linearLayout_device_block).getTag().toString();
        Intent intent = new Intent(this, ControlSystemActivity.class);
        intent.putExtra("device_id", deviceId);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_DEVICE) {
                String deviceId = data.getStringExtra("device_id");
                Log.d(TAG, "device_id=" + deviceId);
                homepageBinding.DrawerLayout.closeDrawer(findViewById(R.id.linearLayout_draw_menu));
            } else if (requestCode == LOGIN_ACTIVITY) {
                Log.d(TAG, "LOGIN_ACTIVITY=");
                homepageBinding.DrawerLayout.closeDrawer(findViewById(R.id.linearLayout_draw_menu));
            }
        }
    }

    @Override
    public void onBackPressed() {
        //双击返回退出App
        if (System.currentTimeMillis() - lastTime > EXIT_TIME) {
            Toast mToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
            mToast.setText("再按一次退出");
            mToast.show();
            lastTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            exit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exit();
        Log.d(TAG, "onDestroy");
    }

    private void exit() {
        try {
            //关闭连接
            MQTTProtocol.getInstance().disConnect();
            Log.d(TAG, "关闭连接");
        } catch (MqttException e) {
            e.printStackTrace();
        }
        finish();
        MQTTProtocol.getInstance().release();
    }

    /*************DrawerLayout.addDrawerListener  Start******************************/
    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

}
