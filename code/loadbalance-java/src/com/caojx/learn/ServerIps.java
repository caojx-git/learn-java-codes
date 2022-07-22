package com.caojx.learn;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务器id
 *
 * @author caojx created on 2022/6/20 8:34 PM
 */
public class ServerIps {

    /**
     * A、B、C 假设是三台服务器
     */
    public static final List<String> LIST = Arrays.asList("A", "B", "C");

    /**
     * 三台服务器的ip地址
     */
    public static final List<String> LIST_IP = Arrays.asList("192.168.0.10", "192.168.0.20", "192.168.0.40", "192.168.0.50");

    /**
     * A、B、C 服务器，带权重
     * 即假如10次请求，A要请求2次，B要请求3次，C要请求5次
     */
    public static final Map<String, Integer> WEIGHT_LIST = new LinkedHashMap<>();
    static {
        WEIGHT_LIST.put("A", 2);
        WEIGHT_LIST.put("B", 3);
        WEIGHT_LIST.put("C", 5);
    }
}
