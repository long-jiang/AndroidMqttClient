package com.example.androidmqttclient.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.Bean.ControlSystemBean;
import com.example.androidmqttclient.Protocol.MQTTProtocol;
import com.example.androidmqttclient.R;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.databinding.ActivityControlSystemBinding;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ControlSystemActivity extends BaseActivity implements View.OnClickListener, IMqttMessageListener {
    public static final String TAG = "ControlSystemActivity";
    private static final String publish_topic = "device_publish_ms";
    private static final String subscribe_topic = "device_subscribe_ms";
    private static final int qos = 2;

    private ActivityControlSystemBinding controlSystemBinding;

    @Override
    public void initData(@Nullable Bundle bundle) {
        StatusBar.setStatusBarColor(this, R.color.colorAccent);
        MQTTProtocol.getInstance().subscribe(subscribe_topic, qos, this);
    }

    @Override
    public View bindLayout() {
        controlSystemBinding = ActivityControlSystemBinding.inflate(getLayoutInflater());
        return controlSystemBinding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        controlSystemBinding.textReturn.setOnClickListener(this);

        controlSystemBinding.buttonWindow.setTag(0);
        controlSystemBinding.buttonLighting.setTag(0);
        controlSystemBinding.buttonAccessControl.setTag(0);
        controlSystemBinding.buttonWindow.setOnClickListener(this);
        controlSystemBinding.buttonLighting.setOnClickListener(this);
        controlSystemBinding.buttonAccessControl.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onBackPressed() {
        finish();//销毁自己 返回上层界面
    }

    private void setViewBackground(final View v, boolean show) {
        final Drawable drawable;
        if (show) {
            drawable = ContextCompat.getDrawable(this, R.drawable.control_button_on_style);
        } else {
            drawable = ContextCompat.getDrawable(this, R.drawable.control_button_off_style);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.setBackgroundDrawable(drawable);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == controlSystemBinding.textReturn.getId()) {
            finish();//销毁自己 返回上层界面
        } else if (v.getId() == controlSystemBinding.buttonWindow.getId()) {
            //窗户按钮的监听
            if ((int) controlSystemBinding.buttonWindow.getTag() == 0) {
                setViewBackground(v, true);
                controlSystemBinding.buttonWindow.setTag(1);
                sendMessage("window", "window_on");
            } else {
                setViewBackground(v, false);
                controlSystemBinding.buttonWindow.setTag(0);
                sendMessage("window", "window_off");
            }
        } else if (v.getId() == controlSystemBinding.buttonLighting.getId()) {
            //灯光按钮的监听
            if ((int) controlSystemBinding.buttonLighting.getTag() == 0) {
                setViewBackground(v, true);
                controlSystemBinding.buttonLighting.setTag(1);
                sendMessage("lighting", "lighting_on");
            } else {
                setViewBackground(v, false);
                controlSystemBinding.buttonLighting.setTag(0);
                sendMessage("lighting", "lighting_off");
            }

        } else if (v.getId() == controlSystemBinding.buttonAccessControl.getId()) {
            //门禁按钮的监听
            if ((int) controlSystemBinding.buttonAccessControl.getTag() == 0) {
                setViewBackground(v, true);
                controlSystemBinding.buttonAccessControl.setTag(1);
                sendMessage("door", "door_on");
            } else {
                setViewBackground(v, false);
                controlSystemBinding.buttonAccessControl.setTag(0);
                sendMessage("door", "door_off");
            }
        }
    }

    public void sendMessage(String type, String msg) {
        String json = "{\n" +
                "    \"type\": \"" + type + "\",\n" +
                "    \"msg\":  \"" + msg + "\"\n" +
                "}";
        Log.d("TESTSSS", json);
        MQTTProtocol.getInstance().publish(publish_topic, qos, json);
    }


    //接收订阅的消息
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Log.d(TAG, "topic= " + topic + "  msg=" + message);
        ControlSystemBean messageBean = null;
        try {//可能异常
            messageBean = JSON.parseObject(message.toString(), ControlSystemBean.class);
        } catch (Exception e) {
            Log.i(TAG, " JSON 解析错误 e=" + e);
        }

        //该方法内不允许出现 异常 否则将导致一直重连服务器
        assert messageBean != null;
        final ControlSystemBean messageBeanFinal = messageBean;
        switch (messageBean.getType()) {
            case "door": //门禁的消息
                if (messageBean.getMsg().equals("door_on")) {
                    controlSystemBinding.buttonAccessControl.setTag(1);
                    setViewBackground(controlSystemBinding.buttonAccessControl, true);
                } else if (messageBean.getMsg().equals("door_off")) {
                    controlSystemBinding.buttonAccessControl.setTag(0);
                    setViewBackground(controlSystemBinding.buttonAccessControl, false);
                }
                break;
            case "lighting": //灯光的消息
                if (messageBean.getMsg().equals("lighting_on")) {
                    controlSystemBinding.buttonLighting.setTag(1);
                    setViewBackground(controlSystemBinding.buttonLighting, true);
                } else if (messageBean.getMsg().equals("lighting_off")) {
                    controlSystemBinding.buttonLighting.setTag(0);
                    setViewBackground(controlSystemBinding.buttonLighting, false);
                }
                break;
            case "window": //窗户的消息
                if (messageBean.getMsg().equals("window_on")) {
                    controlSystemBinding.buttonWindow.setTag(1);
                    setViewBackground(controlSystemBinding.buttonWindow, true);
                } else if (messageBean.getMsg().equals("window_off")) {
                    controlSystemBinding.buttonWindow.setTag(0);
                    setViewBackground(controlSystemBinding.buttonWindow, false);
                }
                break;
            case "lamp_num": //光敏值
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        controlSystemBinding.textViewPhotosensitive
                                .setText(messageBeanFinal.getMsg());
                    }
                });

                break;
            case "temp_num": //温度
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        controlSystemBinding.textViewTemperature.setText(messageBeanFinal.getMsg());
                    }
                });
                break;

            case "device_state":
                 if(messageBeanFinal.getMsg().equals("go_online")) {
                    runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        controlSystemBinding.textViewDeviceState.setText("设备上线");
                    }
                });
                 }
                break;
        }
    }
}
