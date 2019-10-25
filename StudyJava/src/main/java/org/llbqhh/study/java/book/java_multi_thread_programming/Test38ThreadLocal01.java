package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 验证ThreadLocak的隔离性
 */
public class Test38ThreadLocal01 {
    public static void main(String[] args) {
        Thread3801 t1 = new Thread3801();
        t1.setName("t1");
        Thread3801 t2 = new Thread3801();
        t2.setName("t2");
        t1.start();
        t2.start();
        /*
        可以看到，运行结果是交替打印，两个线程从ThreadLocal中设置和取出值互不影响
        运行结果：
        Thread t1 get Value=t1:1
        Thread t2 get Value=t2:1
        Thread t2 get Value=t2:2
        Thread t1 get Value=t1:2
        Thread t1 get Value=t1:3
        Thread t2 get Value=t2:3
        Thread t2 get Value=t2:4
        Thread t1 get Value=t1:4
        Thread t2 get Value=t2:5
        Thread t1 get Value=t1:5
         */
    }
}
class Tools3801 {
    public static ThreadLocal t1 = new ThreadLocal();
}
class Thread3801 extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                Tools3801.t1.set(this.getName() + ":" + (i + 1));
                System.out.println("Thread " + this.getName() + " get Value=" + Tools3801.t1.get());
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}