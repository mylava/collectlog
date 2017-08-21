package com.ruwe.collectlog.model;

import java.io.Serializable;

/**
 * @author lipengfei
 */
public class UserInfo implements Serializable{
    //session id
    public String sessionId;
    //包括访客的ID，如果已登录，与userId相同
    public String uniqueId;
    //cookie
    public String cookie;

    public String getSessionId() {
        return sessionId;
    }

    public UserInfo sessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }
    public String getCookie() {
        return cookie;
    }

    public UserInfo cookie(String cookie) {
        this.cookie = cookie;
        return this;
    }
}
