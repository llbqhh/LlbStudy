package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.Random;

/**
 * 多生产与多消费：操作值-假死
 */
public class Test28ProducerConsumer02 {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        Producer2702[] pThread = new Producer2702[2];
        Consumer2702[] cThread = new Consumer2702[2];

        for (int i = 0; i < 2; i++) {
            pThread[i] = new Producer2702(lock);
            pThread[i].setName("生产者" + (i + 1));
            cThread[i] = new Consumer2702(lock);
            cThread[i].setName("消费者" + (i + 1));
            pThread[i].start();
            cThread[i].start();
        }

        Thread.sleep(5000);

        Thread[] threadArray = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        Thread.currentThread().getThreadGroup().enumerate(threadArray);
        for (int i = 0; i < threadArray.length; i++) {
            System.out.println(threadArray[i].getName() + " " + threadArray[i].getState());
        }
        /*
        因为notify只唤醒一个线程，并且有可能唤醒同类线程，所以在运行了一段时间后，
        生产者2生产数据后进入waiting，然后唤醒了同类都生产者1，生产者1发现有产品，直接进入waiting状态
        然后唤醒了消费者2，消费者2消费了数据后进入waiting状态，却唤醒了消费者1，消费者1发现没有产品，也进入waiting状态，
        至此，所有生产者消费者线程都进入了waiting状态
        解决此类问题可以将所有的notify换成notifyAll，这样可以唤醒所有线程
        运行结果：
        生产者 生产者2runnable了
        生产者 生产者2waiting了
        生产者 生产者1waiting了
        消费者 消费者2runnable了
        消费者 消费者1waiting了
        消费者 消费者2waiting了
        main RUNNABLE
        Monitor Ctrl-Break RUNNABLE
        生产者1 WAITING
        消费者1 WAITING
        生产者2 WAITING
        消费者2 WAITING
         */
    }
}

class Producer2702 extends Thread{
    Object lock;
    public Producer2702(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            this.setValue();
        }
    }

    public void setValue() {
        try {
            synchronized (lock) {
                // 注意生产者和消费者都是while循环，如果都是if，则线程不会卡住
                while (!ValueObject.value.equals("")) {
                    System.out.println("生产者 " + Thread.currentThread().getName() + "waiting了");
                    lock.wait();
                }

                System.out.println("生产者 " + Thread.currentThread().getName() + "runnable了");
                String value = System.currentTimeMillis() + "_" + System.nanoTime();
                ValueObject.value = value;
                lock.notify();
                // 使用notifyAll线程不会卡住，因为会唤醒所有线程
//                lock.notifyAll();
            }
//            Thread.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer2702 extends Thread{
    private Object lock;
    public Consumer2702(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            this.getValue();
        }
    }

    public void getValue() {
        try {
            synchronized (lock) {
                // 注意生产者和消费者都是while循环，如果都是if，则线程不会卡住
                while (ValueObject.value.equals("")) {
                    System.out.println("消费者 " + Thread.currentThread().getName() + "waiting了");
                    lock.wait();
                }
                System.out.println("消费者 " + Thread.currentThread().getName() + "runnable了");
                ValueObject.value = "";
                lock.notify();
                // 使用notifyAll线程不会卡住，因为会唤醒所有线程
//                lock.notifyAll();
            }
            Thread.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}