package com.example.androidmqttclient.Bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class MQTTMessageBean implements Serializable {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
