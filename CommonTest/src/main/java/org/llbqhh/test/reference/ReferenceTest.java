package org.llbqhh.test.reference;

import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReferenceTest {
    @Test
    public void testSoftReference() {
        SoftReference<byte[]> test = new SoftReference<>(new byte[1024*1024]);
        List<Object> list = new ArrayList<>();
        int i = 0;
        while (test.get() != null) {
//            System.out.println(test.get() + " no gc " + i);
            list.add(new SoftReference<>(new SoftReference<>(new byte[1024*1024])));
            i++;
        }
        System.out.println("gc...");
    }

    @Test
    public void testWeakReference() {
        // 弱引用只要垃圾回收发生就会被回收
        WeakReference<Object> test = new WeakReference<>(new Object());
        Map<String, WeakReference> map = new HashMap<>();
        map.put("test", test);
        System.out.println(map.get("test").get());
        int i = 0;
        while (test.get() != null) {
            System.out.println(test.get() + " no gc " + i);
            i++;
        }
        System.out.println("gc...");
        System.out.println(map.get("test").get());
    }

    @Test
    public void testReferenceQueue() {
        ReferenceQueue<byte[]> rq = new ReferenceQueue<>();
        Map<Object, Object> map = new HashMap<>();
        Thread t = new Thread(() -> {
            try {
                int cnt = 0;
                SoftReference<byte[]> k;
                while ((k = (SoftReference) rq.remove()) != null) {
                    System.out.println((cnt++) + " garbage collect:" + k);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
        for (int i = 0; i < 10000; i++) {
            SoftReference<byte[]> b = new SoftReference<>(new byte[1024*1024], rq);
            map.put(b, new Object());
        }
        System.out.println("map.size:" + map.size());
    }
}
