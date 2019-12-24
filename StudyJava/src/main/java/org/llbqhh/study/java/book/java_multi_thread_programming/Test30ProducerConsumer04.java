package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.ArrayList;
import java.util.List;

/**
 * 一生产与多消费：操作栈：解决异常与假死问题
 * 多生产与多消费等情况类似
 */
public class Test30ProducerConsumer04 {
    public static void main(String[] args) {
        MyStack2904 myStack2904 = new MyStack2904();
        PThread2904 p = new PThread2904(myStack2904);
        CThread2904 c1 = new CThread2904(myStack2904);
        CThread2904 c2 = new CThread2904(myStack2904);
        CThread2904 c3 = new CThread2904(myStack2904);
        p.start();
        c1.start();
        c2.start();
        c3.start();
        /*
        很快会抛出IndexOutOfBoundsException异常，
        因为MyStack类中使用if语句作为条件判断，多个消费者在size为0时同时wait，
        而当size为1时（生产者生产后），某个消费者消费数据，而后又唤醒了另一个消费者，
        此时size为0，但是消费者会调用list的get(0)方法，导致抛出异常。
        应该改为while循环，则可避免此问题。不过这样又会出现假死卡住的问题，需要将notify改为notifyAll
        运行结果：
        push=1
        pop=0
        pop操作中的：Thread-3 线程呈wait状态
        pop操作中的：Thread-2 线程呈wait状态
        pop操作中的：Thread-1 线程呈wait状态
        push=1
        pop=0
        pop操作中的：Thread-3 线程呈wait状态
        Exception in thread "Thread-2" java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
         */
    }
}

class PThread2904 extends Thread {
    private MyStack2904 myStack2904;
    public PThread2904(MyStack2904 myStack2904) {
        this.myStack2904 = myStack2904;
    }

    @Override
    public void run() {
        while (true) {
            myStack2904.push();
        }
    }
}

class CThread2904 extends Thread {
    private MyStack2904 myStack2904;
    public CThread2904(MyStack2904 myStack2904) {
        this.myStack2904 = myStack2904;
    }

    @Override
    public void run() {
        while (true) {
            myStack2904.pop();
        }
    }
}

class MyStack2904 {
    private List list = new ArrayList<>();
    public synchronized void push() {
        try {
            if (list.size() == 1) {
                this.wait();
            }
            list.add("anyString=" + Math.random());
            this.notify();
            System.out.println("push=" + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized String pop() {
        String returnValue = "";
        try {
            while (list.size() == 0) { // 由于这里使用的是if，所以有可能在size为0时多个消费者阻塞，而在某个消费者成功消费后调用notify方法时又一次唤醒消费者，此时size已经为0，而消费者又去调用get(0)方法，导致异常
                System.out.println("pop操作中的：" + Thread.currentThread().getName() + " 线程呈wait状态");
                this.wait();
            }
            returnValue = "" + list.get(0);
            list.remove(0);
            this.notify();
            // 使用notifhyAll解决假死卡住的问题
//            this.notifyAll();
            System.out.println("pop=" + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}