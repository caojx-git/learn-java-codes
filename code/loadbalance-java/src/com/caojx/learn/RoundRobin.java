package com.caojx.learn;

/**
 * 轮询
 *
 * @author caojx created on 2022/6/20 8:38 PM
 */
public class RoundRobin {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getServer());
        }
    }

    // 位置
    public static Integer POS = 0;

    /**
     * 轮询按序遍历即可
     *
     * @return
     */
    public static String getServer() {
        String ip = null;

        synchronized (POS) {
            if (POS >= ServerIps.LIST.size()) {
                POS = 0;
            }

            ip = ServerIps.LIST.get(POS);
            POS++;
        }

        return ip;
    }

}
