package org.llbqhh.test.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HashMapTest {
    public static void main(String[] args) {
        testNull();
    }

    /**
     * null可以作为key或value
     */
    public static void testNull() {
        Map<String, String> testMap = new HashMap<String, String>();
        testMap.put(null, "testNull");
        testMap.put("testNull", null);
        System.out.println(testMap.get(null));
        if (testMap.containsKey("testNull")) {
            System.out.println(testMap.get("testNull"));
        }
    }

    public static void testOld() {
        HashMap<String, String> block = new HashMap<String, String>();
        for (int i = 0; i < 10000000; i++) {
            block.put("tsetestse" + i, "values" + i);
        }
        System.out.println(block.size());

        long start = System.currentTimeMillis();
        for (String key : block.keySet()) {
// String value = block
// this.put(key, block.get(key));
            String value = block.get(key);
        }
        long end = System.currentTimeMillis();
        System.out.println("keySet:" + (end - start));

        start = System.currentTimeMillis();
        for (Entry<String, String> entry : block.entrySet()) {
// this.put(entry.getKey(), entry.getValue());
            String key = entry.getKey();
            String value = entry.getValue();
        }
        end = System.currentTimeMillis();
        System.out.println("entrySet:" + (end - start));
    }
}
