package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * yield方法说明
 */
public class Test07Yield {
    public static void main(String[] args) {
        Thread t = new YieldTest();
        t.start();
        /*
        yield()方法的作用是放弃当前的cpu资源，将它让给其他任务（放弃的时间并不能确定）。
        注释掉yield方法，运行结果：
        time : 20
        打开yield方法的注释，运行结果：
        time : 17518
         */
    }
}

class YieldTest extends Thread {
    @Override
    public void run() {
        super.run();
        long beginTime = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; i < 50000000; i++) {
//            Thread.yield();
            count = count + (i + 1);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("time : "  + (endTime - beginTime));
    }
}