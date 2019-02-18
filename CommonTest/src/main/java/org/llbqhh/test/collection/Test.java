package org.llbqhh.test.collection;

import java.util.Hashtable;
import java.util.Iterator;

public class Test {
    public static void main(String[] args) {
        Hashtable ht = new Hashtable();
        Emp emp4 = new Emp("s101", "a1", 2.2f);
        Emp emp5 = new Emp("s102", "a2", 1.2f);
        Emp emp6 = new Emp("s103", "a3", 4.2f);
        ht.put("s101", emp4);
        ht.put("s102", emp5);
        ht.put("s103", emp6);
//      Iterator<String> it = ht.keySet().iterator();
        for (Iterator<String> it = ht.keySet().iterator(); it.hasNext(); ) {
            String key = it.next().toString();
            Emp em = (Emp) ht.get(key);
            System.out.println("���");
        }
    }
}

class Emp {
    String no;
    String name;
    float sal;

    public Emp(String no, String name, float sal) {
        this.no = no;
        this.name = name;
        this.sal = sal;
    }

    public void getno() {
        this.no = no;
    }
}
