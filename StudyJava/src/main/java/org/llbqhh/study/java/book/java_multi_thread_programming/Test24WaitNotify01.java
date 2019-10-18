package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * wait/notify简单使用
 */
public class Test24WaitNotify01 {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        // 第一个线程调用lock的wait方法，此时会释放掉lock的锁
        MyThread2401 myThread2401 = new MyThread2401(lock);
        // 第二个线程对lock加锁后，调用lock的notify方法
        MyThread2402 myThread2402 = new MyThread2402(lock);
        myThread2401.start();
        Thread.sleep(3000);
        myThread2402.start();
        /*
        在调用wait和notify方法时，线程必须持有调用对象的锁，否则会抛出异常。
        调用notify方法后并不是直接释放锁，而是需要在notify所在的同步代码块执行完毕后才释放。
        运行结果：
        开始 wait time=1571296119275
        开始 notify time=1571296122278
        结束 notify time=1571296122278
        结束 wait time=1571296122279
         */
    }
}

class MyThread2401 extends Thread {
    private Object lock;
    public MyThread2401(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {
                System.out.println("开始 wait time=" + System.currentTimeMillis());
                lock.wait();
                System.out.println("结束 wait time=" + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThread2402 extends Thread {
    private Object lock;
    public MyThread2402(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        synchronized (lock) {
            System.out.println("开始 notify time=" + System.currentTimeMillis());
            lock.notify();
            System.out.println("结束 notify time=" + System.currentTimeMillis());
        }
    }
}