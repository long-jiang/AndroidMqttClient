package com.example.androidmqttclient.Protocol;

import android.util.Log;

import com.example.androidmqttclient.Callback.MQTTCallbackBus;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class MQTTProtocol {
    private static final String TAG = "MQTTProtocol";
    private MqttCallback mCallback;
    private static MqttClient client;//客户端
    private MqttConnectOptions conOpt;//连接选项
    private static MQTTProtocol mInstance = null;

    private MQTTProtocol() {
        mCallback = new MQTTCallbackBus();
    }

    public static MQTTProtocol getInstance() {
        if (null == mInstance) {
            synchronized (MQTTProtocol.class) {
                if (mInstance == null) {
                    mInstance = new MQTTProtocol();
                }
            }
        }
        return mInstance;
    }

    //释放单例, 及其所引用的资源
    public void release() {
        try {
            if (mInstance != null) {
                disConnect();
                mInstance = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "release : " + e.toString());
        }
    }

    /**
     * M Q T T 连接
     * @param url      (tcp://xxxx:1863)
     * @param name     用户名
     * @param password 密码
     * @param id       客户端Id
     */
    public void createConnect(String url, String name, String password, String id, String topic) {
        Log.d(TAG,"URL= "+url+" name= "+name+" password= "+password+" id= "+id+" topic= "+topic);
        String tmpDir = System.getProperty("java.io.tmpdir");  // 获取默认的临时文件路径
        //在数据发送过程中 如果客户端没有接收到 数据包会一直保存在文件中，直到成功为止
        assert tmpDir != null;
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
        try {
            conOpt = new MqttConnectOptions(); // 构建包含连接参数的连接选择对象
            conOpt.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1); // 设置MQTT版本
            conOpt.setCleanSession(false); // 设置清空Session，false表示服务器会保留客户端的连接记录，true表示每次以新的身份连接到服务器
            conOpt.setKeepAliveInterval(10); // 客户端每隔10秒向服务端发送心跳包判断客户端是否在线
            if (name != null) {  // 设置账号
                conOpt.setUserName(name);
            }
            if (password != null) {// 设置密码
                conOpt.setPassword(password.toCharArray());
            }
            // 最后的遗言(连接断开时， 发动"close"给订阅了topic该主题的用户告知连接已中断)
            conOpt.setWill(topic, "close".getBytes(), 2, true);
            conOpt.setAutomaticReconnect(true);// 客户端是否自动尝试重新连接到服务器
            client = new MqttClient(url, id, dataStore); // 创建MQTT客户端
            client.setCallback(mCallback); // 设置回调
            connect();
        } catch (MqttException e) {
            Log.e(TAG, "createConnect : " + e.toString());
        }
    }

    //建立连接
    public void connect() {
        if (client != null) {
            try {
                client.connect(conOpt);
                Log.e(TAG, "连接成功 : ");
            } catch (Exception e) {
                //TODO：该错误需通知到外部
                Log.e(TAG, "doConnect : " + e.toString());
            }
        }
    }

    /**
     * 发布消息
     *
     * @param topicName 主题名称
     * @param qos       质量(0,1,2)
     * @param payload   发送的内容
     */
    public void publish(String topicName, int qos, byte[] payload) {
        if (client != null && client.isConnected()) {
            // 创建和配置一个消息
            MqttMessage message = new MqttMessage(payload);
            message.setPayload(payload);
            message.setQos(qos);
            try {
                client.publish(topicName, message);
            } catch (MqttException e) {
                Log.e(TAG, "publish : " + e.toString());
            }
        }
    }

    public void publish(String topicName, int qos, String payload) {
        if (client != null && client.isConnected()) {
            // 创建和配置一个消息
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setPayload(payload.getBytes());
            message.setQos(qos);
            try {
                client.publish(topicName, message);
            } catch (MqttException e) {
                Log.e(TAG, "publish : " + e.toString());
            }
        }
    }

    /**
     * 订阅主题
     *
     * @param topicName 主题名称
     * @param qos       消息质量
     */
    public void subscribe(String topicName, int qos) {
        if (client != null && client.isConnected()) {
            try {
                client.subscribe(topicName, qos);
            } catch (MqttException e) {
                Log.e(TAG, "subscribe : " + e.toString());
            }
        }
    }

     public void subscribe(String topicName, int qos, IMqttMessageListener messageListener) {
        if (client != null && client.isConnected()) {
            try {
                client.subscribe(topicName,qos,messageListener);
            } catch (MqttException e) {
                Log.e(TAG, "subscribe : " + e.toString());
            }
        }
    }

    /**
     * 取消主题
     *
     * @param topicName 主题名称
     */
    public void unsubscribe(String topicName) {
        if (client != null && client.isConnected()) {
            try {
                client.unsubscribe(topicName);
            } catch (MqttException e) {
                Log.e(TAG, "subscribe : " + e.toString());
            }
        }
    }

    //取消连接
    public void disConnect() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
        }
    }

    // 判断是否连接
    public boolean isConnected() {
        return client != null && client.isConnected();
    }
}
