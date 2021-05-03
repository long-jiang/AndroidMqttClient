package com.example.androidmqttclient.Bean;

import java.io.Serializable;

public class ControlSystemBean implements Serializable {
    private String type;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
