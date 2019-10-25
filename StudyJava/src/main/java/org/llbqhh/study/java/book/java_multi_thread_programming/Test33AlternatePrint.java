package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 示例：交替打印
 */
public class Test33AlternatePrint {
    public static void main(String[] args) {
        PrintTools printTools = new PrintTools();
        Thread printA = new Thread() {
            @Override
            public void run() {
                while(true) {
                    printTools.printA();
                }
            }
        };
        Thread printB = new Thread() {
            @Override
            public void run() {
                while(true) {
                    printTools.printB();
                }
            }
        };
        printA.start();
        printB.start();
        /*
        我们使用prevIsA作为标记，来实现交替打印
        运行结果：
        =====
        =====
        =====
        =====
        =====
        -----
        -----
        -----
        -----
        -----
        =====
        =====
        =====
        =====
        =====
         */
    }
}

class PrintTools {
    volatile private boolean prevIsA = false;
    synchronized public void printA() {
        try {
            while (prevIsA == true) {
                wait();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("-----");
            }
            prevIsA = true;
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void printB() {
        try {
            while (prevIsA == false) {
                wait();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("=====");
            }
            prevIsA = false;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
