package com.example.androidmqttclient.Bean;

public class DeviceBean {
    private int deviceImage;
    private String deviceId;
    private String deviceName;
    private String deviceDescribe;//设备描述

    public DeviceBean(int deviceImage, String deviceId, String deviceName, String deviceDescribe) {
        this.deviceImage = deviceImage;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDescribe = deviceDescribe;
    }

    public int getDeviceImage() {
        return deviceImage;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceDescribe() {
        return deviceDescribe;
    }

}
