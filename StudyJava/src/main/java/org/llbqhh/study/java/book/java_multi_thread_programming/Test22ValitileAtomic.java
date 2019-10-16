package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * volatile只保证可见性，不保证原子性
 */
public class Test22ValitileAtomic {
    public static void main(String[] args) {
        MyThread22[] myThread22s = new MyThread22[100];
        for (int i = 0; i < 100; i++) {
            myThread22s[i] = new MyThread22();
        }
        for (int i = 0; i < 100; i++) {
            myThread22s[i].start();
        }
        /*
        由于volatile只保证可见性，不保证原子性，而count++实际上并不是原子操作，会出现多线程问题，所以结果并非100000
        运行结果，最后几行为：
        count=95595
        count=96595
        count=97595
        count=98595
        count=99595
         */
    }

}

class MyThread22 extends Thread {
    volatile  public static int count;
    private static  void addCount() {
        for (int i = 0; i < 1000; i++) {
            count++;
        }
        System.out.println("count=" + count);
    }

    @Override
    public void run() {
        addCount();
    }
}