package org.llbqhh.test.number;

import org.junit.Test;

/**
 * @author lilibiao
 * @date 2020/4/17 11:41 上午
 */
public class DoubleTest {
    @Test
    public void test() {
        System.out.println("Primitive type: double, size in bits: " + Double.SIZE);
        System.out.println("Wapper class :java.lang.Double");
        System.out.println("最小正标准值的常量: Double.MIN_NORMAL=" + Double.MIN_NORMAL);
        System.out.println("最小正非零值的常量: Double.MIN_VALUE=" + Double.MIN_VALUE);
        System.out.println("正无穷: Double.POSITIVE_INFINITY=" + Double.POSITIVE_INFINITY);
        System.out.println("正无穷: Double.POSITIVE_INFINITY=" + (1.0 / 0.0));
        System.out.println("负无穷: Double.NEGATIVE_INFINITY=" + Double.NEGATIVE_INFINITY);
        System.out.println("非数字nan: Double.NaN=" + Double.NaN);
        System.out.println(Double.BYTES);
        /*
        Primitive type: double, size in bits: 64
        Wapper class :java.lang.Double
        最小正标准值的常量: Double.MIN_NORMAL=2.2250738585072014E-308
        最小正非零值的常量: Double.MIN_VALUE=4.9E-324
        正无穷: Double.POSITIVE_INFINITY=Infinity
        正无穷: Double.POSITIVE_INFINITY=Infinity
        负无穷: Double.NEGATIVE_INFINITY=-Infinity
        非数字nan: Double.NaN=NaN
         */
    }
}
