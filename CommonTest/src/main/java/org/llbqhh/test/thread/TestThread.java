package org.llbqhh.test.thread;

public class TestThread extends Thread {
    private boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        TestThread tt = new TestThread();
        tt.start();
        Thread.sleep(5000);
        System.out.println("interrupt");
        System.out.println("alive:" + tt.isAlive());
        System.out.println("interrupted:" + tt.isInterrupted());
        tt.interrupt();
        System.out.println("alive:" + tt.isAlive());
        System.out.println("interrupted:" + tt.isInterrupted());
// tt.stop = true;
        tt.start();
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println("���š�����" + this.isAlive() + "," + this.isInterrupted());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
