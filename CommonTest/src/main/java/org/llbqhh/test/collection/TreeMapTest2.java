package org.llbqhh.test.collection;

import org.junit.Test;

import java.util.TreeMap;

/**
 * TreeMap.subMap ���ص���һ����ͼ�����е�map��ײ���treemap���Ǹ�ʵ��
 *
 * @author Administrator
 */
public class TreeMapTest2 {
    @Test
    public void test02() {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("20160701", "a");
        map.put("20160702", "b");
        map.put("20160705", "c");
        System.out.println(map);
    }

    public static void main(String[] args) throws InterruptedException {
        new TreeMapTest2().test02();
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