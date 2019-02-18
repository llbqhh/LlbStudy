package org.llbqhh.test.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static AtomicInteger runningThreads = new AtomicInteger();

    public static ConcurrentHashMap<String, String> map;

    public static void main(String[] args) throws InterruptedException {
        startThreads();
        waitOk();
    }

    /**
     * ���������߳�
     */
    public static void startThreads() {
        MonitorTestThread mtt = new MonitorTestThread();
        mtt.setDaemon(true);
        mtt.start();
    }

    /**
     * ���߳�ִ�����
     */
    public static void waitOk() {
        while (true) {
            try {
                Thread.sleep(10000);
                System.out.println("running threads num = " + runningThreads.get());
                if (runningThreads.get() < 1) {
                    Thread.sleep(10000);
                    if (runningThreads.get() < 1) {
                        System.out.println("the end");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
