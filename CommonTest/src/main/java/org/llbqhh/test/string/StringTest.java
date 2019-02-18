package org.llbqhh.test.string;

import java.util.TreeMap;

public class StringTest {
    public static void main(String[] args) {
        String a = "a";
        String ab = "a��fdsfsdaf";

// char[] c = {Character.MAX_VALUE};
// System.out.println(new String(c));
        String maxChar = new String(new char[]{Character.MAX_VALUE});
        String str = "test" + maxChar;
        System.out.println(str.compareTo(ab));
        TreeMap<String, String> hm = new TreeMap<String, String>();
        hm.subMap("", "");
// System.out.println(new String(new char[Character.MAX_VALUE)]);
    }
}
