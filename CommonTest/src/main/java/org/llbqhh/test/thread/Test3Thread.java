package org.llbqhh.test.thread;

import java.util.Date;

public class Test3Thread extends Thread {
    public boolean stop = false;

    int count;
    DataTask dt;
    Process p = null;

    public Test3Thread(DataTask dt2) {
        this.dt = dt2;
    }

    @Override
    public void run() {
        System.out.println("finished:" + dt.finishedThreads + ",total:" + dt.totalThreads);
//　DataTarget = DataTarget.replace("/j/", "/d/");
//　System.out.println(DataTarget);
        System.out.println(new Date() + "��ʼCommandline ");

        // Ϊȷ����־������ȷ���Ƚ���ѭ��
// while (true) {
        // ��¼�Ƿ�����ʧ��--ʵ��ֻ�ܼ�¼���߳��Ƿ񱻴��
        boolean isFailed = false;
        boolean isDestory = false;
        //����cmd
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //�ȵ�cmdִ���꣬��ʱ����ܳ���
        for (int i = 0; i < DataTask.totalThreads; i++) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("����ϣ��������ء�����");
                isDestory = true;
                e.printStackTrace();
            }
        }

// isDestory = count == 1 ? false:true;
// count++;

        long fileLength = isDestory ? 0 : 10;

        if (fileLength != 0) {
            Test.runningThreads.decrementAndGet();
            // �õ����·�����ļ���С
            dt.finishedThreads.incrementAndGet();
            System.out.println("ͬ���ɹ�����");
            return;
        } else {
            // ����ո����ص��ļ���СΪ0����ȴ�60��������������
            try {
                // �õ����·�����ļ���С
                System.out.println("��С�ǣ�" + 0);
                // ����ļ���СΪ0����60����������(�������Ϊ��ʱ����ϣ���ֻ��Ϣ5����)
                Thread.currentThread().sleep((isDestory ? 5 : 60) * 60);
                run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("finished:" + dt.finishedThreads + ",total:" + dt.totalThreads);
    }
}
