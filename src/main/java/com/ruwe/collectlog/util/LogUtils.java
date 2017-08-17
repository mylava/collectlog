package com.ruwe.collectlog.util;

import com.ruwe.collectlog.constant.LogType;
import com.ruwe.collectlog.context.LogContext;
import com.ruwe.collectlog.model.BaseLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipengfei on 2017/6/4.
 */
public final class LogUtils {
    private static final Logger LOG = LoggerFactory.getLogger(LogUtils.class);

    public static void info(Logger LOG, String title, Object... params) {
        if(LOG.isInfoEnabled()) {
            BaseLog baseLog = LogContext.getBaseLog(null);
            baseLog.logType(LogType.CUSTOM_INFO);
            LOG.info(baseLog.toJson(title,formatParam(params)));
        }

    }

    public static void debug(Logger LOG, String title, Object... params) {
        if(LOG.isDebugEnabled()) {
            BaseLog baseLog = LogContext.getBaseLog(null);
            baseLog.logType(LogType.CUSTOM_DEBUG);
            LOG.debug(baseLog.toJson(title,formatParam(params)));
        }

    }

    public static void warn(Logger LOG, String title, Object... params) {
        if(LOG.isWarnEnabled()) {
            BaseLog baseLog = LogContext.getBaseLog(null);
            baseLog.logType(LogType.CUSTOM_WARN);
            LOG.warn(baseLog.toJson(title,formatParam(params)));
        }
    }

    public static void error(Logger LOG, String title, Object... params) {
        if(LOG.isErrorEnabled()) {
            BaseLog baseLog = LogContext.getBaseLog(null);
            baseLog.logType(LogType.CUSTOM_ERR);
            LOG.error(baseLog.toJson(title,formatParam(params)));
        }
    }


    public static void error(Logger LOG, String title, Throwable e, Object... params) {
        if(LOG.isErrorEnabled()) {
            BaseLog baseLog = LogContext.getBaseLog(null);
            baseLog.logType(LogType.CUSTOM_ERR);
            Map<Object, Object> errMessage = new HashMap();
            errMessage.put("params",params);
            errMessage.put(title,e.getMessage());
            LOG.error(baseLog.toJson(errMessage));
        }

    }

    private static Map<Object, Object> formatParam(Object... params) {
        Map<Object, Object> param = new HashMap();
        if(null != params && params.length > 0) {
            for(int index = 0; index < params.length; index += 2) {
                Object paramName = "";
                Object paramValue = "";

                try {
                    paramName = params[index];
                    paramValue = params[index + 1];
                } catch (IndexOutOfBoundsException e) {
                    error(LOG, "日志参数格式化时数组越界.", e, params);
                }
                param.put(paramName,paramValue);
            }
            return param;
        } else {
            return param;
        }
    }


}
