package org.llbqhh.test.lock;

public class LockPrint extends Thread {
    @Override
    public void run() {
// lock();
        staticLock();
        super.run();
    }

    private static synchronized void staticLock() {
        System.out.println(Thread.currentThread().getName() + "go");
        try {
            System.out.println(Thread.currentThread().getName() + "sleep");
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "ok");
    }

    private synchronized void lock() {
        System.out.println(this.getName() + "go");
        try {
            System.out.println(this.getName() + "sleep");
            this.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + "ok");
    }
}
