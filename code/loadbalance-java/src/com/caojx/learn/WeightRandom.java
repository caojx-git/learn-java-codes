package com.caojx.learn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 加权随机
 *
 * @author caojx created on 2022/6/20 8:49 PM
 */
public class WeightRandom {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }

    /**
     * 缺点权重大ips越大占内存带权重随机
     *
     * @return
     */
    public static String getServer() {

        List<String> ips = new ArrayList<>();

        for (String ip : ServerIps.WEIGHT_MAP.keySet()) {
            Integer weight = ServerIps.WEIGHT_MAP.get(ip);

            // weight多少在ips里面存多少  例A权重为2 在ips里面存两个
            for (int i = 0; i < weight; i++) {
                ips.add(ip);
            }
        }

        // 随机取，这里说的是概率
        Random random = new Random();
        int randomPos = random.nextInt(ips.size());
        return ips.get(randomPos);
    }
}
