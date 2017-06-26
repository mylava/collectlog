package com.ruwe.collectlog.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.ruwe.collectlog.constant.LogType;
import com.ruwe.collectlog.constant.MSName;
import com.ruwe.collectlog.context.InvokeTree;
import com.ruwe.collectlog.util.IdGenerator;
import com.ruwe.collectlog.util.LocalAddress;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础日志Bean
 * Created by lipengfei on 2017/6/6.
 */
public class BaseLog implements Serializable{
    //日志ID，和TraceId一致，用于调用链追踪
    protected String id;
    //日志类型
    protected LogType logType;
    //当前时间(记录调用时间、响应时间等)
    protected Long now;
    //所在服务的IP
    protected String localIp;
    //所在服务的主机名
    protected String localHostName;
    //微服务名称
    protected MSName msName;
    //调用链对象
    protected InvokeTree invokeTree;

    private static IdGenerator idGenerator = new IdGenerator(0,0);

    //设置调用链转换JSONString时的格式
    private static ValueFilter valueFilter = new ValueFilter() {
        @Override
        public Object process(Object object, String name, Object value) {
            if(name.equals("invokeTree")){
                BaseLog baseLog = (BaseLog) object;
                return baseLog.getInvokeTree().toString();
            }
            return value;
        }
    };

    BaseLog() {
    }

    public String getId() {
        return id;
    }

    public BaseLog id(String id) {
        this.id = id;
        return this;
    }

    public LogType getLogType() {
        return logType;
    }

    public BaseLog logType(LogType logType) {
        this.logType = logType;
        return this;
    }

    public Long getNow() {
        return now;
    }

    public BaseLog now(Long now) {
        this.now = now;
        return this;
    }

    public String getLocalIp() {
        return localIp;
    }

    private BaseLog localIp(String localIp) {
        this.localIp = localIp;
        return this;
    }
    public String getLocalHostName() {
        return localHostName;
    }

    private BaseLog localHostName(String localHostName) {
        this.localHostName = localHostName;
        return this;
    }

    public MSName getMsName() {
        return msName;
    }

    public BaseLog msName(MSName msName) {
        this.msName = msName;
        return this;
    }

    public InvokeTree getInvokeTree() {
        return invokeTree;
    }

    public BaseLog invokeTree(InvokeTree invokeTree) {
        this.invokeTree = invokeTree;
        return this;
    }

    public static BaseLog build(String traceId){
        if (null != traceId && !traceId.isEmpty()) {
            return build().id(traceId);
        } else {
            return build().id(String.valueOf(idGenerator.nextId()));
        }
    }

    private static BaseLog build(){
        return new BaseLog()
                .localIp(LocalAddress.ipAddress())
                .localHostName(LocalAddress.hostName())
                .invokeTree(new InvokeTree());
    }

    /**
     * 将BaseLog对象转换成JSONString
     * @return
     */
    public String parseLog() {
        Map<String,Object> map = new HashMap<>();
        map.put(this.getClass().getName(),this);
        return JSON.toJSONString(map, valueFilter);
    }

    /**
     * 将BaseLog对象转换成JSONString
     * @param obj 表示补充对象
     * @return
     */
    public String parseLog(Object obj) {
        Map<String,Object> map = new HashMap<>();
        map.put(this.getClass().getName(),this);
        if (null != obj)
            map.put(obj.getClass().getName(),obj);
        return JSON.toJSONString(map, valueFilter);
    }

    /**
     * 将BaseLog对象转换成JSONString
     * @param title 补充对象的描述
     * @param replenish 补充对象
     * @return
     */
    public String parseLog(String title, Map<Object,Object> replenish) {
        Map<String,Object> map = new HashMap<>();
        map.put(this.getClass().getName(),this);
        if (null != replenish)
            map.put(title,replenish);
        return JSON.toJSONString(map, valueFilter);
    }

  /*  public String parseLog(Object obj, Map<String,String> replenish) {
        Map<String,Object> map = new HashMap<>();
        map.put(this.getClass().getName(),this);
        if (null != obj)
            map.put(obj.getClass().getName(),obj);
        if (null != replenish)
            map.put("replenish",replenish);
        return JSON.toJSONString(map, valueFilter);
    }*/

}
