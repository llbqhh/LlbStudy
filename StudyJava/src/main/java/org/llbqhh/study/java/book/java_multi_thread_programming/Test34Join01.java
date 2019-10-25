package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * join的用法：等待线程对象执行完（销毁）
 */
public class Test34Join01 {
    public static void main(String[] args) throws InterruptedException {
        JoinTest01 t = new JoinTest01();
        t.start();
        // 调用join后，当前线程阻塞，t所属线程继续执行，待t所属线程销毁后再继续执行当前线程
        t.join();
        System.out.println("当JoinTest01对象执行完毕后再执行");
        /*
        运行结果：
        8800
        当JoinTest01对象执行完毕后再执行
         */
    }
}
class JoinTest01 extends Thread {
    @Override
    public void run() {
        try {
            int secondValue = (int) (Math.random() * 10000);
            System.out.println(secondValue);
            Thread.sleep(secondValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}