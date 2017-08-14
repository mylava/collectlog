package com.ruwe.collectlog.constant;

/**
 * Created by lipengfei on 2017/6/1.
 * MicroService Name
 */
public enum MSName {
    KOULIANG_API("口粮API"),
    CMS_API("CMS_API"),
    USER_CENTER("USERCENTER");

    private String description;

    MSName(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
