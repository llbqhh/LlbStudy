package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock简单测试
 */
public class Test47ReentrantReadWriteLock {
    public static void main(String[] args) {
        MyService47 myService47 = new MyService47();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                myService47.read();
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                myService47.read();
//                myService47.write();
            }
        };
        t1.start();
        t2.start();
        /*
        由于读读不互斥，所以两个线程同时获得读锁
        如果将t2线程读read方法改为write方法，则两个线程变为竞争关系，只能一个执行完另一个才能执行

        运行结果：
        获得读锁 Thread-1 1572332543264
        获得读锁 Thread-0 1572332543264

        修改t2线程为write方法后，运行结果：
        获得读锁 Thread-0 1572332603008
        获得写锁 Thread-1 1572332606010
         */
    }
}
class MyService47 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public void read() {
        try {
            lock.readLock().lock();
            System.out.println("获得读锁 " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
    public void write() {
        try {
            lock.writeLock().lock();
            System.out.println("获得写锁 " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}