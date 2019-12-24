package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 锁其他对象
 */
public class Test11SynchronizedObject {
    public static void main(String[] args) {
        SynchronizedObjectTest test = new SynchronizedObjectTest();

        // t1线程执行test对象的同步方法a()
        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                test.a();
            }
        };

        // t2线程执行test对象的b()方法中的同步代码块
        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                test.b();
            }
        };

        t1.start();
        t2.start();

        // 主线程获取test对象的锁后执行自己的代码
        synchronized (test) {
            System.out.println("main同步代码块 begin ,thread=" + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main同步代码块 end ,thread=" + Thread.currentThread().getName());
        }

        /*
        三个线程实际都是获取的同一个对象锁，所以方法调用都是同步（阻塞）的，当然b方法的同步代码块前后的代码是异步执行的
        执行结果：
        main同步代码块 begin ,thread=main
        b方法同步代码块之前的代码执行！
        main同步代码块 end ,thread=main
        b同步代码块 begin ,thread=Thread-1
        b同步代码块 end ,thread=Thread-1
        b方法同步代码块之后的代码执行！
        a同步方法 begin ,thread=Thread-0
        a同步方法 end ,thread=Thread-0
         */
    }
}

class SynchronizedObjectTest {
    public synchronized void a() {
        System.out.println("a同步方法 begin ,thread=" + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("a同步方法 end ,thread=" + Thread.currentThread().getName());
    }

    public void b() {
        // 其他代码
        System.out.println("b方法同步代码块之前的代码执行！");
        synchronized (this) {
            System.out.println("b同步代码块 begin ,thread=" + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("b同步代码块 end ,thread=" + Thread.currentThread().getName());
        }
        // 其他代码
        System.out.println("b方法同步代码块之后的代码执行！");
    }
}