package com.ruwe.collectlog.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.ruwe.collectlog.constant.LogType;
import com.ruwe.collectlog.constant.MSName;
import com.ruwe.collectlog.context.InvokeTree;

import java.util.Map;

/**
 * HttpRequest请求日志Bean
 * Created by lipengfei on 2017/5/27.
 */
public class RequestLog extends BaseLog {
    //request host
    public String host;
    //request serverName
    public String serverName;
    //referer
    public String referer;
    //userAgent
    public String userAgent;
    //mime type
    public String contentType;
    //请求方法
    public String method;
    //请求字符串
    public String queryString;
    //request uri
    public String requestURI;
    //request url
    public String requestURL;
    //content path
    public String contentPath;
    //servlet path
    public String servletPath;
    //请求参数
    public Map<String, String[]> params;
    //设备信息
    public DeviceInfo deviceInfo;
    //用户信息
    public UserInfo userInfo;
    //客户端ip
    public String clientIp;

    public RequestLog() {
    }

    public RequestLog(BaseLog log) {
        this.id = log.getId();
        this.localIp = log.getLocalIp();
        this.localHostName = log.localHostName;
    }

    public RequestLog now(Long now) {
        this.now = now;
        return this;
    }

    public RequestLog logType(LogType logType) {
        this.logType = logType;
        return this;
    }

    public RequestLog invokeTree(InvokeTree invokeTree) {
        this.invokeTree = invokeTree;
        return this;
    }

    public RequestLog msName(MSName msName) {
        this.msName = msName;
        return this;
    }

    public String getHost() {
        return host;
    }

    public RequestLog host(String host) {
        this.host = host;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public RequestLog serverName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getReferer() {
        return referer;
    }

    public RequestLog referer(String referer) {
        this.referer = referer;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public RequestLog userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public RequestLog contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RequestLog method(String method) {
        this.method = method;
        return this;
    }

    public String getQueryString() {
        return queryString;
    }

    public RequestLog queryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public RequestLog requestURI(String requestURI) {
        this.requestURI = requestURI;
        return this;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public RequestLog requestURL(String requestURL) {
        this.requestURL = requestURL;
        return this;
    }

    public String getContentPath() {
        return contentPath;
    }

    public RequestLog contentPath(String contentPath) {
        this.contentPath = contentPath;
        return this;
    }

    public String getServletPath() {
        return servletPath;
    }

    public RequestLog servletPath(String servletPath) {
        this.servletPath = servletPath;
        return this;
    }

    public Map<String, String[]> getParams() {
        return params;
    }

    public RequestLog params(Map<String, String[]> params) {
        this.params = params;
        return this;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public RequestLog deviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public RequestLog userInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    public String getClientIp() {
        return clientIp;
    }

    public RequestLog clientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public static RequestLog build(BaseLog log) {
        return new RequestLog(log);
    }

    /**
     * 将JSONString对象转换成RequestLog
     * @param json
     * @return
     */
    public static RequestLog toRequestLog(String json) {
        Map<String, RequestLog> map = JSONObject.parseObject(json,new TypeReference<Map<String, RequestLog>>(){});
        if (map.containsKey(RequestLog.class.getName())) {
            return map.get(RequestLog.class.getName());
        }
        return null;
    }
}
