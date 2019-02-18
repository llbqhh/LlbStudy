package org.llbqhh.test.reflect;

import scala.collection.JavaConversions;
import scala.collection.Seq;

public class ReflectExecutor {
    private static StringBuilder sb = new StringBuilder("hello");

    public String getName() {
        return "getName";
    }

    private String pre;

    public void print(String str) {
        if (pre != null) {
            str = pre + str;
        }
        System.out.println(str);
    }

    public void printList(Seq<Object> strs) {
        String str = "";
        if (pre != null) {
            str = pre + str;
        }
        for (Object s : JavaConversions.asJavaCollection(strs)) {
            str += s;
        }
        System.out.println(str);
    }

    public ReflectExecutor(String pre) {
        this.pre = pre;
    }

    public ReflectExecutor() {
    }
}
