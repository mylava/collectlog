package com.ruwe.log.aspect;

import com.ruwe.log.constant.LogType;
import com.ruwe.log.constant.MSName;
import com.ruwe.log.context.InvokeTree;
import com.ruwe.log.context.LogContext;
import com.ruwe.log.model.BaseLog;
import com.ruwe.log.model.RequestLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by lipengfei on 2017/6/2.
 */
public class WebLogger {
    private static Logger LOGGER_WEB = LoggerFactory.getLogger("WEB");
    private static Logger LOGGER_ERR = LoggerFactory.getLogger("ERROR");
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

        String methodName = joinPoint.getTarget().getClass().getCanonicalName()+ "." + joinPoint.getSignature().getName();

        BaseLog baseLog = LogContext.getBaseLog(null);
        InvokeTree invokeTree = baseLog.getInvokeTree();
        invokeTree.start(methodName);

        //获取请求参数
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;

        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            }
        }

        if (request != null) {
            RequestLog log = RequestLog.build(baseLog)
                    .now(System.currentTimeMillis())
                    .logType(LogType.parseRequest(logType))
                    .msName(MSName.valueOf(msName))
                    .invokeTree(invokeTree)
                    .appInfo("")
                    .host(request.getHeader("Host"))
                    .serverName(request.getServerName())
                    .referer(request.getHeader("Referer"))
                    .userAgent(request.getHeader("User-Agent"))
                    .cookie(request.getHeader("Cookie"))
                    .clientIp(request.getRemoteAddr())
                    .contentType(request.getContentType())
                    .method(request.getMethod())
                    .sessionId(request.getRequestedSessionId())
                    .requestURI(request.getRequestURI())
                    .requestURL(request.getRequestURL().toString())
                    .contentPath(request.getContextPath())
                    .servletPath(request.getServletPath())
                    .params(request.getParameterMap());


            if (null != request.getQueryString() && !"".equals(request.getQueryString())) {
                try {
                    log.queryString(URLDecoder.decode(request.getQueryString(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    log.queryString(request.getQueryString());
                    LOGGER_ERR.error(log.parseLog(e.getMessage()));
                }
            }

            LOGGER_WEB.info(log.parseLog());
        }

        Object obj = null;
        try {
            obj = joinPoint.proceed();
            invokeTree.exit();
            baseLog = baseLog.now(System.currentTimeMillis())
                    .logType(LogType.parseResponse(logType))
                    .msName(MSName.valueOf(msName))
                    .invokeTree(invokeTree);

            LOGGER_WEB.info(baseLog.parseLog(obj));
        } catch (Throwable throwable) {
            LOGGER_ERR.error(throwable.getMessage());
            //因为这个aop在业务aop之后，在记录异常信息后继续抛出异常，由业务aop进行封装
            throw throwable;
        }
        return obj;
    }
}
