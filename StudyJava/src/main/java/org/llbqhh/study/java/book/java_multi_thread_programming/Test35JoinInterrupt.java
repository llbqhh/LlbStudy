package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * join和interrupt相遇会抛出异常
 */
public class Test35JoinInterrupt {
    public static void main(String[] args) throws InterruptedException {
        JoinInterrupt test = new JoinInterrupt();
        test.start();
        Thread.sleep(3000);
        test.interrupt();
        /*
        如果join和interrupt方法彼此遇到，则会出现异常，可以观察到，其实join内部使用了wait，因为异常是从wait方法中抛出
        由于test的内部的线程t还在运行，所以程序并未停止
        运行结果：
        线程进入catch语句
        java.lang.InterruptedException
            at java.lang.Object.wait(Native Method)
         */
    }
}

class JoinInterrupt extends Thread {
    @Override
    public void run() {
        try {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        Math.random();
                    }
                }
            };
//            t.setDaemon(true); // 如果不注释这行代码，程序会一直运行下去，因为t线程并未受到影响
            t.start();
            t.join();
            System.out.println("线程成功执行完");
        } catch (InterruptedException e) {
            System.out.println("线程进入catch语句");
            e.printStackTrace();
        }
    }
}