package com.caojx.learn;

import java.util.Random;

/**
 * 随机轮询
 *
 * @author caojx created on 2022/6/20 8:34 PM
 */
public class RandomSelect {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }

    /**
     * 随机访问集合中的某一个值即可
     *
     * @return
     */
    public static String getServer() {
        Random random = new Random();
        int rand = random.nextInt(ServerIps.LIST.size());

        // 随机访问集合中的某一个值即可
        return ServerIps.LIST.get(rand);
    }
}
