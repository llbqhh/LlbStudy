package org.llbqhh.test.memory;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        getSystemMemory();

        test2();
    }

    /**
     * ��ȡϵͳ����
     */
    public static void test2() {
        MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memorymbean.getHeapMemoryUsage();
        System.out.println("INIT HEAP: " + usage.getInit());
        System.out.println("MAX HEAP: " + usage.getMax());
        System.out.println("USE HEAP: " + usage.getUsed());
        System.out.println("\nFull Information:");
        System.out.println("Heap Memory Usage: "
                + memorymbean.getHeapMemoryUsage());
        System.out.println("Non-Heap Memory Usage: "
                + memorymbean.getNonHeapMemoryUsage());

        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        System.out.println("===================java options=============== ");
        System.out.println(inputArguments);

        System.out.println("=======================ͨ��java����ȡ���ϵͳ״̬============================ ");
        int i = (int) Runtime.getRuntime().totalMemory() / 1024; //Java ������е��ڴ�����,���ֽ�Ϊ��λ
        System.out.println("�ܵ��ڴ��� i is " + i);
        int j = (int) Runtime.getRuntime().freeMemory() / 1024; //Java ������еĿ����ڴ���
        System.out.println("�����ڴ��� j is " + j);
        System.out.println("����ڴ��� is " + Runtime.getRuntime().maxMemory() / 1024);

        System.out.println("=======================OperatingSystemMXBean============================ ");
        OperatingSystemMXBean osm = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println(osm.getFreeSwapSpaceSize() / 1024 / 1024);
        System.out.println(osm.getFreePhysicalMemorySize() / 1024 / 1024);
        System.out.println(osm.getTotalPhysicalMemorySize() / 1024 / 1024);

        //��ȡ����ϵͳ�����Ϣ
        System.out.println("osm.getArch() " + osm.getArch());
        System.out.println("osm.getAvailableProcessors() " + osm.getAvailableProcessors());
        //System.out.println("osm.getCommittedVirtualMemorySize() "+osm.getCommittedVirtualMemorySize());
        System.out.println("osm.getName() " + osm.getName());
        //System.out.println("osm.getProcessCpuTime() "+osm.getProcessCpuTime());
        System.out.println("osm.getVersion() " + osm.getVersion());
        //��ȡ����������ڴ�ʹ�����
        System.out.println("=======================MemoryMXBean============================ ");
        MemoryMXBean mm = (MemoryMXBean) ManagementFactory.getMemoryMXBean();
        System.out.println("getHeapMemoryUsage " + mm.getHeapMemoryUsage());
        System.out.println("getNonHeapMemoryUsage " + mm.getNonHeapMemoryUsage());
        //��ȡ�����̵߳ĸ���״̬��CPU ռ��������Լ�����ϵͳ�е��߳�״��
        System.out.println("=======================ThreadMXBean============================ ");
        ThreadMXBean tm = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        System.out.println("getThreadCount " + tm.getThreadCount());
        System.out.println("getPeakThreadCount " + tm.getPeakThreadCount());
        System.out.println("getCurrentThreadCpuTime " + tm.getCurrentThreadCpuTime());
        System.out.println("getDaemonThreadCount " + tm.getDaemonThreadCount());
        System.out.println("getCurrentThreadUserTime " + tm.getCurrentThreadUserTime());

        //��ǰ���������
        System.out.println("=======================CompilationMXBean============================ ");
        CompilationMXBean gm = (CompilationMXBean) ManagementFactory.getCompilationMXBean();
        System.out.println("getName " + gm.getName());
        System.out.println("getTotalCompilationTime " + gm.getTotalCompilationTime());

        //��ȡ����ڴ�ص�ʹ�����
        System.out.println("=======================MemoryPoolMXBean============================ ");
        List<MemoryPoolMXBean> mpmList = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean mpm : mpmList) {
            System.out.println("getUsage " + mpm.getUsage());
            System.out.println("getMemoryManagerNames " + mpm.getMemoryManagerNames().toString());
        }
        //��ȡGC�Ĵ����Լ�����ʱ��֮�����Ϣ
        System.out.println("=======================MemoryPoolMXBean============================ ");
        List<GarbageCollectorMXBean> gcmList = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcm : gcmList) {
            System.out.println("getName " + gcm.getName());
            System.out.println("getMemoryPoolNames " + gcm.getMemoryPoolNames());
        }
        //��ȡ����ʱ��Ϣ
        System.out.println("=======================RuntimeMXBean============================ ");
        RuntimeMXBean rmb = (RuntimeMXBean) ManagementFactory.getRuntimeMXBean();
        System.out.println("getClassPath " + rmb.getClassPath());
        System.out.println("getLibraryPath " + rmb.getLibraryPath());
        System.out.println("getVmVersion " + rmb.getVmVersion());
    }

    public static void test1() throws InterruptedException {
        List<String> testStrs = new ArrayList<String>();
// List<Integer> testStrs = new ArrayList<Integer>();
        testStrs.add("1");
// testStrs.add(1);
        long num = 0;
        while (true) {
            num++;
            if (testStrs.size() != 0) {
                for (int i = 0; i <= 1000000; i++) {
                    testStrs.add("testtesttest" + num);
// testStrs.add(i);
                }
            }
            if (testStrs.size() >= 23000000) {
// if(testStrs.size()>=5000000){
                testStrs.clear();
// testStrs = new ArrayList<String>();
                testStrs = null;
                break;
            }
            System.out.println("set size:" + testStrs.size());
            getJvmMemory();
            getSystemMemory();
            System.out.println("=================");
            Thread.currentThread().sleep(300);
        }
        testStrs = null;
    }

    /**
     * ��ȡjvm�ڴ�
     */
    public static void getJvmMemory() {
        Runtime r = Runtime.getRuntime();
        System.out.println("max(m):" + r.maxMemory() / 1024 / 1024); //����ڴ�
        System.out.println("total(m):" + r.totalMemory() / 1024 / 1024);    //�ѷ����ڴ�
        System.out.println("freeMemory(m):" + r.freeMemory() / 1024 / 1024);    //�������
    }

    /**
     * ��ȡϵͳ�ڴ�
     */
    public static void getSystemMemory() {
        OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        System.out.println("ϵͳ�����ڴ��ܼ�(m)��" + osmb.getTotalPhysicalMemorySize()
                / 1024 / 1024);
        System.out.println("ϵͳ��������ڴ��ܼ�(m)��" + osmb.getFreePhysicalMemorySize()
                / 1024 / 1024);

        double totalMemory = (double) osmb.getTotalPhysicalMemorySize();
        long freeMemory = osmb.getFreePhysicalMemorySize();
        double memoryUsage = (totalMemory - freeMemory) / totalMemory;
        System.out.println("�ڴ�ʹ����:" + memoryUsage);
        if (memoryUsage >= 0.65) {
            System.out.println("�ڴ�ʹ�ù���:" + memoryUsage);
        } else {
            System.out.println("һ��Ҳ����");
        }
    }
}
