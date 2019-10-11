package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.Random;

/**
 * 线程优先级说明
 */
public class Test08Priority {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            PriorityTest01 pt01 = new PriorityTest01();
            pt01.setPriority(java.lang.Thread.NORM_PRIORITY);
            PriorityTest02 pt02 = new PriorityTest02();
            pt02.setPriority(java.lang.Thread.MAX_PRIORITY);

            pt01.start();
            pt02.start();
            /*
            可以看到，从整体上看，大部分到pt02线程是先于pt01线程执行完的
            这就是线程优先级的规则性
            运行结果：
            ---------- thread 1 use time=97
            ========== thread 2 use time=97
            ---------- thread 1 use time=98
            ========== thread 2 use time=98
            ========== thread 2 use time=104
            ========== thread 2 use time=106
            ---------- thread 1 use time=105
            ---------- thread 1 use time=107
            ---------- thread 1 use time=107
            ========== thread 2 use time=107
             */
        }
    }
}

class PriorityTest01 extends Thread {
    @Override
    public void run() {
        super.run();
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Random random = new Random();
            random.nextInt();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("---------- thread 1 use time=" + (endTime - beginTime));
    }
}

class PriorityTest02 extends Thread {
    @Override
    public void run() {
        super.run();
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Random random = new Random();
            random.nextInt();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("========== thread 2 use time=" + (endTime - beginTime));
    }
}