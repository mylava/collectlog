package com.ruwe.collectlog.test;

/**
 * Created by lipengfei on 2017/6/1.
 */
@Deprecated
public class LogContext {
    //线程容器
    private static final ThreadLocal<LogContext> trace = new ThreadLocal<LogContext>();
    private String traceId;
    private InvokeTree invokeTree;


    public static ThreadLocal<LogContext> getTrace() {
        return trace;
    }

    public static String getTraceId() {
        LogContext context = getContext(false);
        return context.traceId;
    }

    /**
     * 获取ThreadLocal中当前线程对应的LogContext实例
     * @param throwFlag set方法传入false,当context为空时new一个新实例;get方法传入true,当context为空时throw异常
     * @return
     */
    private static LogContext getContext(boolean throwFlag){
        LogContext context = trace.get();
        if (context == null) {
            if (throwFlag) {
                throw new RuntimeException("context can not be access now");
            }else {
                context = build();
            }
        }
        return context;
    }

    private static LogContext build(){
        LogContext context = new LogContext();
//        context.traceId = IdGenerator.nextId();
        trace.set(context);
        return context;
    }





}
