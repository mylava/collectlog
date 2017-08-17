package com.ruwe.collectlog.test;

import com.ruwe.collectlog.model.RequestLog;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author lipengfei
 */
public class Analysis {
    public static void main(String[] args) {
        String line = FileUtil.readJsonFile("/Users/lipengfei/Desktop/test.txt");
        line = line.substring(line.indexOf("{"));


        RequestLog requestLog = RequestLog.toRequestLog(line);
        Map<String, String[]> params = requestLog.getParams();
        Set<String> set = params.keySet();
        for (String key:set) {

            System.out.println(Arrays.toString(params.get(key)));
        }
    }
}
