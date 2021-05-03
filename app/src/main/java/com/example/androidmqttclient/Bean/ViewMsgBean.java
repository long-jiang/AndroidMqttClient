package com.example.androidmqttclient.Bean;

public class ViewMsgBean {
    private String msg;//可能是Json格式的
    private String deviceId;
    private String viewName;

    public String getMsg() {
        return msg;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getViewName() {
        return viewName;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

}
