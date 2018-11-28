package com.pop.test.jvm

import spock.lang.Specification

/**
 * Created by peng on 2017-03-29.
 */
class Memory extends Specification{
    int _1MB = 1024*1024;

    /**
     * -verbose:gc
     -Xms20M
     -Xmx20M
     -Xmn10M
     -XX:+PrintGCDetails
     -XX:SurvivorRatio=8
     -XX:MaxTenuringThreshold=15
     -XX:+UseSerialGC
     */
    def 'testTenuringThreshold2'() {

        when:
        println("....................start");
        def allocation1 = new byte[_1MB / 4];
        println("....................1");
        def allocation2 = new byte[_1MB / 4];
        println("....................2");
        def allocation3 = new byte[_1MB * 4];
        println("....................3");

        then:
        1==1
    }

}
