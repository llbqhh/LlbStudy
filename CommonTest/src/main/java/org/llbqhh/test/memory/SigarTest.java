package org.llbqhh.test.memory;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

import java.text.DecimalFormat;

public class SigarTest {
    public static void main(String[] args) {
        getPhysicalMemory();
    }

    public static void getPhysicalMemory() {
        DecimalFormat df = new DecimalFormat("#0.00");
        Sigar sigar = new Sigar();
        Mem mem;
        try {
            mem = sigar.getMem();
            // �ڴ�����
            System.out.println("�ڴ�������" + df.format((float) mem.getTotal() / 1024 / 1024 / 1024) + "G");
            //
            System.out.println("�ڴ�����" + df.format((float) mem.getActualFree() / 1024 / 1024 / 1024) + "G");
            // ��ǰ�ڴ�ʹ����
            System.out.println("�ڴ�����" + df.format((float) mem.getRam() / 1024 / 1024 / 1024) + "G");
            System.out.println("�ڴ�����" + df.format((float) mem.getActualUsed() / 1024 / 1024 / 1024) + "G");
// mem.getRam()
            System.out.println("��ǰ�ڴ�ʹ������" + df.format((float) mem.getUsed() / 1024 / 1024 / 1024) + "G");
            // ��ǰ�ڴ�ʣ����
            System.out.println("��ǰ�ڴ�ʣ������" + df.format((float) mem.getFree() / 1024 / 1024 / 1024) + "G");
            // b)ϵͳҳ���ļ���������Ϣ
            Swap swap = sigar.getSwap();
// sigar.get
            // ����������
            System.out.println("������������" + df.format((float) swap.getTotal() / 1024 / 1024 / 1024) + "G");
            // ��ǰ������ʹ����
            System.out.println("��ǰ������ʹ������" + df.format((float) swap.getUsed() / 1024 / 1024 / 1024) + "G");
            // ��ǰ������ʣ����
            System.out.println("��ǰ������ʣ������" + df.format((float) swap.getFree() / 1024 / 1024 / 1024) + "G");
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }
}
