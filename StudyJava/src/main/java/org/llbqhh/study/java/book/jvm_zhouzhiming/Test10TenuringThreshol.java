package org.llbqhh.study.java.book.jvm_zhouzhiming;

/**
 * 长期存活的对象将进入老年代
 * VM参数： -XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 */
public class Test10TenuringThreshol {
    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("unused")
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4]; // 什么时候进入老年代取决于-XX:MaxTenuringThreshold设置
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testTenuringThreshold();

        /*
        TODO：
        我没有测出来当-XX:MaxTenuringThreshold=15时的情况，在这种情况下的输出很奇怪，在第一次垃圾回收时new threshold=1，在第二次垃圾回收时new threshold=15
        怀疑是这里的变化使得结果和书上说得不太一样，以后相关积累多了以后可以仔细研究一下原因

        当-XX:MaxTenuringThreshold=15时，我测试的运行结果：

        [GC (Allocation Failure) [DefNew
        Desired survivor size 524288 bytes, new threshold 1 (max 15)
        - age   1:     654784 bytes,     654784 total
        : 6034K->639K(9216K), 0.0035738 secs] 6034K->4735K(19456K), 0.0035988 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
        [GC (Allocation Failure) [DefNew
        Desired survivor size 524288 bytes, new threshold 15 (max 15)
        : 4735K->0K(9216K), 0.0008265 secs] 8831K->4719K(19456K), 0.0008427 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        Heap
         def new generation   total 9216K, used 4260K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
          eden space 8192K,  52% used [0x00000007bec00000, 0x00000007bf0290f0, 0x00000007bf400000)
          from space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
          to   space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
         tenured generation   total 10240K, used 4719K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
           the space 10240K,  46% used [0x00000007bf600000, 0x00000007bfa9bf88, 0x00000007bfa9c000, 0x00000007c0000000)
         Metaspace       used 2967K, capacity 4496K, committed 4864K, reserved 1056768K
          class space    used 327K, capacity 388K, committed 512K, reserved 1048576K
         */

        /*
        下面是原书中的解释和运行结果

        分别以-XX:MaxTenuringThreshold=1和-XX:MaxTenuringThreshold=15两
        种设置来执行testTenuringThreshold()方法， 此方法中的allocation1对象需
        要256KB内存， Survivor空间可以容纳。 当MaxTenuringThreshold=1时， allocation1对象在第二
        次GC发生时进入老年代， 新生代已使用的内存GC后非常干净地变成0KB。 而
        MaxTenuringThreshold=15时， 第二次GC发生后， allocation1对象则还留在新生代Survivor空
        间， 这时新生代仍然有404KB被占用。

        运行结果：

        [GC[DefNew
        Desired Survivor size 524288 bytes,new threshold 1（ max 1）
        -age 1： 414664 bytes， 414664 total
        ： 4859K-＞ 404K（ 9216K） ， 0.0065012 secs]4859K-＞ 4500K（ 19456K） ， 0.0065283 secs][Times： user=0.02 sys=0.00， real=0.02 secs]
        [GC[DefNew
        Desired Survivor size 524288 bytes,new threshold 1（ max 1）
        ： 4500K-＞ 0K（ 9216K） ， 0.0009253 secs]8596K-＞ 4500K（ 19456K） ， 0.0009458 secs][Times： user=0.00 sys=0.00， real=0.00 secs]
        Heap
        def new generation total 9216K,used 4178K[0x029d0000， 0x033d0000， 0x033d0000）
        eden space 8192K， 51%used[0x029d0000， 0x02de4828， 0x031d0000）
        from space 1024K， 0%used[0x031d0000， 0x031d0000， 0x032d0000）
        to space 1024K， 0%used[0x032d0000， 0x032d0000， 0x033d0000）
        tenured generation total 10240K,used 4500K[0x033d0000， 0x03dd0000， 0x03dd0000）
        the space 10240K， 43%used[0x033d0000， 0x03835348， 0x03835400， 0x03dd0000）
        compacting perm gen total 12288K,used 2114K[0x03dd0000， 0x049d0000， 0x07dd0000）
        the space 12288K， 17%used[0x03dd0000， 0x03fe0998， 0x03fe0a00， 0x049d0000）
        No shared spaces configured.
        以MaxTenuringThreshold=15参数来运行的结果：
        [GC[DefNew
        Desired Survivor size 524288 bytes,new threshold 15（ max 15）
        -age 1： 414664 bytes， 414664 total
        ： 4859K-＞ 404K（ 9216K） ， 0.0049637 secs]4859K-＞ 4500K（ 19456K） ， 0.0049932 secs][Times： user=0.00 sys=0.00， real=0.00 secs]
        [GC[DefNew
        Desired Survivor size 524288 bytes,new threshold 15（ max 15）
        -age 2： 414520 bytes， 414520 total
        ： 4500K-＞ 404K（ 9216K） ， 0.0008091 secs]8596K-＞ 4500K（ 19456K） ， 0.0008305 secs][Times： user=0.00 sys=0.00， real=0.00 secs]
        Heap
        def new generation total 9216K,used 4582K[0x029d0000， 0x033d0000， 0x033d0000）
        eden space 8192K， 51%used[0x029d0000， 0x02de4828， 0x031d0000）
        from space 1024K， 39%used[0x031d0000， 0x03235338， 0x032d0000）
        to space 1024K， 0%used[0x032d0000， 0x032d0000， 0x033d0000）
        tenured generation total 10240K,used 4096K[0x033d0000， 0x03dd0000， 0x03dd0000）
        the space 10240K， 40%used[0x033d0000， 0x037d0010， 0x037d0200， 0x03dd0000）
        compacting perm gen total 12288K,used 2114K[0x03dd0000， 0x049d0000， 0x07dd0000）
        the space 12288K， 17%used[0x03dd0000， 0x03fe0998， 0x03fe0a00， 0x049d0000）
        No shared spaces configured.
         */
    }
}
