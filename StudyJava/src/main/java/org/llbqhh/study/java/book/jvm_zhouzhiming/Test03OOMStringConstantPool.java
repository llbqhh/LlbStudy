package org.llbqhh.study.java.book.jvm_zhouzhiming;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试常量池溢出
 * jdk1.6 VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * jdk1.8 VM Args: -Xmx20m -Xms20m
 */
public class Test03OOMStringConstantPool {
    public static void main(String[] args) {
        List list = new ArrayList();
        long i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
        /*
        此示例需要在1.6中测试，
        在1.8中已经去掉了PermSize和MaxPermSize这两个参数，并且常量池也已经改到了堆中

        jdk1.6运行结果：
        Exception in thread"main"java.lang.OutOfMemoryError： PermGen space
        at java.lang.String.intern（ Native Method）

        jdk1.8运行结果：
        Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
         */
    }
}
