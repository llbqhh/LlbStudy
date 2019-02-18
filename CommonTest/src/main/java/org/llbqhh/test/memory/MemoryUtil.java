package org.llbqhh.test.memory;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;

public class MemoryUtil {
    public static void main(String[] args) {
        System.out.println(getHardUsage("D:\\"));
        System.out.println(getMemoryUsage());
    }

    /**
     * ��ȡ����·������������Ӳ��ʹ����
     *
     * @return
     */
    public static double getHardUsage(String path) {
        File file = new File(path);
        double totalSpace = (double) file.getTotalSpace();
        long usableSpace = file.getUsableSpace();
        double hardUsage = (totalSpace - usableSpace) / totalSpace;
        return hardUsage;
    }

    /**
     * ��ȡϵͳ�ڴ�ʹ����
     *
     * @return
     */
    public static double getMemoryUsage() {
        OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        double totalMemory = (double) osmb.getTotalPhysicalMemorySize();
        long freeMemory = osmb.getFreePhysicalMemorySize();
        double memoryUsage = (totalMemory - freeMemory) / totalMemory;
        return memoryUsage;
    }
}
