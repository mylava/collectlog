package com.ruwe.collectlog.aspect;

import com.ruwe.collectlog.constant.LogType;
import com.ruwe.collectlog.constant.MSName;
import com.ruwe.collectlog.context.InvokeTree;
import com.ruwe.collectlog.context.LogContext;
import com.ruwe.collectlog.model.BaseLog;
import com.ruwe.collectlog.model.DeviceInfo;
import com.ruwe.collectlog.model.RequestLog;
import com.ruwe.collectlog.model.UserInfo;
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
            DeviceInfo deviceInfo = new DeviceInfo()
                    .clientVersion(request.getParameter("clientVersion"))
                    .equipment(request.getParameter("equipment"))
                    .gps(request.getParameter("gps"))
                    .imei(request.getParameter("imei"))
                    .network(request.getParameter("network"))
                    .platform(request.getParameter("platform"))
                    .wifi(request.getParameter("wifi"));

            UserInfo userInfo = new UserInfo()
                    .cookie(request.getHeader("Cookie"))
                    .sessionId(request.getParameter("sessionId"));

            RequestLog log = RequestLog.build(baseLog)
                    .now(System.currentTimeMillis())
                    .logType(LogType.parseRequest(logType))
                    .msName(MSName.valueOf(msName))
                    .invokeTree(invokeTree)
                    .host(request.getHeader("Host"))
                    .serverName(request.getServerName())
                    .referer(request.getHeader("Referer"))
                    .userAgent(request.getHeader("User-Agent"))
                    .clientIp(request.getRemoteAddr())
                    .contentType(request.getContentType())
                    .method(request.getMethod())
                    .requestURI(request.getRequestURI())
                    .requestURL(request.getRequestURL().toString())
                    .contentPath(request.getContextPath())
                    .servletPath(request.getServletPath())
                    .params(request.getParameterMap())
                    .deviceInfo(deviceInfo)
                    .userInfo(userInfo);


            if (null != request.getQueryString() && !"".equals(request.getQueryString())) {
                try {
                    log.queryString(URLDecoder.decode(request.getQueryString(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    log.queryString(request.getQueryString());
                    LOGGER_ERR.error(log.toJson(e.getMessage()));
                }
            }

            LOGGER_WEB.info(log.toJson());
        }

        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOGGER_ERR.error(throwable.getMessage());
            //因为这个aop在业务aop之后，在记录异常信息后继续抛出异常，由业务aop进行封装
            throw throwable;
        } finally {
            RequestLog requestLog = RequestLog.build(baseLog)
                    .now(System.currentTimeMillis())
                    .logType(LogType.parseResponse(logType))
                    .msName(MSName.valueOf(msName))
                    .invokeTree(invokeTree);
            LOGGER_WEB.info(requestLog.toJson());

            invokeTree.exit();
            baseLog.invokeTree(invokeTree);
        }
        return obj;
    }
}
