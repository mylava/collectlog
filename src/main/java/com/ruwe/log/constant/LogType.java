package com.ruwe.log.constant;

/**
 * Created by lipengfei on 2017/6/6.
 */
public enum LogType {
    API_REQUEST("API调用"),
    API_RESPONSE("API响应"),
    //是否加入 process usercenter 类型
    PROCESS_REQUEST("PROCESS调用"),
    PROCESS_RESPONSE("PROCESS响应"),
    DB_REQUEST("DB调用"),
    DB_RESPONSE("DB响应"),
    JOB_START("job开始"),
    JOB_END("job结束"),
    CUSTOM_DEBUG("自定义DEBUG"),
    CUSTOM_INFO("自定义INFO"),
    CUSTOM_WARN("自定义WARN"),
    CUSTOM_ERR("自定义ERROR"),
    THIRD_REQUEST("第三方调用"),
    THIRD_RESPONSE("第三方响应");

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
            case "DB" :
                result = LogType.DB_REQUEST;
                break;
            case "JOB" :
                result = LogType.JOB_START;
                break;
            case "THIRD" :
                result = LogType.THIRD_REQUEST;
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
            case "DB" :
                result = LogType.DB_RESPONSE;
                break;
            case "JOB" :
                result = LogType.JOB_END;
                break;
            case "THIRD" :
                result = LogType.THIRD_RESPONSE;
                break;
            default:
                result = LogType.API_RESPONSE;
                break;
        }
        return  result;
    }

}
