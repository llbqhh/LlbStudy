package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 如何启动一个线程
 */
public class Test01StartThread implements PrintThreadName{
    public static void main(String[] args) {
        // main函数所在线程
        Test01StartThread test01 = new Test01StartThread();
        // 继承thread
        Thread t1 = new ThreadTest();
        // 实现runnable
        Thread t2 = new Thread(new RunnableTest());

        t1.start();
        t2.start();
        test01.printThreadName();

        /*
         注意，最后一行代码是在主线程中执行的
         运行结果：
         Thread:Thread-0
         Runnable:Thread-1
         main:main
         上述运行结果输出的顺序可能变化，但每行的内容不变
         执行start方法的顺序不代表线程启动/执行的顺序
          */
    }
}

/**
 * 打印类名和线程名
 */
interface PrintThreadName {
    default void printThreadName() {
        System.out.println(String.format("%s:%s", this.getClass().getSimpleName(), Thread.currentThread().getName()));
    }
}

class ThreadTest extends Thread implements PrintThreadName {
    @Override
    public void run() {
        super.run();
        printThreadName();
    }
}

class RunnableTest implements Runnable,PrintThreadName {
    @Override
    public void run() {
        printThreadName();
    }
}