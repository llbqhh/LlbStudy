package org.llbqhh.study.java.book.java_multi_thread_programming;

import org.junit.Test;

/**
 * 正确地停止线程
 */
public class Test04Interrupt {
    /*
    本例子中只讲了interrupt相关。使用interrupt主要使用【异常退出】，【睡眠退出】以及【直接return】。
    大部分测试方法放到了相关类的的main函数中，因为有些方法放在junit下测试会产生不正确的结果
     */

    @Test
    public void test01() throws InterruptedException {
        Thread t = new InteruptThread01();
        t.start();
        t.interrupt();
        System.out.println("当前线程是否标识停止：" + t.interrupted()); // 实际应该使用Thread.interrupted()
        System.out.println("当前线程是否标识停止：" + t.interrupted()); // 实际应该使用Thread.interrupted()
        System.out.println("线程对象是否标识停止：" + t.isInterrupted());
        System.out.println("线程对象是否标识停止：" + t.isInterrupted());
        /*
        t.interrupted()查看的是当前线程是否被中断，但是程序运行到这里当前线程是主线程，所以当下线程标识为false
        运行结果：
        当前线程是否标识停止：false
        当前线程是否标识停止：false
        线程对象是否标识停止：true
        线程对象是否标识停止：true
         */
    }

    @Test
    public void test02() throws InterruptedException {
        Thread.currentThread().interrupt();
        Thread t = new InteruptThread01();
        t.start();
        t.interrupt();
        System.out.println("当前线程是否标识停止：" + t.interrupted()); // 实际应该使用Thread.interrupted()
        System.out.println("当前线程是否标识停止：" + t.interrupted()); // 实际应该使用Thread.interrupted()
        System.out.println("线程对象是否标识停止：" + t.isInterrupted());
        System.out.println("线程对象是否标识停止：" + t.isInterrupted());
        /*
        由于第一行就对主线程就行了interrupt，所以t.interrupted()第一次返回true，而调用一次以后状态被擦除，所以第二次调用返回false
        运行结果：
        当前线程是否标识停止：true
        当前线程是否标识停止：false
        线程对象是否标识停止：true
        线程对象是否标识停止：true
         */
    }
}

class InteruptThread01 extends Thread {
    private boolean print;
    public InteruptThread01(boolean print) {
        this.print = print;
    }
    public InteruptThread01() {
        this.print = false;
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t = new InteruptThread01(true);
        t.start();
        Thread.sleep(1000);
        t.interrupt();
        /*
        这里使用interrupt方法线程不会停止
         */
    }
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 500000; i++) {
            if (print) {
                System.out.println("i=" + (i + 1));
            }
        }
    }
}

class InteruptThread02 extends Thread {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new InteruptThread02();
        t.start();
        Thread.sleep(1000);
        t.interrupt();
        System.out.println("end!");
        /*
        异常退出法
        运行结果：
        i=304735
        i=304736
        i=304737
        end!
        需要退出！
        进入catch方法，退出程序！
         */
    }
    @Override
    public void run() {
        super.run();
        try {
            for (int i = 0; i < 500000; i++) {
                if (this.isInterrupted()) {
                    System.out.println("需要退出！");
                    throw new InterruptedException();
                }
                System.out.println("i=" + (i + 1));
            }
        } catch (InterruptedException e) {
            System.out.println("进入catch方法，退出程序！");
            e.printStackTrace();
        }
    }
}

class InteruptThread03 extends Thread {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new InteruptThread03();
        t.start();
        Thread.sleep(1000);
        t.interrupt();
        System.out.println("main end!");
        /*
        在线程sleep时，调用interrupt方法，程序退出
        运行结果：
        run begin
        main end!
        进入catch方法，退出程序！
         */
    }
    @Override
    public void run() {
        super.run();
        try {
            System.out.println("run begin");
            Thread.sleep(200000);
            System.out.println("run end");
        } catch (InterruptedException e) {
            System.out.println("进入catch方法，退出程序！");
            e.printStackTrace();
        }
    }
}

class InteruptThread04 extends Thread {
    public static void main(String[] args) {
        Thread t = new InteruptThread04();
        t.start();
        t.interrupt();
        System.out.println("main end!");
        /*
        去掉main方法中的sleep方法调用，即先调用interrupt方法，然后线程才进行睡眠，程序依然退出
        运行结果：
        i=9998
        i=9999
        run begin
        先执行interrupt,然后线程才sleep,同样进入catch方法，退出程序！
        java.lang.InterruptedException: sleep interrupted
         */
    }
    @Override
    public void run() {
        super.run();
        try {
            for (int i = 0; i < 10000; i++) {
                System.out.println("i=" + i);
            }
            System.out.println("run begin");
            Thread.sleep(200000);
            System.out.println("run end");
        } catch (InterruptedException e) {
            System.out.println("先执行interrupt,然后线程才sleep,同样进入catch方法，退出程序！");
            e.printStackTrace();
        }
    }
}

class InteruptThread05 extends Thread {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new InteruptThread05();
        t.start();
        Thread.sleep(100);
        t.interrupt();
        System.out.println("main end!");
        /*
        使用return退出
        运行结果：
        time is : 1570698903497
        time is : 1570698903497
        main end!
        停止！
         */
    }
    @Override
    public void run() {
        super.run();
        while (true) {
            if (this.isInterrupted()) {
                System.out.println("停止！");
                return;
            }
            System.out.println("time is : " + System.currentTimeMillis());
        }
    }
}