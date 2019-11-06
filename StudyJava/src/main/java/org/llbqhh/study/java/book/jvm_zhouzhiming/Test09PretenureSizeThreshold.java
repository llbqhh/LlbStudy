package org.llbqhh.study.java.book.jvm_zhouzhiming;

/**
 * 我们使用Serial垃圾收集器来测试：大对象直接进入老年代
 * VM参数： -XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 */
public class Test09PretenureSizeThreshold {
    private static final int _1MB = 1024 * 1024;

    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB]; //直接分配在老年代中
    }

    public static void main(String[] args) {
        testPretenureSizeThreshold();

        /*
        我们看到Eden空间几乎没有
        被使用， 而老年代的10MB空间被使用了40%， 也就是4MB的allocation对象直接就分配在老
        年代中， 这是因为PretenureSizeThreshold被设置为3MB（ 就是3145728， 这个参数不能像-Xmx
        之类的参数一样直接写3MB） ， 因此超过3MB的对象都会直接在老年代进行分配。 注意
        PretenureSizeThreshold参数只对Serial和ParNew两款收集器有效， Parallel Scavenge收集器不
        认识这个参数， Parallel Scavenge收集器一般并不需要设置。 如果遇到必须使用此参数的场
        合， 可以考虑ParNew加CMS的收集器组合。

        运行结果：
        Heap
         def new generation   total 9216K, used 1682K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
          eden space 8192K,  20% used [0x00000007bec00000, 0x00000007beda49b0, 0x00000007bf400000)
          from space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
          to   space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
         tenured generation   total 10240K, used 4096K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
           the space 10240K,  40% used [0x00000007bf600000, 0x00000007bfa00010, 0x00000007bfa00200, 0x00000007c0000000)
         Metaspace       used 2943K, capacity 4496K, committed 4864K, reserved 1056768K
          class space    used 320K, capacity 388K, committed 512K, reserved 1048576K
         */
    }
}
