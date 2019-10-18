package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * notify每次唤醒一个线程，notifyAll一次将全部线程唤醒
 */
public class Test26NotifyOne {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        // 启动三个线程，都调用lock的wait方法
        NotifyOneThread t1 = new NotifyOneThread(lock);
        NotifyOneThread t2 = new NotifyOneThread(lock);
        NotifyOneThread t3 = new NotifyOneThread(lock);
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(1000);
        synchronized (lock) { // 如果不对lock加锁而直接执行wait或notify方法，会抛出java.lang.IllegalMonitorStateException异常
            lock.notify();
//            lock.notifyAll();
        }
        /*
        由于notify随机唤醒一个线程，所以另外两个线程依然是暂停的状态。
        如果使用notifyAll，则三个线程都会被唤醒并最终执行完成。
        运行结果：
        begin wait thread name = Thread-0
        begin wait thread name = Thread-2
        begin wait thread name = Thread-1
        end wait thread name = Thread-0
         */
    }
}

class NotifyOneThread extends Thread {
    private Object lock;
    public NotifyOneThread(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {
                System.out.println("begin wait thread name = " + Thread.currentThread().getName());
                lock.wait();
                System.out.println("end wait thread name = " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
