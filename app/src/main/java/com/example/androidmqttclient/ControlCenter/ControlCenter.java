package com.example.androidmqttclient.ControlCenter;

import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.androidmqttclient.Bean.ViewMsgBean;


import java.util.HashMap;
import java.util.Map;

/*
单例模式 程序启动就创建 程序销毁才销毁
 */
public class ControlCenter {
    private Map<String, Map<String, View>> _map;
    private static ControlCenter instance = null;

    private ControlCenter(){
        _map =new HashMap<>();
    }

    public static synchronized ControlCenter getInstance() {
        if (instance == null) {
            instance = new ControlCenter();
        }
        return instance;
    }

    //添加设备
    public void addDevice(String deviceId) {
        Map<String, View> deviceMap = new HashMap<>();
        _map.put(deviceId, deviceMap);
    }

    //注册控件
    public void registerView(String deviceId, String viewName, View view) {
        Map<String, View> deviceMap = _map.get(deviceId);
        assert deviceMap != null;
        deviceMap.put(viewName, view);
    }

    //将数据显示到对应的控件视图上
    public void viewDisplayMsg(String msg) {
        ViewMsgBean viewMsgBean = JSON.parseObject(String.valueOf(msg), ViewMsgBean.class);
        Map<String, View> deviceMap = _map.get(viewMsgBean.getDeviceId());
        assert deviceMap != null;
        TextView text = (TextView) deviceMap.get(viewMsgBean.getViewName());
        assert text != null;
        text.setText(viewMsgBean.getMsg());
    }

}
