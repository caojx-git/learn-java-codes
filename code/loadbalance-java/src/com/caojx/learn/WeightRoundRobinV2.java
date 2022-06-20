package com.caojx.learn;

/**
 * 优化加权轮询
 *
 * @author caojx created on 2022/6/20 8:49 PM
 */
public class WeightRoundRobinV2 {

    public static String getServer(Integer num) {
        int totalWeights = ServerIps.WEIGHT_LIST.values().stream().mapToInt(w -> w).sum();

        Integer pos = num % totalWeights;

        for (String ip : ServerIps.WEIGHT_LIST.keySet()) {
            Integer weight = ServerIps.WEIGHT_LIST.get(ip);

            // 坐标小于权重，说明可以由该服务器处理
            if (pos < weight) {
                return ip;
            }

            // 位置坐标=位置坐标-权重
            pos = pos - weight;
        }

        return "";
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer(i));
        }
    }
}
