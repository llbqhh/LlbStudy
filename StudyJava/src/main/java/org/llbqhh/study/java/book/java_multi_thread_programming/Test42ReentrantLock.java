package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试多线程使用ReentrantLock同步
 */
public class Test42ReentrantLock {
    public static void main(String[] args) {
        MyService42 myService42 = new MyService42();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                myService42.methodA();
            }
        };
        t1.setName("A");
        Thread t2 = new Thread() {
            @Override
            public void run() {
                myService42.methodB();
            }
        };
        t2.setName("B");
        t1.start();
        t2.start();
        /*
        调用lock()代码的线程就获得了"对象见识其"，其他线程只能等待锁被释放后再执行
        运行结果
        methodA begin threadName=A time=1572234673305
        methodA end threadName=A time=1572234676306
        methodB begin threadName=B time=1572234676306
        methodB end threadName=B time=1572234679307
         */
    }
}
class MyService42 {
    private Lock lock = new ReentrantLock();
    public void methodA() {
        try {
            lock.lock();
            System.out.println("methodA begin threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            Thread.sleep(3000);
            System.out.println("methodA end threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void methodB() {
        try {
            lock.lock();
            System.out.println("methodB begin threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            Thread.sleep(3000);
            System.out.println("methodB end threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}