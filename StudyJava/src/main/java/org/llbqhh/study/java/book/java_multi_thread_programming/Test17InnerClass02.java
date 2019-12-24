package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 内部类的锁和外部类的锁道理是一样的，主要看锁的是什么对象
 */
public class Test17InnerClass02 {
    public static void main(String[] args) {
        OutClass.InnerClass1 innerClass1 = new OutClass.InnerClass1();
        OutClass.InnerClass2 innerClass2 = new OutClass.InnerClass2();
        // method1方法对innerClass2对象加锁
        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                innerClass1.method1(innerClass2);
            }
        };

        // method2方法对innerClass1对象加锁
        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                innerClass1.method2();
            }
        };

        // method3方法对innerClass2对象加锁
        Thread t3 = new Thread() {
            @Override
            public void run() {
                super.run();
                innerClass2.method3();
            }
        };

        t1.start();
        t2.start();
        t3.start();

        /*
        由于t1、t3线程都是对innerClass2加锁，所以他们之间是同步的，t1和t2线程分别对不同对象加锁，所以他们可以异步执行
        运行结果：
        Thread-1进入InnerClass1类中的method2方法
        Thread-0进入InnerClass1类中的method1方法
        j=0
        i=0
        j=1
        i=1
        i=2
        j=2
        i=3
        j=3
        i=4
        j=4
        Thread-0离开InnerClass1类中的method1方法
        Thread-1离开InnerClass1类中的method2方法
        Thread-2进入InnerClass2类中的method3方法
        k=0
        k=1
        k=2
        k=3
        k=4
        Thread-2离开InnerClass2类中的method3方法
         */
    }
}

class OutClass {
    static class InnerClass1 {
        public void method1(InnerClass2 innerClass2) {
            String threadName = Thread.currentThread().getName();
            synchronized (innerClass2) {
                System.out.println(threadName + "进入InnerClass1类中的method1方法");
                for (int i = 0; i < 5; i++) {
                    System.out.println("i=" + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(threadName + "离开InnerClass1类中的method1方法");
            }
        }

        public synchronized void method2() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "进入InnerClass1类中的method2方法");
            for (int j = 0; j < 5; j++) {
                System.out.println("j=" + j);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(threadName + "离开InnerClass1类中的method2方法");
        }
    }
    static class InnerClass2 {
        public synchronized void method3() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "进入InnerClass2类中的method3方法");
            for (int k = 0; k < 5; k++) {
                System.out.println("k=" + k);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(threadName + "离开InnerClass2类中的method3方法");
        }
    }
}