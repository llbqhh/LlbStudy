package org.llbqhh.test.thread;

public class MonitorTestThread extends Thread {
    private boolean stop = false;

    @Override
    public void run() {
        DataTask dt = new DataTask();
        dt.start();

        while (true) {
            try {
                Thread.sleep(100);
// System.out.println("running threads num = "+DataProcess.runningThreads.get());
                if (dt.finishedThreads.get() == dt.totalThreads || (dt.finishedThreads.get() + dt.failedNum.get()) == dt.totalThreads) {
                    if (dt.failedNum.get() > 0) {
                        System.out.println("�쳣����");
                    } else {
                        System.out.println("ȫ������");
                    }
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
