package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于lock、condition的交替打印示例
 */
public class Test44AlternatePrint {
    public static void main(String[] args) {
        MyService44 myService44 = new MyService44();
        Thread[] a = new Thread[10];
        Thread[] b = new Thread[10];
        for (int i = 0; i < 10; i++) {
            a[i] = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        myService44.set();
                    }
                }
            };
            b[i] = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        myService44.get();
                    }
                }
            };
            a[i].start();
            b[i].start();
        }
        /*
        可以看出，【打印-】和【打印=】是交替出现的
        运行结果：
        打印=
        有可能==连续
        打印-
        有可能--连续
        打印=
        有可能==连续
        打印-
         */
    }
}
class MyService44 {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean hasValue = false;
    public void set() {
        try {
            lock.lock();
            while (hasValue == true) {
                System.out.println("有可能--连续");
                condition.await();
            }
            System.out.println("打印-");
            hasValue = true;
            // 这里如果使用signal，则会出现由于仅通知同类线程而造成程序卡住的情况
            condition.signalAll();
            // 如果不注释掉下面两行，则从这里的输出可以看出，condition.signalAll()也是需要执行完lock.unlock才会真正释放锁
//            Thread.sleep(5000);
//            System.out.println("unlock...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void get() {
        try {
            lock.lock();
            while (hasValue == false) {
                System.out.println("有可能==连续");
                condition.await();
            }
            System.out.println("打印=");
            hasValue = false;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}