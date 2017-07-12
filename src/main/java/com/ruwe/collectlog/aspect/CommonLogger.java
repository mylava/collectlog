package com.ruwe.collectlog.aspect;

import com.ruwe.collectlog.constant.LogType;
import com.ruwe.collectlog.constant.MSName;
import com.ruwe.collectlog.context.InvokeTree;
import com.ruwe.collectlog.context.LogContext;
import com.ruwe.collectlog.model.BaseLog;
import com.ruwe.collectlog.model.CommonLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lipengfei on 2017/6/2.
 */
public class CommonLogger {
    private static Logger LOGGER_PRO = LoggerFactory.getLogger("COMMON");
    private String msName;
    private String logType;

    public void setMsName(String msName) {
        if (msName != null && !msName.isEmpty()) {
            this.msName = msName.toUpperCase();
        } else {
            this.msName = "KOULIANG_API";
        }
    }

    public void setLogType(String logType) {
        if (logType != null && !logType.isEmpty()) {
            this.logType = logType.toUpperCase();
        }
    }

    public Object record(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getTarget().getClass().getCanonicalName() + "." + joinPoint.getSignature().getName();


        BaseLog baseLog = LogContext.getBaseLog(null);
        InvokeTree invokeTree = baseLog.getInvokeTree();

        if (invokeTree.getCurNode() == null) {
            invokeTree.start(methodName);
        } else {
            invokeTree.enter(methodName);
        }

        //获取请求参数
        Object[] args = joinPoint.getArgs();

        CommonLog log = CommonLog.build(baseLog)
                .now(System.currentTimeMillis())
                .logType(LogType.parseRequest(logType))
                .msName(MSName.valueOf(msName))
                .invokeTree(invokeTree)
                .params(args);

        LOGGER_PRO.info(log.parseLog());

        Object obj = joinPoint.proceed();

        invokeTree.exit();

        baseLog = log
                .now(System.currentTimeMillis())
                .logType(LogType.parseResponse(logType))
                .msName(MSName.valueOf(msName))
                .invokeTree(invokeTree)
                .result(obj);

        LOGGER_PRO.info(baseLog.parseLog());
        return obj;
    }
}
