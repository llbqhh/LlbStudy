package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition实现三种输出按顺序打印
 */
public class Test46ConditionSequentialPrint {
    private static volatile int nextPrintWho = 1;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();

    public static void main(String[] args) {
        Thread a = new Thread() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    while (nextPrintWho != 1) {
                        conditionA.await();
                    }
                    System.out.println("ThreadA...");
                    nextPrintWho = 2;
                    conditionB.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };
        Thread b = new Thread() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    while (nextPrintWho != 2) {
                        conditionB.await();
                    }
                    System.out.println("ThreadB...");
                    nextPrintWho = 3;
                    conditionC.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };
        Thread c = new Thread() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    while (nextPrintWho != 3) {
                        conditionC.await();
                    }
                    System.out.println("ThreadC...");
                    nextPrintWho = 1;
                    conditionA.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };
        Thread[] aArr = new Thread[5];
        Thread[] bArr = new Thread[5];
        Thread[] cArr = new Thread[5];
        for (int i = 0; i < 5; i++) {
            aArr[i] = new Thread(a);
            bArr[i] = new Thread(b);
            cArr[i] = new Thread(c);
            aArr[i].start();
            bArr[i].start();
            cArr[i].start();
        }
        /*
        通过使用不同的Condition，可以使输出按照一定顺序执行
        输出结果：
        ThreadA...
        ThreadB...
        ThreadC...
        ThreadA...
        ThreadB...
        ThreadC...
        ThreadA...
        ThreadB...
        ThreadC...
        ThreadA...
        ThreadB...
        ThreadC...
        ThreadA...
        ThreadB...
        ThreadC...
         */
    }
}
