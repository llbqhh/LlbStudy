package org.llbqhh.test.number;

import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author lilibiao
 * @date 2020/4/19 3:46 下午
 */
public class Striped64TestForDebug {
    @Test
    public void testLongAccumulate() {
        LongAdder longAdder = new LongAdder();
        longAdder.add(1);
    }
}
