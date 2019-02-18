package org.llbqhh.test.bitwise.operations;

import org.junit.Test;

/**
 * Created by lilibiao on 2017/8/25.
 */
public class BitwiseOR {
    static final int YARN = 1;
    static final int STANDALONE = 2;
    static final int MESOS = 4;
    static final int LOCAL = 8;
    static final int ALL_CLUSTER_MGRS = YARN | STANDALONE | MESOS | LOCAL;

    @Test
    public void testAnd() {
        System.out.println(YARN & MESOS);
        System.out.println(ALL_CLUSTER_MGRS & LOCAL);
    }

    @Test
    public void testOr() {
        System.out.println(ALL_CLUSTER_MGRS);
        System.out.println(Integer.toBinaryString(ALL_CLUSTER_MGRS));
    }
}
