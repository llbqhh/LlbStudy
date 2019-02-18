package org.llbqhh.test.memory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapMemoryTest {
    public static void main(String[] args) {
        getJvmMemory();
        Map<String, Set<String>> tests = new TreeMap<String, Set<String>>();
        long num = 0;
        for (int i = 0; i < 10000000; i++) {
            num++;
            String r = "wwwwwwwwwwwwwwwwwwwwww" + i;
            Set<String> locs = new HashSet<String>();
            locs.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww1234567890");
            locs.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww1234567890");
            locs.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww1234567890");
            locs.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww1234567890");
            locs.add("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww1234567890");
            tests.put(r, locs);
            if (num % 1000000 == 0) {
                System.out.println(num);
            }
        }
        getJvmMemory();
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
}
