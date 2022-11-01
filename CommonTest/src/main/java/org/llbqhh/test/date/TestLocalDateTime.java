package org.llbqhh.test.date;

import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @Author lilibiao
 * @Date 2020/11/9
 * @Description:
 */
public class TestLocalDateTime {
    @Test
    public void testLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toString());
    }
}
