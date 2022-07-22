package com.caojx.learn;

import java.util.HashMap;
import java.util.Map;

/**
 * 平滑加权轮询
 *
 * @author caojx created on 2022/6/20 8:49 PM
 */
public class WeightRoundRobinV3 {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }

    /**
     * 动态权重
     */
    public static Map<String, Weight> currWeights = new HashMap<>();

    public static String getServer() {
        int totalWeights = ServerIps.WEIGHT_LIST.values().stream().mapToInt(w -> w).sum();

        // 初始化一个 动态权重，currWeights 默认值 0
        if (currWeights.isEmpty()) {
            ServerIps.WEIGHT_LIST.forEach((ip, weight) -> {
                currWeights.put(ip, new Weight(ip, weight, 0));
            });
        }

        // currWeight +weight 动态权重+固定权重
        for (Weight weight : currWeights.values()) {
            weight.setCurrentWeight(weight.getCurrentWeight() + weight.getWeight());
        }

        // 找最大值 max(currWeight)
        Weight maxCurrentWeight = null;
        for (Weight weight : currWeights.values()) {
            if (maxCurrentWeight == null || weight.getCurrentWeight() > maxCurrentWeight.getCurrentWeight()) {
                maxCurrentWeight = weight;
            }
        }

        // 动态变化的权重：max(currWeight)-=sum(weight)
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - totalWeights);


        return maxCurrentWeight.getIp();
    }

    /**
     * 权重
     */
    public static class Weight {
        /**
         * 服务器主机
         */
        private String ip;

        /**
         * 固定权重
         */
        private Integer weight;

        /**
         * 当前权重
         */
        private Integer currentWeight;

        public Weight(String ip, Integer weight, Integer currentWeight) {
            this.ip = ip;
            this.weight = weight;
            this.currentWeight = currentWeight;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Integer getCurrentWeight() {
            return currentWeight;
        }

        public void setCurrentWeight(Integer currentWeight) {
            this.currentWeight = currentWeight;
        }
    }
}
