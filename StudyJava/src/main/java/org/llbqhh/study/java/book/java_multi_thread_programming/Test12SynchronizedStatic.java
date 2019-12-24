package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 静态方法同步是锁class
 */
public class Test12SynchronizedStatic {
    public static void main(String[] args) {
        SynchronizedStaticTest test = new SynchronizedStaticTest();

        // t1线程调用静态同步方法
        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                SynchronizedStaticTest.printA();
            }
        };

        // t2线程也是调用的静态同步方法
        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                test.printB();
            }
        };

        // t3线程调用的是非静态的同步方法
        Thread t3 = new Thread() {
            @Override
            public void run() {
                super.run();
                test.printC();
            }
        };

        t1.start();
        t2.start();
        t3.start();
        /*
        由于t1,t2线程调用的都是SynchronizedStaticTest类的静态同步方法，获取的都是对class的锁，所以他们互相之间是同步的效果
        而t3线程调用的是非静态的同步方法，获取的是对象的锁，和class锁不是同一个，所以他可以和其他两个线程异步执行
        运行结果：
        线程Thread-0在1570861548795进入printA
        线程Thread-2在1570861548795进入printC
        线程Thread-2在1570861551816离开printC
        线程Thread-0在1570861551816离开printA
        线程Thread-1在1570861551817进入printB
        线程Thread-1在1570861554821离开printB
         */
    }
}

class SynchronizedStaticTest {
    public static synchronized void printA() {
        try {
            System.out.println(String.format("线程%s在%s进入printA", Thread.currentThread().getName(), System.currentTimeMillis()));
            Thread.sleep(3000);
            System.out.println(String.format("线程%s在%s离开printA", Thread.currentThread().getName(), System.currentTimeMillis()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void printB() {
        try {
            System.out.println(String.format("线程%s在%s进入printB", Thread.currentThread().getName(), System.currentTimeMillis()));
            Thread.sleep(3000);
            System.out.println(String.format("线程%s在%s离开printB", Thread.currentThread().getName(), System.currentTimeMillis()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printC() {
        try {
            System.out.println(String.format("线程%s在%s进入printC", Thread.currentThread().getName(), System.currentTimeMillis()));
            Thread.sleep(3000);
            System.out.println(String.format("线程%s在%s离开printC", Thread.currentThread().getName(), System.currentTimeMillis()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
