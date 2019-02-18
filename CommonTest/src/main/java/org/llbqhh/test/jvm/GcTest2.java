package org.llbqhh.test.jvm;

import java.util.HashSet;
import java.util.Set;

public class GcTest2 {
    public void test() {
        Set<String> st = new HashSet<String>();

        for (int i = 0; i < 10000000; i++) {
            st.add("fasldkfjlasdjflkasjdflasjfl" + i);
        }
        st.clear();
        st = null;
    }
}
