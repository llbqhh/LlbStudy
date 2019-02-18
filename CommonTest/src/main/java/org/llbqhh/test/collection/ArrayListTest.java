package org.llbqhh.test.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ArrayListTest {
    public static void main(String[] args) {
        ArrayList<String> ar = new ArrayList<String>();
        ar.add("aa");
        ar.add("bb");
        ar.add("cc");
        ar.add("dd");
        ar.add("ee");
        Random r = new Random();
        System.out.println(ar.get(r.nextInt(ar.size())));
        Collections.shuffle(ar);
        System.out.println(ar);
    }
}
