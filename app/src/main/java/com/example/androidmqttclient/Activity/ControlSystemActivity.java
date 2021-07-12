package com.example.androidmqttclient.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.androidmqttclient.Base.BaseActivity;
import com.example.androidmqttclient.Bean.ControlSystemBean;
import com.example.androidmqttclient.Protocol.MQTTProtocol;
import com.example.androidmqttclient.R;
import com.example.androidmqttclient.Util.StatusBar;
import com.example.androidmqttclient.databinding.ActivityControlSystemBinding;
import com.orhanobut.logger.Logger;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

 /*
   TODO:应该销毁调 一个设备对应一个service 程序销毁后才销毁掉
 */
public class ControlSystemActivity extends BaseActivity implements View.OnClickListener, IMqttMessageListener {
    private static final String publish_topic = "device_publish_ms";
    private static final String subscribe_topic = "device_subscribe_ms";
    private static final int qos = 2;

    private String fieldName;
    private String deviceId;
    private ActivityControlSystemBinding controlSystemBinding;

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        deviceId = bundle.getString("device_id");
        fieldName = bundle.getString("field_name");
        StatusBar.setStatusBarColor(this, R.color.colorAccent);

        //订阅subscribe_topic的信息
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
        controlSystemBinding.textViewDeviceName.setText(fieldName);
        controlSystemBinding.buttonWindow.setTag(0);
        controlSystemBinding.buttonLighting.setTag(0);
        controlSystemBinding.buttonAccessControl.setTag(0);
        controlSystemBinding.buttonWindow.setOnClickListener(this);
        controlSystemBinding.buttonLighting.setOnClickListener(this);
        controlSystemBinding.buttonAccessControl.setOnClickListener(this);
        controlSystemBinding.textViewMakeAnAppointment.setOnClickListener(this);
        windowDataInit();//回复状态
    }

    @Override
    public void doBusiness() {

    }

    private void windowDataInit() {
        @SuppressLint("CommitPrefEdits")
        SharedPreferences sharedPreferences =
                getSharedPreferences((fieldName + "data"), Context.MODE_PRIVATE);

        String window = sharedPreferences.getString("buttonWindow", "");
        String lighting = sharedPreferences.getString("buttonLighting", "");
        String accessControl = sharedPreferences.getString("buttonAccessControl", "");
        String temperature = sharedPreferences.getString("textViewTemperature", "");
        String photosensitive = sharedPreferences.getString("textViewPhotosensitive", "");


        Logger.d(window + " " + lighting + " " + accessControl + " " + temperature + " " + photosensitive);

        controlSystemBinding.textViewTemperature.setText(temperature);
        controlSystemBinding.textViewPhotosensitive.setText(photosensitive);

        setViewBackground(controlSystemBinding.buttonWindow, window.equals("1"));
        controlSystemBinding.buttonWindow.setTag(window);

        setViewBackground(controlSystemBinding.buttonLighting, lighting.equals("1"));
        controlSystemBinding.buttonLighting.setTag(lighting);

        setViewBackground(controlSystemBinding.buttonAccessControl,
                          accessControl.equals("1"));
        controlSystemBinding.buttonAccessControl.setTag(accessControl);

    }

    //保存数据
    private void windowDataSave() {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit =
                getSharedPreferences((fieldName + "data"), Context.MODE_PRIVATE).edit();
        edit.putString("buttonWindow", controlSystemBinding.buttonWindow.getTag().toString());
        edit.putString("buttonLighting", controlSystemBinding.buttonLighting.getTag().toString());
        edit.putString("buttonAccessControl",
                       controlSystemBinding.buttonAccessControl.getTag().toString());
        edit.putString("textViewTemperature",
                       controlSystemBinding.textViewTemperature.getText().toString());
        edit.putString("textViewPhotosensitive",
                       controlSystemBinding.textViewPhotosensitive.getText().toString()).apply();

        Logger.d(controlSystemBinding.buttonWindow.getTag().toString() + "\n");
        Logger.d(controlSystemBinding.buttonLighting.getTag().toString() + "\n");
        Logger.d(controlSystemBinding.buttonAccessControl.getTag().toString() + "\n");
    }

    @Override
    public void onBackPressed() {
        //TODO: 应该不finish 保存接收消息  程序销毁的时候一起销毁
        windowDataSave();
        finish();//销毁自己 返回上层界面
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        windowDataSave();
        finish();
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
            if (controlSystemBinding.buttonWindow.getTag() == "0") {
                setViewBackground(v, true);
                controlSystemBinding.buttonWindow.setTag("1");
                sendMessage("window", "window_on");
            } else {
                setViewBackground(v, false);
                controlSystemBinding.buttonWindow.setTag("0");
                sendMessage("window", "window_off");
            }
        } else if (v.getId() == controlSystemBinding.buttonLighting.getId()) {
            //灯光按钮的监听
            if (controlSystemBinding.buttonLighting.getTag() == "0") {
                setViewBackground(v, true);
                controlSystemBinding.buttonLighting.setTag("1");
                sendMessage("lighting", "lighting_on");
            } else {
                setViewBackground(v, false);
                controlSystemBinding.buttonLighting.setTag("0");
                sendMessage("lighting", "lighting_off");
            }

        } else if (v.getId() == controlSystemBinding.buttonAccessControl.getId()) {
            //门禁按钮的监听
            if (controlSystemBinding.buttonAccessControl.getTag() == "0") {
                setViewBackground(v, true);
                controlSystemBinding.buttonAccessControl.setTag("1");
                sendMessage("door", "door_on");
            } else {
                setViewBackground(v, false);
                controlSystemBinding.buttonAccessControl.setTag("0");
                sendMessage("door", "door_off");
            }
        } else if (v.getId() == controlSystemBinding.textViewMakeAnAppointment.getId()) {//修改
//            Intent intent = new Intent(this, MakeAnAppointmentActivity.class);
//            intent.putExtra("field_name",
//                            controlSystemBinding.textViewDeviceName.getText().toString());
//            startActivity(intent);
        }
    }

    public void sendMessage(String type, String msg) {
//        String json = "{\n" +
//                "    \"type\": \"" + type + "\",\n" +
//                "    \"msg\":  \"" + msg + "\"\n" +
//                "}";
        JSONObject object = new JSONObject();
        object.put("type", type);
        object.put("msg", msg);
        Logger.d(object);
        MQTTProtocol.getInstance().publish(publish_topic, qos, object.toString());
    }

    //接收订阅的消息
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Logger.d("topic= " + topic + "  msg=" + message);
        ControlSystemBean messageBean = null;
        try {//可能异常
            messageBean = JSON.parseObject(message.toString(), ControlSystemBean.class);
        } catch (Exception e) {
            Logger.e(" JSON 解析错误 e=" + e);
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
                if (messageBeanFinal.getMsg().equals("go_online")) {
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
