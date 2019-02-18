package org.llbqhh.test.thread;

public class Test2Thread implements Runnable {
    public boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        Test2Thread t2t = new Test2Thread();
        Thread tt = new Thread(t2t);
        tt.start();
        System.out.println("alive:" + tt.isAlive());
        System.out.println("interrupted:" + tt.isInterrupted());
        Thread.sleep(5000);
        System.out.println("interrupt");
        tt.interrupt();
        t2t.stop = true;
        Thread.sleep(5000);
        System.out.println("alive:" + tt.isAlive());
        System.out.println("interrupted:" + tt.isInterrupted());
        Thread.sleep(5000);
        tt.start();
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println("���š�����" + Thread.currentThread().isAlive() + "," + Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
