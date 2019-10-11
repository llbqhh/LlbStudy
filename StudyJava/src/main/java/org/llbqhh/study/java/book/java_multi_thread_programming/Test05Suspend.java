package org.llbqhh.study.java.book.java_multi_thread_programming;

import org.junit.Test;

/**
 * suspend和resume方法说明
 */
public class Test05Suspend {
    @Test
    public void test01() throws InterruptedException {
        SuspendThread01 thread01 = new SuspendThread01();
        thread01.start();
        Thread.sleep(100);
        thread01.suspend();
        System.out.println("main end!");
        /*
        suspend和resume方法缺点1：独占
        由于System.out.println()方法是一个同步方法，
        如果正好执行到println方法内时调用了suspend方法，则同步锁不会释放，后续对println方法的所有调用都会阻塞
        所以程序会在打印了一些数字后卡住，不会打印main end！
         */
    }

    @Test
    public void test02() throws InterruptedException {
        MyObject01 myObject01 = new MyObject01();
        Thread t1 = new Thread(() -> myObject01.setValue("a", "aa"));
        t1.setName("a");
        t1.start();
        Thread.sleep(1000);
        t1.suspend();
//        myObject01.setValue("c", "cc");
        Thread t2 = new Thread(() -> myObject01.pringUsernamePassword());
        t2.start();

        Thread.sleep(1000);
        t1.resume();
        Thread.sleep(1000);
        myObject01.pringUsernamePassword();
        /*
        suspend和resume方法缺点2：不同步
        线程t1在修改了一半数据后挂起，此时t2打印出的实际上是不一致的数据
        而后调用t1的resume方法恢复线程后，再打印的结果是正常的结果
        此外还可以注意到，虽然挂起后没有释放该方法的锁，但是t2调用的是该对象的非同步方法，不受影响。
        如果将注释的那行打开，程序就会在执行到这里时卡住
        运行结果：
        停止a线程！
        a pw
        a aa
         */
    }
}

class SuspendThread01 extends Thread {
    private long i = 0;
    @Override
    public void run() {
        super.run();
        while (true) {
            i++;
            System.out.println(i);
        }
    }
}

class MyObject01 {
    private String username = "zs";
    private String password = "pw";
    synchronized public void setValue(String u, String p) {
        this.username = u;
        if (Thread.currentThread().getName().equals("a")) {
            System.out.println("停止a线程！");
            Thread.currentThread().suspend();
        }
        this.password = p;
    }
    public void pringUsernamePassword() {
        System.out.println(String.format("%s %s", username, password));
    }
}
