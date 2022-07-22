package com.caojx.learn;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性哈希策略 Demo
 */
public class ConsistentHashing {

    public static void main(String[] args) {
        String[] requestIps = {"192.168.0.8", "192.168.0.18", "192.168.0.28"};
        for(String requestIp: requestIps){
            String address = getServer(requestIp);
            System.out.println("请求 " + requestIp + " 被分配给服务 " + address);
        }
    }

    public static String getServer(String requestId) {
        // 新建 TreeMap 集合 ，以 Key，Value 的方式绑定 Hash 值与地址
        SortedMap<Integer, String> serverHashMap = new TreeMap<>();
        // 计算 Server 地址的 Hash 值
        for (String address : ServerIps.LIST_IP) {
            int serverHash = Math.abs(address.hashCode());
            // 绑定 Hash 值与地址
            serverHashMap.put(serverHash, address);
        }

        int requestHash = Math.abs(requestId.hashCode());
        // 在 serverHashMap 中寻找所有大于 requestHash 的 key
        SortedMap<Integer, String> tailMap = serverHashMap.tailMap(requestHash);
        //如果有大于 requestHash 的 key， 第一个 key 就是离 requestHash 最近的 serverHash
        if (!tailMap.isEmpty()) {
            Integer key = tailMap.firstKey();
            // 根据 key 获取 Server address
            return serverHashMap.get(key);
        } else {
            // 如果 serverHashMap 中没有比 requestHash 大的 key
            // 则直接在 serverHashMap 取第一个服务
            Integer key = serverHashMap.firstKey();
            // 根据 key 获取 Server address
            return serverHashMap.get(key);
        }
    }
}

