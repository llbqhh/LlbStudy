package org.llbqhh.test.number;

import org.junit.Test;

/**
 * @author lilibiao
 * @date 2020/4/16 10:35 上午
 */
public class IntegerTest {
    @Test
    public void testInteger01() {
        // 转成字符串
        System.out.println(Integer.toString(3)); //3
        // 转成指定进制的字符串
        System.out.println(Integer.toString(3,2)); //11
        // 转换成2进制字符串
        System.out.println(Integer.toBinaryString(3)); //11
    }

    @Test
    public void testInteger02() {
        testIntegerI(10);
        testIntegerI(129);
        /*
        false
        false
        true // -128～127的Integer或Long会在valueOf方法中缓存
        false
        false
        false
         */
    }

    public void testIntegerI(int i) {
        Integer i1 = new Integer(i);
        Integer i2 = new Integer(i);
        System.out.println(i1 == i2);

        Integer i3 = new Integer(i + "");
        Integer i4 = new Integer(i + "");
        System.out.println(i3 == i4);

        Integer i5 = Integer.valueOf(i + "");
        Integer i6 = Integer.valueOf(i + "");
        System.out.println(i5 == i6);
    }
}
