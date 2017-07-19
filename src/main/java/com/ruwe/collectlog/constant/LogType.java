package com.ruwe.collectlog.constant;

/**
 * Created by lipengfei on 2017/6/6.
 */
public enum LogType {
    API_REQUEST("API调用"),
    API_RESPONSE("API响应"),
    //是否加入 process usercenter 类型
    @Deprecated
    PROCESS_REQUEST("PROCESS调用"),
    @Deprecated
    PROCESS_RESPONSE("PROCESS响应"),
    SERVICE_REQUEST("SERVICE调用"),
    SERVICE_RESPONSE("SERVICE响应"),
    HANDLER_REQUEST("HANDLER调用"),
    HANDLER_RESPONSE("HANDLER响应"),
    DB_REQUEST("DB调用"),
    DB_RESPONSE("DB响应"),
    JOB_START("JOB开始"),
    JOB_END("JOB结束"),
    CUSTOM_DEBUG("自定义DEBUG"),
    CUSTOM_INFO("自定义INFO"),
    CUSTOM_WARN("自定义WARN"),
    CUSTOM_ERR("自定义ERROR"),
    OPEN_REQUEST("第三方调用"),
    OPEN_RESPONSE("第三方响应");

    private String description;

    LogType(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public static LogType parseRequest(String value) {
        LogType result = null;
        switch (value) {
            case "API" :
                result = LogType.API_REQUEST;
                break;
            case "PROCESS" :
                result = LogType.PROCESS_REQUEST;
                break;
            case "SERVICE" :
                result = LogType.SERVICE_REQUEST;
                break;
            case "HANDLER" :
                result = LogType.HANDLER_REQUEST;
                break;
            case "DB" :
                result = LogType.DB_REQUEST;
                break;
            case "JOB" :
                result = LogType.JOB_START;
                break;
            case "OPEN" :
                result = LogType.OPEN_REQUEST;
                break;
            default:
                result = LogType.API_REQUEST;
                break;
        }
        return  result;
    }

    public static LogType parseResponse(String value) {
        LogType result = null;
        switch (value) {
            case "API" :
                result = LogType.API_RESPONSE;
                break;
            case "PROCESS" :
                result = LogType.PROCESS_RESPONSE;
                break;
            case "SERVICE" :
                result = LogType.SERVICE_RESPONSE;
                break;
            case "HANDLER" :
                result = LogType.HANDLER_RESPONSE;
                break;
            case "DB" :
                result = LogType.DB_RESPONSE;
                break;
            case "JOB" :
                result = LogType.JOB_END;
                break;
            case "OPEN" :
                result = LogType.OPEN_RESPONSE;
                break;
            default:
                result = LogType.API_RESPONSE;
                break;
        }
        return  result;
    }

}
