package org.llbqhh.test.collection;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Created by lilibiao on 2017/10/12.
 */
public class Java8CollectionTest {
    public static void main(String[] args) {
    }

    @Test
    public void testHashtable() {
        int insertNum = 0;
        int threshold = 1000000;
        long start;
        long end;
        Hashtable<String, String> table = new Hashtable<>();
        start = System.nanoTime();
        while (insertNum < threshold) {
            insertNum++;
            table.put(insertNum + "", insertNum + "");
        }
        end = System.nanoTime();
        System.out.println(end - start);
        //10180305630
        //10549690322
    }

    @Test
    public void testHashMap() {
        //7462797954
        //6386464136
        int insertNum = 0;
        int threshold = 1000000;
        long start;
        long end;
        Map<String, String> map = new HashMap<>();
        start = System.nanoTime();
        while (insertNum < threshold) {
            insertNum++;
            map.put(insertNum + "", insertNum + "");
        }
        end = System.nanoTime();
        System.out.println(end - start);
    }

    @Test
    public void testHashtableIndex() {
        String key = "testkey";
        String[] tab = new String[100];
        int hashtableHash = key.hashCode();
        System.out.println(hashtableHash);
        System.out.println(0x7FFFFFFF);
        System.out.println(Integer.MAX_VALUE);
        System.out.println((hashtableHash & 0x7FFFFFFF));
        System.out.println(Integer.toBinaryString(0x7FFFFFFF));
        int hashtableIndex = (hashtableHash & 0x7FFFFFFF) % tab.length;
        System.out.println(String.format("%s %s", hashtableHash, hashtableIndex));
        int hashMapHash = hash(key);
        int hashMapIndex = (tab.length - 1) & hashMapHash;
        System.out.println(String.format("%s %s", hashMapHash, hashMapIndex));
    }

    static final int hash(Object key) {
        int h = key.hashCode();
        return (key == null) ? 0 : (h) ^ (h >>> 16);
    }

    @Test
    public void testTreeMapBuildFromSorted() {
        TreeMap<String, String> tm = new TreeMap();
        int i = 0;
        while (i < 4) {
            i++;
            tm.put("i" + i, "");
        }
        System.out.println("begin new");
        TreeMap<String, String> ntm = new TreeMap<String, String>(tm);
        System.out.println(ntm);
    }

    @Test
    public void testPriorityQueueAdd() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 10; i > 0; i--) {
            pq.add(i);
        }
        System.out.println(pq);
    }

    private int size = 10;
    private int[] queue = {8, 3, 1, 4, 7, 9, 10, 5, 6, 2};

    @Test
    public void testPriorityQueueHeapfi() {
        for (int i = (size >>> 1) - 1; i >= 0; i--)
            siftDownComparable(i, queue[i]);
    }

    @SuppressWarnings("unchecked")
    private void siftDownComparable(int k, int x) {
        Integer key = x;
        int half = size >>> 1;        // loop while a non-leaf
        while (k < half) {
            int child = (k << 1) + 1; // assume left child is least
            Integer c = queue[child];
            int right = child + 1;
            if (right < size &&
                    (c).compareTo(queue[right]) > 0)
                child = right;
                c = queue[child];
            if (key.compareTo(c) <= 0)
                break;
            queue[k] = c;
            k = child;
            for (int i : queue) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
        queue[k] = key;
        for (int i : queue) {
            System.out.print(i + ",");
        }
        System.out.println();
    }

    @Test
    public void testPriorityQueueGrow() {
        int oldCapacity = 16;
        int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                (oldCapacity + 2) :
                (oldCapacity >> 1));
        System.out.println(newCapacity);
    }

    @Test
    public void testArrayDequeFrontBack() {
        int length = 16;
        int head = 10;
        int tail = 5;
        int i = 11;
        final int mask = length - 1;
        final int h = head;
        final int t = tail;
        final int front = (i - h) & mask;
        final int back = (t - i) & mask;
        System.out.println(front);
        System.out.println(back);
    }

    @Test
    public void testArrayDeque() {
        int head = 0;
        int tail = 0;
        int length = 16;
        head = (head - 1) & (length - 1);
        System.out.println("head : " + head);
        head = (head - 1) & (length - 1);
        System.out.println("head : " + head);
        tail = (tail + 1) & (length - 1);
        System.out.println("tail : " + tail);
        tail = (tail + 1) & (length - 1);
        System.out.println("tail : " + tail);
    }

    @Test
    public void testArrayDequeAddFirst() {
        ArrayDeque<String> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst("abc");
    }

    @Test
    public void testAllocateElements() {
        int initialCapacity = 8;
        int numElements = 65;
        if (numElements >= initialCapacity) {
            initialCapacity = numElements;
            System.out.println(Integer.toBinaryString(initialCapacity));
            initialCapacity |= (initialCapacity >>> 1);
            System.out.println(Integer.toBinaryString(initialCapacity));
            initialCapacity |= (initialCapacity >>> 2);
            System.out.println(Integer.toBinaryString(initialCapacity));
            initialCapacity |= (initialCapacity >>> 4);
            System.out.println(Integer.toBinaryString(initialCapacity));
            initialCapacity |= (initialCapacity >>> 8);
            System.out.println(Integer.toBinaryString(initialCapacity));
            initialCapacity |= (initialCapacity >>> 16);
            System.out.println(Integer.toBinaryString(initialCapacity));
            initialCapacity++;

            if (initialCapacity < 0)   // Too many elements, must back off
                initialCapacity >>>= 1; // Good luck allocating 2 ^ 30 elements
        }
        System.out.println(initialCapacity);
        System.out.println(Integer.toBinaryString(initialCapacity));
    }

    @Test
    public void testBitSet() {
        BitSet bitSet = new BitSet();
        bitSet.set(12);
        bitSet.set(13);
        bitSet.set(14);
        bitSet.set(22);
        System.out.println(bitSet.nextClearBit(12));
        System.out.println(bitSet.nextClearBit(22));
        System.out.println(bitSet.nextClearBit(123));
    }

    @Test
    public void testArrayListSet() {
        ArrayList<String> list = new ArrayList<>(7);
        list.set(10, "10");
        System.out.println(list);
    }

    @Test
    public void testArrayListNull() {
        ArrayList<String> list = new ArrayList<>(7);
        System.out.println(list.get(3));
        System.out.println(list.contains(null));
        System.out.println(list.indexOf(null));
    }

    @Test
    public void testArrayList() {
        ArrayList<String> list = new ArrayList<>();
        int i = 1;
        long start;
        long end;
        while (true) {
            i = i << 1;
            list.add("" + i);
            start = System.nanoTime();
            list.ensureCapacity(i);
            end = System.nanoTime();
            System.out.println(String.format("i %s ,time duration : %s", i, (end - start) / 1000));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testStream() {
        Set<String> set = new LinkedHashSet<>();
        for (int i = 0; i < 10000; i++) {
            set.add(i + "");
        }

        Map<String, Integer> count = new HashMap<>();
        Stream stream = set.parallelStream();
//        Stream stream = set.stream();
        stream.forEach(s -> {
            String tName = Thread.currentThread().getName();
            System.out.println(tName + "-" + s);
            count.compute(tName, new BiFunction<String, Integer, Integer>() {
                @Override
                public synchronized Integer apply(String s, Integer integer) {
                    int result = 1;
                    if (count.containsKey(s)) {
                        result = result + count.get(s);
                    }
                    return result;
                }
            });
        });
        System.out.println(count);
    }

    @Test
    public void testLinkedHashMap() {
        Map<String, String> map = new CacheMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put(i + "", i + "");
        }
        map.forEach((k, v) -> System.out.println(k));
    }
}

class CacheMap<K, V> extends LinkedHashMap<K, V> {
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > 10;
    }
}
