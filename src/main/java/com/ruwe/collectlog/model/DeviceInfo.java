package com.ruwe.collectlog.model;

import java.io.Serializable;

/**
 * @author lipengfei
 * 设备相关的信息 操作系统、应用版本、网络环境(wifi、运营商)、手机型号、gps、IMEI
 */
public class DeviceInfo implements Serializable {
    //手机型号
    public String equipment;
    //gps
    public String gps;
    //设备识别码
    public String imei;
    //操作系统
    public String platform;
    //是否使用wifi
    public String wifi;
    //运营商
    public String network;
    //应用版本，值为app的版本
    public String clientVersion;

    public DeviceInfo() {
    }

    public String getEquipment() {
        return equipment;
    }

    public DeviceInfo equipment(String equipment) {
        this.equipment = equipment;
        return this;
    }

    public String getGps() {
        return gps;
    }

    public DeviceInfo gps(String gps) {
        this.gps = gps;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public DeviceInfo imei(String imei) {
        this.imei = imei;
        return this;
    }

    public String getPlatform() {
        return platform;
    }

    public DeviceInfo platform(String platform) {
        this.platform = platform;
        return this;
    }

    public String getWifi() {
        return wifi;
    }

    public DeviceInfo wifi(String wifi) {
        this.wifi = wifi;
        return this;
    }

    public String getNetwork() {
        return network;
    }

    public DeviceInfo network(String network) {
        this.network = network;
        return this;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public DeviceInfo clientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
        return this;
    }
}
