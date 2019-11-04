package org.llbqhh.study.java.book.jvm_zhouzhiming;

/**
 * 动态对象年龄判定
 * VM参数： -XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
 */
public class Test11TenuringThreshol2 {

    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("unused")
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        // allocation1+allocation2大于survivo空间一半
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[_1MB / 4];

        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testTenuringThreshold();

        /*
        下面是原书中的解释和运行结果

        设置-XX:MaxTenuringThreshold=15， 会发现运行结果中Survivor的空间占用仍然为0%， 而老年代比预
        期增加了6%， 也就是说， allocation1、 allocation2对象都直接进入了老年代， 而没有等到15
        岁的临界年龄。 因为这两个对象加起来已经到达了512KB， 并且它们是同年的， 满足同年对
        象达到Survivor空间的一半规则。 我们只要注释掉其中一个对象new操作， 就会发现另外一个
        就不会晋升到老年代中去了。

        运行结果：
        [GC[DefNew
        Desired Survivor size 524288 bytes,new threshold 1（ max 15）
        -age 1： 676824 bytes， 676824 total
        ： 5115K-＞ 660K（ 9216K） ， 0.0050136 secs]5115K-＞ 4756K（ 19456K） ， 0.0050443 secs][Times： user=0.00 sys=0.01， real=0.01 secs]
        [GC[DefNew
        Desired Survivor size 524288 bytes,new threshold 15（ max 15）
        ： 4756K-＞ 0K（ 9216K） ， 0.0010571 secs]8852K-＞ 4756K（ 19456K） ， 0.0011009 secs][Times： user=0.00 sys=0.00， real=0.00 secs]
        Heap
        def new generation total 9216K,used 4178K[0x029d0000， 0x033d0000， 0x033d0000）
        eden space 8192K， 51%used[0x029d0000， 0x02de4828， 0x031d0000）
        from space 1024K， 0%used[0x031d0000， 0x031d0000， 0x032d0000）
        to space 1024K， 0%used[0x032d0000， 0x032d0000， 0x033d0000）
        tenured generation total 10240K,used 4756K[0x033d0000， 0x03dd0000， 0x03dd0000）
        the space 10240K， 46%used[0x033d0000， 0x038753e8， 0x03875400， 0x03dd0000）
        compacting perm gen total 12288K,used 2114K[0x03dd0000， 0x049d0000， 0x07dd0000）
        the space 12288K， 17%used[0x03dd0000， 0x03fe09a0， 0x03fe0a00， 0x049d0000）
        No shared spaces configured.
         */
    }
}
