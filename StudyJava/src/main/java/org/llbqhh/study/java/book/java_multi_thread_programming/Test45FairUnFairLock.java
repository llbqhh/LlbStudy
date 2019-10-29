package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁与非公平锁
 */
public interface Test45FairUnFairLock {
    public static void main(String[] args) {
        // 测试公平锁
        testFair(true);
        // 测试非公平锁
        testFair(false);
        /*
        从结果来看，公平锁更容易呈现有序状态，非公平锁更容易出现乱序的结果
        输出结果：
        test fair:true
        ThreadName=Thread-0获得锁定
        ThreadName=Thread-1获得锁定
        ThreadName=Thread-2获得锁定
        ThreadName=Thread-3获得锁定
        ThreadName=Thread-4获得锁定
        test fair:false
        ThreadName=Thread-0获得锁定
        ThreadName=Thread-2获得锁定
        ThreadName=Thread-1获得锁定
        ThreadName=Thread-3获得锁定
        ThreadName=Thread-4获得锁定
         */
    }

    public static void testFair(boolean isFair) {
        System.out.println("test fair:" + isFair);
        MyService45 myService45 = new MyService45(isFair);
        Thread[] tArr = new Thread[5];
        for (int i = 0; i < 5; i++) {
            tArr[i] = new Thread() {
                @Override
                public void run() {
                    myService45.serviceMethod();
                }
            };
            tArr[i].setName("Thread-" + i);
        }
        for (int i = 0; i < 5; i++) {
            tArr[i].start();
        }
        for (int i = 0; i < 5; i++) {
            try {
                tArr[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class MyService45 {
    private ReentrantLock lock;
    public MyService45(boolean isFair) {
        lock = new ReentrantLock(isFair);
    }
    public void serviceMethod() {
        try {
            lock.lock();
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "获得锁定");
        } finally {
            lock.unlock();
        }
    }
}
