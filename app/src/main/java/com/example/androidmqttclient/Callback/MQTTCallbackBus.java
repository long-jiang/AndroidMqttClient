package com.example.androidmqttclient.Callback;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTCallbackBus implements MqttCallback {
    private static final String TAG = "MQTTCallbackBus";

    //连接中断
    @Override
    public void connectionLost(Throwable cause) {
        Log.e(TAG, "cause : " + cause.toString());
        // 重连的逻辑
    }

    //消息送达
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Log.e(TAG, "topic : " + topic + "\t Message : " + message.toString());
        // 该函数体内部异常的话 会导致程序一直重连 故可能异常的方法调用均需要放在try{}中
        //所有未指定监听器的订阅消息都在这里
    }

    //交互完成
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.e(TAG, "token : " + token.toString());
    }
}
