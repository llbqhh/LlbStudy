package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * wait状态下调用intterupt
 */
public class Test25WaitInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        // 线程启动后调用lock的wait方法
        WaitThreadTest thread = new WaitThreadTest(lock);
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();
        /*
        wait状态下调用interrupt会抛出InterruptedException异常
        运行结果：
        begin wait
        java.lang.InterruptedException
            at java.lang.Object.wait(Native Method)
            at java.lang.Object.wait(Object.java:502)
            at org.llbqhh.study.java.book.java_multi_thread_programming.WaitThreadTest.run(Test25WaitInterrupt.java:23)
        出现异常，因为呈wait状态的线程被interrupt了
         */
    }
}

class WaitThreadTest extends Thread{
    private Object lock;
    public WaitThreadTest(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {
                System.out.println("begin wait");
                lock.wait();
                System.out.println("end wait");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("出现异常，因为呈wait状态的线程被interrupt了");
        }
    }
}