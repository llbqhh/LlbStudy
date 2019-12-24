package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.Random;

/**
 * Vlatile另一个功能：禁止指令重排序
 */
public class Test23VolatileSingleton {
    public static void main(String[] args) {
        Thread[] threads = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            threads[i] = new Thread() {
                @Override
                public void run() {
                    VolatileSingleton volatileSingleton = VolatileSingleton.getInstance();
                    System.out.println(volatileSingleton.hashCode());
                    volatileSingleton.method(); // error2
                }
            };
            threads[i].start();
        }

        /*
        分析：
        如果不加volatile，有个很大的隐患。实例化对象的那行代码（标记为error的那行），实际上可以分解成以下三个步骤：
        1、分配内存空间
        2、初始化对象
        3、将对象指向刚分配的内存空间
        但是有些编译器为了性能的原因，可能会将第二步和第三步进行重排序，顺序就成了：
        1、1分配内存空间
        2、将对象指向刚分配的内存空间
        3、初始化对象
        就会导致某一个线程刚刚分配了内存空间但是还没初始化对象时，其他线程就已经认为这个对象已经初始化，从而调用了实际还没完成初始化的单例对象

        原本增加了对应的测试，即error2处的调用，想要测试未初始化成功的单例对象调用方法时报错，但是没有测试出来。

        执行结果：
        305859450
        305859450
        305859450

         */
    }

    /*

     */
}

class VolatileSingleton {
    private static volatile VolatileSingleton uniqueSingleton;

    private VolatileSingleton() {
    }

    /**
     * 用来测试没有初始化成功的单例对象调用方法时报错
     */
    public void method() {
    }

    public static VolatileSingleton getInstance() {
        if (null == uniqueSingleton) {
            try {
                Thread.sleep(new Random().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (VolatileSingleton.class) {
                if (null == uniqueSingleton) {
                    uniqueSingleton = new VolatileSingleton(); // error
                }
            }
        }
        return uniqueSingleton;
    }
}