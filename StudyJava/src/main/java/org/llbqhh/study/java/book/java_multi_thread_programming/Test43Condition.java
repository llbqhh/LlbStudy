package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition的正确用法
 */
public class Test43Condition {
    public static void main(String[] args) throws InterruptedException {
        MyService43 myService43 = new MyService43();
        Thread t = new Thread() {
            @Override
            public void run() {
                myService43.await();
            }
        };
        t.start();
        Thread.sleep(3000);
        myService43.signal();
        /*
        调用condition的await和signal方法前必须要获得所属lock的锁，否则会抛异常IllegalMonitorStateException
        运行结果：
        await 时间为1572317198190
        signal 时间为1572317201194
         */
    }
}
class MyService43 {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void await() {
        try {
            lock.lock();
            System.out.println("await 时间为" + System.currentTimeMillis());
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 注意，异常情况下也需要自己释放锁
            lock.unlock();
        }
    }
    public void signal() {
        try {
            lock.lock();
            System.out.println("signal 时间为" + System.currentTimeMillis());
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}