package com.ruwe.collectlog.model;

import com.ruwe.collectlog.constant.LogType;
import com.ruwe.collectlog.constant.MSName;
import com.ruwe.collectlog.context.InvokeTree;

/**
 * Created by lipengfei on 2017/6/7.
 */
public class CommonLog extends BaseLog {
    private Object[] params;
    private Object result;

    private CommonLog() {
    }

    private CommonLog(BaseLog log) {
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

    private CommonLog localIp(String localIp) {
        this.localIp = localIp;
        return this;
    }

    private CommonLog localHostName(String localHostName) {
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
}
