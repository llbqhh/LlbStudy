package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 同步代码块
 */
public class Test10Synchronized {
    public static void main(String[] args) {
        SynchronizedObjectTestThread01 test = new SynchronizedObjectTestThread01();
        Thread a = new Thread(test);
        a.setName("a");
        Thread b = new Thread(test);
        b.setName("b");
        a.start();
        b.start();
        /*
        可以看到，两个线程可以同时进入doLongTimeTask方法，只是在需要同步的地方才加锁
        运行结果：
        begin threadName=b
        begin threadName=a
        长时间处理任务后返回的值1 threadName=a
        长时间处理任务后返回的值2 threadName=a
        end threadName=a
        长时间处理任务后返回的值1 threadName=b
        长时间处理任务后返回的值2 threadName=b
        end threadName=b
         */
    }
}

class SynchronizedObjectTestThread01 extends Thread{
    private String getData1;
    private String getData2;

    @Override
    public void run() {
        super.run();
        doLongTimeTask();
    }

    public void doLongTimeTask() {
        try {
            System.out.println("begin threadName=" + Thread.currentThread().getName());
            Thread.sleep(3000);
            String privateGetData1 = "长时间处理任务后返回的值1 threadName=" + Thread.currentThread().getName();
            String privateGetData2 = "长时间处理任务后返回的值2 threadName=" + Thread.currentThread().getName();
            synchronized (this) {
                getData1 = privateGetData1;
                getData2 = privateGetData2;
                System.out.println(getData1);
                System.out.println(getData2);
            }
            System.out.println("end threadName=" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
