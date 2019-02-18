package org.llbqhh.test.thread;

public class KillThreadTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(1000 * 60 * 60 * 3);
        try {
            Thread thread = new KillThread(3000);
            thread.setDaemon(true);
            thread.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("休息5秒");
        Thread.currentThread().sleep(5000);
        System.out.println("休息完毕");
    }
}
