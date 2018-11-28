package com.pop.test.jvm;

/**
 * Created by peng on 2017-03-29.
 */
public class Test {
    private static final int _1MB = 1024 * 1024;

    /**
     * -verbose:gc
     -Xms20M
     -Xmx20M
     -Xmn10M
     -XX:+PrintGCDetails
     -XX:SurvivorRatio=8
     -XX:MaxTenuringThreshold=15
     -XX:+UseSerialGC
     * @param args
     */
    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[_1MB / 4];
        System.out.println("..................1");
        allocation2 = new byte[_1MB / 4];
        System.out.println("..................2");
        allocation3 = new byte[_1MB * 4];
        System.out.println("..................3");
        allocation4 = new byte[_1MB * 4];
        System.out.println("..................4");

    }
}
