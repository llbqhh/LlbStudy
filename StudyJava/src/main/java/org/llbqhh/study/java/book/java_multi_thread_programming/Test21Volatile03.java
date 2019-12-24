package org.llbqhh.study.java.book.java_multi_thread_programming;

/**
 * synchronized代码块具有将线程工作内存中的私有变量与公共内存中变量同步的功能
 */
public class Test21Volatile03 {
    public static void main(String[] args) throws InterruptedException {
        RunThread03 thread = new RunThread03();
        thread.start();
        Thread.sleep(1000);
        thread.setRunning(false);
        System.out.println("stop it!");
        /*
        如果不在isRunning前加volatile
        则需要在适当位置增加synchronized代码块，可以锁任何东西
        注释中的System.out.println和synchronized是一个效果，因为其内部有synchronized代码块
        运行结果：
        run...start
        stop it!
        run...stop, i=47821973
         */
    }
}

class  RunThread03 extends Thread {
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
//            System.out.println("anything");
            synchronized ("anything") {
            }
        }
        System.out.println("run...stop, i=" + i);
    }
}
