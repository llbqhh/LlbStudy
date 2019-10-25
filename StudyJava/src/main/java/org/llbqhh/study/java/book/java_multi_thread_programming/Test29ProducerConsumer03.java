package org.llbqhh.study.java.book.java_multi_thread_programming;

import java.util.ArrayList;
import java.util.List;

/**
 * 一生产与一消费：操作栈
 */
public class Test29ProducerConsumer03 {
    public static void main(String[] args) {
        MyStack2903 myStack2903 = new MyStack2903();
        PThread2903 p = new PThread2903(myStack2903);
        CThread2903 c = new CThread2903(myStack2903);
        p.start();
        c.start();
        /*
        容器size不会大于1，值在0和1之间进行交替，也就是生产和消费这两个过程在交替执行
        运行结果：
        pop操作中的：Thread-1 线程呈wait状态
        push=1
        pop=0
        pop操作中的：Thread-1 线程呈wait状态
        push=1
        pop=0
         */
    }
}

class PThread2903 extends Thread {
    private MyStack2903 myStack2903;
    public PThread2903(MyStack2903 myStack2903) {
        this.myStack2903 = myStack2903;
    }

    @Override
    public void run() {
        while (true) {
            myStack2903.push();
        }
    }
}

class CThread2903 extends Thread {
    private MyStack2903 myStack2903;
    public CThread2903(MyStack2903 myStack2903) {
        this.myStack2903 = myStack2903;
    }

    @Override
    public void run() {
        while (true) {
            myStack2903.pop();
        }
    }
}

class MyStack2903 {
    private List list = new ArrayList<>();
    synchronized public void push() {
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
    synchronized public String pop() {
        String returnValue = "";
        try {
            if (list.size() == 0) {
                System.out.println("pop操作中的：" + Thread.currentThread().getName() + " 线程呈wait状态");
                this.wait();
            }
            returnValue = "" + list.get(0);
            list.remove(0);
            this.notify();
            System.out.println("pop=" + list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}