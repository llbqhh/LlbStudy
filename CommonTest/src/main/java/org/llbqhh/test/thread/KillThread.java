package org.llbqhh.test.thread;

public class KillThread extends Thread {
    private long timeOut;
    private long startTime;

    public KillThread(long timeOut) {
        this.timeOut = timeOut;
// System.out.println("timeOut"+timeOut);
        this.startTime = System.currentTimeMillis();
// System.out.println("startTime"+startTime);
    }

    @Override
    public void run() {
        while (true) {
            long waitTime = System.currentTimeMillis() - startTime;
// System.out.println("waitTime"+waitTime);
            if (waitTime >= timeOut) {
                System.out.println("超时退出:" + waitTime);
                System.exit(0);
// System.exit(1);
            } else {
                System.out.println("未超时:" + waitTime);
            }
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
