package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 将String类型作为锁时的另一个例子，即字符串发生变化时，获取的锁将会是不同的锁
 */
public class Test18LockChange {
    public static void main(String[] args) throws InterruptedException {
        StringLockTestThread test = new StringLockTestThread();
        Thread t1 = new Thread(test);
        Thread t2 = new Thread(test);
        // t1线程启动后，将lock值改变
        t1.start();
        Thread.sleep(200);
        // t2线程启动后，获取的lock和t1对象不同
        t2.start();

        /*
        因为两个线程锁住的对象不同，所以互相是异步执行
        执行结果：
        Thread-1 begin 1571025861442
        Thread-2 begin 1571025861646
        Thread-1 end 1571025863442
        Thread-2 end 1571025863648
         */
    }
}

class StringLockTestThread extends Thread {
    String lock = "123";
    public void testMethod() {
        try {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " begin " + System.currentTimeMillis());
                lock = "456";
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " end " + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        super.run();
        testMethod();
    }
}

