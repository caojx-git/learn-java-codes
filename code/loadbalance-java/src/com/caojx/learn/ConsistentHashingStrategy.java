package com.caojx.learn;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性哈希策略 Demo
 */
public class ConsistentHashingStrategy {
    public static void main(String[] args) {
        // 模拟 Server 地址列表
        String[] serverList = {"192.168.0.15", "192.168.0.30", "192.168.0.45"};
        // 新建 TreeMap 集合 ，以 Key，Value 的方式绑定 Hash 值与地址
        SortedMap<Integer, String> serverHashMap = new TreeMap<>();
        // 计算 Server 地址的 Hash 值
        for (String address : serverList) {
            int serverHash = Math.abs(address.hashCode());
            // 绑定 Hash 值与地址
            serverHashMap.put(serverHash, address);
        }
        // 模拟 Request 地址
        String[] requestList = {"192.168.0.10", "192.168.0.20", "192.168.0.40", "192.168.0.50"};
        // 计算 Request 地址的 Hash 值
        for (String request : requestList) {
            int requestHash = Math.abs(request.hashCode());
            // 在 serverHashMap 中寻找所有大于 requestHash 的 key
            SortedMap<Integer, String> tailMap = serverHashMap.tailMap(requestHash);
            //如果有大于 requestHash 的 key， 第一个 key 就是离 requestHash 最近的 serverHash
            if (!tailMap.isEmpty()) {
                Integer key = tailMap.firstKey();
                // 根据 key 获取 Server address
                String address = serverHashMap.get(key);
                System.out.println("请求 " + request + " 被分配给服务 " + address);
            } else {
                // 如果 serverHashMap 中没有比 requestHash 大的 key
                // 则直接在 serverHashMap 取第一个服务
                Integer key = serverHashMap.firstKey();
                // 根据 key 获取 Server address
                String address = serverHashMap.get(key);
                System.out.println("请求 " + request + " 被分配给服务 " + address);
            }
        }
    }
}
