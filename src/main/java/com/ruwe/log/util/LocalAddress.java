package com.ruwe.log.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by lipengfei on 2017/6/2.
 */
public class LocalAddress {
    private static Logger LOGGER = LoggerFactory.getLogger("init");

    private static InetAddress address;

    static {
        try {
            address=address.getLocalHost();
        } catch (UnknownHostException e) {
            LOGGER.error(e.getMessage());
        }
    }
    public static String ipAddress() {
        if (null != address) {
            return address.getHostAddress();
        }
        return "";
    }
    public static String hostName() {
        if (null != address) {
            return address.getHostName();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(LocalAddress.ipAddress());
        System.out.println(LocalAddress.hostName());
    }

}
