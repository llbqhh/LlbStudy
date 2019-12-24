package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 可以为不同的同步方法提供不同的锁
 */
public class Test14SynchronizedObject {
}

class SynchronizedObjectTest01 {
    public static void main(String[] args) {
        SynchronizedObjectTest01 test = new SynchronizedObjectTest01();
        // t1线程调用test对象的methodA同步方法，这个方法本身永远不会退出
        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                test.methodA();
            }
        };

        // t2线程调用test对象的methodB同步方法
        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                test.methodB();
            }
        };

        t1.start();
        t2.start();
        /*
        由于两个同步方法都获取了对象锁，所以a方法开始后，b方法永远无法执行
        运行结果：
        a begin
         */
    }
    public synchronized void methodA() {
        System.out.println("a begin");
        boolean flag = true;
        while (flag) {
            //
        }
        System.out.println("a end");
    }

    public synchronized void methodB() {
        System.out.println("b begin");
        System.out.println("b end");
    }
}

class SynchronizedObjectTest02 {
    public static void main(String[] args) {
        SynchronizedObjectTest02 test = new SynchronizedObjectTest02();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                test.methodA();
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                test.methodB();
            }
        };

        t1.start();
        t2.start();
        /*
        由于两个同步方法锁了不同的对象，所以a方法开始后，b方法也可以异步执行
        运行结果：
        a begin
        b begin
        b end
         */
    }

    Object lock1 = new Object();
    public void methodA() {
        synchronized (lock1) {
            System.out.println("a begin");
            boolean flag = true;
            while (flag) {
                //
            }
            System.out.println("a end");
        }
    }

    Object lock2 = new Object();
    public  void methodB() {
        synchronized (lock2) {
            System.out.println("b begin");
            System.out.println("b end");
        }
    }
}
