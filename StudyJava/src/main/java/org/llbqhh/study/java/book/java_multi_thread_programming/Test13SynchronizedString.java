package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 字符串作为锁时需要注意——字符串常量池
 */
public class Test13SynchronizedString {
    public static void main(String[] args) {
        SynchronizedStringTest test = new SynchronizedStringTest();
        Thread t1 = new Thread(){
            @Override
            public void run() {
                super.run();
                String a = "test";
                test.print(a);
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                super.run();
                String b = "test";
                test.print(b);
            }
        };

        t1.start();
        t2.start();
        /*
        虽然线程t1传入的参数是a，线程t2传入的是b，但是他们都是同一个字符串test，用的是String常量池中的同一个对象，所以有同步的效果
        运行结果：
        Thread-0
        Thread-0
        Thread-0
        Thread-0
         */
    }
}
class SynchronizedStringTest {
    public static void print(String str) {
        synchronized (str) {
//            for (;;) {
            while (true) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}