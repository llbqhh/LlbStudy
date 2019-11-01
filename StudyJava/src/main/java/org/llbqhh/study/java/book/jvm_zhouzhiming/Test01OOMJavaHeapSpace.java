package org.llbqhh.study.java.book.jvm_zhouzhiming;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存OOM测试
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class Test01OOMJavaHeapSpace {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        while(true) {
            list.add(new Object());
        }
        /*
        由于设置了内存大小为20m，所以程序很快报错退出，并将堆快照存在了本地文件中
        运行结果：
        java.lang.OutOfMemoryError: Java heap space
        Dumping heap to java_pid74966.hprof ...
        Heap dump file created [27795694 bytes in 0.090 secs]
        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            at java.util.Arrays.copyOf(Arrays.java:3210)
         */
    }
}