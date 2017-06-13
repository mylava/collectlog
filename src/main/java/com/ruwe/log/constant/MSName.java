package com.ruwe.log.constant;

/**
 * Created by lipengfei on 2017/6/1.
 * MicroService Name
 */
public enum MSName {
    KOULIANG_API("口粮API"),
    USER_CENTER("USERCENTER");

    private String description;

    MSName(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
