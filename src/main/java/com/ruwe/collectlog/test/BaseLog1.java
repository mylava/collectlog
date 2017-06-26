package com.ruwe.collectlog.test;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipengfei on 2017/5/26.
 */
@Deprecated
public class BaseLog1<T> {

    //json-------考虑是否放入ua
    private String appInfo;
    //host
    private String host;
    //referer
    private String referer;
    //userAgent
    private String userAgent;
    //cookie
    private String cookie;
    //客户端ip
    private String clientIp;
    //mime type
    private String contentType;
    //serverName
    private String serverName;
    //服务端ip
    private String serverIp;
    //请求方法
    private String method;
    //请求字符串
    private String queryString;
    //session id
    private String sessionId;
    //request uri
    private String requestURI;
    //request url
    private String requestURL;
    //content path
    private String contentPath;
    //servlet path
    private String servletPath;
    //请求参数
    private Map<String,String[]> params;
    //请求时间
    private long requestTime;
    //响应时间
    private long responseTime;

    public String getHost() {
        return host;
    }

    public BaseLog1 host(String host) {
        this.host = host;
        return this;
    }

    public String getReferer() {
        return referer;
    }

    public BaseLog1 referer(String referer) {
        this.referer = referer;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public BaseLog1 userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getCookie() {
        return cookie;
    }

    public BaseLog1 cookie(String cookie) {
        this.cookie = cookie;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public BaseLog1 contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public BaseLog1 serverName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public BaseLog1 method(String method) {
        this.method = method;
        return this;
    }

    public String getQueryString() {
        return queryString;
    }

    public BaseLog1 queryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public BaseLog1 sessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public BaseLog1 requestURI(String requestURI) {
        this.requestURI = requestURI;
        return this;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public BaseLog1 requestURL(String requestURL) {
        this.requestURL = requestURL;
        return this;
    }

    public String getContentPath() {
        return contentPath;
    }

    public BaseLog1 contentPath(String contentPath) {
        this.contentPath = contentPath;
        return this;
    }

    public String getServletPath() {
        return servletPath;
    }

    public BaseLog1 servletPath(String servletPath) {
        this.servletPath = servletPath;
        return this;
    }

    public Map<String, String[]> getParams() {
        return params;
    }

    public BaseLog1 params(Map<String, String[]> params) {
        this.params = params;
        return this;
    }

    public String getClientIp() {
        return clientIp;
    }

    public BaseLog1 clientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public String getServerIp() {
        return serverIp;
    }

    public BaseLog1 serverIp(String serverIp) {
        this.serverIp = serverIp;
        return this;
    }

    public String getAppInfo() {
        return appInfo;
    }

    public BaseLog1 appInfo(String appInfo) {
        this.appInfo = appInfo;
        return this;
    }

    public String parseLog(Object obj) {
        Map<String,Object> map = new HashMap<>();
        map.put(this.getClass().getName(),this);
        if (null != obj)
            map.put(obj.getClass().getName(),obj);
        return JSON.toJSONString(map);
    }

    public String parseLog(BaseLog1 log, T obj) {
        Map<String,Object> map = new HashMap<>();
        if (null != log)
            map.put(log.getClass().getName(),log);
        if (null != obj)
            map.put(obj.getClass().getName(),obj);
        return JSON.toJSONString(map);
    }

    public String parseLog(BaseLog1 log, T obj, Map<String,String> replenish) {
        Map<String,Object> map = new HashMap<>();
        if (null != log)
            map.put(log.getClass().getName(),log);
        if (null != obj)
            map.put(obj.getClass().getName(),obj);
        if (null != replenish)
            map.put("replenish",replenish);
        return JSON.toJSONString(map);
    }
}
