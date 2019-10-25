package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * join 释放锁，sleep不会释放锁
 */
public class Test36Join02 {
    public static void main(String[] args) throws InterruptedException {
        JoinTest3602 t2 = new JoinTest3602();
        JoinTest3601 t1 = new JoinTest3601(t2);

        t1.start();
        /*
        t1线程join后释放了锁，t2线程获得锁并执行
        运行结果：
        t1 get lock...
        t2 get lock and release...
        t1 release lock...
         */
    }
}
class JoinTest3601 extends Thread {
    private JoinTest3602 joinTest3602;
    public JoinTest3601(JoinTest3602 joinTest3602) {
        this.joinTest3602 = joinTest3602;
    }
    @Override
    public void run() {
        synchronized (joinTest3602) {
            System.out.println("t1 get lock...");
            try {
                joinTest3602.start();
                joinTest3602.join(); // 如果把join注释掉，只留下sleep，则t2线程需要等t1线程执行完毕后才能执行，因为sleep不释放锁
                // do something
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1 release lock...");
        }
    }
}
class JoinTest3602 extends Thread {
    @Override
    synchronized public void run() {
        System.out.println("t2 get lock and release...");
    }
}
