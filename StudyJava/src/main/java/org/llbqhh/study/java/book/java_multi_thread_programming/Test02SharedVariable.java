package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 线程间共享变量
 */
public class Test02SharedVariable {
    public static void main(String[] args) {
        Thread myThread = new MyThread();
        Thread t1 = new Thread(myThread);
        Thread t2 = new Thread(myThread);
        Thread t3 = new Thread(myThread);
        Thread t4 = new Thread(myThread);
        Thread t5 = new Thread(myThread);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        /*
        如果MyThread的run方法不加锁，则运行结果如下，thread-1和thread-2出现线程不安全的现象(出现两个count:3)；
        由Thread-1计算，count:3
        由Thread-5计算，count:0
        由Thread-2计算，count:3
        由Thread-4计算，count:1
        由Thread-3计算，count:2

        加锁后，运行结果则类似下述，因为给整个方法加了锁，所以每次只会有一个线程进入run方法：
        由Thread-1计算，count:4
        由Thread-5计算，count:3
        由Thread-4计算，count:2
        由Thread-3计算，count:1
        由Thread-2计算，count:0
         */
    }
}

class MyThread extends Thread {
    private int count = 5;
    @Override
    public synchronized void run() {
        super.run();
        count--;
        System.out.println(String.format("由%s计算，count:%s", Thread.currentThread().getName(), count));
    }
}