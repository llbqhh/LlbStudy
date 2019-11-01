package org.llbqhh.study.java.book.jvm_zhouzhiming;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 测试直接内存内存溢出
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M 经测试这个参数好像无效，所以将分配单位提高到了1g
 */
public class Test06OOMDirectMemory {
    private static final int _1GB = 1024 * 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        // Unsafe类需要使用反射方法获得
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            // 分配直接内存
            unsafe.allocateMemory(_1GB);
        }
        /*
        抛出oom异常
        运行结果：
        Exception in thread "main" java.lang.OutOfMemoryError
            at sun.misc.Unsafe.allocateMemory(Native Method)
         */
    }
}
