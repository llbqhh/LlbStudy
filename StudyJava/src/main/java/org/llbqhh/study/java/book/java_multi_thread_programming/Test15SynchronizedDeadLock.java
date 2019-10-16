package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 死锁
 */
public class Test15SynchronizedDeadLock {
    public static void main(String[] args) throws InterruptedException {
        SynchronizedDeadLockTestThread test = new SynchronizedDeadLockTestThread();
        // t1线程执行对lock1加锁后需要lock2才能继续运行
        Thread t1 = new Thread(test);
        test.setFlag("a");
        t1.start();
        Thread.sleep(1000);

        // t2线程则相反，对lock2加锁后需要lock1才能继续运行
        Thread t2 = new Thread(test);
        test.setFlag("b");
        t2.start();

        /*
        两个线程互相在等待对方持有对锁，造成死锁
        运行结果：
        username = a
        username = b
         */
    }
}

class SynchronizedDeadLockTestThread implements Runnable {
    public String username;
    public Object lock1 = new Object();
    public Object lock2 = new Object();
    public void setFlag(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        if ("a".equals(username)) {
            synchronized (lock1) {
                try {
                    System.out.println("username = " + username);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("lock1 -> lock2 end");
                }
            }
        }
        if ("b".equals(username)) {
            synchronized (lock2) {
                try {
                    System.out.println("username = " + username);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("lock1 -> lock2 end");
                }
            }
        }
    }
}
