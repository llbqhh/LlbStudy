package org.llbqhh.test.generic;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> al = new ArrayList<Integer>();
        al.add(1);
        al.add(4);
        test(al);
    }

    public static void test(ArrayList<?> list) {
        for (Object l : list) {
            System.out.println(l);
        }
    }
}
