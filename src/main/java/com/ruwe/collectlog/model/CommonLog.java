package com.ruwe.collectlog.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ruwe.collectlog.constant.LogType;
import com.ruwe.collectlog.constant.MSName;
import com.ruwe.collectlog.context.InvokeTree;

import java.util.Map;

/**
 * Created by lipengfei on 2017/6/7.
 */
public class CommonLog extends BaseLog {
    public Object[] params;
    public Object result;

    public CommonLog() {
    }

    public CommonLog(BaseLog log) {
        this.id(log.getId())
                .localIp(log.getLocalIp())
                .localHostName(log.getLocalHostName());
    }

    public CommonLog id(String id) {
        this.id = id;
        return this;
    }

    public CommonLog logType(LogType logType) {
        this.logType = logType;
        return this;
    }

    public CommonLog invokeTree(InvokeTree invokeTree) {
        this.invokeTree = invokeTree;
        return this;
    }

    public CommonLog now(Long now) {
        this.now = now;
        return this;
    }

    public CommonLog localIp(String localIp) {
        this.localIp = localIp;
        return this;
    }

    public CommonLog localHostName(String localHostName) {
        this.localHostName = localHostName;
        return this;
    }

    public CommonLog msName(MSName msName) {
        this.msName = msName;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public CommonLog params(Object[] params) {
        this.params = params;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public CommonLog result(Object result) {
        this.result = result;
        return this;
    }

    public static CommonLog build(BaseLog log) {
        return new CommonLog(log);
    }

    /**
     * 将JSONString对象转换成CommonLog
     *
     * @param json
     * @return
     */
    public static CommonLog toCommonLog(String json) {
        Map<String, CommonLog> map = JSONObject.parseObject(json, new TypeReference<Map<String, CommonLog>>() {
        });
        if (map.containsKey(CommonLog.class.getName())) {
            return map.get(CommonLog.class.getName());
        }
        return null;
    }
}
