package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * volatile可以强制子线程从公共堆栈中取得变量的值，而不是从线程私有数据栈中取得变量的值
 */
public class Test20Volatile02 {
    public static void main(String[] args) throws InterruptedException {
        RunThread02 thread = new RunThread02();
        thread.start();
        Thread.sleep(1000);
        thread.setRunning(false);
        System.out.println("stop it!");
        /*
        在isRunning前加上volatile后，程序可以正常退出，不会再死循环
        运行结果：
        run...start
        stop it!
        run...stop, i=2882258431
         */
    }
}

class  RunThread02 extends Thread {
    private volatile boolean isRunning = true;

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
        }
        System.out.println("run...stop, i=" + i);
    }
}
