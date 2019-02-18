package org.llbqhh.test.thread;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class DataTask {
    public AtomicInteger finishedThreads = new AtomicInteger(0);
    public AtomicInteger failedNum = new AtomicInteger(0);
    public String nextTableName = "";
    public static int totalThreads = 10;

    private int checkTimeout = 1;
    private Test3Thread thread;
    private Calendar startTime;
    private Calendar startTime2;

    public void start() {
        while (true) {
            if (thread == null) {
                startTime = Calendar.getInstance();
                startTime2 = Calendar.getInstance();
                thread = new Test3Thread(this);
                thread.setDaemon(true);
                Test.runningThreads.incrementAndGet();
                thread.start();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (thread.isAlive() && checkTimeout == 1) {
                if (Calendar.getInstance().getTimeInMillis()
                        - startTime2.getTimeInMillis() > 10) {
                    try {
                        if (thread.isAlive()) {
                            //�������û��,����ɱ���߳�
                            thread.interrupt();
                            //��Ϣ3�룬���߳�ʱ�����
                            Thread.currentThread().sleep(1000);
                            //���ÿ�ʼʱ��
                            startTime2 = Calendar.getInstance();
                        }
                    } catch (Exception e) {
                    }

                    if (!thread.isAlive() || !thread.isInterrupted()) {
                        startTime2 = Calendar.getInstance();
                        thread.start();
// thread.start();
                    } else {
                        System.out.println(new Date() + "�޷�����Commandline");
// thread.interrupt();
                        //����޷��������̻߳�������У���Ҫ ��������+1
// dt.failedNum.incrementAndGet();
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
