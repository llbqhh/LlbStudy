package org.llbqhh.study.java.book.jvm_zhouzhiming;

/**
 * 我们使用Serial垃圾收集器来测试
 * VM参数：-XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 */
public class Test08Allocation {
    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; //出现一次Minor GC
    }

    public static void main(String[] args) throws InterruptedException {
        testAllocation();

        /*
        尝试分配3个2MB大小和1个4MB大小的对象，
        在运行时通过-Xms20M、 -Xmx20M、 -Xmn10M这3个参数限制了Java堆大小为20MB， 不可扩
        展， 其中10MB分配给新生代， 剩下的10MB分配给老年代。 -XX： SurvivorRatio=8决定了新
        生代中Eden区与一个Survivor区的空间比例是8:1， 从输出的结果也可以清晰地看到“eden
        space 8192K、 from space 1024K、 to space 1024K”的信息， 新生代总可用空间为
        9216KB（ Eden区+1个Survivor区的总容量） 。

        执行testAllocation（ ） 中分配allocation4对象的语句时会发生一次Minor GC， 这次GC的
        结果是新生代6651KB变为148KB， 而总内存占用量则几乎没有减少（ 因为allocation1、
        allocation2、 allocation3三个对象都是存活的， 虚拟机几乎没有找到可回收的对象） 。 这次
        GC发生的原因是给allocation4分配内存的时候， 发现Eden已经被占用了6MB， 剩余空间已不
        足以分配allocation4所需的4MB内存， 因此发生Minor GC。 GC期间虚拟机又发现已有的3个
        2MB大小的对象全部无法放入Survivor空间（ Survivor空间只有1MB大小） ， 所以只好通过分
        配担保机制提前转移到老年代去。
        这次GC结束后， 4MB的allocation4对象顺利分配在Eden中， 因此程序执行完的结果是
        Eden占用4MB（ 被allocation4占用） ， Survivor空闲， 老年代被占用6MB（ 被allocation1、allocation2、 allocation3占用） 。

        运行结果：
        [GC (Allocation Failure) [DefNew: 7991K->407K(9216K), 0.0045155 secs] 7991K->6552K(19456K), 0.0045412 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        Heap
         def new generation   total 9216K, used 4669K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
          eden space 8192K,  52% used [0x00000007bec00000, 0x00000007bf0297a0, 0x00000007bf400000)
          from space 1024K,  39% used [0x00000007bf500000, 0x00000007bf565ff0, 0x00000007bf600000)
          to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
         tenured generation   total 10240K, used 6144K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
           the space 10240K,  60% used [0x00000007bf600000, 0x00000007bfc00030, 0x00000007bfc00200, 0x00000007c0000000)
         Metaspace       used 3010K, capacity 4496K, committed 4864K, reserved 1056768K
          class space    used 328K, capacity 388K, committed 512K, reserved 1048576K

         */
    }
}
