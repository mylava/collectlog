package com.ruwe.collectlog.context;


import com.ruwe.collectlog.model.BaseLog;

/**
 * 线程上下文环境
 * Created by lipengfei on 2017/6/2.
 */
public class LogContext {
    //线程容器
    private static final ThreadLocal<LogContext> local = new ThreadLocal<LogContext>();
    //追踪Id
    private String traceId;
    private BaseLog baseLog;


    private LogContext() {
    }

    /**
     * 初始化LogContext
     */
    public static LogContext getContext(String traceId) {
        LogContext context = local.get();
        if (context == null) {
            context = new LogContext();
            context.baseLog = BaseLog.build(traceId);
            context.traceId = context.baseLog.getId();
            local.set(context);
        }
        return context;
    }

    public static String getTraceId(String traceId) {
        return getContext(traceId).traceId;
    }

    public static BaseLog getBaseLog(String traceId) {
        return getContext(traceId).baseLog;
    }

    public static void clear() {
        local.remove();
    }

}
