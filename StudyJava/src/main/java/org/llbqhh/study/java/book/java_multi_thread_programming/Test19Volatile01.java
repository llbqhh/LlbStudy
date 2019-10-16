package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * 主线程中改变变量值，子线程的工作内存并没有刷新，导致死循环
 */
public class Test19Volatile01 {
    public static void main(String[] args) throws InterruptedException {
        RunThread01 thread = new RunThread01();
        thread.start();
        Thread.sleep(1000);
        thread.setRunning(false);
        System.out.println("stop it!");

        /*
        由于thread.setRunning(false)更新的是公共堆栈中的isRunning变量值，
        而thread线程在私有堆栈中取得isRunning的值一直为true，
        所以thread线程无法停止，一直在死循环。
        注意，如果将注释掉的一行System.out.println放开，则不会一直死循环，
        因为println中有synchronized代码块，synchronized代码块会强制刷新
        运行结果：
        run...start
        stop it!
         */
    }
}

class  RunThread01 extends Thread {
    private boolean isRunning = true;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        System.out.println("run...start");
        long i = 0;
        while (isRunning) {
            i++;
//            System.out.println("thread name = " + Thread.currentThread().getName());
        }
        System.out.println("run...stop, i=" + i);
    }
}